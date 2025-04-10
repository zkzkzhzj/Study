package designpattern.Behavioral;

/**
 * 요청을 처리할 수 있는 객체들을 사슬처럼 연결
 *  - 요청을 보낼 때 각 객체가 책임이 있으면 처리, 없으면 다음 객체에게 넘기는 구조
 * 
 * 요청의 처리자가 여러 명일 경우
 * 처리 우선순위에 따라 순서대로 위임하고 싶을 때
 * 처리 로직을 하드코딩 조건문으로 처리하고 있던 코드를 분리하고 싶을 때
 */
public class ChainOfResponsibility {

	public static void main(String[] args) {
		Handler teamLeader = new TeamLeader();
		Handler master = new Master();
		Handler ceo = new CEO();

		// 체인 연결
		teamLeader.setNextHandler(master);
		master.setNextHandler(ceo);

		// 클라이언트 요청 처리
		int[] requests = {50, 300, 800, 1500};
		for (int amount : requests) {
			System.out.println("요청 금액: " + amount + "만원");
			teamLeader.handlerRequest(amount);
			System.out.println("---------------------");
		}
	}
}
/**
 * Handler(인터페이스)
 *  - 요청 처리 메서드 정의 + 다음 핸들러 참조
 * 
 * ConcreteHandler
 *  - 각자 처리할 수 있는 조건 정의
 * 
 * Client
 *  - 최초 요청을 체인에 전달
 */
// 결재 금액 시나리오
// Handler
abstract class Handler {
	private Handler nextHandler;
	
	void setNextHandler(Handler handler) {
		this.nextHandler = handler;
	};
	
	public Handler getHandler() {
		return nextHandler;
	}
	
	abstract boolean handlerRequest(int amount);
}

// ConcreteHandler
class TeamLeader extends Handler {
	private final int maxAmount = 100;
	
	@Override
	public boolean handlerRequest(int amount) {
		if (amount <= maxAmount) {
			System.out.println("비용 처리 완료[팀장]");
			return true;
		} else if (getHandler() != null) {
			return getHandler().handlerRequest(amount);
		} else {
			System.out.println("비용 처리 불가[팀장]");
			return false;	
		}
	}
}

class Master extends Handler {
	private final int maxAmount = 500;

	@Override
	public boolean handlerRequest(int amount) {
		if (amount <= maxAmount) {
			System.out.println("비용 처리 완료[마스터]");
			return true;
		} else if (getHandler() != null) {
			return getHandler().handlerRequest(amount);
		} else {
			System.out.println("비용 처리 불가[마스터]");
			return false;	
		}
	}
}

class CEO extends Handler {

	@Override
	public boolean handlerRequest(int amount) {
		System.out.println("비용 처리 완료[CEO]");
		return true;
	}
}

/**
 * GPT 의 개선점
 * 
 * 추상클래스에서 nextHandler 필드를 공통으로 제공하면 중복 제거 가능
 *  - 추상 클래스에선 기본 코드도 지정 가능하다. 놓치지 말자.
 */
