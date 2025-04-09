package designpattern.Behavioral;

/**
 * 알고리즘의 구조(뼈대)를 정의하고, 세부 구현은 하위 클래스에 위임
 *  - 공통된 흐름은 상위 클래스에서 고정, 세부 단게는 하위 클래스에서 다르게 구현
 * 
 * 여러 클래스들이 공통된 작업 흐름을 따르지만 일부 단계만 다르게 처리하고 싶을 때 사용
 */
public class Template {

	public static void main(String[] args) {
		WorkRoutine senior = new Senior();
		WorkRoutine junior = new Junior();
		
		senior.dailyRoutine();
		junior.dailyRoutine();
	}
}

/**
 * AbstractClass
 *  - 알고리즘의 템플릿(전체 구조)을 정의
 * 
 * ConcreteClass
 *  - 템플릿의 일부 단계를 구체적으로 구현
 * 
 * Template Method
 *  - 알고리즘의 단계들을 정의한 메소드
 *  - 일부는 고정, 일부는 추상 메소드로 위임
 */
// AbstractClass
abstract class WorkRoutine {
	// Template Method
	// 고정된 메소드의 경우 오버라이드 되지 않도록 final 키워드 기입
	public final void dailyRoutine() {
		introduce();
		goToWork();
		workAM();
		launch();
		workPM();
		overtime();
		leaveWork();
		System.out.println();
	}
	
	public abstract void introduce();
	
	public final void goToWork() {
		System.out.println("출근합니다.");
	}

	public abstract void workAM();
	
	public abstract void workPM();
	
	public abstract void launch();
	
	public final void leaveWork() {
		System.out.println("퇴근합니다.");
	}
	
	public void overtime() { }
}

// ConcreteClass
class Senior extends WorkRoutine {

	@Override
	public void introduce() {
		System.out.println("나는 시니어 개발자입니다.");
	}
	
	@Override
	public void launch() {
		System.out.println("점심으로 알밥을 먹습니다.");
	}
	
	@Override
	public void workAM() {
		System.out.println("코드 리뷰를 합니다.");
	}

	@Override
	public void workPM() {
		System.out.println("테이블 설계를 합니다.");
	}
}

class Junior extends WorkRoutine {

	@Override
	public void introduce() {
		System.out.println("나는 주니어 개발자입니다.");
	}
	
	@Override
	public void launch() {
		System.out.println("점심으로 비빔밥을 먹습니다.");
	}

	@Override
	public void workAM() {
		System.out.println("코드 리뷰를 받습니다.");
	}

	@Override
	public void workPM() {
		System.out.println("UI 개발을 합니다.");
	}
	
	@Override
	public void overtime() {
		System.out.println("페이지 완성을 위해 야근을 합니다.");
	}
}

/**
 * GPT 의 개선점
 * 
 * work() 메소드도 바뀔 수 있을 것 같다
 *  - 추상 메소드로 두기
 * 
 * Hook Method 활용도 고려
 *  - 선택적으로 오버라이드 할 수 있는 단계도 필요할 수 있다
 *  - 빈 메서드(Hook Method) 를 선언 해두면 됨
 */
