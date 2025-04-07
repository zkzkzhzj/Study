package designpattern.Behavioral;

import java.util.ArrayList;
import java.util.List;

/**
 * 객체의 상태 변화 발생
 *  - 의존 관계에 있는 다른 객체들에게 자동으로 알림 발송
 *  - 발행-구독(Publish-Subscribe) 모델로도 알려져 있다
 *  
 * 이벤트 기반 시스템
 *  - 상태 변화가 생길 때 자동으로 알림을 받고 처리할 수 있음
 * 느슨한 결합
 *  - 주체(Subject)와 옵저버(Observer)간의 의존성이 낮아짐
 * 확장성
 *  - 기존 코드를 수정하지 않더라도 새로운 옵저버 추가 용이
 *  - OCP 준수
 */
public class Observer {
	
	public static void main(String[] args) {
		NewsPublisher publisher = new NewsPublisher();
        publisher.publishNews("Email", "Java 패턴 학습!");
        publisher.publishNews("SMS", "옵저버 패턴 적용!");
        publisher.publishNews("Push", "알림 시스템 구현!");
        
		System.out.println("--------------------------------------");
		
		Youtuber youTuber = new Youtuber();

        ObserverInterface emailSub = new EmailSubscriber("Alice");
        ObserverInterface pushSub = new PushSubscriber("Charlie");

        // 구독자 등록
        youTuber.registerObserver(emailSub);
        youTuber.registerObserver(pushSub);

        // 영상 업로드
        youTuber.upload("첫 영상!");

        // 구독자 한 명 해제
        youTuber.removeObserver(emailSub);

        // 영상 업로드
        youTuber.upload("두 번째 영상!");
	}
}

// 미적용
class NewsPublisher {
	
	public void publishNews(String type, String news) {
	    if (type.equals("Email")) {
	        System.out.println("Email로 뉴스 전송: " + news);
	    } else if (type.equals("SMS")) {
	        System.out.println("SMS로 뉴스 전송: " + news);
	    } else if (type.equals("Push")) {
	        System.out.println("Push 알림으로 뉴스 전송: " + news);
	    } else {
	        System.out.println("알 수 없는 구독 유형: " + news);
	    }
	}
}

// 적용
/**
 * Subject(주체 인터페이스)
 *  - 상태 변화 관리
 *  - 옵저버를 등록/제거/통지하는 메소드 정의
 *  - 발행자(Publish) 역할
 * 
 * ConcreteSubject(구체 주체 클래스)
 *  - 상태 변화 발생 -> 등록된 옵저버들에게 알림
 *  - 실제 데이터를 가지며 변화가 생기면 통지
 * 
 * Observer(옵저버 인터페이스)
 *  - 상태 변화를 수신하는 메소드 정의
 *  - 구독자(Subscribe) 역할
 * 
 * ConcreteObserver(구체 옵저버 클래스)
 *  - 알림을 받으면 동작 수행
 *  - 주체와 연결되어 있으며 상태 변화에 반응
 */
// Subject
interface Subject {
	void registerObserver(ObserverInterface observerI);
	void removeObserver(ObserverInterface observerI);
	void notifyObserver(String message);
}

// ConcreteSubject
class Youtuber implements Subject {
	// 구독자 목록
	private List<ObserverInterface> observerList = new ArrayList<>();
	
	@Override
	public void registerObserver(ObserverInterface observer) {
	    if (observer == null) {
	        System.out.println("구독자 정보가 올바르지 않습니다.");
	        return;
	    }
	    if (observerList.contains(observer)) {
	    	removeObserver(observer);
	        return;
	    }
		observerList.add(observer);
		System.out.println(observer + " 구독");
	}

	@Override
	public void removeObserver(ObserverInterface observer) {
	    if (observer == null) {
	        System.out.println("구독자 정보가 올바르지 않습니다.");
	        return;
	    }
		observerList.remove(observer);
		System.out.println(observer + " 구독 취소");
	}

	@Override
	public void notifyObserver(String message) {
		for (ObserverInterface observer : observerList) {
			observer.update(message);
		}
	}
	
	public void upload(String title) {
		notifyObserver(title);
	}
}

// Observer
interface ObserverInterface {
    void update(String message);
}

// ConcreteObserver
class EmailSubscriber implements ObserverInterface {
	private String name;
	
	public EmailSubscriber(String name) {
		this.name = name;
	}

	@Override
	public void update(String message) {
		System.out.println(name + "에게 이메일 알림: " + message);
	}
	
	@Override
	public String toString() {
		return "이메일 구독자 : " + name;
	}
}

//구체 구독자: 푸시 알림
class PushSubscriber implements ObserverInterface {
	private String name;

	public PushSubscriber(String name) {
	    this.name = name;
	}

	@Override
	public void update(String message) {
	    System.out.println(name + "에게 푸시 알림: " + message);
	}

	@Override
	public String toString() {
		return "푸시 구독자 : " + name;
	}
}

/**
 * GPT 의 개선점
 * 
 * NPE 에러 방지 추가
 * 
 * 알림 로그 개선
 *  - 알림 내용을 더 명확히 하자 라는 피드백이지만 패턴 공부에 맞지 않는 피드백이라 반영 X
 */