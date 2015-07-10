package kei.mobilehero.classes.general;

import java.util.ArrayList;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Dice {
    private int numberDices;
    private int numberSides;

    public Dice(int numberDices, int numberSides){
        this.numberDices = numberDices;
        this.numberSides = numberSides;
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

    public ArrayList<Integer> roll(){
        ArrayList<Integer> listResult = new ArrayList<>();
        for (int i = 0; i < numberDices; i++) {
            int random = (int) (Math.random() * (numberSides)) + 1;
            listResult.add(random);
        }
        return listResult;
    }

    public int getSumOfRolls(ArrayList<Integer> listResult){
        int sum = 0;
        for (int value : listResult){
            sum += value;
        }
        return sum;
    }

    public ArrayList<String> listResultToListString(ArrayList<Integer> listResult){
        ArrayList<String> listString = new ArrayList<>();
        for (int i = 0; i < listResult.size(); i++){
            listString.add("Dé numéro " + (i + 1) + " :           " + listResult.get(i));
        }
        return listString;
    }
}
