package designpattern.Behavioral;

import java.util.Stack;

/**
 * 객체 상태를 저장하고, 필요할 때 복원
 *  - Ctrl + Z 기능과 유사
 *  
 *  사용자의 작업을 되돌릴 수 있게 하고 싶을 때
 *  객체의 상태를 저장해야 하지만 캡슐화를 깨고 싶지 않을 때
 *  복잡한 복원 로직 없이, 간단히 상태 스냅샷을 저장하고 되돌리고 싶을 때
 */
public class Memento {

	public static void main(String[] args) {
		TextEditor editor = new TextEditor();
		EditorHistory history = new EditorHistory();
		
		editor.write("First");
		history.save(editor.save());
		
		editor.write("Second");
		history.save(editor.save());
		
		editor.write("Other Data");
		System.out.println("현재 메모 : " + editor.read());
		
		editor.restore(history.redo());
		System.out.println("현재 메모 redo : " + editor.read());
		
		editor.restore(history.undo());
		System.out.println("현재 메모 undo : " + editor.read());
		
		editor.restore(history.undo());
		System.out.println("현재 메모 undo : " + editor.read());
		
		editor.restore(history.redo());
		System.out.println("현재 메모 redo : " + editor.read());
		
		editor.restore(history.redo());
		System.out.println("현재 메모 redo : " + editor.read());
		
		editor.restore(history.undo());
		System.out.println("현재 메모 undo : " + editor.read());
		
		editor.restore(history.undo());
		System.out.println("현재 메모 undo : " + editor.read());
	}
}
/**
 * Originator
 *  - 상태를 저장하고 복원할 수 있는 클래스
 * 
 * Memento
 *  - Originator 의 상태를 정보를 저장하는 객체
 * 
 * Caretaker
 *  - Memento 를 보관하는 객체(되돌리기 기능 담당)
 */
// Memento
class EditorSnapshot {
	private final String content;
	
	public EditorSnapshot(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
}

// Originator
class TextEditor {
	private String content;
	
	public void write(String text) {
		this.content = text;
		System.out.println("[입력됨] " + content);
	}
	
	public String read() {
		return content;
	}
	
	public EditorSnapshot save() {
		return new EditorSnapshot(content);
	}
	
	public void restore(EditorSnapshot snapshot) {
		if (snapshot == null) {
			System.out.println("저장된 메모가 없습니다.");
			return;
		}
		this.content = snapshot.getContent();
		System.out.println("[복원됨] " + content);
	}
}

// Caretaker
class EditorHistory {
	private Stack<EditorSnapshot> undoHistory = new Stack<>();
	private Stack<EditorSnapshot> redoHistory = new Stack<>();
	
	public void save(EditorSnapshot snapshot) {
		undoHistory.push(snapshot);
	}
	
	public EditorSnapshot undo() {
		if (!undoHistory.isEmpty()) {
			EditorSnapshot snapshot = undoHistory.pop();
			redoHistory.push(snapshot);
			return snapshot;
		}
		return null;
	}
	
	public EditorSnapshot redo() {
		if (!redoHistory.isEmpty()) {
			EditorSnapshot snapshot = redoHistory.pop();
			undoHistory.push(snapshot);
			return snapshot;
		}
		return null;
	}
	
	public boolean hasHistory() {
		return !undoHistory.isEmpty() && !redoHistory.isEmpty();
	}
}
/**
 * GPT 의 개선점
 * 
 * 되돌리기 기능은 존재했지만 다시 앞으로가는 기능이 없으므로 추가
 */

