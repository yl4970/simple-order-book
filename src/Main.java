import java.util.*;

public class Main {
    public static void main(String[] args) {
        OrderBook ob = new OrderBook();

        Order order_1 = new Order(Order.side.ASK, 100, 99.7);
        Order order_2 = new Order(Order.side.ASK, 80, 100.1);
        Order order_3 = new Order(Order.side.BID, 300, 100.8);
        Order order_4 = new Order(Order.side.BID, 800, 100.3);
        Order order_5 = new Order(Order.side.ASK, 100, 100.8);
        Order order_6 = new Order(Order.side.BID, 1000, 100.7);
        Order order_7 = new Order(Order.side.ASK, 400, 100.4);

        order_1.place(ob);
        order_2.place(ob);
        order_3.place(ob);
        order_4.place(ob);
        order_5.place(ob);
        order_6.place(ob);
        order_7.place(ob);

        System.out.println(ob.queueMap);
    }
}