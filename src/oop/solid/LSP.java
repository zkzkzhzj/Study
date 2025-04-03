package oop.solid;

public class LSP {
	public static void main(String[] args) {
        Shape rectangle = new Rectangle();
        ((Rectangle) rectangle).setWidth(5);
        ((Rectangle) rectangle).setHeight(10);
        rectangle.getArea();

        Shape square = new Square();
        ((Square) square).setSide(7);
        square.getArea();
    	
    	/**
    	 * 부가 설명
    	 * 옳바른 상속 관계의 특징을 정의
    	 * 서브 타입은 언제나 기반 타입으로 교체가 가능 해야 함
    	 * 자식 클래스에서는 부모 클래스의 행위는 모두 보장 되어야 함
    	 * 다형성 원리 : 업 캐스팅을 해도 문제가 없어야 함
    	 *  - 업 캐스팅 : 자식이 부모로 캐스팅 된다(부모로 묶어 간단하게 관리하기 위해서 사용[예시])
    	 *  - 다운 캐스팅 : 업 캐스팅 된 객체를 다시 복구 하는 개념(원래 있던 기능 회복)
    	 *  쉽게 말해 다형성을 지원하기 위한 원칙이 LSP
    	 */
	}
}

//LSP(Liskov Substitution Principle) : 리스코프 치환 원칙 위배
/**
 * 부모 클래스의 기능을 확장
 * 부모 클래스의 행위를 무효화, 변형 하면 안됨(현재 위반 중)
 * 부모 객체를 사용하는 모든 곳에서 자식 객체로 교체해도 문제 없어야 함(is-a 관계 성립해야함)
 * 
 * 사각형과 정사각형의 특성을 생각해보면 상속 관계가 올바르지 않다.
 * 정사각형이 사각형을 상속받으면 사각형의 규칙 위반(폭과 높이가 같은 정사각형, 그렇지 않은 사각형)
 * 폭과 높이를 각각 설정하는 사각형의 특성이 정사각형에서는 무시 됨
 * 
 * 자식이 부모를 대체해도 문제가 없어야 하지만 현재 문제가 많음.
 */
class RectangleF {
    protected int width;
    protected int height;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea() {
        return width * height;
    }
}

class SquareF extends RectangleF {
    @Override
    public void setWidth(int side) {
        this.width = this.height = side;
    }

    @Override
    public void setHeight(int side) {
        this.width = this.height = side;
    }
}


// 수정본
interface Shape {
    void getArea();
}

class Rectangle implements Shape {
    private int width;
    private int height;

    void setWidth(int width) {
        this.width = width;
    }

    void setHeight(int height) {
        this.height = height;
    }

	@Override
	public void getArea() {
		System.out.println("Rectangle Area: " + (width * height));
	}
}

class Square implements Shape {
    private int side;
    
    void setSide(int side) {
    	this.side = side;
    }

	@Override
	public void getArea() {
		System.out.println("Square Area: " + (side * side));
	}
}


// GPT 의 추가 개선 포인트 (내부 변수를 private 으로 캡슐화 추천)