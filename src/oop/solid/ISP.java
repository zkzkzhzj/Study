package oop.solid;

public class ISP {

	public static void main(String[] args) {

        Workable worker1 = new Human();
        Workable worker2 = new Robot();
        Eatable eater = new Human();

        worker1.work();
        worker2.work();
        eater.eat();
		/**
		 * 부가 설명
		 * 범용적인 인터페이스보다 사용에 맞게 인터페이스를 세분화, 분리 해야 함
		 * 클라이언트의 목적과 용도에 맞는 인터페이스 제공
		 * SRP - ISP 관계
		 * class 의 단일 책임, interface 의 단일 책임
		 * SRP 가 만족되더라도 ISP 가 만족된다는 보장은 없다(책임의 범위에 따라 달라질 수 있음)
		 * 
		 * 주의점
		 * 인터페이스 분리는 최초에 한번 잘 설계 해야 함
		 * 추후 수정이 들어갈 경우 연계되어 많은 곳 수정이 필요할 수 있다
		 */
	}
}

// ISP(Interface Segregation Principle) : 인터페이스 분리 원칙 위배
interface WorkerF {
    void work();
    void eat();
}

class HumanF implements WorkerF {
    public void work() {
        System.out.println("Human working");
    }

    public void eat() {
        System.out.println("Human eating");
    }
}

class RobotF implements WorkerF {
    public void work() {
        System.out.println("Robot working");
    }

    public void eat() {
        // 로봇은 먹을 수 없음
        throw new UnsupportedOperationException("Robot can't eat");
    }
}


// 수정본
interface Workable {
	void work();
}

interface Eatable {
	void eat();
}

class Human implements Workable, Eatable {

	@Override
	public void eat() {
        System.out.println("Human eating");
	}

	@Override
	public void work() {
        System.out.println("Human working");
	}
}

class Robot implements Workable {

	@Override
	public void work() {
        System.out.println("Robot working");
	}
}

// GPT 의 추가 개선 포인트 (네이밍 개선 : 동작 -> 역할 중심)