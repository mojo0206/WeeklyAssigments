import java.util.*;

class ParkingSpot {
    String licensePlate;
    long entryTime; // in milliseconds
    boolean occupied;

    ParkingSpot() {
        licensePlate = null;
        occupied = false;
        entryTime = 0;
    }
}

public class WeeklyAssigment1 {

    static final int SIZE = 500;
    static ParkingSpot[] spots = new ParkingSpot[SIZE];

    static int totalProbes = 0;
    static int totalParked = 0;

    static {
        for (int i = 0; i < SIZE; i++) {
            spots[i] = new ParkingSpot();
        }
    }

    // Simple hash function: sum of ASCII % SIZE
    public static int hash(String licensePlate) {
        int sum = 0;
        for (char c : licensePlate.toCharArray()) sum += c;
        return sum % SIZE;
    }

    // Park a vehicle using linear probing
    public static void parkVehicle(String licensePlate) {
        int idx = hash(licensePlate);
        int probes = 0;

        while (spots[idx].occupied) {
            probes++;
            idx = (idx + 1) % SIZE;
        }

        spots[idx].occupied = true;
        spots[idx].licensePlate = licensePlate;
        spots[idx].entryTime = System.currentTimeMillis();

        totalProbes += probes;
        totalParked++;

        System.out.println("Vehicle " + licensePlate +
                " parked at spot #" + idx + " (" + probes + " probes)");
    }

    // Exit a vehicle and calculate fee
    public static void exitVehicle(String licensePlate) {

        for (int i = 0; i < SIZE; i++) {
            if (spots[i].occupied && spots[i].licensePlate.equals(licensePlate)) {

                long durationMs = System.currentTimeMillis() - spots[i].entryTime;
                double hours = durationMs / (1000.0 * 60 * 60);
                double fee = hours * 5; // $5 per hour

                spots[i].occupied = false;
                spots[i].licensePlate = null;
                spots[i].entryTime = 0;

                totalParked--;

                System.out.println("Vehicle " + licensePlate +
                        " exited from spot #" + i +
                        ", Duration: " + String.format("%.2f", hours) +
                        "h, Fee: $" + String.format("%.2f", fee));

                return;
            }
        }

        System.out.println("Vehicle " + licensePlate + " not found.");
    }

    // Generate statistics
    public static void getStatistics() {
        int occupiedCount = 0;
        for (ParkingSpot spot : spots) if (spot.occupied) occupiedCount++;

        double occupancy = (occupiedCount * 100.0) / SIZE;
        double avgProbes = totalParked == 0 ? 0 : (totalProbes * 1.0 / totalParked);

        System.out.println("Occupancy: " + String.format("%.2f", occupancy) + "%" +
                ", Avg Probes: " + String.format("%.2f", avgProbes));
    }

    public static void main(String[] args) throws InterruptedException {

        parkVehicle("ABC-1234");
        parkVehicle("ABC-1235");
        parkVehicle("XYZ-9999");

        Thread.sleep(2000); // simulate 2 seconds parked

        exitVehicle("ABC-1234");

        getStatistics();
    }
}