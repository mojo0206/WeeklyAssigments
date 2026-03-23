import java.util.HashMap;
import java.util.ArrayList;
//Social Media Username
public class WeeklyAssigment1 {

    static HashMap<String, Integer> usernameMap = new HashMap<>();
    static HashMap<String, Integer> attemptMap = new HashMap<>();

    // Check availability
    public static boolean checkAvailability(String username) {

        if (attemptMap.containsKey(username)) {
            attemptMap.put(username, attemptMap.get(username) + 1);
        } else {
            attemptMap.put(username, 1);
        }

        if (usernameMap.containsKey(username)) {
            return false;
        }

        return true;
    }

    // Register username
    public static void register(String username, int userId) {
        usernameMap.put(username, userId);
    }

    // Suggest alternatives
    public static ArrayList<String> suggestAlternatives(String username) {

        ArrayList<String> list = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            String suggestion = username + i;

            if (!usernameMap.containsKey(suggestion)) {
                list.add(suggestion);
            }
        }

        return list;
    }

    // Most attempted username
    public static String getMostAttempted() {

        String name = "";
        int max = 0;

        for (String key : attemptMap.keySet()) {
            if (attemptMap.get(key) > max) {
                max = attemptMap.get(key);
                name = key;
            }
        }

        return name + " (" + max + " attempts)";
    }

    public static void main(String[] args) {

        register("john_doe", 1);

        System.out.println(checkAvailability("john_doe"));
        System.out.println(checkAvailability("jane_smith"));

        System.out.println(suggestAlternatives("john_doe"));

        checkAvailability("admin");
        checkAvailability("admin");
        checkAvailability("admin");

        System.out.println(getMostAttempted());
    }
}