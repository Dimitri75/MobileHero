package classes;

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
}
