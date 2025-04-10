package designpattern.Creational;

/**
 * 복잡한 객체의 생성 과정과 표현을 분리
 *  - 동일한 생성 절차로 다양한 객체 표현을 가능하게 함
 * 
 * 생성할 객체가 많은 파라미터나 조합을 가질 때
 * 생성과 표현을 분리해서 관리하고 싶을 때
 * 객체 생성 과정이 단계적으로 이루어지는 경우
 * 복잡한 생성자 대신 가독성 좋고 유연한 방식을 원할 때
 */
public class Builder {

	public static void main(String[] args) {
		AccountBuilder builder = new IndivBuilder();
		AccountDirector accountDirector = new AccountDirector(builder);
		
		Account account = accountDirector.construct(
				"zkzkzhzj", "1234", "이무진", "test@gmail.com", "010-0000-0000"
				).build();
		
		account.info();
		
		System.out.println("--------------------------------");
		
		Account account2 = new IndivBuilder2()
			.setID("zkzkzhzj")
			.setPW("1234")
			.setName("이무진")
			.setPhone("010-1111-1111")
			.build();

		account2.info();
	}
}
/**
 * Product(결과물 클래스)
 *  - 최종적으로 만들고 싶은 클래스
 * 
 * Builder(인터페이스 or 추상 클래스)
 *  - Product 객체를 만드는 단계 정의
 * 
 * ConcreteBuilder(구현 클래스)
 *  - Builder 를 실제로 구현, 생성될 product 를 내부적으로 소지
 * 
 * 
 * Director
 *  - Builder 의 각 메소드를 순서대로 호출, 전체 생성 흐름 정의
 * 
 * Client
 *  - Director 에게 어떤 Builder 를 쓸지 지정해서 Product 에게 요청
 */
// Product
class Account {
	private String id;
	private String pw;
	private String name;
	private String email;
	private String phone;

	public void setID(String id) {
		this.id = id;
	}

	public void setPW(String pw) {
		this.pw = pw;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void info() {
		System.out.println("ID : " + id);
		System.out.println("PW : " + pw);
		System.out.println("NAME : " + name);
		System.out.println("EMAIL : " + email);
		System.out.println("PHONE : " + phone);
	}
}

// Builder
interface AccountBuilder {
	void setID(String id);
	void setPW(String pw);
	void setName(String name);
	void setEmail(String email);
	void setPhone(String phone);
	Account build();
}

// ConcreteBuilder
class IndivBuilder implements AccountBuilder {
	private Account account;
	
	public IndivBuilder() {
		account = new Account();
	}

	@Override
	public void setID(String id) {
		account.setID(id);
	}

	@Override
	public void setPW(String pw) {
		account.setPW(pw);
	}

	@Override
	public void setName(String name) {
		account.setName(name);
	}

	@Override
	public void setEmail(String email) {
		account.setEmail(email);
	}

	@Override
	public void setPhone(String phone) {
		account.setPhone(phone);
	}

	@Override
	public Account build() {
		return account;
	}
}


// Director
class AccountDirector {
	private AccountBuilder builder;
	
	public AccountDirector(AccountBuilder builder) {
		this.builder = builder;
	}
	
	public AccountBuilder construct(String id, String pw, String name, String email, String phone) {
		builder.setID(id);
		builder.setPW(pw);
		builder.setName(name);
		builder.setEmail(email);
		builder.setPhone(phone);
		return builder;
	}
}

/**
 * Builder 패턴의 2가지 방법
 * 
 * Classic Builder + Director(상단)
 *  - 정해진 생성 절차를 재사용할 때
 *  - 생성 흐름을 Director 가 가짐
 *  - 유연하지 않으며 제한적이다
 *  - 디렉터가 많아지면 가독성이 낮다
 *  - 코드 재사용성이 강하다
 *  - 정형화된 객체에 사용하면 좋다
 *  
 *  Fluent Builder(하단)
 *   - 클라이언트가 직접 생성 흐름 구성
 *   - 매우 유연하다
 *   - 가독성 또한 좋다
 *   - 재사용성은 낮다, 매번 구성
 *   - DTO, 설정, 유저 입력 기반 객체에 사용하면 좋다
 */
interface AccountBuilder2 {
	AccountBuilder2 setID(String id);
	AccountBuilder2 setPW(String pw);
	AccountBuilder2 setName(String name);
	AccountBuilder2 setEmail(String email);
	AccountBuilder2 setPhone(String phone);
	Account build();
}

class IndivBuilder2 implements AccountBuilder2 {
	private Account account;

	public IndivBuilder2() {
		account = new Account();
	}

	@Override
	public AccountBuilder2 setID(String id) {
		account.setID(id);
		return this;
	}

	@Override
	public AccountBuilder2 setPW(String pw) {
		account.setPW(pw);
		return this;
	}

	@Override
	public AccountBuilder2 setName(String name) {
		account.setName(name);
		return this;
	}

	@Override
	public AccountBuilder2 setEmail(String email) {
		account.setEmail(email);
		return this;
	}

	@Override
	public AccountBuilder2 setPhone(String phone) {
		account.setPhone(phone);
		return this;
	}

	@Override
	public Account build() {
		return account;
	}
}
