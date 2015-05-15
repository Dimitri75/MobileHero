package kei.mobilehero.classes.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
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
    private static String DATA = "DATA";

    public static Loader getInstance() {
        return ourInstance;
    }

    private Loader() {
        File root = new File(DATA);
        if (!root.exists()) {
            root.mkdir();
        }
    }

    private ArrayList<Game> loadData() {
        ArrayList<Game> listGames = new ArrayList<>();
        File currentDir = new File(DATA);
        if (currentDir.exists() && currentDir.isDirectory()) {
            for (File g : currentDir.listFiles()) {
                if (g.isDirectory()) {
                    Game game = new Game(g.getName());

                    File gameDir = new File(DATA + "/" + game.getName());
                    if (gameDir.exists() && gameDir.isDirectory()) {
                        for (File r : gameDir.listFiles()) {
                            if (r.isDirectory()) {
                                Round round = new Round(r.getName());

                                File roundDir = new File(DATA + "/" + game.getName() + "/" + round.getName());
                                if (roundDir.exists() && roundDir.isDirectory()) {
                                    Matcher matcher;
                                    Pattern pattern = Pattern.compile("([^\\s]+(\\.(?i)(hero))$)");

                                    for (File c : roundDir.listFiles()) {
                                        matcher = pattern.matcher(c.getName());
                                        if (c.isFile() && matcher.find()) {
                                            FileInputStream inputStream;
                                            ObjectInputStream objectInputStream;

                                            try {
                                                // Open the save file
                                                inputStream = new FileInputStream(c.getAbsolutePath());
                                                objectInputStream = new ObjectInputStream(inputStream);

                                                // Read the object
                                                Character character = (Character) objectInputStream.readObject();

                                                // Add the character to the array
                                                if (character != null) {
                                                    round.getCharacters().add(character);
                                                }

                                                // Close the stream
                                                objectInputStream.close();
                                            } catch (Exception e) {
                                                e.printStackTrace();
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

