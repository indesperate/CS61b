package Discussion6.ParkingLot;

public abstract class ParkingLot {
    /** using priority queue */
    public ParkingLot(int[] compactDistance, int[] regularDistance, int[] handicappedDistance) {

    }
    public abstract boolean park(Car car);
    public abstract void leave(Car car);
}
