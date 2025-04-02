import java.util.*;

public class OrderBook {
    private HashMap<Integer, Order> orderMap;
    private HashMap<Integer, HashSet<Double>> priceSet;
    private PriorityQueue<Double> ask; // sorted price list for asks
    private PriorityQueue<Double> bid; // sorted price list for bids
    public HashMap<AbstractMap.SimpleEntry<Double, Integer>, LinkedList<Order>> queueMap;
    private HashMap<AbstractMap.SimpleEntry<Double, Integer>, Double> sharesMap;

    // Constructor
    public OrderBook() {
        orderMap = new HashMap<Integer, Order>();
        ask = new PriorityQueue<Double>(Comparator.reverseOrder());
        bid = new PriorityQueue<Double>(Comparator.reverseOrder());
        queueMap = new HashMap<AbstractMap.SimpleEntry<Double, Integer>, LinkedList<Order>>();
        sharesMap = new HashMap<AbstractMap.SimpleEntry<Double, Integer>, Double>();
        priceSet = new HashMap<Integer, HashSet<Double>>();
    }

    public void place(Order order) {
        matchOrders(order);
        if (order.getShares() > 0) {
            addOrderToBook(order);
        }
    }

    public void cancel(Order order) {
        removeOrder(order);
    }

    // method to add order
    private void addOrderToBook(Order order) {
        orderMap.put(order.getId(), order);
        // check if price exists in priceSet
        // if not, it means key is missing from all data structures
        priceSet.putIfAbsent(order.getSign(), new HashSet<>());
        if (!priceSet.get(order.getSign()).contains(order.getPrice())) {
            // add to priceSet
            priceSet.put(order.getSign(), new HashSet<Double>());
            priceSet.get(order.getSign()).add(order.getPrice());
            // add to sorted price list of the same side
            PriorityQueue<Double> samebook = getBook(order, "same");
            samebook.add(order.getPrice());
            // add key to queueMap and sharesMap
            queueMap.put(order.getKey(), new LinkedList<Order>());
            sharesMap.put(order.getKey(), 0.0);
        }
        queueMap.get(order.getKey()).add(order);
        sharesMap.put(order.getKey(), sharesMap.get(order.getKey()) + order.getShares());
    }

    // method to match order
    private void matchOrders(Order order) {
        PriorityQueue<Double> oppobook = getBook(order, "opposite");
        while (!oppobook.isEmpty() &&
                oppobook.peek() + order.getPrice() >= 0 &&
                order.getShares() > 0) {
            double bestPrice = oppobook.peek();
            AbstractMap.SimpleEntry<Double, Integer> oppoKey = new AbstractMap.SimpleEntry<>(bestPrice, order.getSign() * (-1));
            Order matchedOrder = queueMap.get(oppoKey).peek();
            double tradeShares = Math.min(order.getShares(), matchedOrder.getShares());
            fillShares(order, tradeShares);
            fillShares(matchedOrder, tradeShares);
        }
    }

    // method to remove order
    private void removeOrder(Order order) {
        orderMap.remove(order.getId());
        queueMap.get(order.getKey()).remove(order);
        sharesMap.put(order.getKey(), sharesMap.get(order.getKey()) - order.getShares());
        if (sharesMap.get(order.getKey()) == 0) {
            PriorityQueue<Double> samebook = getBook(order, "same");
            samebook.remove(order.getPrice());
            queueMap.remove(order.getKey());
        }
    }

    // method to reduce shares of an order from data structures
    private void fillShares(Order order, double shares) {
        // reduce order shares
        order.reduceShares(shares);
        // identify if order to fill is existing order to further remove it from corresponding data structures
        if (orderMap.containsKey(order.getId())) {
            sharesMap.put(order.getKey(), sharesMap.get(order.getKey()) - shares);
            // remove order if remaining shares is 0
            if (order.getShares() == 0) {
                removeOrder(order);
            }
        }
    }

    // get the same/opposite side book per order's side
    private PriorityQueue<Double> getBook(Order order, String SAME_OR_OPPOSITE) {
        if (SAME_OR_OPPOSITE.equalsIgnoreCase("same")) {
            return order.getIsBid() ? ask : bid;
        } else if (SAME_OR_OPPOSITE.equalsIgnoreCase("opposite")) {
            return order.getIsBid() ? bid : ask;
        } else { System.out.println("ERROR - input 'same' or 'opposite'.");
            return null;
        }
    }
}