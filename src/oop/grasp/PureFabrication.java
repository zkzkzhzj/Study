package oop.grasp;

/**
 * 순수 구현 클래스
 * 
 * 시스템의 응집도나 결합도를 높이기 위함
 *  - 도메인 모델과 관련이 없는 인공적인 클래스를 만들어 책임을 맡김
 * 
 * 의미 없는 클래스가 왜 필요할까?
 *  - 도메인 객체에 책임을 몰아주면 응집도도 낮아지고 결합도도 높아짐
 *  - 하지만, Information Expert 원칙만 따르면 책임이 과도하게 몰리는 경우 발생
 *  - 그래서 도메인 모델과 상관없는 기술 책임을 처리할 클래스를 생성해서 해결
 */
public class PureFabrication {

	public static void main(String[] args) {
		// 수정 전
		PureFabricationUser user = new PureFabricationUser("A");
        user.saveToFile();
        
        // 수정 후
        PureFabricationRepository repository = new PureFabricationRepository();
        repository.saveToFile(user.getUsername());
	}
}

class PureFabricationUser {
    private String username;

    public PureFabricationUser(String username) {
        this.username = username;
    }

    // 수정 전
    public void saveToFile() {
        System.out.println("파일로 저장: " + username);
    }
    
    // 추가
    public String getUsername() {
    	return username;
    }
}

// 수정 후
class PureFabricationRepository {

    public void saveToFile(String username) {
        System.out.println("파일로 저장: " + username);
    }
}
