public class Main {
    public static void main(String[] args) {

        int numOfSpaceships = 10;

        int initialSpaceStationN = 400;
        int initialSpaceStationQ = 400;

        int initialSpaceshipN = 50;
        int initialSpaceshipQ = 50;

        int refillFuelN = 400;
        int refillFuelQ = 400;

        //Creating the space station with initial fuel
        SpaceStation spaceStation = new SpaceStation(initialSpaceStationN, initialSpaceStationQ);

        //Create a few spaceships and initialize them.
        //Also creating a fuel ship
        Spaceship[] spaceships = new Spaceship[numOfSpaceships];
        for(int i = 0; i < spaceships.length; i++){
            spaceships[i] = new Spaceship(new CompositeFuel(initialSpaceshipN,initialSpaceshipQ), spaceStation);
        }
        FuelShip fuelShip = new FuelShip(new CompositeFuel(initialSpaceshipN, initialSpaceshipQ), new CompositeFuel(refillFuelN, refillFuelQ), spaceStation);

        //Create threads for them and starting
        Thread[] spaceshipThreads = new Thread[spaceships.length];
        for(int i = 0; i < spaceshipThreads.length; i++){
            spaceshipThreads[i] = new Thread(spaceships[i]);
            spaceshipThreads[i].start();
        }
        Thread fuelShipThread = new Thread(fuelShip);
        fuelShipThread.start();
    }
}
