package kei.mobilehero.classes.general;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.File;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Round implements Parcelable{
    private String id;
    private String name;
    private ArrayList<kei.mobilehero.classes.general.Character> characters;
    private Dice dice;
    private Game game;

    public Round(String name, Game game){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.game = game;
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

    public void setCharacters(ArrayList<kei.mobilehero.classes.general.Character> characters) {
        this.characters = characters;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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

    public boolean delete(Context context) {
        File dir = new File(context.getFilesDir(), game.getName() + "/" + name);
        if (dir.exists()) {
            if (dir.delete()) return true;
        }
        else Log.v("Round delete()", "Directory doesn't exist.");
        return false;
    }

    // PARCELABLE
    /**
     * Instanciate a round using Parcelable
     * @param in
     */
    public Round(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();

        Bundle b = in.readBundle(Character.class.getClassLoader());
        characters = b.getParcelableArrayList("characters");
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);

        Bundle b = new Bundle();
        b.putParcelableArrayList("characters", characters);
        dest.writeBundle(b);
    }

    public static final Parcelable.Creator<Round> CREATOR = new Parcelable.Creator<Round>() {

        public Round createFromParcel(Parcel in) {
            return new Round(in);
        }

        public Round[] newArray(int size) {
            return new Round[size];
        }
    };
    //END PARCELABLE
}
