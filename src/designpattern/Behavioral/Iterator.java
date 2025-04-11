package designpattern.Behavioral;

import java.util.ArrayList;
import java.util.List;

/**
 * 컬렉션 내부 구조에 의존하지 않음
 *  - 요소를 순차적으로 접근
 * 
 * 배열, 리스트, 트리 등 다양한 구조를 하나의 순회 인터페이스로 추상화
 * 클라이언트가 컬렉션 내부 구현을 몰라도 안전히 순회 가능
 * 다형성 + 캡슐화 유지
 */
public class Iterator {

	public static void main(String[] args) {
        ChatLog log = new ChatLog();
        log.addMessage("10:01", "Alice", "안녕!");
        log.addMessage("10:02", "Bob", "하이!");
        log.addMessage("10:03", "Charlie", "점심 뭐 먹지?");

        MessageIterator<Message> iterator = log.iterator();
        while (iterator.hasNext()) {
            Message msg = iterator.next();
            System.out.println(msg.showMessage());
        }
	}
}
/**
 * Iterator
 *  - 순회 기능 인터페이스
 * 
 * ConcreteIterator
 *  - 실제 순회 로직 담당 클래스
 * 
 * Aggregate
 *  - 컬렉션 인터페이스
 * 
 * ConcreteAggregate
 *  - 실제 컬렉션 데이터 보관 클래스
 * 
 * Client
 *  - Iterator 사용해서 순회 진행
 */
class Message {
	private String time;
	private String sender;
	private String content;
	
	public Message(String time, String sender, String content) {
		this.time = time;
		this.sender = sender;
		this.content = content;
	}
	
	public String showMessage() {
		return "[" + time + "] " + sender + ": " + content;
	}
}

// Iterator
interface MessageIterator<T> {
	boolean hasNext();
	T next();
}

// ConcreteIterator
class ChatIterator implements MessageIterator<Message> {
    private List<Message> messages;
    private int position = 0;

    public ChatIterator(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean hasNext() {
        return position < messages.size();
    }

    @Override
    public Message next() {
        return messages.get(position++);
    }
}

// Aggregate
interface ChatLogAggregate {
	MessageIterator<Message> iterator();
}

// ConcreteAggregate
class ChatLog implements ChatLogAggregate {
    private List<Message> messages = new ArrayList<>();

    public void addMessage(String time, String sender, String content) {
        messages.add(new Message(time, sender, content));
    }

    @Override
    public MessageIterator<Message> iterator() {
        return new ChatIterator(messages);
    }
}