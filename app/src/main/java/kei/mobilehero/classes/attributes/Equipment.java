package kei.mobilehero.classes.attributes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Equipment extends AttributeBase implements Parcelable{
    private String equipmentPosition;
    private HashMap<String, Effect> effects;

    public Equipment(String name, String description, double weight, String equipmentPosition){
        super(name, description, weight);
        this.equipmentPosition = equipmentPosition;
        effects = new HashMap<>();
    }


    public Equipment(String name, String description, double value, String equipmentPosition, HashMap<String, Effect> impacts){
        super(name, description, value);
        this.equipmentPosition = equipmentPosition;
        this.effects = impacts;
    }

    public double getWeight() {
        return getValue();
    }

    public void setWeight(double weight) {
        setValue(weight);
    }

    public String getEquipmentPosition() {
        return equipmentPosition;
    }

    public void setEquipmentPosition(String equipmentPosition) {
        this.equipmentPosition = equipmentPosition;
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
     * Instanciate an equipment using Parcelable
     * @param in
     */
    public Equipment(Parcel in) {
        super(in.readString(), in.readString(), in.readDouble());
        this.setId(in.readString());
        this.effects = in.readHashMap(Effect.class.getClassLoader());
    }

    public static final Parcelable.Creator<Equipment> CREATOR = new Parcelable.Creator<Equipment>() {

        public Equipment createFromParcel(Parcel in) {
            return new Equipment(in);
        }

        public Equipment[] newArray(int size) {
            return new Equipment[size];
        }
    };
    //END PARCELABLE
}
