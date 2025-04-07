package designpattern.Creational;

/**
 * 객체 생성의 책임을 서브 클래스에 위임
 *  - 객체 생성 로직을 캡슐화
 *  - 상위 클래스는 생성 방식을 모르게 하고, 서브 클래스가 구체적인 객체를 결정하도록 함
 *  
 *  객체 생성 로직을 분리하여 유연성 증가
 *   - 새로운 객체 유형이 추가되어도 기존 코드 수정이 최소화
 *  코드 복잡도 감소
 *   - 객체 생성 코드를 별도로 분리하여 가독성 향상
 *  OCP 준수
 *   - 기존 코드를 수정하지 않아도 새로운 객체 유형 추가
 */
public class FactoryMethod {

	public static void main(String[] args) {
		NotificationService service = new NotificationService();
		service.sendNotification("SMS");
		service.sendNotification("Email");
		service.sendNotification("Push");

		System.out.println("--------------------------------------");
		
        NotificationType[] types = {
        		NotificationType.SMS,
        		NotificationType.EMAIL,
        		NotificationType.PUSH
        };
        
        for (NotificationType type : types) {
        	NotificationCreator factory = getFactory(type);
        	Notification notification = factory.createNotification();
        	notification.sendNotification();
        }
	}
	
	private static NotificationCreator getFactory(NotificationType type) {
		switch (type) {
		case SMS: return new SMSFactory();
		case EMAIL: return new EmailFactory();
		case PUSH: return new PushFactory();
		default: throw new IllegalArgumentException("Unknown type");
		}
	}
}

// 미 적용
class NotificationService {
    public void sendNotification(String type) {
        if (type.equals("SMS")) {
            System.out.println("Sending SMS notification");
        } else if (type.equals("Email")) {
            System.out.println("Sending Email notification");
        } else if (type.equals("Push")) {
            System.out.println("Sending Push notification");
        } else {
            System.out.println("Unknown notification type");
        }
    }
}

// 적용
/**
 * 팩토리 머소드 패턴 구조
 * 
 * 핵심은 "객체 생성의 책임을 하위 클래스에 위임"
 * 
 * Product(인터페이스)
 *  - 공통 메소드 정의
 * 
 * ConcreteProduct(구현 클래스들)
 *  - Product 인터페이스를 구체적으로 구현
 * 
 * Creator(추상 클래스 또는 인터페이스)
 *  - 팩토리 메소드 선언
 *  - 객체 생성을 추상 메소드로 선언하여 서브 클래스가 구체화
 *  - 객체 생성 처리 메서드(someOperartion) : 객체 생성에 관한 전처리, 후처리를 템플릿화한 메소드
 *  - 팩토리 메서드(createProduct) : 서브 공장 클래스에서 재정의할 객체 생성 추상 메서드
 * 
 * ConcreteCreator(구현 클래스)
 *  - 팩토리 메소드 구현
 *  - 특정 Product 객체를 생성하여 반환
 */
// Product
interface Notification {
	void sendNotification();
}

// ConcreteProduct
class SMSNotification implements Notification {

	@Override
	public void sendNotification() {
        System.out.println("Sending SMS notification");
	}
}

class EmailNotification implements Notification {

	@Override
	public void sendNotification() {
        System.out.println("Sending Email notification");
	}
}

class PushNotification implements Notification {

	@Override
	public void sendNotification() {
        System.out.println("Sending Push notification");
	}
}

// Creator
interface NotificationCreator {
	Notification createNotification();
}

// ConcreteCreator
class SMSFactory implements NotificationCreator {

	@Override
	public Notification createNotification() {
        return new SMSNotification();
	}
}

class EmailFactory implements NotificationCreator {

	@Override
	public Notification createNotification() {
        return new EmailNotification();
	}
}

class PushFactory implements NotificationCreator {

	@Override
	public Notification createNotification() {
        return new PushNotification();
	}
}

// GPT 개선점에 추가 개선
enum NotificationType {
	SMS, EMAIL, PUSH
}

/**
 * GPT 의 개선점
 * 
 * 객체 생성 방식의 개선
 *  - 팩토리 배열을 직접 만들지 말고 동적으로 생성하는 구조 고려
 * 
 * 인터페이스 명명 개선
 *  - NotificationFactory 라는 이름은 포괄적
 *  - Creator 라는 이름으로 명시, 연관성 강조
 */
