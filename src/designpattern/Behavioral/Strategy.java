package designpattern.Behavioral;

/**
 * 행위(알고리즘)를 캡슐화
 *  - 동적으로 행위를 교체할 수 있도록 만듬
 *  - 상황에 따라 다른 전략을 선택하여 유연하게 사용
 *  
 *  유연한 알고리즘 교체
 *  조건문 최소화
 *   - if-else, switch 없이 독립적으로 캡슐화
 *  확장성
 *   - 새로운 알고리즘 추가 시 기존 코드 수정없이 확장
 *   - OCP 준수
 */
public class Strategy {

	public static void main(String[] args) {
		PaymentService service = new PaymentService();
        service.processPayment("CreditCard", 1000);
        service.processPayment("PayPal", 2000);
        service.processPayment("KakaoPay", 3000);

		System.out.println("--------------------------------------");
		
        PaymentProcessor processor = new PaymentProcessor();
        processor.setAmount(5500);
        processor.setStrategy(new CreditCardPayment());
        processor.process();
        processor.setStrategy(new PayPalPayment());
        processor.process();
        processor.setStrategy(new KakaoPayPayment());
        processor.process();
	}
}

// 미적용
class PaymentService {
	
    public void processPayment(String type, int amount) {
        if (type.equals("CreditCard")) {
            System.out.println("Processing credit card payment of " + amount);
        } else if (type.equals("PayPal")) {
            System.out.println("Processing PayPal payment of " + amount);
        } else if (type.equals("KakaoPay")) {
            System.out.println("Processing KakaoPay payment of " + amount);
        } else {
            System.out.println("Unknown payment type");
        }
    }
}

// 적용
/**
 * Strategy(전략 인터페이스)
 *  - 공통 행위(알고리즘)를 정의 인터페이스
 *  
 * ConcreteStrategy(구체 전략 클래스)
 *  - Strategy 인터페이스를 구현하여 정의
 *  - 새로운 전략이 추가되어도 기존 코드 수정없이 확장 가능
 *  
 * Context(문맥 클래스)
 *  - 전략 객체를 가지고 있는 클래스
 *  - 전략을 설정(set)하거나 실행(execute)하는 역할
 *  - 전략 객체를 교체하여 행위를 동적으로 변경 가능
 */
// 카드사 별로 수수료가 다름
// Strategy
interface PaymentStrategy {
	void pay(int amount);
}

// ConcreteStrategy
class CreditCardPayment implements PaymentStrategy {

	@Override
	public void pay(int amount) {
        System.out.println("Processing credit card payment of " + (amount * 0.8));
	}
}

class PayPalPayment implements PaymentStrategy {

	@Override
	public void pay(int amount) {
        System.out.println("Processing PayPal payment of " + (amount * 0.7));
	}
}

class KakaoPayPayment implements PaymentStrategy {

	@Override
	public void pay(int amount) {
        System.out.println("Processing KakaoPay payment of " + (amount * 0.9));
	}
}

// Context
class PaymentProcessor {
	private PaymentStrategy strategy;
	private int amount = 0;
	
	public void setStrategy(PaymentStrategy strategy) {
		this.strategy = strategy;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void process() {
		if (strategy == null) {
			System.out.println("결재 방식을 선택해주세요.");
			return;
		}
		strategy.pay(amount);
	}
}

/**
 * GPT 의 개선점
 * 
 * process() 메소드 내부에서 NPE 방지 추가
 * 
 * 수수료 계산을 하드코딩하지 말고 변수로 두기
 *  - 현재 연습에서는 크게 상관없는 것 같아 반영X, 하지만 고려
 * 
 * 전략 설정을 생성자에서 초기화
 *  - 현재 결재 방식을 유연하게 바꾸는게 더 어울린다고 판단하여 메소드로 사용
 */