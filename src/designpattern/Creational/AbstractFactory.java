package designpattern.Creational;

/**
 * 관련 있는 여러 객체를 생성하는 인터페이스 제공, 구체적인 클래스는 지정하지 않음
 *  - 제품군(Product family) 단위로 객체 생성을 추상화한 패턴
 * 
 * 서로 관련된 객체들을 세트로 생성해야 할 때
 *  - 제품군 간의 일관성 유지
 *  - 구현을 캡슐화하고 변경에 유연하게 대처 가능
 */
public class AbstractFactory {

	public static void main(String[] args) {
		GUIFactory circleFactory = new CircleFactory();
		GUIFactory squareFactory = new SquareFactory();
		
		Application app = new Application(circleFactory);
		
		app.render();
		System.out.println("--------------------");
		
		app.changeGUI(squareFactory);
		app.render();
	}
}

/**
 * AbstractFactory
 *  - 제품군 생성에 대한 인터페이스
 * 
 * ConcreteFactory
 *  - 실제 제품군 생성
 * 
 * AbstractProduct
 *  - 제품에 대한 인터페이스
 * 
 * ConcreteProduct
 *  - 실제 제품 클래스
 * 
 * Client
 *  - 팩토리를 사용하지만 구체적인 클래스를 모름
 */
// AbstractProduct
interface Button {
	void click();
}

interface Checkbox {
	void check();
}

// ConcreteProduct
class CircleButton implements Button {

	@Override
	public void click() {
		System.out.println("동그라미 버튼 클릭");
	}
}

class CircleCheckbox implements Checkbox {

	@Override
	public void check() {
		System.out.println("동그라미 체크박스 클릭");
	}
}

class SquareButton implements Button {

	@Override
	public void click() {
		System.out.println("네모 버튼 클릭");
	}
}

class SquareCheckbox implements Checkbox {

	@Override
	public void check() {
		System.out.println("네모 체크박스 클릭");
	}
}

// AbstractFactory
interface GUIFactory {
	Button createButton();
	Checkbox createCheckbox();
}

// ConcreteFactory
class CircleFactory implements GUIFactory {

	@Override
	public Button createButton() {
		return new CircleButton();
	}

	@Override
	public Checkbox createCheckbox() {
		return new CircleCheckbox();
	}
}

class SquareFactory implements GUIFactory {

	@Override
	public Button createButton() {
		return new SquareButton();
	}

	@Override
	public Checkbox createCheckbox() {
		return new SquareCheckbox();
	}
}

// Client
class Application {
	private GUIFactory factory;

    public Application(GUIFactory factory) {
    	this.factory = factory;
    }
    
    public void changeGUI(GUIFactory factory) {
    	this.factory = factory;
    }

    public void render() {
        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();
        button.click();
        checkbox.check();
    }
}

/**
 * GPT 의 개선점
 * 
 * Application 에서 객체 생성을 render 시점에 하는 것을 추천
 */
