package oop.grasp;

import java.util.ArrayList;
import java.util.List;

/**
 * 정보 전문가
 * 
 * 정보를 가장 잘 알고 있는 객체에 책임을 할당하라
 */
public class InformationExpert {

	public static void main(String[] args) {
		OrderCalculator calculator = new OrderCalculator();
		Order order = new Order();
		order.addOrderLine(1000);
		order.addOrderLine(2000);
		order.addOrderLine(1050);
		order.addOrderLine(1305);
		order.addOrderLine(11000);
		
		System.out.println("Total : " + calculator.calculateTotal(order));
		System.out.println("Fix Total : " + calculator.fixCalculateTotal(order));
	}
}

/**
 * 수정 전
 * 
 * 현재 OrderCalculator 가 Order 에 대해서 너무 많은 걸 알고 계산 중
 */
class OrderCalculator {
	// 너무 많은 것을 알고 있는 계산 메소드
    public int calculateTotal(Order order) {
        int total = 0;
        for (OrderLine line : order.getLines()) {
            total += line.getPrice();
        }
        return total;
    }
    
    // 수정한 계산 메소드
    public int fixCalculateTotal(Order order) {
    	return order.getTotalPrice();
    }
}

class Order {
    private List<OrderLine> lines = new ArrayList<OrderLine>();
    
    public void addOrderLine(int price)  {
    	lines.add(new OrderLine(price));
    }

    public List<OrderLine> getLines() {
        return lines;
    }
    
    // 책임을 Order 에게 주도록 수정
    public int getTotalPrice() {
    	int total = 0;
        for (OrderLine line : lines) {
            total += line.getPrice();
        }
        return total;
    }
}

class OrderLine {
    private final int price;
    
    public OrderLine(int price) {
    	this.price = price;
	}

    public int getPrice() {
        return price;
    }
}
