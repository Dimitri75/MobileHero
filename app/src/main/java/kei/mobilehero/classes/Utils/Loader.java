package kei.mobilehero.classes.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kei.mobilehero.classes.general.Character;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Loader {
    private static Loader ourInstance = new Loader();
    private static String DATA = "data";

    public static Loader getInstance() {
        return ourInstance;
    }

    private Loader() {
        File root = new File(DATA);
        if (!root.exists()){
            root.mkdir();
        }
    }

    private ArrayList<String> loadGames() {
        ArrayList<String> listGames = new ArrayList<>();
        File currentDir = new File(DATA);
        if (currentDir.exists() && currentDir.isDirectory()) {
            for (File f : currentDir.listFiles()) {
                if (f.isDirectory()) {
                    listGames.add(f.getName());
                }
            }
            return listGames;
        }
        return null;
    }

    private ArrayList<String> loadRounds(String gameName){
        ArrayList<String> listRounds = new ArrayList<>();
        File gameDir = new File(DATA+"/"+gameName);
        if (gameDir.exists() && gameDir.isDirectory()){
            for (File f : gameDir.listFiles()){
                if (f.isDirectory()){
                    listRounds.add(f.getName());
                }
            }
            return listRounds;
        }
        return null;
    }

    private ArrayList<Character> loadCharacters(String gameName, String roundName){
        ArrayList<Character> listCharacters;
        Character character;
        Pattern pattern;
        Matcher matcher;

        File roundDir = new File(DATA+"/"+gameName+"/"+roundName);
        if (roundDir.exists() && roundDir.isDirectory()){
            listCharacters = new ArrayList<>();
            pattern = Pattern.compile("([^\\s]+(\\.(?i)(hero))$)");

            for (File f : roundDir.listFiles()){
                matcher = pattern.matcher(f.getName());
                if (f.isFile() && matcher.find()){
                    FileInputStream inputStream;
                    ObjectInputStream objectInputStream;

                    try {
                        // Open the save file
                        inputStream = new FileInputStream(f.getAbsolutePath());
                        objectInputStream = new ObjectInputStream(inputStream);

                        // Read the object
                        character = (Character) objectInputStream.readObject();

                        // Add the character to the array
                        if (character != null){
                            listCharacters.add(character);
                        }

                        // Close the stream
                        objectInputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return listCharacters;
        }
        return null;
    }
}
