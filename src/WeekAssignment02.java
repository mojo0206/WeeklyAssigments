import java.time.LocalTime;
import java.util.ArrayList;

class Transaction {
    String id;
    double fee;
    LocalTime timestamp;

    public Transaction(String id, double fee, String ts) {
        this.id = id;
        this.fee = fee;
        this.timestamp = LocalTime.parse(ts); // HH:mm format
    }

    @Override
    public String toString() {
        return id + ":" + fee + "@" + timestamp;
    }
}

public class WeekAssignment02 {

    // Bubble Sort for fees (stable)
    public static void bubbleSortFees(ArrayList<Transaction> txs) {
        int n = txs.size();
        int swaps = 0, passes = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (txs.get(j).fee > txs.get(j + 1).fee) {
                    Transaction temp = txs.get(j);
                    txs.set(j, txs.get(j + 1));
                    txs.set(j + 1, temp);
                    swaps++;
                    swapped = true;
                }
            }
            passes++;
            if (!swapped) break; // early termination
        }
        System.out.println("BubbleSort completed: " + passes + " passes, " + swaps + " swaps");
    }

    // Insertion Sort for fee + timestamp (stable)
    public static void insertionSortFeeTimestamp(ArrayList<Transaction> txs) {
        int n = txs.size();
        for (int i = 1; i < n; i++) {
            Transaction key = txs.get(i);
            int j = i - 1;

            while (j >= 0 && (txs.get(j).fee > key.fee ||
                    (txs.get(j).fee == key.fee && txs.get(j).timestamp.isAfter(key.timestamp)))) {
                txs.set(j + 1, txs.get(j)); // shift right
                j--;
            }
            txs.set(j + 1, key); // insert key
        }
    }

    // Detect high-fee outliers (> threshold)
    public static ArrayList<Transaction> detectHighFeeOutliers(ArrayList<Transaction> txs, double threshold) {
        ArrayList<Transaction> outliers = new ArrayList<>();
        for (Transaction t : txs) {
            if (t.fee > threshold) outliers.add(t);
        }
        return outliers;
    }

    // Main sorting handler
    public static void sortTransactions(ArrayList<Transaction> txs) {
        int size = txs.size();
        if (size <= 100) {
            bubbleSortFees(txs);
            System.out.println("Sorted transactions (Bubble Sort by fee): " + txs);
        } else if (size <= 1000) {
            insertionSortFeeTimestamp(txs);
            System.out.println("Sorted transactions (Insertion Sort by fee+timestamp): " + txs);
        } else {
            System.out.println("Batch too large for simple sorting. Use efficient sort (e.g., MergeSort).");
        }

        ArrayList<Transaction> outliers = detectHighFeeOutliers(txs, 50.0);
        if (outliers.isEmpty()) {
            System.out.println("High-fee outliers: none");
        } else {
            System.out.println("High-fee outliers: " + outliers);
        }
    }

    // Sample usage
    public static void main(String[] args) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("id1", 10.5, "10:00"));
        transactions.add(new Transaction("id2", 25.0, "09:30"));
        transactions.add(new Transaction("id3", 5.0, "10:15"));
        transactions.add(new Transaction("id4", 55.0, "11:00")); // outlier

        System.out.println("Original transactions: " + transactions + "\n");
        sortTransactions(transactions);
    }
}