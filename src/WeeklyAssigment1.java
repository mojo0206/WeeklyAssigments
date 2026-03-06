import java.util.*;

class TokenBucket {
    int tokens;
    int maxTokens;
    long lastRefillTime;
    int refillRate; // tokens per hour

    TokenBucket(int maxTokens) {
        this.maxTokens = maxTokens;
        this.tokens = maxTokens;
        this.refillRate = maxTokens;
        this.lastRefillTime = System.currentTimeMillis();
    }

    // refill tokens every hour
    void refill() {
        long currentTime = System.currentTimeMillis();
        long diff = currentTime - lastRefillTime;

        if (diff >= 3600000) { // 1 hour
            tokens = maxTokens;
            lastRefillTime = currentTime;
        }
    }
    boolean allowRequest() {
        refill();

        if (tokens > 0) {
            tokens--;
            return true;
        }

        return false;
    }

    int remaining() {
        refill();
        return tokens;
    }
}

public class WeeklyAssigment1 {

    static HashMap<String, TokenBucket> clients = new HashMap<>();
    static int LIMIT = 1000;

    public static void checkRateLimit(String clientId) {

        if (!clients.containsKey(clientId)) {
            clients.put(clientId, new TokenBucket(LIMIT));
        }

        TokenBucket bucket = clients.get(clientId);

        if (bucket.allowRequest()) {
            System.out.println("Allowed (" + bucket.remaining() + " requests remaining)");
        } else {
            System.out.println("Denied (0 requests remaining, try again later)");
        }
    }

    public static void getRateLimitStatus(String clientId) {

        if (!clients.containsKey(clientId)) {
            System.out.println("Client not found");
            return;
        }

        TokenBucket bucket = clients.get(clientId);

        int used = LIMIT - bucket.remaining();

        System.out.println("{used: " + used +
                ", limit: " + LIMIT +
                ", remaining: " + bucket.remaining() + "}");
    }

    public static void main(String[] args) {

        checkRateLimit("abc123");
        checkRateLimit("abc123");
        checkRateLimit("abc123");

        getRateLimitStatus("abc123");
    }
}