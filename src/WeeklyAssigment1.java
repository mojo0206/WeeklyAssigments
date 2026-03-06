import java.util.HashMap;
import java.util.LinkedList;
//FlashSaleInventory
public class WeeklyAssigment1  {

    static HashMap<String, Integer> stockMap = new HashMap<>();
    static HashMap<String, LinkedList<Integer>> waitingList = new HashMap<>();

    // Add product with stock
    public static void addProduct(String productId, int stock) {
        stockMap.put(productId, stock);
        waitingList.put(productId, new LinkedList<>());
    }

    // Check stock
    public static void checkStock(String productId) {
        int stock = stockMap.getOrDefault(productId, 0);
        System.out.println(productId + " → " + stock + " units available");
    }

    // Purchase item (synchronized to prevent overselling)
    public synchronized static void purchaseItem(String productId, int userId) {

        int stock = stockMap.getOrDefault(productId, 0);

        if (stock > 0) {
            stockMap.put(productId, stock - 1);
            System.out.println("User " + userId + " purchase SUCCESS, remaining: " + (stock - 1));
        }
        else {
            LinkedList<Integer> queue = waitingList.get(productId);
            queue.add(userId);
            System.out.println("Stock finished. User " + userId +
                    " added to waiting list. Position: " + queue.size());
        }
    }

    public static void main(String[] args) {

        addProduct("IPHONE15_256GB", 3);

        checkStock("IPHONE15_256GB");

        purchaseItem("IPHONE15_256GB", 101);
        purchaseItem("IPHONE15_256GB", 102);
        purchaseItem("IPHONE15_256GB", 103);

        purchaseItem("IPHONE15_256GB", 104);
        purchaseItem("IPHONE15_256GB", 105);
    }
}