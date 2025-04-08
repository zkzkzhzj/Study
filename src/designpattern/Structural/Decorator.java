package designpattern.Structural;

/**
 * 기존 객체에 새로운 기능을 동적으로 추가
 *  - 상속 없이 기능 확장
 * 
 * 객체에 새로운 책임을 추가
 * 상속의 대안으로 활용
 * 기본 기능을 유지하면서도 기능 추가
 */
public class Decorator {

	public static void main(String[] args) {
		Coffee iceAme = new IceAmericano();
		Coffee smallIceAme = new SizeDecorator(iceAme, CoffeeSize.SMALL);
		Coffee largeIceAme = new SizeDecorator(iceAme, CoffeeSize.LARGE);

		System.out.println(iceAme.description() + " -> ￦" + iceAme.cost());
		System.out.println(smallIceAme.description() + " -> ￦" + smallIceAme.cost());
		System.out.println(largeIceAme.description() + " -> ￦" + largeIceAme.cost());
	}
}

/**
 * Component(컴포넌트)
 *  - 기본 기능을 정의하는 인터페이스 또는 추상 클래스
 * 
 * ConcreteComponent(구체 컴포넌트)
 *  - Component 인터페이스를 구현, 기본 동작 정의
 * 
 * Decorator(데코레이터)
 *  - Component 인터페이스를 구현, 기존 컴포넌트를 감싸는 래퍼 역할
 * 
 * ConcreteDecorator(구체 데코레이터)
 *  - Decorator 를 상속하여 구체적인 기능을 추가
 */
// Component
interface Coffee {
	int cost();
	boolean isHot();
	String description();
}

// ConcreteComponent
class IceAmericano implements Coffee {

	@Override
	public int cost() {
		return 1500;
	}

	@Override
	public boolean isHot() {
		return false;
	}

	@Override
	public String description() {
		return "Simple IceAmericano";
	}
}

// Decorator
abstract class CoffeeDecorator implements Coffee {
	protected Coffee coffee;
	
	public CoffeeDecorator(Coffee coffee) {
		this.coffee = coffee;
	}
	
	@Override
	public int cost() {
		return coffee.cost();
	}
	
	@Override
	public boolean isHot() {
		return coffee.isHot();
	}
	
	@Override
	public String description() {
		return coffee.description();
	}
}

// ConcreteDecorator
enum CoffeeSize {
	SMALL(-500), MEDIUM(0), LARGE(500);
	
	private int priceDiff;
	
	CoffeeSize(int priceDiff) {
		this.priceDiff = priceDiff;
	}
	
	public int getPriceDiff() {
		return priceDiff;
	}
}

class SizeDecorator extends CoffeeDecorator {
	private CoffeeSize size;

	public SizeDecorator(Coffee coffee, CoffeeSize size) {
		super(coffee);
		this.size = size;
	}
	
	@Override
	public String description() {
		return coffee.description() + "(" + size + ")";
	}
	
	@Override
	public int cost() {
		return coffee.cost() + size.getPriceDiff();
	}
}

/**
 * GPT 의 개선점
 * 
 * enum class 에서 값을 가져올 수 있도록 수정
 *  - enum class 사용법을 좀 더 확인하자
 */
