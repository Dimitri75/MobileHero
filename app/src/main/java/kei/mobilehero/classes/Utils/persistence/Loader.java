package kei.mobilehero.classes.utils.persistence;

import android.content.Context;

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
                    if (gameDir.exists() && gameDir.isDirectory()) {
                        for (File r : gameDir.listFiles()) {
                            if (r.isDirectory()) {
                                Round round = new Round(r.getName());
                                listGames.get(listGames.indexOf(game)).getRounds().add(round);

                                File roundDir = new File(root, game.getName() + "/" + round.getName());
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
                                                    listGames.get(listGames.indexOf(game)).getRounds().get(listGames.get(listGames.indexOf(game)).getRounds().indexOf(round)).getCharacters().add(character);
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

