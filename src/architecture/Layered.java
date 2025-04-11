package architecture;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 계층형 아키텍처
 * 
 * 애플리케이션을 기능별로 책임을 나눈 계층으로 나눔
 *  - 각 계층이 자신의 책임에만 집중
 */
public class Layered {

	public static void main(String[] args) {
		// 수정 전
		System.out.println("수정 전 로직 시작");
		Scanner sc = new Scanner(System.in);

        System.out.print("아이디를 입력하세요: ");
        String username = sc.nextLine();
        System.out.print("비밀번호를 입력하세요: ");
        String password = sc.nextLine();

        List<LayeredUser> users = new ArrayList<>();
        users.add(new LayeredUser(username, password));

        boolean loginSuccess = false;
        for (LayeredUser user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loginSuccess = true;
                break;
            }
        }

        if (loginSuccess) {
            System.out.println("로그인 성공");
        } else {
            System.out.println("로그인 실패");
        }
        
        System.out.println("수정 후 로직 시작");
        LayeredUI ui = new LayeredUI(sc);
        ui.startRegister();
        ui.startLogin();
        ui.changeDBRepo();
        ui.startLogin();
        
        sc.close();
	}
}
/**
 * Presentation Layer
 *  - 사용자와의 인터페이스(UI), 요청 수신, 응답 처리
 * 
 * Service (Business) Layer
 *  - 도메인 로직 처리, 트랜잭션, 유스케이스
 * 
 * Repository (Persistence) Layer
 *  - 데이터 저장, 조회, DB 접근 처리
 */

class LayeredUser {
	private String username;
	private String password;
	
	public LayeredUser(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
}

// 수정 후
class LayeredService {
	private LayeredRepository repository;
	
	public LayeredService() {
		repository = new LayeredInMemoryRepository();
	}
	
	public void addUser(String username, String password) {
		repository.save(username, password);
	}
	
	public boolean loginProcess(String username, String password) {
		return repository.login(username, password);
	}
	
	public void changeRepository(LayeredRepoType type) {
		switch (type) {
		case MEMORY: this.repository = new LayeredInMemoryRepository(); break;
		case DB: this.repository = new LayeredDBRepository(); break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}
}

class LayeredController {
	private LayeredService layeredService;
	
	public LayeredController() {
		layeredService = new LayeredService();
	}
	
	public void addUser(String username, String password) {
		layeredService.addUser(username, password);
	}
	
	public boolean loginProcess(String username, String password) {
		return layeredService.loginProcess(username, password);
	}
	
	public void changeRepository(LayeredRepoType type) {
		layeredService.changeRepository(type);
	}
}
enum LayeredRepoType {
	MEMORY, DB
}

class LayeredUI {
	private LayeredController controller;
	private Scanner scan;
	
	public LayeredUI(Scanner scan) {
		controller = new LayeredController();
		this.scan = scan;
	}
	
	public void changeInmemoryRepo() {
		controller.changeRepository(LayeredRepoType.MEMORY);
		System.out.println("Memory 저장 방식으로 변경");
	}
	
	public void changeDBRepo() {
		controller.changeRepository(LayeredRepoType.DB);
		System.out.println("DB 저장 방식으로 변경");
	}
	
	public void startRegister() {
		System.out.println("회원가입 시작");
        System.out.print("아이디를 입력하세요: ");
        String username = scan.nextLine();
        System.out.print("비밀번호를 입력하세요: ");
        String password = scan.nextLine();
        
        controller.addUser(username, password);
	}
	
	public void startLogin() {
		System.out.println("로그인 시작");
        System.out.print("아이디를 입력하세요: ");
        String username = scan.nextLine();
        System.out.print("비밀번호를 입력하세요: ");
        String password = scan.nextLine();
		
        boolean isLogin = controller.loginProcess(username, password);
        
        if (isLogin) {
        	System.out.println("로그인 성공");
        	return;
        }
        
        System.out.println("로그인 실패");
	}
}

// GPT 의 피드백 - Repository 계층 추가
interface LayeredRepository {
	void save(String username, String password);
	boolean login(String username, String password);
}

class LayeredInMemoryRepository implements LayeredRepository {
	private List<LayeredUser> userList = new ArrayList<LayeredUser>();

	@Override
	public void save(String username, String password) {
		userList.add(new LayeredUser(username, password));
		System.out.println("Memory : 회원가입 완료");
	}

	@Override
	public boolean login(String username, String password) {
		return userList.stream().anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
	}
}

class LayeredDBRepository implements LayeredRepository {
	private List<LayeredUser> userList = new ArrayList<LayeredUser>();

	@Override
	public void save(String username, String password) {
		userList.add(new LayeredUser(username, password));
		System.out.println("DB : 회원가입 완료");
	}

	@Override
	public boolean login(String username, String password) {
		return userList.stream().anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
	}
}
