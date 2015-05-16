package kei.mobilehero.classes.general;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Game {
    private String id;
    private String name;
    private List<Round> rounds;

    public Game(String name){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        rounds = new ArrayList<>();
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

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public boolean save(Context context){
        File dir = new File (context.getFilesDir(), name);
        if (!dir.exists()) {
            if (dir.mkdir()){
                Log.v("Game save()", "Game saved in "+ dir.getAbsolutePath());
                return true;
            }
        }
        else Log.v("Game save()", "Directory already exists.");
        return false;
    }

    public boolean delete(Context context) {
        File dir = new File(context.getFilesDir(), name);
        if (dir.exists()) {
            if (dir.delete()) return true;
        }
        else Log.v("Game delete()", "Directory doesn't exist.");
        return false;
    }

    @Override
    public String toString(){
        return name;
    }
}
