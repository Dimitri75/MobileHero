package classes;

import java.util.List;
import java.util.UUID;

import classes.Utils.Dice;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Round {
    private String id;
    private String name;
    private List<Character> characters;
    private Dice dice;

    public Round(String name){
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }
}
