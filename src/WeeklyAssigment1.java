import java.util.*;

public class WeeklyAssigment1 {
    //Plagiarism Detection System
    // n-gram size
    static int N = 5;

    // ngram -> set of document IDs
    static HashMap<String, HashSet<String>> ngramIndex = new HashMap<>();

    // store document text
    static HashMap<String, String> documents = new HashMap<>();


    // Break text into n-grams
    public static List<String> generateNGrams(String text) {
        String[] words = text.toLowerCase().split("\\s+");
        List<String> grams = new ArrayList<>();

        for (int i = 0; i <= words.length - N; i++) {
            String gram = "";
            for (int j = 0; j < N; j++) {
                gram += words[i + j] + " ";
            }
            grams.add(gram.trim());
        }

        return grams;
    }


    // Add document to database
    public static void addDocument(String docId, String text) {

        documents.put(docId, text);

        List<String> grams = generateNGrams(text);

        for (String g : grams) {

            if (!ngramIndex.containsKey(g)) {
                ngramIndex.put(g, new HashSet<>());
            }

            ngramIndex.get(g).add(docId);
        }
    }


    // Analyze document for plagiarism
    public static void analyzeDocument(String docId) {

        String text = documents.get(docId);

        List<String> grams = generateNGrams(text);

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String g : grams) {

            if (ngramIndex.containsKey(g)) {

                for (String otherDoc : ngramIndex.get(g)) {

                    if (!otherDoc.equals(docId)) {

                        matchCount.put(otherDoc,
                                matchCount.getOrDefault(otherDoc, 0) + 1);
                    }
                }
            }
        }

        System.out.println("Analyzing: " + docId);
        System.out.println("Extracted " + grams.size() + " n-grams\n");

        for (String doc : matchCount.keySet()) {

            int matches = matchCount.get(doc);
            double similarity = (matches * 100.0) / grams.size();

            System.out.println("Found " + matches + " matching n-grams with " + doc);
            System.out.println("Similarity: " + String.format("%.2f", similarity) + "%");

            if (similarity > 50) {
                System.out.println("PLAGIARISM DETECTED\n");
            } else if (similarity > 10) {
                System.out.println("Suspicious\n");
            } else {
                System.out.println("Low similarity\n");
            }
        }
    }


    public static void main(String[] args) {

        String doc1 = "data structures and algorithms are important for computer science students learning programming concepts";
        String doc2 = "data structures and algorithms are very important subjects for students studying computer science";
        String doc3 = "football players train every day to improve their speed strength and teamwork skills";

        addDocument("essay_089.txt", doc1);
        addDocument("essay_092.txt", doc2);
        addDocument("essay_123.txt", doc3);

        analyzeDocument("essay_092.txt");
    }
}