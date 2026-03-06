import java.util.*;

public class WeeklyAssigment1 {
//Real-Time Analytics Dashboard for Website
//Traffic
    // pageUrl -> visit count
    static HashMap<String, Integer> pageViews = new HashMap<>();

    // pageUrl -> set of unique users
    static HashMap<String, HashSet<String>> uniqueVisitors = new HashMap<>();

    // traffic source -> count
    static HashMap<String, Integer> trafficSources = new HashMap<>();


    // Process incoming page view event
    public static void processEvent(String url, String userId, String source) {

        // Count page views
        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        // Track unique visitors
        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        // Track traffic source
        trafficSources.put(source, trafficSources.getOrDefault(source, 0) + 1);
    }


    // Display dashboard
    public static void getDashboard() {

        System.out.println("\n--- REAL TIME DASHBOARD ---\n");

        // Sort pages by views
        List<Map.Entry<String, Integer>> list = new ArrayList<>(pageViews.entrySet());
        list.sort((a, b) -> b.getValue() - a.getValue());

        System.out.println("Top Pages:");

        int count = 0;

        for (Map.Entry<String, Integer> entry : list) {

            String page = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(page).size();

            count++;

            System.out.println(count + ". " + page +
                    " - " + views + " views (" + unique + " unique)");

            if (count == 10) break;
        }

        System.out.println("\nTraffic Sources:");

        int total = 0;

        for (int v : trafficSources.values()) {
            total += v;
        }

        for (String source : trafficSources.keySet()) {

            int countSource = trafficSources.get(source);
            double percent = (countSource * 100.0) / total;

            System.out.println(source + ": " + String.format("%.2f", percent) + "%");
        }
    }


    public static void main(String[] args) throws Exception {

        processEvent("/article/breaking-news", "user_123", "Google");
        processEvent("/article/breaking-news", "user_456", "Facebook");
        processEvent("/sports/championship", "user_789", "Direct");
        processEvent("/sports/championship", "user_111", "Google");
        processEvent("/sports/championship", "user_222", "Google");
        processEvent("/article/breaking-news", "user_123", "Google");

        getDashboard();
    }
}