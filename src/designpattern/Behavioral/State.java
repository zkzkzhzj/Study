package designpattern.Behavioral;

/**
 * 객체 내부 상태에 따라 행동이 바뀌도록 함
 *  - 상태가 변할 때 객체의 행동도 함께 바뀌는 구조
 *  - 조건문 없이 상태마다 클래스 분리
 */
public class State {

	public static void main(String[] args) {
        ApprovalContext rejectDoc = new ApprovalContext();
        ApprovalContext progressDoc = new ApprovalContext();
        
        rejectDoc.getState();
        rejectDoc.progress();
        rejectDoc.getState();
        rejectDoc.reject();
        rejectDoc.getState();
        rejectDoc.progress();
        rejectDoc.reject();
        System.out.println("--------------------------");
        progressDoc.getState();
        progressDoc.reject();
        progressDoc.getState();
        progressDoc.progress();
        progressDoc.getState();
        progressDoc.progress();
        progressDoc.getState();
        progressDoc.progress();
        progressDoc.reject();
	}
}

/**
 * State(인터페이스)
 *  - 모든 상태 클래스가 구현해야 할 공통 인터페이스
 * 
 * ConcreteState
 *  - 상태별 클래스(정상, 피로, 수면 등)
 * 
 * Context(문맥 클래스)
 *  - 현재 상태를 포함하고 있고, 상태 변경 가능
 * 
 * Client
 *  - Context 사용
 */
// State
interface ApprovalState {
	void progress(ApprovalContext context);
	void reject(ApprovalContext context);
	void getState();
}

// ConcreteState
class DraftState implements ApprovalState {

	@Override
	public void progress(ApprovalContext context) {
		System.out.println("작성 완료 -> 검토 요청");
		context.setState(new ReviewState());
	}

	@Override
	public void reject(ApprovalContext context) {
		System.out.println("작성 중에는 반려가 되지 않습니다.");
	}

	@Override
	public void getState() {
		System.out.println("작성 중 상태");
	}
}

class ReviewState implements ApprovalState {

	@Override
	public void progress(ApprovalContext context) {
		System.out.println("검토 완료 -> 승인 완료");
		context.setState(new ApprovedState());
	}

	@Override
	public void reject(ApprovalContext context) {
		System.out.println("검토 완료 -> 반려");
		context.setState(new RejectState());
	}

	@Override
	public void getState() {
		System.out.println("검토 중 상태");
	}
}

class ApprovedState implements ApprovalState {

	@Override
	public void progress(ApprovalContext context) {
		System.out.println("이미 승인이 완료된 문서");
	}

	@Override
	public void reject(ApprovalContext context) {
		System.out.println("승인이 완료되어 반려 불가");
	}

	@Override
	public void getState() {
		System.out.println("승인 완료 상태");
	}
}

class RejectState implements ApprovalState {

	@Override
	public void progress(ApprovalContext context) {
		System.out.println("반려되어 승인이 불가");
	}

	@Override
	public void reject(ApprovalContext context) {
		System.out.println("이미 반려된 문서");
	}

	@Override
	public void getState() {
		System.out.println("반려 상태");
	}
}

// Context
class ApprovalContext {
	private ApprovalState state;
	
	public ApprovalContext() {
		this.state = new DraftState();
	}
	
	// 상태 변경(각, 상태 클래스가 바뀌며 상태 변경)
	public void setState(ApprovalState state) {
		this.state = state;
	}
	
	// interface 의 메소드를 호출 할 수 있도록 메소드 제공
	public void progress() {
		state.progress(this);
	}
	
	public void reject() {
		state.reject(this);
	}
	
	public void getState() {
		state.getState();
	}
}

/**
 * GPT 의 개선점
 * 
 * 상태 확인 메소드 추가
 */
