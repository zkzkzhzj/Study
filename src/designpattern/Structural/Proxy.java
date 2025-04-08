package designpattern.Structural;

/**
 * 대리자 역할을 하는 객체를 통해 실제 객체에 접근
 *  - 실제 객체를 감싸고 대신 처리하는 구조, 접근 제어나 기능 확장을 목적으로 함
 * 
 * 대리자 역할
 *  - 실제 객체와 같은 인터페이스를 구현하여 대리 객체를 통해 접근
 * 접근 제어
 *  - 직접 접근을 막고 대리 객체를 통해 제어
 * 기능 추가
 *  - 실제 객체 호출 전후로 로깅, 캐싱, 권한 확인 등을 추가할 수 있음
 */
public class Proxy {

	public static void main(String[] args) {
		ProjectDocument projectDocument = new ProjectDocument();
		ProjectProxy master = new ProjectProxy(projectDocument, Authority.MASTER);
		ProjectProxy guest = new ProjectProxy(projectDocument, Authority.GUEST);
		
		master.open();
		guest.open();
	}
}

/**
 * Subject(인터페이스)
 *  - 실제 객체와 대리 객체가 구현할 공통 인터페이스
 * 
 * RealSubject(실제 객체)
 *  - 실제 로직이 구현된 객체
 * 
 * Proxy(대리 객체)
 *  - RealSubject를 감싸고 있으며, 인터페이스를 통해 대신 호출
 * 
 * Client(클라이언트)
 *  - Proxy를 통해 RealSubject에 접근
 */
// Subject
interface Documnet {
	void open();
}

// RealSubject
class ProjectDocument implements Documnet {

	@Override
	public void open() {
		System.out.println("업무 문서 확인");
	}
}

enum Authority {
	MASTER, NORMAL, GUEST
}

class ProjectProxy implements Documnet {
	private Documnet documnet;
	private Authority auth;
	private Logger log = new ConsoleLogger();
	
	public ProjectProxy(Documnet documnet, Authority auth) {
		this.documnet = documnet;
		this.auth = auth;
	}
	
	private boolean hasAccess() {
		return auth == Authority.MASTER || auth == Authority.NORMAL;
	}
	
	
	@Override
	public void open() {
		if (hasAccess()) {
			log.logging("문서를 열었습니다.");
			documnet.open();
			return;
		}
		
		log.logging("권한이 없어 문서를 열지 못했습니다.");
	}
}

interface Logger {
	void logging(String message);
}

class ConsoleLogger implements Logger {

	@Override
	public void logging(String message) {
        System.out.println("ConsoleLog : " + message);
	}
}

/**
 * GPT 의 개선점
 * 
 * ProjectProxy 에서 ProjectDocument 말고 Document 인터페이스 객체로 받는 것이 유연함
 * 
 * 접근 권한 체크를 메소드로 권한
 * 
 * Log interface 를 두어 확장성 고려하기
 */
