package Discussion6.ParkingLot;

public interface Car {
    boolean isCompact();
    boolean isHandicapped();
    boolean park(ParkingLot parkingLot);
    void leaveSpot(ParkingLot parkingLot);
}
