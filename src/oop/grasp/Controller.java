package oop.grasp;

/**
 * 컨트롤러
 * 
 * 시스템 이벤트를 처리할 책임을 어느 객체에 할당할지 결정하는 원칙
 * 
 * 사용자로부터 들어오는 시스템 이벤트 처리
 *  - 책임을 UI 객체가 아니라 적절한 도메인 객체 또는 대표 객체에게 할당해야 함
 */
public class Controller {

	public static void main(String[] args) {
        ControllerUI ui = new ControllerUI();
        ui.handleUserRegistration("TEST", "1234");
	}
}

/**
 * 수정전
 * UI가 처리까지 직접 진행
 * 
 * 수정 후
 * Controller 객체가 담당
 */
class ControllerUI {
	private ControllerController controller = new ControllerController();
	
	public void handleUserRegistration(String username, String password) {
//	    System.out.println("사용자 " + username + " 등록 완료!");
		
		controller.saveUser(username, password);
	}
}

// 수정 후
class ControllerController {
	public void saveUser(String username, String password) {
	    System.out.println("사용자 " + username + " 등록 완료!");
	}
}