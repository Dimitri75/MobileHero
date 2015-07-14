package kei.mobilehero.classes.attributes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Caracteristic extends AttributeBase implements Parcelable{
    public double bonus;

    public Caracteristic(String name, String description, double value){
        super(name, description, value);
    }

    @Override
    public String toString() {
        String s_bonus = "";
        if (bonus > 0)
            s_bonus = "        + " + bonus;
        else if (bonus < 0)
            s_bonus = "        - " + -bonus;

        return super.getName() + "  ( "+super.getValue()+" )  " + s_bonus;
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
    }

    /**
     * Instanciate a caracteristic using Parcelable
     * @param in
     */
    public Caracteristic(Parcel in) {
        super(in.readString(), in.readString(), in.readDouble());
        this.setId(in.readString());
    }

    public static final Parcelable.Creator<Caracteristic> CREATOR = new Parcelable.Creator<Caracteristic>() {

        public Caracteristic createFromParcel(Parcel in) {
            return new Caracteristic(in);
        }

        public Caracteristic[] newArray(int size) {
            return new Caracteristic[size];
        }
    };
    //END PARCELABLE
}
