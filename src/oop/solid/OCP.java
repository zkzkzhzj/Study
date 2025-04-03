package oop.solid;

public class OCP {
	public static void main(String[] args) {
		// 추가 결제 건이 나오면 process 내부를 직접 수정해야 하며 else if 구문을 추가해야 한다.
		PaymentProcessor paymentProcessor = new PaymentProcessor();
		paymentProcessor.process("CreditCard");
		paymentProcessor.process("PayPal");
		paymentProcessor.process("KakaoPay");

		// interface 상속을 받은 구현체만 추가하면 되며 기존 로직에 영향이 가지 않는다.
		Payment creditCard = PaymentFactory.getPayment(PaymentType.CREDIT_CARD);
		creditCard.process();
		Payment paypal = PaymentFactory.getPayment(PaymentType.PAYPAL);
		paypal.process();
		Payment kakaoPay = PaymentFactory.getPayment(PaymentType.KAKAOPAY);
		kakaoPay.process();

		/**
		 * 부가 설명
		 * 기존의 코드를 변경하지 않으며 새로운 기능이 추가될 수 있도록 설계 확장에는 개방적, 수정에는 폐쇄적 유지 보수와 확정성에 대해
		 * 이점 코드의 안정성도 올라감 추상화와 다형성의 활용이 중요함 - 추상화 : 객체(object)의 성질, 공통성, 본질을 추출 - 다형성 :
		 * 하나의 메소드가 상속되어 다양하게 동작(Overriding), 매개 변수를 다르게 줌으로써 동일한 메소드 명으로 여러
		 * 정의(Overloading)
		 */
	}
}

// OCP(Open-Closed Principle) : 개방-폐쇄 원칙 위배
class PaymentProcessor {
	public void process(String paymentType) {
		if (paymentType.equals("CreditCard")) {
			System.out.println("Processing credit card payment");
		} else if (paymentType.equals("PayPal")) {
			System.out.println("Processing PayPal payment");
		} else if (paymentType.equals("KakaoPay")) {
			System.out.println("Processing KakaoPay payment");
		}
	}
}

// 수정본
interface Payment {
	void process();
}

class Card implements Payment {

	@Override
	public void process() {
		System.out.println("Processing credit card payment");
	}
}

class PayPal implements Payment {

	@Override
	public void process() {
		System.out.println("Processing PayPal payment");
	}
}

class KakaoPay implements Payment {

	@Override
	public void process() {
		System.out.println("Processing KakaoPay payment");
	}
}

// GPT 의 추가 개선 포인트 (Factory Pattern 사용)
class PaymentFactory {
//	public static Payment getPayment(String paymentType) {
//		switch (paymentType) {
//		case "CreditCard":
//			return new Card();
//		case "PayPal":
//			return new PayPal();
//		case "KakaoPay":
//			return new KakaoPay();
//		default:
//			throw new IllegalArgumentException("Unknown payment type");
//		}
//	}
	public static Payment getPayment(PaymentType type) {
		switch (type) {
		case CREDIT_CARD:
			return new Card();
		case PAYPAL:
			return new PayPal();
		case KAKAOPAY:
			return new KakaoPay();
		default:
			throw new IllegalArgumentException("Unknown payment type");
		}
	}
}

// GPT 개선을 보고 내가 추가한 enum class 
enum PaymentType {
	CREDIT_CARD, PAYPAL, KAKAOPAY
}