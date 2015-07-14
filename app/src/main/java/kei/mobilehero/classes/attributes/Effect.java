package kei.mobilehero.classes.attributes;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Effect extends AttributeBase implements Parcelable {
    private Caracteristic caracteristic;

    public Effect(String name, String description, double value, Caracteristic caracteristic) {
        super(name, description, value);
        this.caracteristic = caracteristic;
    }

    @Override
    public String toString() {
        String val = super.getValue() > 0 ? "+ " + super.getValue() : "- " + super.getValue();
        return super.getName() + "  ( " + val + " : " + caracteristic.getName() + " )  ";

    }

    public Caracteristic getCaracteristic() {
        return caracteristic;
    }

    public void setCaracteristic(Caracteristic caracteristic) {
        this.caracteristic = caracteristic;
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

        Bundle b = new Bundle();
        b.putParcelable("caracteristic", caracteristic);
        dest.writeBundle(b);
    }

    /**
     * Instanciate an effect using Parcelable
     * @param in
     */
    public Effect(Parcel in) {
        super(in.readString(), in.readString(), in.readDouble());
        this.setId(in.readString());

        Bundle b = in.readBundle(Caracteristic.class.getClassLoader());
        caracteristic = b.getParcelable("caracteristic");
    }






    public static final Parcelable.Creator<Effect> CREATOR = new Parcelable.Creator<Effect>() {

        public Effect createFromParcel(Parcel in) {
            return new Effect(in);
        }

        public Effect[] newArray(int size) {
            return new Effect[size];
        }
    };
    //END PARCELABLE
}
