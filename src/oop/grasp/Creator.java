package oop.grasp;

import java.util.ArrayList;
import java.util.List;

/**
 * 생성자
 * 
 * 어떤 클래스가 다른 객체를 생성할 책임을 가져야 할지 결정하는 원칙
 */
public class Creator {

	public static void main(String[] args) {
		CreatorOrderService service = new CreatorOrderService(new CreatorOrder());
		
		service.addLineItem("A", 1);
		service.addLineItem("B", 11);
		service.addLineItem("C", 111);
		service.addLineItem("D", 1111);
		service.addLineItem("E", 11111);
	}
}

/**
 * OrderService 가 LineItem 을 생성해주고
 * Order 자체는 아이템 추가만 해주고 있다
 * 
 * 하지만, Creator 원칙에 의거하면
 * Order 가 LineItem을 포함하고 있으니 생성도 Order 가 책임을 가지는 것이 자연스럽다.
 */
class CreatorLineItem {
    private String name;
    private int quantity;

    public CreatorLineItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}

class CreatorOrderService {
	// 수정 후
	private CreatorOrder order;
	
	public CreatorOrderService(CreatorOrder order) {
		this.order = order;
	}
	
    public void addLineItem(String name, int quantity) {
    	order.addItem(name, quantity);
    }
    
    // 수정 전
    public CreatorLineItem createLineItem(String name, int quantity) {
        return new CreatorLineItem(name, quantity);
    }
}

class CreatorOrder {
    private List<CreatorLineItem> items = new ArrayList<>();

    // 수정 후
    public void addItem(String name, int quantity) {
        items.add(new CreatorLineItem(name, quantity));
    }
    
    // 수정 전
    public void addItem(CreatorLineItem item) {
        items.add(item);
    }
}