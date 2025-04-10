package oop.grasp;

/**
 * 높은 응집도
 * 
 * 클래스나 모듈이 관련 있는 책임만 가지도록 하여, 단일 목적에 집중
 * 
 * 하나의 클래스가 너무 많은 책임을 가지면 응집도가 낮아진다.
 * 관련 있는 기능끼리 묶고, 관련 없는건 분리하여 클래스가 명확한 역할을 갖게 하기
 */
public class HighCohesion {

	public static void main(String[] args) {
		HighCohesionUser user = new HighCohesionUser("A");
		
		HighCohesionRepository repository = new HighCohesionRepository();
		HighCohesionSendEmail sendEmail = new HighCohesionSendEmail();
		HighCohesionValidate valid = new HighCohesionValidate();
		
		repository.saveToDatabase(user.getUsername());
		sendEmail.sendWelcomeEmail(user.getUsername());
		valid.validatePassword("1234123");
	}
}

class HighCohesionUser {
    private String username;

    public HighCohesionUser(String username) {
        this.username = username;
    }
    
    /**
     * 수정 전
     * 
     * 너무 많은 역할 및 책임을 가지고 있다
     */
    public void saveToDatabase() {
        System.out.println("DB 저장: " + username);
    }

    public void sendWelcomeEmail() {
        System.out.println("이메일 전송: " + username);
    }

    public boolean validatePassword(String password) {
        return password.length() >= 4;
    }
    
    /**
     * 수정 후
     * 
     * 단순히 username 만 가지고 있고
     * 각 책임이 필요한 기능들은 클래스 분리
     */
    public String getUsername() {
    	return username;
    }
}

class HighCohesionRepository {

    public void saveToDatabase(String username) {
        System.out.println("DB 저장: " + username);
    }
}

class HighCohesionSendEmail {

    public void sendWelcomeEmail(String username) {
        System.out.println("이메일 전송: " + username);
    }
}

class HighCohesionValidate {

    public boolean validatePassword(String password) {
        return password.length() >= 4;
    }
}
