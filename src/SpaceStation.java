public class SpaceStation {
    private static final int MAX_NITROGEN = 700;
    private static final int MAX_QUANTUM = 650;
    private static final int MAX_VEHICLE_SPACES = 5;

    private int currentSpaceships = 0;

    private Object restLock = new Object();
    private Object criticalLock = new Object();
    private Object concurrentLock = new Object();

    private volatile int currentNAmount;
    private volatile int currentQAmount;

    public SpaceStation(int currentNAmount, int currentQAmount){
        this.currentNAmount = (currentNAmount > MAX_NITROGEN) ? MAX_NITROGEN : currentNAmount;
        this.currentQAmount = (currentQAmount > MAX_QUANTUM) ? MAX_QUANTUM : currentQAmount;

        System.out.println("--- SPACE STATION INFORMATION ---");
        System.out.printf("Space station has the capacity N = %d and Q = %d", currentNAmount, currentQAmount);
        System.out.println();
    }

    public CompositeFuel getFuel(CompositeFuel requestedFuel, SpaceVehicle spaceVehicle) throws InterruptedException{
        synchronized (concurrentLock){
            boolean enoughFuel = requestedFuel.getAmountN() <= currentNAmount && requestedFuel.getAmountQ() <= currentQAmount;
            while(currentSpaceships >= MAX_VEHICLE_SPACES || !enoughFuel){
                concurrentLock.wait();
                enoughFuel = requestedFuel.getAmountN() <= currentNAmount && requestedFuel.getAmountQ() <= currentQAmount;
            }
            currentSpaceships++;

            System.out.printf("Spaceship #%d wants to refuel\n", spaceVehicle.getId());
            System.out.printf("Capacity of the space station\nN = %d\tQ = %d\n", currentNAmount, currentQAmount);
            System.out.println();

            //The time it takes to refuel
            Thread.sleep(2000);

            currentNAmount -= requestedFuel.getAmountN();
            currentQAmount -= requestedFuel.getAmountQ();

            System.out.printf("Spaceship #%d succeeded in refueling!\n", spaceVehicle.getId());
            System.out.printf("Capacity of the space station is now\nN = %d\tQ = %d", currentNAmount, currentQAmount);
            System.out.println();
        }

        synchronized (concurrentLock){
            currentSpaceships--;
            concurrentLock.notify();
        }

        return new CompositeFuel(requestedFuel.getAmountN(), requestedFuel.getAmountQ());
    }

    public void refillFuelTank(CompositeFuel deliveredFuel, FuelShip fuelShip) throws InterruptedException{
        synchronized (concurrentLock){
            while(currentSpaceships >= MAX_VEHICLE_SPACES){
                concurrentLock.wait();
            }
            currentSpaceships++;
        }

        int restockedN = deliveredFuel.getAmountN();
        int restockedQ = deliveredFuel.getAmountQ();

        synchronized (concurrentLock){
            boolean fuelWithinLimits = restockedN + currentNAmount <= MAX_NITROGEN &&
                    restockedQ + currentQAmount <= MAX_QUANTUM;
            while(!fuelWithinLimits){
                concurrentLock.wait();
                fuelWithinLimits = restockedN + currentNAmount <= MAX_NITROGEN &&
                        restockedQ + currentQAmount <= MAX_QUANTUM;
            }
            this.currentNAmount += restockedN;
            this.currentQAmount += restockedQ;
            deliveredFuel.setAmountN(0);
            deliveredFuel.setAmountQ(0);
            System.out.printf("Fuel ship refueled the space station.\n", fuelShip.getId());
            System.out.printf("The space stations current capacity is now\nN = %d\t Q = %d", currentNAmount, currentQAmount);
            System.out.println();
            currentSpaceships--;
            concurrentLock.notify();
        }
    }
}
