package kei.mobilehero.classes.attributes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Skill extends AttributeBase implements Parcelable{
    private HashMap<String, Effect> effects;

    public Skill(String name, String description, double value){
        super(name, description, value);
        effects = new HashMap<>();
    }

    public Skill(String name, String description, double value, HashMap<String, Effect> impacts){
        super(name, description, value);
        effects = impacts;
    }

    public HashMap<String, Effect> getEffects() {
        return effects;
    }

    public void setEffects(HashMap<String, Effect> effects) {
        this.effects = effects;
    }

    //PARCELABLE
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
        dest.writeString(getName());
        dest.writeString(getDescription());
        dest.writeDouble(getValue());
        dest.writeString(getId());
        dest.writeMap(effects);
    }

    /**
     * Instanciate a skill using Parcelable
     * @param in
     */
    public Skill(Parcel in) {
        super(in.readString(), in.readString(), in.readDouble());
        this.setId(in.readString());
        this.effects = in.readHashMap(Effect.class.getClassLoader());
    }

    public static final Parcelable.Creator<Skill> CREATOR = new Parcelable.Creator<Skill>() {

        public Skill createFromParcel(Parcel in) {
            return new Skill(in);
        }

        public Skill[] newArray(int size) {
            return new Skill[size];
        }
    };
    //END PARCELABLE
}
