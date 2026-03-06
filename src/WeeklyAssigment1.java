import java.util.*;
//Two-Sum Problem Variants for Financial
//Transactions
class Transaction {
    int id;
    int amount;
    String merchant;
    String account;
    long timestamp; // milliseconds

    Transaction(int id, int amount, String merchant, String account, long timestamp) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.account = account;
        this.timestamp = timestamp;
    }
}

public class WeeklyAssigment1 {

    // Classic Two-Sum
    public static List<int[]> findTwoSum(List<Transaction> transactions, int target) {
        List<int[]> result = new ArrayList<>();
        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {
            int complement = target - t.amount;
            if (map.containsKey(complement)) {
                result.add(new int[]{map.get(complement).id, t.id});
            }
            map.put(t.amount, t);
        }
        return result;
    }

    // Two-Sum within 1 hour (3600 * 1000 ms)
    public static List<int[]> findTwoSumTimeWindow(List<Transaction> transactions, int target) {
        List<int[]> result = new ArrayList<>();
        transactions.sort(Comparator.comparingLong(t -> t.timestamp));

        for (int i = 0; i < transactions.size(); i++) {
            Transaction t1 = transactions.get(i);
            for (int j = i + 1; j < transactions.size(); j++) {
                Transaction t2 = transactions.get(j);
                if (t2.timestamp - t1.timestamp > 3600000) break;
                if (t1.amount + t2.amount == target) {
                    result.add(new int[]{t1.id, t2.id});
                }
            }
        }
        return result;
    }

    // K-Sum (recursive)
    public static List<List<Integer>> findKSum(List<Transaction> transactions, int target, int k) {
        List<List<Integer>> res = new ArrayList<>();
        kSumHelper(transactions, target, k, 0, new ArrayList<>(), res);
        return res;
    }

    private static void kSumHelper(List<Transaction> transactions, int target, int k,
                                   int start, List<Integer> path, List<List<Integer>> res) {
        if (k == 2) { // two-sum
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int i = start; i < transactions.size(); i++) {
                int complement = target - transactions.get(i).amount;
                if (map.containsKey(complement)) {
                    List<Integer> temp = new ArrayList<>(path);
                    temp.add(map.get(complement));
                    temp.add(transactions.get(i).id);
                    res.add(temp);
                }
                map.put(transactions.get(i).amount, transactions.get(i).id);
            }
        } else { // k > 2
            for (int i = start; i < transactions.size(); i++) {
                path.add(transactions.get(i).id);
                kSumHelper(transactions, target - transactions.get(i).amount, k - 1,
                        i + 1, path, res);
                path.remove(path.size() - 1);
            }
        }
    }

    // Duplicate detection: same amount, same merchant, different accounts
    public static List<String> detectDuplicates(List<Transaction> transactions) {
        List<String> duplicates = new ArrayList<>();
        HashMap<String, HashMap<Integer, HashSet<String>>> map = new HashMap<>();

        for (Transaction t : transactions) {
            map.putIfAbsent(t.merchant, new HashMap<>());
            HashMap<Integer, HashSet<String>> amountMap = map.get(t.merchant);

            amountMap.putIfAbsent(t.amount, new HashSet<>());
            HashSet<String> accounts = amountMap.get(t.amount);

            if (!accounts.isEmpty() && !accounts.contains(t.account)) {
                duplicates.add("Duplicate: merchant=" + t.merchant + ", amount=" + t.amount +
                        ", accounts=" + accounts + " & " + t.account);
            }

            accounts.add(t.account);
        }

        return duplicates;
    }

    public static void main(String[] args) {

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1, 500, "Store A", "acc1", 1000));
        transactions.add(new Transaction(2, 300, "Store B", "acc2", 2000));
        transactions.add(new Transaction(3, 200, "Store C", "acc3", 2500));
        transactions.add(new Transaction(4, 500, "Store A", "acc2", 3000));

        System.out.println("Two-Sum (target=500): " + findTwoSum(transactions, 500));
        System.out.println("Two-Sum Time Window (target=500): " + findTwoSumTimeWindow(transactions, 500));
        System.out.println("K-Sum (k=3, target=1000): " + findKSum(transactions, 1000, 3));
        System.out.println("Duplicate Detection: " + detectDuplicates(transactions));
    }
}