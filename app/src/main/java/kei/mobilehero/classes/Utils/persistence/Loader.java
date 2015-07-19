package kei.mobilehero.classes.utils.persistence;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import kei.mobilehero.classes.general.Character;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Loader {
    private static Loader INSTANCE = new Loader();

    public static Loader getInstance() {
        return INSTANCE;
    }

    private Loader() {
    }

    /**
     * Load all the saved games
     * @param context
     * @return
     */
    public ArrayList<Game> loadGames(Context context){
        File root = context.getFilesDir();

        ArrayList<Game> listGames = new ArrayList<>();
         if (root.exists() && root.isDirectory() && root.listFiles().length >= 1) {
            for (File g : root.listFiles()) {
                if (g.isDirectory() && !g.getName().startsWith(".")) {
                    Game game = new Game(g.getName());
                    listGames.add(game);
                }
            }
        }
        return listGames;
    }

    /**
     * Load all the rounds attached to the specified game
     * @param context
     * @param game
     * @return
     */
    public Game loadRounds(Context context, Game game){
        game.getRounds().clear();
        File root = context.getFilesDir();

        File gameDir = new File(root, game.getName());
        if (gameDir.exists() && gameDir.isDirectory() && gameDir.listFiles().length >= 1) {
            for (File r : gameDir.listFiles()) {
                if (r.isDirectory() && !r.getName().startsWith(".")) {
                    game.getRounds().add(new Round(r.getName()));
                }
            }
        }
        return game;
    }

    /**
     * Load all the characters attached to the specified round
     * @param context
     * @param game
     * @param round
     * @return
     */
    public Round loadCharacters(Context context, Game game, Round round){
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

    /**
     * Load a character by its id, game and round
     * @param context
     * @param id
     * @param game
     * @param round
     * @return
     */
    public Character loadCharacterById(Context context, String id, Game game, Round round){
        File root = context.getFilesDir();

        File roundDir = new File(root, game.getName() + "/" + round.getName());
        if (roundDir.exists() && roundDir.isDirectory()) {

            File charFile = new File(roundDir, "character." + id + ".json");

            if (charFile.exists()) {
                Character character = new Character(charFile);
                return character;
            }
        }
        return new Character("");
    }

    /**
     * Load all the characters defined as model
     * @param context
     * @return
     */
    public List<Character> loadCharacterModels(Context context){
        ArrayList<Character> listModel = new ArrayList<>();

        File root = context.getFilesDir();
        File modelsDir = new File(root, ".model");
        if (modelsDir.exists() && modelsDir.isDirectory()) {
            Matcher matcher;
            // regex : "^character\\.[0-9a-z\\-]+.json$"
            Pattern pattern = Pattern.compile("([^\\s]+(\\.(?i)(json))$)");

            for (File modelFile : modelsDir.listFiles()) {
                matcher = pattern.matcher(modelFile.getName());
                if (modelFile.isFile() && matcher.find()) {
                    // Use the gson constructor
                    Character character = new Character(modelFile);

                    // Add the character to the array
                    if (character != null) {
                        listModel.add(character);
                    }
                }
            }
        }
        return listModel;
    }

    public File exportCharacterToZip(Context context, String id, Game game, Round round){
        File root = context.getFilesDir();

        // TODO
        /*
        File roundDir = new File(root, game.getName() + "/" + round.getName());
        if (roundDir.exists() && roundDir.isDirectory()) {

            File charFile = new File(roundDir, "character." + id + ".json");

            if (charFile.exists()) {
                Character character = new Character(charFile);
                return character;
            }
        }*/

        return null;
    }

}

