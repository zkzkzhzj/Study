package designpattern.Behavioral;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 문법(규칙)을 클래스로 표현하고, 그 문법에 따라 해석하는 패턴
 * 
 * 간단한 도메인 어가 필요할 때
 * 수식, 명령어, 규칙 같은 걸 문장처럼 정의하고 실행 할 때
 * 자주 구문 분석(파싱)하거나 계산하는 로직이 있을 때
 */
public class Interpreter {

	public static void main(String[] args) {
//		Expression three = new NumberExpression(25);
//		Expression five = new NumberExpression(13);
//		Expression add = new PlusExpression(three, five);
//		Expression minus = new MinusExpression(three, five);
//		Expression multiply = new MultiplyExpression(three, five);
//		Expression divide = new DivideExpression(three, five);
//
//		System.out.println(add.interpret());
//		System.out.println(minus.interpret());
//		System.out.println(multiply.interpret());
//		System.out.println(divide.interpret());
		// 수식 생성 : (x + 3) * y
		Expression expression = new MultiplyExpression(
				new PlusExpression(
						new VariableExpression("x"),
						new NumberExpression(3)
						),
				new VariableExpression("y")
				);
		
		// 해석 정보
		Context context = new Context();
		context.setVariable("x", 2);
		context.setVariable("y", 4);
		
		int result = expression.interpret(context);
		System.out.println("결과 : " + result);
		
		System.out.println("----------------------------");
		
		// 권한 규칙 : Admin OR (Manager AND HR)
		ExpressionR admin = new RoleExpression("Admin");
		ExpressionR manager = new RoleExpression("Manager");
		ExpressionR hr = new RoleExpression("HR");
		
		// 권한 설정
		ExpressionR managerAndHr = new AndExpression(manager, hr);
		ExpressionR rule = new OrExpression(admin, managerAndHr);
		
		RoleContext user1 = new RoleContext(Set.of("Manager", "HR"));
		RoleContext user2 = new RoleContext(Set.of("HR", "Guest"));
		RoleContext user3 = new RoleContext(Set.of("Admin", "Dispatch"));
		System.out.println("USER1 권한 여부 : " + rule.interpret(user1));
		System.out.println("USER2 권한 여부 : " + rule.interpret(user2));
		System.out.println("USER3 권한 여부 : " + rule.interpret(user3));
	}
}

/**
 * AbstractExpression
 *  - 모든 표현식이 따르는 인터페이스
 * 
 * TerminalExpression
 *  - 더 이상 분해되지 않는 기본 표현식
 * 
 * NonTerminalExpression
 *  - 다른 표현식을 조합한 복합 표현식
 * 
 * Context
 *  - 해석 시 필요한 정보
 * 
 * Client
 *  - 표현식을 만들고 실행하는 쪽
 */
// AbstractExpression
interface Expression {
//	int interpret();
	int interpret(Context context);
}

// TerminalExpression
// 숫자 표현
class NumberExpression implements Expression {
	private int number;
	
	public NumberExpression(int number) {
		this.number = number;
	}
	
	@Override
	public int interpret(Context context) {
		return number;
	}
}

// 추가 - 문자 표현
class VariableExpression implements Expression {
	private String name;
	
	public VariableExpression(String name) {
		this.name = name;
	}
	
	@Override
	public int interpret(Context context) {
		return context.getVariable(name);
	}
}

// NonTerminalExpression
// 덧셈 표현
class PlusExpression implements Expression {
	private Expression left;
	private Expression right;
	
	public PlusExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public int interpret(Context context) {
		
		return left.interpret(context) + right.interpret(context);
	}
}

// 뺄셈 표현
class MinusExpression implements Expression {
	private Expression left;
	private Expression right;
	
	public MinusExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public int interpret(Context context) {
		
		return left.interpret(context) - right.interpret(context);
	}
}

// 곱셈 표현
class MultiplyExpression implements Expression {
	private Expression left;
	private Expression right;
	
	public MultiplyExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public int interpret(Context context) {
		
		return left.interpret(context) * right.interpret(context);
	}
}

// 나눗셈 표현
class DivideExpression implements Expression {
	private Expression left;
	private Expression right;
	
	public DivideExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public int interpret(Context context) {
	    if (right.interpret(context) == 0) {
	        throw new ArithmeticException("0 으로 나눌 수 없습니다.");
	    }
		return left.interpret(context) / right.interpret(context);
	}
}

// Context - 문자가 들어올 경우 해석
class Context {
    private Map<String, Integer> variables = new HashMap<>();

    public void setVariable(String name, int value) {
        variables.put(name, value);
    }

    public int getVariable(String name) {
        if (!variables.containsKey(name)) {
            throw new IllegalArgumentException("변수 " + name + "은(는) 정의되지 않았습니다.");
        }
        return variables.get(name);
    }
}

/**
 * GPT 의 개선점
 * 
 * 나눗셈 0 예외 처리 추가
 * 
 * Context 클래스 추가
 *  - 변수 X,Y 를 넣거나, 문장을 파싱하려면 필요할 수 있다
 */

// 위 예제로는 감이 잘 오지 않아 조금더 복잡한 예제를 진행
class RoleContext {
	private Set<String> roles;
	
	public RoleContext(Set<String> roles) {
		this.roles = roles;
	}
	
	public boolean hasRole(String role) {
		return roles.contains(role);
	}
}

interface ExpressionR {
	boolean interpret(RoleContext context);
}

// 기본 권한 체크
class RoleExpression implements ExpressionR {
	private String role;
	
	public RoleExpression(String role) {
		this.role = role;
	}
	
	@Override
	public boolean interpret(RoleContext context) {
		return context.hasRole(role);
	}
}

// 권한 체크 조건
class AndExpression implements ExpressionR {
	private ExpressionR left, right;
	
	public AndExpression(ExpressionR left, ExpressionR right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public boolean interpret(RoleContext context) {
        return left.interpret(context) && right.interpret(context);
	}
}

class OrExpression implements ExpressionR {
	private ExpressionR left, right;
	
	public OrExpression(ExpressionR left, ExpressionR right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public boolean interpret(RoleContext context) {
        return left.interpret(context) || right.interpret(context);
	}
}