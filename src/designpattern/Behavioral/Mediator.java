package designpattern.Behavioral;

import java.util.HashMap;
import java.util.Map;

/**
 * 많은 객체들이 서로 복잡하게 의존할 때, 그 의존성을 하나의 중재자 객체로 위임
 *  - 복잡도와 결합도를 낮춤
 * 객체들은 서로 직접 통신 하지 않음
 *  - 중재자 전달 -> 중재자가 중간에서 조율/전달
 */
public class Mediator {

	public static void main(String[] args) {
		ChatMediator chatRoom = new ChatRoom();
		
		User notice = new Notice(chatRoom, "Notice");
		User user1 = new NormalUser(chatRoom, "Alice");
		User user1_2 = new NormalUser(chatRoom, "Alice");
		User user2 = new NormalUser(chatRoom, "Bob");
		User user3 = new NormalUser(chatRoom, "Charlie");

		chatRoom.addUser(notice);
		chatRoom.addUser(user1);
		chatRoom.addUser(user1_2);
		chatRoom.addUser(user2);
		chatRoom.addUser(user3);

		user1.send("안녕하세요!, Charlie", user3);
		user3.send("반가워요, Alice!", user1);
		user2.send("반가워 공지사항!", notice);
		notice.sendAll("공지사항입니다");
	}
}

/**
 * Mediator (중재자)
 *  - 모든 의사소통을 담당하는 객체
 *  - 개별 객체들 간의 직접적인 참조 없이 중심에서 관리
 * 
 * Concrete Mediator(구현 중재자)
 *  - 실제로 중재 역할을 수행
 *  - 누가 어떤 메시지를 보냈는지 알고, 누구에게 전달할지 판단
 * 
 * Colleague(참여자)
 *  - 각각의 객체들
 *  - 서로 직접 소통하지 않고 Mediator 를 통해서 소통
 */
// Mediator
interface ChatMediator {
	void sendMessage(String message, User sender, User receive);
	void sendAll(String message, User sender);
	void addUser(User user);
}

// 참여자
abstract class User {
	private ChatMediator mediator;
	private String name;
	
	public User(ChatMediator mediator, String name) {
		this.mediator = mediator;
		this.name = name;
	}
	
	public ChatMediator getMediator() {
		return mediator;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract void send(String message, User receiver);
	
	public void sendAll(String message) { }
	
	public void receive(String message, User sender) { }
}

// Concrete Mediator
class ChatRoom implements ChatMediator {
//	List<User> userList = new ArrayList<>();
	Map<String, User> userMap = new HashMap<>();

	@Override
	public void addUser(User user) {
		if (userMap.containsKey(user.getName())) {
			System.out.println("[System]: 이미 존재하는 사용자입니다: " + user.getName());
			return;
		}
		userMap.put(user.getName(), user);
	}
	
	@Override
	public void sendMessage(String message, User sender, User receiver) {
//		for (User user : userList) {
//			if (user == receiver) {
//				user.receive(message, sender);
//			}
//		}
		userMap.get(receiver.getName()).receive(message, sender);
	}

	@Override
	public void sendAll(String message, User sender) {
		userMap.forEach((name, user) -> {
			if (!sender.getName().equals(name)) {
				user.receive(message, sender);
			}
		});
	}
}

// Colleague
class NormalUser extends User {

	public NormalUser(ChatMediator mediator, String name) {
		super(mediator, name);
	}

	@Override
	public void send(String message, User receiver) {
		this.getMediator().sendMessage(message, this, receiver);
	}
	
	@Override
	public void receive(String message, User sender) {
		System.out.println("[" + sender.getName() + "]: " + message + "(" + this.getName() + ")");
	}
}

class Notice extends User {

	public Notice(ChatMediator mediator, String name) {
		super(mediator, name);
	}

	@Override
	public void send(String message, User receiver) {
		this.getMediator().sendMessage(message, this, receiver);
	}
	
	@Override
	public void sendAll(String message) {
		this.getMediator().sendAll(message, this);
	}
	
	@Override
	public void receive(String message, User sender) {
		this.getMediator().sendMessage("공지사항 계정으로는 메시지 전송이 불가능 합니다.", this, sender);
	}
}

/**
 * GPT 의 개선점
 * 
 * List<User> 보단 Map<String, User> 로 관리
 * 
 * sendAll 메소드 추가 추천
 * 
 * name 및 mediator protected -> private 으로 변경
 */
