package kei.mobilehero.classes.general;

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

    public boolean save(){
        File dir = new File ("DATA/"+name);
        if (!dir.exists()) {
            dir.mkdir();
            return true;
        }
        return false;
    }

    public boolean delete(String gameName) {
        File dir = new File("DATA/"+name);
        if (dir.exists()) {
            dir.delete();
            return true;
        }
        return false;
    }
}
