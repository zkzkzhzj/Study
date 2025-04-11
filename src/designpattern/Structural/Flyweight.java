package designpattern.Structural;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 공유를 통해 객체 생성을 최소화
 *  - 많은 수의 유사 객체를 생성해야 할 때
 *  - 메모리 낭비를 줄이고, 동통된 속성은 공유, 개별 속성만 외부화
 * 
 * 언제 쓰면 좋을 까?
 *  - 폰트 렌더링 -> 같은 글자, 색상, 폰트를 가진 문자 반복
 *  - 게임 오브젝트 -> 많은 수의 나무, 몬스터, 지형 등 동일한 속성 객체들
 *  - GUI 아이콘 -> 동일한 아이콘을 여러 곳 표시
 */
public class Flyweight {

	public static void main(String[] args) {
		Forest forest = new Forest();
		forest.plantTree(10, 20, "apple", "black", "big");
		forest.plantTree(50, 20, "apple", "black", "big");
		forest.plantTree(50, 20, "apple", "black", "small");
		forest.plantTree(10, 20, "cow", "red", "small");
		
		forest.drawAll();
	}
}

/**
 * Flyweight
 *  - 공통 인터페이스 또는 추상 클래스
 * 
 * ConcreteFlyweight
 *  - 공유되는 객체, 내부 상태(intrinsic state)를 가짐
 * 
 * FlyweightFactory
 *  - Flyweight 인스턴스를 관리(중복 제거)
 * 
 * Client
 *  - 객체를 사용하는 측, 외부 상태(extrinsic state)를 전달
 * 
 * 
 * 내부 상태(intrinsic)
 *  - 공유 가능한 정보 -> 공통 속성
 * 
 * 외부 상태(extrinsic)
 *  - 공유 불가능한 정보 -> 개별 속성
 */
// Flyweight
interface Draw {
	void coordinate(int x, int y);
}

// ConcreteFlyweight
class TreeDraw implements Draw {
	private String name;
	private String color;
	private String texture;
	
	public TreeDraw(String name, String color, String texture) {
		this.name = name;
		this.color = color;
		this.texture = texture;
	}
	
	
	@Override
	public void coordinate(int x, int y) {
		System.out.println(name + " 나무 -> {" + x + ", " + y + "} Draw");
	}
}

// FlyweightFactory
class TreeFactory {
	private static Map<String, TreeDraw> treeMap = new HashMap<>();
	
	public static TreeDraw getTree(String name, String color, String texture) {
		String key = name + color + texture;
		
		if (!treeMap.containsKey(key)) {
			treeMap.put(key, new TreeDraw(name, color, texture));
		}
		
		return treeMap.get(key);
	}
}

// Client
class Tree {
	private int x;
	private int y;
	private TreeDraw tree;
	
	public Tree(int x, int y, TreeDraw tree) {
		this.x = x;
		this.y = y;
		this.tree = tree;
	}
	
	public void draw() {
		tree.coordinate(x, y);
	}
}

class Forest {
    List<Tree> trees = new ArrayList<>();

    public void plantTree(int x, int y, String name, String color, String texture) {
    	TreeDraw type = TreeFactory.getTree(name, color, texture);
        Tree tree = new Tree(x, y, type);
        trees.add(tree);
    }

    public void drawAll() {
        for (Tree tree : trees) {
            tree.draw();
        }
    }
}

