package kei.mobilehero.classes.general;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Game implements Parcelable {
    private String id;
    private String name;
    private ArrayList<Round> rounds;

    public Game(String name) {
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

    public void setRounds(ArrayList<Round> rounds) {
        this.rounds = rounds;
    }

    public boolean save(Context context) {
        File dir = new File(context.getFilesDir(), name);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                Log.v("Game save()", "Game saved in " + dir.getAbsolutePath());
                return true;
            }
        } else Log.v("Game save()", "Directory already exists.");
        return false;
    }

    public boolean delete(Context context) {
        File dir = new File(context.getFilesDir(), name);
        if (dir.exists()) {
            if (dir.delete()) return true;
        } else Log.v("Game delete()", "Directory doesn't exist.");
        return false;
    }

    @Override
    public String toString() {
        return name;
    }

    // PARCELABLE
    @Override
    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
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
        b.putParcelableArrayList("rounds", rounds);
        dest.writeBundle(b);
    }

    /**
     * Instanciate a game using Parcelable
     * @param in
     */
    public Game(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();

        Bundle b = in.readBundle(Round.class.getClassLoader());
        rounds = b.getParcelableArrayList("rounds");
    }

    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {

        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
    //END PARCELABLE
}
