package designpattern.Behavioral;

import java.util.List;

/**
 * 요소 객체에 새로운 연산을 추가하고 싶지만, 해당 클래스 코드를 변경하고 싶지 않을 때 사용
 *  - 방문자(Visitor)가 요소(Element)를 방문하여 연산 처리
 *  - 연산을 외부로 분리
 */
public class Visitor {

	public static void main(String[] args) {
		List<Employee> employees = List.of(new Staff("A", 4000), new Manager("B", 5000, 2000));
		
		EmployeeVisitor salaryChecker = new PayCalculator();
		EmployeeVisitor workManage = new WorkManagement();
		
		for (Employee e : employees) {
			e.accept(salaryChecker);
			e.accept(workManage);
		}
	}
}

/**
 * Element(요소)
 *  - 방문자(Visitor)를 받아들이는 인터페이스
 * 
 * ConcreteElement(구체 요소)
 *  - 실제 요소 클래스
 * 
 * Visitor(방문자 인터페이스)
 *  - 방문 가능한 대상마다 메소드 정의
 * 
 * ConcreteVisitor(구현 방문자)
 *  - 실제 연산을 수행하는 클래스
 * 
 * Client(클라이언트)
 *  - 방문자를 요소에 전달
 */
// Element
interface Employee {
    String getName();
    int getSalary();
    void work();
	void accept(EmployeeVisitor visitor);
}

// ConcreteElement
class Staff implements Employee {
	private String name;
	private int salary;
	
	public Staff(String name, int salary) {
		this.name = name;
		this.salary = salary;
	}
	
	@Override
	public int getSalary() {
		return salary;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void accept(EmployeeVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void work() {
		System.out.println("업무 하기");
	}
}

class Manager implements Employee {
	private String name;
	private int salary;
	private int bonus;
	
	public Manager(String name, int salary, int bonus) {
		this.name = name;
		this.salary = salary;
		this.bonus = bonus;
	}

	@Override
    public int getSalary() {
        return salary;
    }

	@Override
    public String getName() {
        return name;
    }

    public int getBonus() {
        return bonus;
    }

	@Override
	public void accept(EmployeeVisitor visitor) {
        visitor.visit(this);
	}

	@Override
	public void work() {
		System.out.println("Staff 관리 하기");
	}
	
	public void afterWork() {
		System.out.println("Staff 면접 보기");
	}
}

// Visitor
interface EmployeeVisitor {
	void visit(Staff staff);
	void visit(Manager manager);
}

// ConcreteVisitor
class PayCalculator implements EmployeeVisitor {

	@Override
	public void visit(Staff staff) {
		System.out.println("[급여] " + staff.getName() + ": " + staff.getSalary());
	}

	@Override
	public void visit(Manager manager) {
		int total = manager.getSalary() + manager.getBonus();
		System.out.println("[급여] " + manager.getName() + ": " + total);
	}
}

class WorkManagement implements EmployeeVisitor {

	@Override
	public void visit(Staff staff) {
		staff.work();
	}

	@Override
	public void visit(Manager manager) {
		manager.work();
		manager.afterWork();
	}
}

/**
 * Visitor 패턴을 왜 쓸까?
 * 
 * 위 예시를 보면 Employee 인터페이스를 상속받는 구현체 클래스들은 상태만 가진다.
 * 즉, 기능에 대한 처리는 담당하지 않는다.
 * 
 * Visitor 객체가 접근하여 기능에 대한 처리를 대신 해준다.
 * 
 * 그럼 장점이 뭘까?
 * 
 * Manager 의 경우 Staff 보다 많은 일을 하고 월급도 보너스를 받는다.
 * Visitor 없이 클래스 자체에서 구현하게 되면 너무 많은 책임을 지게 되기도하고 코드가 복잡해진다.
 * 그래서 상태에 대한 값만 추가하고 기능에 대한 상세 내용은 각 Visitor 에게 맡기는 것
 * 추가된 기능에 따라 각 Visitor 는 서로 관련도 없고 간섭이 적어진다.
 * 
 * 즉, 상태는 클래스에 있고 행동은 Visitor 에게 위임 시킨다.
 * 
 * 그러면 언제 쓰면 좋을까?
 * 
 * Element 구조가 안정되어 있고 자주 바뀌지 않을 때
 * 하지만, 새로운 연산이 자꾸 추가될 때
 * 또한, 기능에 대한 구현이 다를 때 추천한다.
 */
