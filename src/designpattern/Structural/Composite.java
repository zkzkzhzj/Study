package designpattern.Structural;

import java.util.ArrayList;
import java.util.List;

/**
 * 객체들을 트리 구조로 구성
 *  - 부분-전체 계층 구조
 * 
 * 사용시점
 *  - 객체들이 부분-전체 관계를 가질 때
 *  - 클라이언트가 단일 객체와 복합 객체를 동일하게 처리해야 할 때
 *  - 객체를 트리 구조로 구성하고 싶을 때
 */
public class Composite {

	public static void main(String[] args) {
		// 단일
		Employee ceo = new IndivdualEmployee("J", "CEO");
		Employee cto = new IndivdualEmployee("Z", "CTO");
		Employee[] headList = {ceo, cto};
		Employee frontSenior = new IndivdualEmployee("A", "Senior");
		Employee frontJunior1 = new IndivdualEmployee("D", "Junior");
		Employee frontJunior2 = new IndivdualEmployee("E", "Junior");
		Employee frontJunior3 = new IndivdualEmployee("F", "Junior");
		Employee[] frontList = {frontSenior, frontJunior1, frontJunior2, frontJunior3};
		Employee backSenior = new IndivdualEmployee("B", "Senior");
		Employee backJunior1 = new IndivdualEmployee("G", "Junior");
		Employee backJunior2 = new IndivdualEmployee("H", "Junior");
		Employee[] backList = {backSenior, backJunior1, backJunior2};
		Employee designSenior = new IndivdualEmployee("C", "Senior");
		Employee designJunior = new IndivdualEmployee("I", "Junior");
		Employee[] designList = {designSenior, designJunior};
		
		// 복합
		Organization headOffice = new Organization("Head Office");
		Organization frontDevPart = new Organization("Front Dev Office");
		Organization backDevPart = new Organization("Back Dev Office");
		Organization designDevPart = new Organization("Design Dev Office");
		Organization[] partList = {frontDevPart, backDevPart, designDevPart};
		
		addEmployeesToOrganization(headOffice, headList);
		addEmployeesToOrganization(frontDevPart, frontList);
		addEmployeesToOrganization(backDevPart, backList);
		addEmployeesToOrganization(designDevPart, designList);
		addOrganizationsToOrganization(headOffice, partList);
		
		headOffice.displayHierarchy();
		System.out.println("-------------------------------");
		frontDevPart.displayHierarchy();
		backDevPart.displayHierarchy();
		designDevPart.displayHierarchy();
		System.out.println("-------------------------------");
		headOffice.removeEmployee(designDevPart);
		headOffice.displayHierarchy();
	}
	
    private static void addEmployeesToOrganization(Organization org, Employee... employees) {
        for (Employee employee : employees) {
            org.addEmployee(employee);
        }
    }

    private static void addOrganizationsToOrganization(Organization org, Organization... orgs) {
        for (Organization organization : orgs) {
            org.addEmployee(organization);
        }
    }
//	private static void setEmployee(Organization org, Employee[] list) {
//		for (Employee employee : list) {
//			org.addEmployee(employee);
//		}
//	}
}
/**
 * Component(구성 요소)
 *  - 공통 인터페이스, 단일 객체와 복합 객체를 동일하게 처리
 *  - 공통 메소드 정의
 * 
 * Leaf(단일 객체)
 *  - 트리 구조의 단일 객체를 의미
 *  - 더 이상 하위 요소가 없는 객체
 * 
 * Composite(복합 객체)
 *  - 하위 요소를 가지는 객체, 자기 자신도 Component 취급
 *  - Leaf 와 Composite 를 모두 포함할 수 있다
 * 
 * Client(사용자)
 *  - Component 인터페이스를 통해 객체를 사용
 *  - 복합 객체와 단일 객체를 동일하게 사용 가능
 */
// Component
interface Employee {
	void displayHierarchy();
}

// Leaf(단일)
class IndivdualEmployee implements Employee {
	private String name;
	private String position;
	
	public IndivdualEmployee(String name, String position) {
		this.name = name;
		this.position = position;
	}

	@Override
	public void displayHierarchy() {
        System.out.println("- " + position + ": " + name);
	}
}

// Composite(복합)
class Organization implements Employee {
	private String name;
	private List<Employee> employees = new ArrayList<Employee>();
	
	public Organization(String name) {
		this.name = name;
	}
	
	public void addEmployee(Employee employee) {
		employees.add(employee);
	}
	
	public void removeEmployee(Employee employee) {
		employees.remove(employee);
	}

	@Override
	public void displayHierarchy() {
		System.out.println("Organization: " + name);
		for (Employee employee : employees) {
			employee.displayHierarchy();
		}
	}
}

/**
 * GPT 의 개선점
 * 
 * 메소드명 명확히 변경
 * 
 * 가변 인자 사용
 * 
 * 메소드 오버로딩을 통하여 직원 추가, 조직 추가 분리
 *  - 가독성 증가
 *  - 현실적으로 추가 조건이 다를 수 있다(개인정보확인, 조직코드확인 등)
 *   - 기능 확장 또는 세분화가 필요할 때 더 유연히 대처 가능
 *  - 의도 명확화
 * -> Composite 패턴에서는 동일하게 구조를 가져간다에 몰입되어 구분지어야 할거라는 생각 조차 못했다
 */
