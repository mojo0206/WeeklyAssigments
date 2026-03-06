import java.util.*;
//Autocomplete System for Search Engine
public class WeeklyAssigment1 {

    // query -> frequency
    static HashMap<String, Integer> queryFrequency = new HashMap<>();

    // add or update search query
    public static void updateFrequency(String query) {
        queryFrequency.put(query, queryFrequency.getOrDefault(query, 0) + 1);
        System.out.println("Updated: \"" + query + "\" → Frequency: " + queryFrequency.get(query));
    }

    // return top 10 suggestions for prefix
    public static void search(String prefix) {import java.util.*;

        public class WeeklyAssignment1 {

            // query -> frequency
            static HashMap<String, Integer> queryFrequency = new HashMap<>();

            // add or update search query
            public static void updateFrequency(String query) {
                queryFrequency.put(query, queryFrequency.getOrDefault(query, 0) + 1);
                System.out.println("Updated: \"" + query + "\" → Frequency: " + queryFrequency.get(query));
            }

            // return top 10 suggestions for prefix
            public static void search(String prefix) {

                List<Map.Entry<String, Integer>> matches = new ArrayList<>();

                for (Map.Entry<String, Integer> entry : queryFrequency.entrySet()) {
                    if (entry.getKey().startsWith(prefix)) {
                        matches.add(entry);
                    }
                }

                // sort by frequency (descending)
                matches.sort((a, b) -> b.getValue() - a.getValue());

                System.out.println("\nSuggestions for \"" + prefix + "\":");

                int count = 0;

                for (Map.Entry<String, Integer> entry : matches) {
                    System.out.println((count + 1) + ". " + entry.getKey() +
                            " (" + entry.getValue() + " searches)");
                    count++;

                    if (count == 10) break;
                }

                if (count == 0) {
                    System.out.println("No suggestions found.");
                }
            }

            public static void main(String[] args) {

                updateFrequency("java tutorial");
                updateFrequency("javascript");
                updateFrequency("java download");
                updateFrequency("java tutorial");
                updateFrequency("java 21 features");
                updateFrequency("java 21 features");
                updateFrequency("java 21 features");

                search("jav");
            }
        }

        List<Map.Entry<String, Integer>> matches = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : queryFrequency.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                matches.add(entry);
            }
        }

        // sort by frequency (descending)
        matches.sort((a, b) -> b.getValue() - a.getValue());

        System.out.println("\nSuggestions for \"" + prefix + "\":");

        int count = 0;

        for (Map.Entry<String, Integer> entry : matches) {
            System.out.println((count + 1) + ". " + entry.getKey() +
                    " (" + entry.getValue() + " searches)");
            count++;

            if (count == 10) break;
        }

        if (count == 0) {
            System.out.println("No suggestions found.");
        }
    }

    public static void main(String[] args) {

        updateFrequency("java tutorial");
        updateFrequency("javascript");
        updateFrequency("java download");
        updateFrequency("java tutorial");
        updateFrequency("java 21 features");
        updateFrequency("java 21 features");
        updateFrequency("java 21 features");

        search("jav");
    }
}