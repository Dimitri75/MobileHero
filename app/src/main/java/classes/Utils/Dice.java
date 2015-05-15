package classes.Utils;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Dice {
    private int numberDices;
    private int numberSides;

    public Dice(int numberDices, int numberSides){
        this.numberDices = numberDices;
        this.numberSides = numberDices;
    }

    public int getNumberDices() {
        return numberDices;
    }

    public void setNumberDices(int numberDices) {
        this.numberDices = numberDices;
    }

    public int getNumberSides() {
        return numberSides;
    }

    public void setNumberSides(int numberSides) {
        this.numberSides = numberSides;
    }

    public int roll(){
        int result = 0;
        for (int i = 0; i < numberDices; i++) {
            int random = (int) (Math.random() * (numberSides)) + 1;
            result += random;
        }
        return result;
    }
}
