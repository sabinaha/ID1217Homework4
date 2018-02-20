public class Spaceship extends SpaceVehicle implements Runnable{
    public Spaceship(CompositeFuel initialFuel, SpaceStation spaceStation){
        super(initialFuel, spaceStation);
        this.spaceStation = spaceStation;
    }

    @Override
    public void run(){
        while(true){
            refuel();
            try{
                //Sleeps for 5-10 seconds
                long sleep = (long) (Math.random() * 5000) + 5000;
                System.out.printf("Spaceship #%d slept for %.02f seconds", getId(), sleep/1000.0);
                Thread.sleep(sleep);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
