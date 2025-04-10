package oop.grasp;

/**
 * 낮은 결합도
 * 
 * 클래스들이 서로 최소한으로 의존하도록 설계
 *  - 변경에 덜 민감하고, 재사용성과 유지보수성이 높은 구조
 */
public class LowCoupling {

	public static void main(String[] args) {
		LowCouplingOrder order = new LowCouplingOrder();
//		LowCouplingEmailSender emailSender = new LowCouplingEmailSender();
		LowCouplingSender emailSender = new LowCouplingEmailSender();
		LowCouplingSender smsSender = new LowCouplingSMSSender();

        order.sendConfirmationEmail(emailSender);
        order.sendConfirmationEmail(smsSender);
	}
}
/**
 * 수정 전
 * 
 * Order 객체가 EmailSender 구체화 클래스에 의존하여 변경에 취약하며 전송 방식이 바뀌면 Order 객체도 변경된다
 *  - 결합도가 강한상태
 */
class LowCouplingOrder {
    public void sendConfirmationEmail(LowCouplingSender sender) {
        sender.send("주문이 완료되었습니다.");
    }
}

/**
 * 수정 후
 * 
 * interface 추가하여 interface 상속받은 전송 방식 모두 호환되도록 반영
 */
interface LowCouplingSender {
	void send(String message);
}

class LowCouplingEmailSender implements LowCouplingSender {
	
	@Override
    public void send(String message) {
        System.out.println("이메일 전송: " + message);
    }
}

class LowCouplingSMSSender implements LowCouplingSender {
	
	@Override
    public void send(String message) {
        System.out.println("문자 전송: " + message);
    }
}

