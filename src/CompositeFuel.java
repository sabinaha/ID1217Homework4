public class CompositeFuel {
    private int amountQ;
    private int amountN;

    public CompositeFuel(int amountN, int amountQ){
        this.amountN = amountN;
        this.amountQ = amountQ;
    }

    public int getAmountQ(){
        return this.amountQ;
    }

    public int getAmountN(){
        return this.amountN;
    }

    public void setAmountN(int amountN){
        this.amountN = amountN;
    }

    public void setAmountQ(int amountQ){
        this.amountQ = amountQ;
    }
}
