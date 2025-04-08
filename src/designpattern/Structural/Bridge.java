package designpattern.Structural;

/**
 * 기능 계층과 구현 계층 분리
 *  - 독립적으로 확장할 수 있도록 구조화
 * 계층 구조를 두어 유연성을 증가
 * 
 * 기능과 구현의 독립성
 *  - 서로 독립적으로 확장 가능
 * 객체 조합을 통한 확장성
 *  - 기능 클래스와 구현 클래스의 조합으로 다양한 구조 가능
 * 변경 최소화
 *  - 기능 계층 또는 구현 계층 중 하나를 수정해도 다른 게층에 영향 적음
 */
public class Bridge {
	
	public static void main(String[] args) {
        Device tv = new TV();
        Device radio = new Radio();

        Remote tvRemote = new SmartRemote(tv);
        Remote radioRemote = new SmartRemote(radio);

        tvRemote.powerOn();
        tv.turnOff();
        radio.turnOn();
        radioRemote.powerOff();
	}
}

/**
 * Abstraction(추상화 계층)
 *  - 기능의 인터페이스 정의
 *  - 구현 객체(Implementor)에 대한 참조를 가짐
 * 
 * RefinedAbstraction(확장된 추상화)
 *  - Abstraction 을 확장한 구체적 클래스
 *  - 구현부를 활용하여 실제 작업 수행
 * 
 * Implementor(구현 계층 인터페이스)
 *  - 구현 객체가 가져야 할 공통 메소드 정의
 * 
 * ConcreteImplementor(구현 계층 구현체)
 *  - 구현 계층 인터페이스를 구현하는 구체 클래스
 */
// Abstraction
interface Remote {
	void powerOn();
	void powerOff();
}

// RefinedAbstraction
class SmartRemote implements Remote {
	private Device device;
	
	public SmartRemote(Device device) {
		this.device = device;
	}

	@Override
	public void powerOn() {
		System.out.println("스마트 리모콘 작동");
		device.turnOn();
	}

	@Override
	public void powerOff() {
		System.out.println("스마트 리모콘 작동");
		device.turnOff();
	}
}

// Implementor
interface Device {
	void turnOn();
	void turnOff();
}

// ConcreteImplementor
class TV implements Device {

	@Override
	public void turnOn() {
		System.out.println("TV를 킵니다.");
	}

	@Override
	public void turnOff() {
		System.out.println("TV를 끕니다.");
	}
}

class Radio implements Device {

	@Override
	public void turnOn() {
		System.out.println("Radio를 킵니다.");
	}

	@Override
	public void turnOff() {
		System.out.println("Radio를 끕니다.");
	}
}