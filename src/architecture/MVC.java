package architecture;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * MVC
 * 
 * 초기에는 사용자의 입력, 로직 처리, 결과 출력이 하나의 코드 흐름에 묶여있었음
 * 위 방식은 문제점이 많이 있다
 *  - 변경에 취약
 *  - 재사용성 낮음
 *  - 테스트 어려움
 *  - 역할이 명확하지 않음
 * 
 * 위 문제를 해결하기 위해 등장
 *  - 관심사의 분리 -> 역할을 나누자
 * 
 * 구성 요소
 *  - Model -> 데이터, 비즈니스 로직 처리
 *  - View -> 사용자에게 보여주는 출력 화면
 *  - Controller -> 사용자 입력 처리, 흐름 제어, View <-> Model 연결
 * 
 * 장점
 *  - 변경에 강함
 *  - 테스트 용이
 *  - 재사용성 높음
 *  - 유지보수 쉬움
 *  - 협업에 유리
 *  
 */
public class MVC {

	public static void main(String[] args) {
		// 수정 전
        Scanner sc = new Scanner(System.in);
        System.out.print("아이디를 입력하세요: ");
        String username = sc.nextLine();

        System.out.print("비밀번호를 입력하세요: ");
        String password = sc.nextLine();

        if (username.equals("admin") && password.equals("1234")) {
            System.out.println("로그인 성공!");
        } else {
            System.out.println("로그인 실패!");
        }

        sc.close();
        
        // 수정 후
        MVCController controller = new MVCController();
        controller.saveUser(username, password);
        boolean isLogin = controller.loginProcess(username, password);
        MVCView view = new MVCView();
        if (isLogin) {
        	view.loginSuccess();
        } else {
        	view.loginFail();
        }
	}
}

// 수정 후
class MVCUser {
	private String username;
	private String password;
	
	public MVCUser(String username, String password) {
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

class MVCController {
	private List<MVCUser> list = new ArrayList<MVCUser>();
	
	public void saveUser(String username, String password) {
		System.out.println("유저 정보 저장 완료");
		list.add(new MVCUser(username, password));
	}
	
	public boolean loginProcess(String username, String password) {
		return list.stream().anyMatch(model -> model.getUsername().equals(username) && model.getPassword().equals(password));
	}
}

// GPT 의 추가 개선 사항 -> View 도 역할 분리하자
class MVCView {
	
	public void loginSuccess() {
    	System.out.println("로그인 성공");
	}
	
	public void loginFail() {
    	System.out.println("로그인 실패");
	}
}
