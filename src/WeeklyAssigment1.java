import java.util.*;
//Multi-Level Cache System with Hash Tables
class VideoData {
    String videoId;
    String content; // Simplified for demo
    VideoData(String videoId, String content) {
        this.videoId = videoId;
        this.content = content;
    }
}

public class WeeklyAssigment1 {

    // L1: in-memory cache (LinkedHashMap with access-order)
    static final int L1_CAPACITY = 10000;
    static LinkedHashMap<String, VideoData> L1Cache = new LinkedHashMap<>(L1_CAPACITY, 0.75f, true) {
        protected boolean removeEldestEntry(Map.Entry<String, VideoData> eldest) {
            return size() > L1_CAPACITY;
        }
    };

    // L2: simulated SSD cache (HashMap) with access counts
    static final int L2_CAPACITY = 100000;
    static HashMap<String, VideoData> L2Cache = new HashMap<>();
    static HashMap<String, Integer> accessCount = new HashMap<>();

    // Hit counters
    static int L1Hits = 0, L2Hits = 0, L3Hits = 0;
    static int totalRequests = 0;

    // Access a video
    public static VideoData getVideo(String videoId) {
        totalRequests++;

        // L1 lookup
        if (L1Cache.containsKey(videoId)) {
            L1Hits++;
            System.out.println(videoId + " → L1 Cache HIT (0.5ms)");
            return L1Cache.get(videoId);
        }

        // L2 lookup
        if (L2Cache.containsKey(videoId)) {
            L2Hits++;
            System.out.println(videoId + " → L1 MISS, L2 HIT (5ms)");
            promoteToL1(videoId);
            return L2Cache.get(videoId);
        }

        // L3 (Database) lookup
        L3Hits++;
        System.out.println(videoId + " → L1 MISS, L2 MISS, L3 HIT (150ms)");
        VideoData data = queryDatabase(videoId);
        addToL2(videoId, data);
        return data;
    }

    // Simulate database fetch
    private static VideoData queryDatabase(String videoId) {
        return new VideoData(videoId, "Content of " + videoId);
    }

    // Promote L2 video to L1 based on access
    private static void promoteToL1(String videoId) {
        VideoData data = L2Cache.get(videoId);
        L1Cache.put(videoId, data);
        accessCount.put(videoId, accessCount.getOrDefault(videoId, 0) + 1);
    }

    // Add video to L2 cache
    private static void addToL2(String videoId, VideoData data) {
        if (L2Cache.size() >= L2_CAPACITY) {
            // simple eviction: remove random entry
            Iterator<String> it = L2Cache.keySet().iterator();
            if (it.hasNext()) {
                String key = it.next();
                it.remove();
                accessCount.remove(key);
            }
        }
        L2Cache.put(videoId, data);
        accessCount.put(videoId, 1);
    }

    // Display cache statistics
    public static void getStatistics() {
        double L1HitRate = totalRequests == 0 ? 0 : (L1Hits * 100.0 / totalRequests);
        double L2HitRate = totalRequests == 0 ? 0 : (L2Hits * 100.0 / totalRequests);
        double L3HitRate = totalRequests == 0 ? 0 : (L3Hits * 100.0 / totalRequests);

        double avgTime = (L1Hits * 0.5 + L2Hits * 5 + L3Hits * 150) / (double) totalRequests;

        System.out.println("\n--- Cache Statistics ---");
        System.out.println("L1 Hit Rate: " + String.format("%.2f", L1HitRate) + "%, Avg Time: 0.5ms");
        System.out.println("L2 Hit Rate: " + String.format("%.2f", L2HitRate) + "%, Avg Time: 5ms");
        System.out.println("L3 Hit Rate: " + String.format("%.2f", L3HitRate) + "%, Avg Time: 150ms");
        System.out.println("Overall Avg Time: " + String.format("%.2f", avgTime) + "ms");
    }

    public static void main(String[] args) {

        getVideo("video_123"); // L1 MISS, L2 MISS, L3 HIT
        getVideo("video_123"); // L1 HIT
        getVideo("video_999"); // L1 MISS, L2 MISS, L3 HIT
        getVideo("video_123"); // L1 HIT

        getStatistics();
    }
}