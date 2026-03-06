import java.util.*;

class DNSEntry {
    String ip;
    long expiryTime;

    DNSEntry(String ip, long ttlSeconds) {
        this.ip = ip;
        this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class WeeklyAssigment1 {

    static int capacity = 3;
    static int hits = 0;
    static int misses = 0;

    static LinkedHashMap<String, DNSEntry> cache = new LinkedHashMap<String, DNSEntry>(capacity, 0.75f, true) {
        protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
            return size() > capacity;
        }
    };

    // Resolve domain
    public static String resolve(String domain) {

        if (cache.containsKey(domain)) {
            DNSEntry entry = cache.get(domain);

            if (!entry.isExpired()) {
                hits++;
                System.out.println(domain + " → Cache HIT → " + entry.ip);
                return entry.ip;
            } else {
                cache.remove(domain);
                System.out.println(domain + " → Cache EXPIRED");
            }
        }

        misses++;

        String ip = queryUpstreamDNS(domain);
        cache.put(domain, new DNSEntry(ip, 5));

        System.out.println(domain + " → Cache MISS → Query upstream → " + ip);
        return ip;
    }

    // Simulated DNS lookup
    public static String queryUpstreamDNS(String domain) {
        Random rand = new Random();
        return "172.217.14." + rand.nextInt(255);
    }

    // Cache statistics
    public static void getCacheStats() {
        int total = hits + misses;
        double hitRate = (total == 0) ? 0 : ((double) hits / total) * 100;

        System.out.println("Hits: " + hits);
        System.out.println("Misses: " + misses);
        System.out.println("Hit Rate: " + hitRate + "%");
    }

    public static void main(String[] args) throws Exception {

        resolve("google.com");
        resolve("google.com");

        Thread.sleep(6000); // wait for TTL to expire

        resolve("google.com");

        getCacheStats();
    }
}