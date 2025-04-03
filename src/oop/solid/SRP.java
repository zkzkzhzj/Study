package oop.solid;

public class SRP {
	public static void main(String[] args) {
		// 기능중 하나만 바뀌더라도 클래스 전체에 영향이 갈 수 있다.
		UserManagerAll user = new UserManagerAll();
		String name = "이무진";
		String email = "mjlee3@covision.co.kr";
		user.createUser(name, email);
		user.saveUserToFile(name, email);
		user.sendWelcomeEmail(name, email);

		// 하나의 클래스당 하나의 역할만 가지므로 수정되더라도 다른 클래스에 영향이 없다.
		UserManager userManager = new UserManager();
		FileSaver fileSaver = new FileSaver();
		EmailService emailService = new EmailService();
		UserService userService = new UserService(userManager, fileSaver, emailService);
		userService.processUser(name, email);

		/**
		 * 부가 설명
		 * SRP 를 따랐는지 확인 하는 가장 큰 기준 척도는 "기능 변경"이 일어났을 때 파급 효과 확장성과 유연성 증가 : 별도 클래스
		 * 추가로 해결 가능 의존성 관리 용이 : 필요한 부분에서만 의존성 주입이 가능하므로 테스트코드 작성도 용이함 유지보수의 용이 : 하나의
		 * 책임만 가지고 있기 때문에 의미 파악이 쉬움
		 * 
		 * SRP 원칙 적용시 주의점 클래스명은 책임의 소재를 알 수 있게 작명하자 책임을 분리할 때 항상 결합도, 응집도 따져가며 구성하자 -
		 * 모듈간의 의존(결합도)은 느슨하게, 모듈 내의 구성요소 연관 정도(응집도)는 강하게
		 */
	}

}

/**
 * SRP(Single Responsibility Principle) : 단일 책임의 원칙 위배
 */
class UserManagerAll {

	// 생성
	public void createUser(String name, String email) {
		System.out.printf("사용자 생성 : %s, %s %n", name, email);
	}

	// 저장
	public void saveUserToFile(String name, String email) {
		System.out.printf("파일에 저장 : %s, %s %n", name, email);
	}

	// 메일 발송
	public void sendWelcomeEmail(String name, String email) {
		System.out.printf("환영 이메일 전송 : %s, %s %n", name, email);
	}
}

// 수정본

// 사용자 생성 책임
class UserManager {
	public void createUser(String name, String email) {
		System.out.printf("사용자 생성 : %s, %s %n", name, email);
	}
}

// 파일 저장 책임
class FileSaver {
	public void saveUserToFile(String name, String email) {
		System.out.printf("파일에 저장 : %s, %s %n", name, email);
	}
}

// 이메일 전송 책임
class EmailService {
	public void sendWelcomeEmail(String name, String email) {
		System.out.printf("환영 이메일 전송 : %s, %s %n", name, email);
	}
}

// GPT 의 추가 개선 포인트 (DI : 의존성 주입 고려)
class UserService {
	private final UserManager userManager;
	private final FileSaver fileSaver;
	private final EmailService emailService;

	public UserService(UserManager userManager, FileSaver fileSaver, EmailService emailService) {
		this.userManager = userManager;
		this.fileSaver = fileSaver;
		this.emailService = emailService;
	}

	public void processUser(String name, String email) {
		userManager.createUser(name, email);
		fileSaver.saveUserToFile(name, email);
		emailService.sendWelcomeEmail(name, email);
	}
}