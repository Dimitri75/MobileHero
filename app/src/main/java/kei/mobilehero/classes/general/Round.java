package kei.mobilehero.classes.general;

import android.content.Context;
import android.util.Log;

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

    public boolean save(Context context, String gameName){
        File dir = new File (context.getFilesDir(), gameName + "/" +name);
        if (!dir.exists()) {
            if (dir.mkdir()){
                Log.v("Round save()", "Round saved in "+ dir.getAbsolutePath());
                return true;
            }
        }
        else Log.v("Round save()", "Directory already exists.");
        return false;
    }

    public boolean delete(Context context, String gameName) {
        File dir = new File(context.getFilesDir(), gameName + "/" + name);
        if (dir.exists()) {
            if (dir.delete()) return true;
        }
        else Log.v("Round delete()", "Directory doesn't exist.");
        return false;
    }
}
