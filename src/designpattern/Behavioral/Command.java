package designpattern.Behavioral;

import java.util.Stack;

/**
 * 요청을 객체로 캡슐화
 *  - 명령을 나중에 실행하거나 취소할 수 있는 기능 제공
 * 실행할 기능 매개변수로 전달
 *  - 요청자와 수행자가 느슨하게 결합되도록 설계
 * 
 * 행위 캡슐화
 *  - 어떤 작업을 실행할 지 구체적으로 몰라도 된다
 * 요청과 실행의 분리
 *  - 명령을 저장, 큐잉, 로깅하여 나중에 실행 가능
 * 작업 취소/재실행
 *  - 이전 명령을 취소하거나 다시 실행 가능
 * OCP 준수
 */
public class Command {
	
	public static void main(String[] args) {
        Character character = new Character();
        CommandInvoker invoker = new CommandInvoker();

        CommandInterface move = new MoveCommand(character);
        CommandInterface attack = new AttackCommand(character);
        CommandInterface defend = new DefendCommand(character);

        invoker.executeCommand(move);
        invoker.executeCommand(attack);
        invoker.undoCommand();
        invoker.executeCommand(move);
        invoker.redoCommand();
        invoker.executeCommand(defend);
        invoker.undoCommand();
        invoker.redoCommand();
        invoker.redoCommand();
	}
}

/**
 * Command(명령 인터페이스)
 *  - 실행 메서드(execute)를 선언 - 구현 강제
 * 
 * ConcreteCommand(구체 명령 클래스)
 *  - Command 인터페이스 구현 -> 로직 정의
 *  - Receiver 객체를 호출 하여 작업 수행
 * 
 * Invoker(요청자)
 *  - Command 객체를 가지고 있으며, 실행을 요청
 * 
 * Receiver(수신자)
 *  - 실제 작업을 수행하는 객체
 */
// Command
interface CommandInterface {
	void execute();
	void undo();
	void repo();
}

// ConcreteCommand
class MoveCommand implements CommandInterface {
    private Character character;

    public MoveCommand(Character character) {
        this.character = character;
    }

    @Override
    public void execute() {
        character.move();
    }

    @Override
    public void undo() {
        character.stop();
    }

    @Override
    public void repo() {
        character.move();
    }
}

class AttackCommand implements CommandInterface {
    private Character character;

    public AttackCommand(Character character) {
        this.character = character;
    }

    @Override
    public void execute() {
        character.attack();
    }

    @Override
    public void undo() {
        character.stop();
    }

    @Override
    public void repo() {
        character.attack();
    }
}

class DefendCommand implements CommandInterface {
    private Character character;

    public DefendCommand(Character character) {
        this.character = character;
    }

    @Override
    public void execute() {
        character.defend();
    }

    @Override
    public void undo() {
        character.stop();
    }

    @Override
    public void repo() {
        character.defend();
    }
}

// Receiver
class Character {
	
    public void move() {
        System.out.println("캐릭터가 움직입니다.");
    }

    public void attack() {
        System.out.println("캐릭터가 공격합니다.");
    }

    public void defend() {
        System.out.println("캐릭터가 방어합니다.");
    }

    public void stop() {
        System.out.println("캐릭터가 행동을 멈춥니다.");
    }
}

// Invoker
class CommandInvoker {
	private Stack<CommandInterface> history = new Stack<>();
    private Stack<CommandInterface> redoStack = new Stack<>();
	
	public void executeCommand(CommandInterface command) {
		command.execute();
		history.push(command);
	}
	
	public void undoCommand() {
		if (!history.isEmpty()) {
			CommandInterface command = history.pop();
			command.undo();
            redoStack.push(command);
		}
	}
	
	public void redoCommand() {
		if (!redoStack.isEmpty()) {
	        CommandInterface command = redoStack.pop();
	        command.repo();
	        history.push(command);
		}
	}
}