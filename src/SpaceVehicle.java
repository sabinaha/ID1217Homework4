public abstract class SpaceVehicle {
    private static int id = 0;
    private int myId;
    private CompositeFuel myFuel;
    protected SpaceStation spaceStation;

    public SpaceVehicle(CompositeFuel myFuel, SpaceStation spaceStation){
        this.myId = id++;
        this.myFuel = myFuel;
        this.spaceStation = spaceStation;
    }

    public void refuel(){
        CompositeFuel requestedFuel = new CompositeFuel(50, 50);
        CompositeFuel spaceStationFuel = null;

        System.out.printf("--- SPACE VEHICLE INFORMATION ---\n");
        System.out.printf("BEFORE REFILLING\t#%d had N = %d \tQ = %d\n", myId, myFuel.getAmountN(), myFuel.getAmountQ());
        System.out.println();

        try{
            spaceStationFuel = spaceStation.getFuel(requestedFuel, this);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        if(spaceStationFuel == null){
            System.out.println("NULL fuel, something went wrong somewhere!");
        }
        else{
            myFuel.setAmountN(myFuel.getAmountN() + spaceStationFuel.getAmountN());
            myFuel.setAmountQ(myFuel.getAmountQ() + spaceStationFuel.getAmountQ());

            System.out.printf("AFTER REFILLING\t#%d had N = %d\tQ = %d", myId, myFuel.getAmountN(), myFuel.getAmountQ());
            System.out.println();
        }
    }

    protected  CompositeFuel getMyFuel(){
        return this.myFuel;
    }

    public int getId(){
        return this.myId;
    }
}
