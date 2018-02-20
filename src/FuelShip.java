public class FuelShip extends SpaceVehicle implements Runnable{

    private static final int REFILL_N_CARGO = 400;
    private static final int REFILL_Q_CARGO = 400;

    private CompositeFuel refillFuelTank;

    public FuelShip(CompositeFuel initialFuel, CompositeFuel refillFuelTank, SpaceStation spaceStation){
        super(initialFuel, spaceStation);
        this.refillFuelTank = refillFuelTank;
    }

    public void refillSpaceStation(){
        try{
            this.spaceStation.refillFuelTank(refillFuelTank, this);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(true){
            if(refillFuelTank.getAmountN() > 0 || refillFuelTank.getAmountQ() > 0){
                System.out.println("--- FUEL SHIP TRYING TO REFILL SPACE STATION ---");
                refillSpaceStation();
            }
            else{
                System.out.println("--- REFILLING FUEL SHIPS SUPPLY TANK ---");
                refillFuelTank.setAmountN(REFILL_N_CARGO);
                refillFuelTank.setAmountQ(REFILL_Q_CARGO);
                refillSpaceStation();
            }
            refuel();
            try{
                long sleep = (long) (Math.random() * 5000) + 5000;
                System.out.printf("Fuel ship #%d slept for %.02f seconds\n", getId(), sleep/1000.0);
                Thread.sleep(sleep);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}