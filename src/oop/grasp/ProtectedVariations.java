package oop.grasp;

/**
 * 변경으로부터의 보호
 * 
 * 변화가 예상되는 지점과 그 변화를 사용하는 지점 사이에 추상화된 인터페이스나 중간 계층을 두어 보호
 * 
 * 변경에 민감한 부분이 시스템 전체에 영향을 주지 않도록 막기
 * 추상화 계층(인터페이스, 어댑터 등)을 둬서 변화는 국소화, 사용자는 영향을 받지 않게 함
 */
public class ProtectedVariations {

	public static void main(String[] args) {
		ProtectedVariationsPaymentService service = new ProtectedVariationsPaymentService();
		// 수정 전
		service.pay("카드 정보");
		
		// 수정 후
		service.fixPay(new ProtectedVariationsKakaoPay(), "카드 정보");
		service.fixPay(new ProtectedVariationsPayco(), "카드 정보");
	}
}

/**
 * 수정 전
 * 
 * Service 가 Toss 결제 방식에 직접 의존하고 있다
 * 
 * 추후 결제 방식이 늘어나면 Service 수정 필요
 *  - OCP 위반, 결합도 높음
 */
class ProtectedVariationsPaymentService {
	
	public void pay(String info) {
		ProtectedVariationsToss toss = new ProtectedVariationsToss();
		toss.sendPayment(info);
	}
	
	// 수정 후
	public void fixPay(ProtectedVariationsPayment payment, String info) {
		payment.sendPayment(info);
	}
}

class ProtectedVariationsToss {
	
	public void sendPayment(String info) {
		System.out.println("토스 결제 : " + info);
	}
}

// 수정 후
interface ProtectedVariationsPayment {
	void sendPayment(String info);
}

class ProtectedVariationsKakaoPay implements ProtectedVariationsPayment {

	@Override
	public void sendPayment(String info) {
		System.out.println("카카오페이 결제 : " + info);
	}
}

class ProtectedVariationsPayco implements ProtectedVariationsPayment {

	@Override
	public void sendPayment(String info) {
		System.out.println("페이코 결제 : " + info);
	}
}
