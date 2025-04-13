package mypackage;

import java.util.*;

public class Order {

    public enum side {BID, ASK};

    private static int idCounter = 0;
    private final int id;
    private final boolean isBid;
    private final int sign;
    private final side orderSide;
    private double shares;
    private final double price;

    // Constructor
    public Order(side orderSide, double shares, double price){
        this.id = idCounter ++;
        this.orderSide = orderSide;
        this.isBid = orderSide == side.BID;
        this.sign = isBid? 1 : -1;
        this.shares = shares;
        this.price = price; // give asks negative prices
    }

    @Override
    public String toString() {
        return "mypackage.Order [" + id + "] " + orderSide + ", " + shares + " shares, @" + price;
    }

    // Getters
    public int getId() {
        return id;
    }

    public side getSide() {
        return orderSide;
    }

    public boolean getIsBid() {
        return isBid;
    }

    public int getSign() {
        return sign;
    }

    public double getShares() {
        return shares;
    }

    public double getPrice() {
        return price;
    }

    // reduce order shares when matched with opposite side orders
    public boolean reduceShares(double sharesToReduce) {
        // check if sufficient shares available to reduce
        if (sharesToReduce > this.shares) {
            System.out.println("Insufficient shares remaining.");
            return false;
        } else {
            this.shares -= sharesToReduce;
            notifFilledOrder(this, this.price, sharesToReduce);
            return true;
        }
    }

    // method to place order to orderbook ob
    public void place (OrderBook ob) {
        ob.place(this);
    }

    // method to cancel order to orderbook ob
    public void cancel (OrderBook ob) {
        ob.cancel(this);
    }

    // generate key pair per order for hashmap in orderBook class
    public AbstractMap.SimpleEntry<Double, Integer> getKey() {
        return new AbstractMap.SimpleEntry<>(price, sign);
    }

    // message print for order filled
    private void notifFilledOrder(Order order, double price, double exeShares) {
        System.out.println("[" + order.id + "] " + order.orderSide + " order " +
                (exeShares < order.shares ? "PARTIAL " : "FULLY ") +
                "filled @" + Math.abs(price) + ", "
                + exeShares + " shares.");
    }
}
