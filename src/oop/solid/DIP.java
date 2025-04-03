package oop.solid;

public class DIP {

	public static void main(String[] args) {
		ServiceF userServiceF = new ServiceF();
		userServiceF.registerUser("John");
		userServiceF.getUser(1);

		Service mySqlService = new Service(new MySqlRepo());
//		mySqlService.setRepositoryImplementation(new MySqlRepo());
		mySqlService.registerUser("John");
		mySqlService.getUser(1);

		Service mongoDBService = new Service(new MongoDBRepo());
//		mongoDBService.setRepositoryImplementation(new MongoDBRepo());
		mongoDBService.registerUser("Kim");
		mongoDBService.getUser(1);
		/**
		 * 부가 설명
		 * 객체에서 클래스를 참조해야 한다면 직접 참조하는 것이 아니라 상위 요소(추상 클래스 or 인터페이스)로 참조 직접 참조하는 경우
		 * 변경에 취약하다 객체들이 정보를 주고 받을 때 의존 관계가 형성되는데 추상성 높은 클래스와 통신을 해야 함
		 * 직접 의존을 제거하여 확장성과 유연성이 높아짐
		 * 의존성 주입(DI)를 이용하여 쉽게 교체 가능
		 */
	}
}

// DIP(Dependency inversion principle) : 의존관계 역전 원칙 위배
// 저수준 모듈: 직접 데이터베이스에 접근
class RepositoryF {
	public void save(String name) {
		System.out.println("Saving user to DB: " + name);
	}

	public String findById(int id) {
		return "User" + id;
	}
}

// 고수준 모듈: 회원 서비스
class ServiceF {
	// 저수준 모듈에 직접 접근하기 때문에 문제 발생여지 多
	private RepositoryF repository;

	public ServiceF() {
		this.repository = new RepositoryF();
	}

	public void registerUser(String name) {
		repository.save(name);
		System.out.println("User registered: " + name);
	}

	public void getUser(int id) {
		String user = repository.findById(id);
		System.out.println("Found user: " + user);
	}
}

// 수정본
interface Repository {
	void save(String name);
	String findById(int id);
}

class MySqlRepo implements Repository {

	@Override
	public void save(String name) {
		System.out.println("Saving user to MySql: " + name);
	}

	@Override
	public String findById(int id) {
		return "MySql User" + id;
	}
}

class MongoDBRepo implements Repository {

	@Override
	public void save(String name) {
		System.out.println("Saving user to MongoDB: " + name);
	}

	@Override
	public String findById(int id) {
		return "MongoDB User" + id;
	}
}

class Service {
	private Repository repository;
	
	public Service(Repository repository) {
		this.repository = repository;
	}
	
	// 추천하지 않음 (변경 생성자)
//	void setRepositoryImplementation(Repository repository) {
//		this.repository = repository;
//	}

	void registerUser(String name) {
		repository.save(name);
		System.out.println("User registered: " + name);
	}

	void getUser(int id) {
		String user = repository.findById(id);
		System.out.println("Found user: " + user);
	}
}


// GPT 의 추가 개선 포인트 (불변성 확보를 위해 생성자에서 세팅 + 메소드 이름 명확히)