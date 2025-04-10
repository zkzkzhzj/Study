package oop.grasp;

/**
 * 다형성
 * 
 * 서로 다른 행위를 하는 여러 객체들이 동일한 메시지 처리
 *  - 조건문 분기 대신 다형성을 이용해 각자 행동하게 설계
 */
public class Polymorphism {

	public static void main(String[] args) {
		// 수정 전
		String type = "email";

        if (type.equals("email")) {
            System.out.println("이메일 전송");
        } else if (type.equals("sms")) {
            System.out.println("문자 전송");
        } else if (type.equals("push")) {
            System.out.println("푸시 알림 전송");
        }
        
        PolymorphismEmailSender emailSender = new PolymorphismEmailSender();
        PolymorphismSMSSender smsSender = new PolymorphismSMSSender();
        PolymorphismPushSender pushSender = new PolymorphismPushSender();
        
        String message = "hello";
        emailSender.send(message);
        smsSender.send(message);
        pushSender.send(message);
	}
}

// 수정 후
interface PolymorphismSender {
	void send(String message);
}

class PolymorphismEmailSender implements PolymorphismSender {

	@Override
	public void send(String message) {
		System.out.println("이메일 전송 : " + message);
	}
}

class PolymorphismSMSSender implements PolymorphismSender {

	@Override
	public void send(String message) {
		System.out.println("문자 전송 : " + message);
	}
}

class PolymorphismPushSender implements PolymorphismSender {

	@Override
	public void send(String message) {
		System.out.println("푸시 알림 전송 : " + message);
	}
}
