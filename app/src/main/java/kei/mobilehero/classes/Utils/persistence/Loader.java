package kei.mobilehero.classes.utils.persistence;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kei.mobilehero.classes.general.Character;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Loader {
    private static Loader ourInstance = new Loader();

    public static Loader getInstance() {
        return ourInstance;
    }

    private Loader() {
    }

    public ArrayList<Game> loadGames(Context context){
        File root = context.getFilesDir();

        ArrayList<Game> listGames = new ArrayList<>();
        if (root.exists() && root.isDirectory() && root.listFiles().length >= 1) {
            for (File g : root.listFiles()) {
                if (g.isDirectory()) {
                    Game game = new Game(g.getName());
                    listGames.add(game);
                }
            }
        }
        return listGames;
    }

    public Game loadRounds(Context context, Game game){
        game.getRounds().clear();
        File root = context.getFilesDir();

        File gameDir = new File(root, game.getName());
        if (gameDir.exists() && gameDir.isDirectory() && gameDir.listFiles().length >= 1) {
            for (File r : gameDir.listFiles()) {
                if (r.isDirectory()) {
                    game.getRounds().add(new Round(r.getName()));
                }
            }
        }
        return game;
    }

    public Round loadCharacters(Context context, Game game, Round round){
        game.getRounds().clear();
        round.getCharacters().clear();

        File root = context.getFilesDir();

        File roundDir = new File(root, game.getName() + "/" + round.getName());
        if (roundDir.exists() && roundDir.isDirectory()) {
            Matcher matcher;
            Pattern pattern = Pattern.compile("([^\\s]+(\\.(?i)(json))$)");

            for (File c : roundDir.listFiles()) {
                matcher = pattern.matcher(c.getName());
                if (c.isFile() && matcher.find()) {
                    // Use the gson constructor
                    Character character = new Character(c);

                    // Add the character to the array
                    if (character != null) {
                        round.getCharacters().add(character);
                    }
                }
            }
        }
        return round;
    }

    public ArrayList<Game> loadData(Context context) {
        File root = context.getFilesDir();

        ArrayList<Game> listGames = new ArrayList<>();
        if (root.exists() && root.isDirectory() && root.listFiles().length >= 1) {
            for (File g : root.listFiles()) {
                if (g.isDirectory()) {
                    Game game = new Game(g.getName());
                    listGames.add(game);

                    File gameDir = new File(root, game.getName());
                    if (gameDir.exists() && gameDir.isDirectory() && gameDir.listFiles().length >= 1) {
                        for (File r : gameDir.listFiles()) {
                            if (r.isDirectory()) {
                                Round round = new Round(r.getName());
                                listGames.get(listGames.indexOf(game)).getRounds().add(round);

                                File roundDir = new File(root, game.getName() + "/" + round.getName());
                                if (roundDir.exists() && roundDir.isDirectory()) {
                                    Matcher matcher;
                                    Pattern pattern = Pattern.compile("([^\\s]+(\\.(?i)(xml))$)");

                                    for (File c : roundDir.listFiles()) {
                                        matcher = pattern.matcher(c.getName());
                                        if (c.isFile() && matcher.find()) {
                                            // Use the gson constructor
                                            Character character = new Character(c);

                                            // Add the character to the array
                                            if (character != null) {
                                                listGames.get(listGames.indexOf(game)).getRounds().get(listGames.get(listGames.indexOf(game)).getRounds().indexOf(round)).getCharacters().add(character);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return listGames;
    }
}

