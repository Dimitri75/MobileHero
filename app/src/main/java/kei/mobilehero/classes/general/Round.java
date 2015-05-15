package kei.mobilehero.classes.general;

import java.io.File;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Round {
    private String id;
    private String name;
    private List<kei.mobilehero.classes.general.Character> characters;
    private Dice dice;

    public Round(String name){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        characters = new ArrayList<>();
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

    public List<kei.mobilehero.classes.general.Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<kei.mobilehero.classes.general.Character> characters) {
        this.characters = characters;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public boolean save(String gameName){
        File dir = new File (gameName+"/"+name);
        if (!dir.exists()) {
            dir.mkdirs();
            return true;
        }
        return false;
    }
}
