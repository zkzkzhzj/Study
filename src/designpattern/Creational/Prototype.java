package test;

import java.util.ArrayList;
import java.util.List;

/**
 * 복잡한 객체 생성 비용을 줄이기 위한 패턴
 *  - 기존 객체를 복제(clone)해서 새로운 객체를 만드는 방식
 * 
 * 객체 생성 비용이 큰 경우
 * 유사한 객체를 여러 개 만들어야 하는 경우
 * 객체의 생성자가 복잡하고 반복되는 경우
 */
public class Prototype {

	public static void main(String[] args) {
		Document workDoc = new ProjectDocument(DocumentSecurity.PUBLIC, "테스트 문서1", "퉤스트 내용");
		workDoc.addReviewer("A");
		workDoc.info();
		workDoc.changeDocContent("테스트 내용");
		workDoc.addReviewer("B");
		workDoc.info();
		Document workDocCopy = workDoc.cloneDoc();
		workDocCopy.changeDocContent("개인 수정 사항");
		workDocCopy.addReviewer("C");
		workDoc.info();
		workDocCopy.info();
	}
}
/**
 * Prototype(인터페이스)
 *  - 복제 메소드(clone)를 정의
 * 
 * ConcretePrototype
 *  - 실제 복제 대상 클래스, clone() 구현
 * 
 * Client
 *  - 복제된 객체 사용
 */
enum DocumentSecurity {
	CONFIDENTIAL, SECRET, TOP_SECRET, PUBLIC
}

interface Document {
	void changeDocContent(String content);
	Document cloneDoc();
	void info();
	void addReviewer(String reviewer);
}

class ProjectDocument implements Document {
	private final DocumentSecurity security;
	private String title;
	private String content;
	private int version = 1;
	private List<String> shallowReviewers = new ArrayList<String>();
	private List<String> deepReviewers = new ArrayList<String>();
	
	public ProjectDocument(DocumentSecurity security, String title, String content) {
		this.security = security;
		this.title = title;
		this.content = content;
	}

	@Override
	public void addReviewer(String reviewer) {
		shallowReviewers.add(reviewer);
		deepReviewers.add(reviewer);
	}

	@Override
	public void changeDocContent(String content) {
		this.content = content;
		version++;
	}
	
	@Override
	public Document cloneDoc() {
		ProjectDocument cloneDoc = new ProjectDocument(security, title, content);
		cloneDoc.version = version;
		cloneDoc.shallowReviewers = shallowReviewers;
		cloneDoc.deepReviewers = new ArrayList<String>(deepReviewers);
		return cloneDoc;
	}
	
	@Override
	public void info() {
		System.out.println("문서 확인");
		System.out.println("문서 보안 등급 : " + security);
		System.out.println("문서 제목 : " + title);
		System.out.println("문서 내용 : " + content);
		System.out.println("문서 버전 : " + version);
		System.out.println("리뷰어(얕은복사) : " + shallowReviewers.toString());
		System.out.println("리뷰어(깊은복사) : " + deepReviewers.toString());
	}
}

/**
 * GPT 의 피드백
 * 
 * Shallow Copy && Deep Copy
 * 
 * Shallow Copy
 *  - 내 구현이 처음 Shallow Copy 에 가깝다고 했다.
 *  - 나는 의문이 들었다 -> 왜? 복사한 값을 수정한다고 원본 값이 안바뀌는데?
 *   - 하지만 이건 멍청한 생각, 원시 타입 객체만 존재했기 때문에 내부적으로 새 인스턴스를 생성해서 반환해주기 때문에 그렇게 보였던 것 뿐
 *  - 참조 객체를 추가해서 테스트 해보니 원본 값도 바뀌는걸 확인했다(List 객체)
 * 
 * Deep Copy
 *  - 참조 객체의 경우 그대로 객체를 넣으면 기존의 값을 바라보고 있기 때문에 바뀐다
 *  - 그래서 새로운 참조 객체를 생성해서 넘겨주어야 영향이없이 새로운 인스턴스 객체로 복사된다
 * 
 * 그리고 애초에 Deep || Shallow Copy 에 대한 오해를 했다..
 * 깊은 복사가 영향이 가지 않는 복사, 얕은 복사가 참조 값(원본 값)에 영향을 주는 복사
 */

