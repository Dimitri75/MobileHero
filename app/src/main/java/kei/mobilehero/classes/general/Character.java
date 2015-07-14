package kei.mobilehero.classes.general;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import kei.mobilehero.classes.attributes.Caracteristic;
import kei.mobilehero.classes.attributes.Effect;
import kei.mobilehero.classes.attributes.Equipment;
import kei.mobilehero.classes.attributes.Skill;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Character implements Parcelable{
    private String id;
    private String name;
    private int mana;
    private int life;
    private String gender;
    private String alignment;
    private String race;
    private String className;
    private String picture;
    private int level;
    private HashMap<String, Skill> skills = new HashMap<>();
    private HashMap<String, Caracteristic> caracteristics = new HashMap<>();
    private HashMap<String, Equipment> equipments = new HashMap<>();

    public Character(String name){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.mana = 0;
        this.life = 0;
        this.gender = "";
        this.alignment = "";
        this.race = "";
        this.className = "";
        this.picture = "";
        this.level = 0;

        skills = new HashMap<>();
        caracteristics = new HashMap<>();
        equipments = new HashMap<>();
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

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public HashMap<String, Skill> getSkills() {
        if (skills == null)
            return (skills = new HashMap<>());
        return skills;
    }

    public void setSkills(HashMap<String, Skill> skills) {
        if (skills != null)
            this.skills = skills;
    }

    public HashMap<String, Caracteristic> getCharacteristics() {
        if (caracteristics == null)
            return (caracteristics = new HashMap<>());

        return caracteristics;
    }

    public void setCaracteristics(HashMap<String, Caracteristic> caracteristics) {
        if (caracteristics != null)
            this.caracteristics = caracteristics;
    }

    public HashMap<String, Equipment> getEquipments() {
        if (equipments == null)
            return (equipments = new HashMap<>());

        return equipments;
    }

    public void setEquipments(HashMap<String, Equipment> equipments) {
        if (equipments != null)
            this.equipments = equipments;
    }

    public HashMap<String, Double> getCalculatedCaracteristics(){
        HashMap<String, Double> calculatedCaracteristics = new HashMap<>();
        for (Caracteristic c : caracteristics.values()){
            calculatedCaracteristics.put(c.getName(), c.getValue());
        }

        for (Equipment e : equipments.values()){
            if (!e.getEffects().isEmpty()){
                for (Effect i : e.getEffects().values()){
                    Double value;
                    if ((value = calculatedCaracteristics.get(i.getCaracteristic().getName())) != null){
                        calculatedCaracteristics.put(i.getCaracteristic().getName(), value+i.getValue());
                    }
                }
            }
        }

        for (Skill s : skills.values()){
            if (!s.getEffects().isEmpty()){
                for (Effect e : s.getEffects().values()){
                    Double value;
                    if ((value = calculatedCaracteristics.get(e.getCaracteristic().getName())) != null){
                        calculatedCaracteristics.put(e.getCaracteristic().getName(), value+e.getValue());
                    }
                }
            }
        }
        return calculatedCaracteristics;
    }

    public void setCaracteristicsBonus(){
        HashMap<String, Double> mapBonus = getCalculatedCharacteristicsBonus();

        for (Map.Entry<String, Caracteristic> map : caracteristics.entrySet()){
            map.getValue().bonus = mapBonus.get(map.getKey());
        }
    }

    public HashMap<String, Double> getCalculatedCharacteristicsBonus(){
        HashMap<String, Double> calculatedCaracteristics = new HashMap<>();
        for (Caracteristic c : caracteristics.values()){
            calculatedCaracteristics.put(c.getName(), 0.);
        }

        for (Equipment e : equipments.values()){
            if (!e.getEffects().isEmpty()){
                for (Effect i : e.getEffects().values()){
                    Double value;
                    if ((value = calculatedCaracteristics.get(i.getCaracteristic().getName())) != null){
                        calculatedCaracteristics.put(i.getCaracteristic().getName(), value+i.getValue());
                    }
                }
            }
        }

        for (Skill s : skills.values()){
            if (!s.getEffects().isEmpty()){
                for (Effect e : s.getEffects().values()){
                    Double value;
                    if ((value = calculatedCaracteristics.get(e.getCaracteristic().getName())) != null){
                        calculatedCaracteristics.put(e.getCaracteristic().getName(), value+e.getValue());
                    }
                }
            }
        }
        return calculatedCaracteristics;
    }

    public double getEquipmentWeight(){
        double sum = 0;
        if (equipments.values().isEmpty()) return sum;

        for (Equipment e : equipments.values()){
            sum += e.getValue();
        }
        return sum;
    }

    public boolean delete(Context context, String gameName, String roundName){
        File file = new File (context.getFilesDir() + gameName+"/"+roundName+"/character."+id+".json");
        if (file.exists()){
            if (file.delete()) return true;
        }
        else Log.v("Character delete():", "Directory doesn't exist.");
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
        dest.writeInt(mana);
        dest.writeInt(life);
        dest.writeString(gender);
        dest.writeString(alignment);
        dest.writeString(race);
        dest.writeString(className);
        dest.writeString(picture);
        dest.writeInt(level);
        dest.writeMap(caracteristics);
        dest.writeMap(skills);
        dest.writeMap(equipments);
    }

    /**
     * Instanciate a game using Parcelable
     * @param in
     */
    public Character(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.mana = in.readInt();
        this.life = in.readInt();
        this.gender = in.readString();
        this.alignment = in.readString();
        this.race = in.readString();
        this.className = in.readString();
        this.picture = in.readString();
        this.level = in.readInt();
        this.caracteristics = in.readHashMap(Caracteristic.class.getClassLoader());
        this.skills = in.readHashMap(Skill.class.getClassLoader());
        this.equipments = in.readHashMap(Equipment.class.getClassLoader());
    }

    public static final Parcelable.Creator<Character> CREATOR = new Parcelable.Creator<Character>() {

        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        public Character[] newArray(int size) {
            return new Character[size];
        }
    };
    //END PARCELABLE


    // GSON
    /**
     * Instanciate a character from an xml file using GSON
     * Json to Object
     * @param jsonFile
     */
    public Character(File jsonFile){
        try {
            if (jsonFile.exists() && jsonFile.isFile()) {
                FileReader fileReader = new FileReader(jsonFile);

                Gson gson = new Gson();
                Character characterFromGson = gson.fromJson(fileReader, Character.class);

                if (characterFromGson != null){
                    this.setId(characterFromGson.getId());
                    this.setName(characterFromGson.getName());
                    this.setMana(characterFromGson.getMana());
                    this.setLife(characterFromGson.getLife());
                    this.setGender(characterFromGson.getGender());
                    this.setAlignment(characterFromGson.getAlignment());
                    this.setRace(characterFromGson.getRace());
                    this.setClassName(characterFromGson.getClassName());
                    this.setPicture(characterFromGson.getPicture());
                    this.setLevel(characterFromGson.getLevel());
                    this.setSkills(characterFromGson.getSkills());
                    this.setCaracteristics(characterFromGson.getCharacteristics());
                    this.setEquipments(characterFromGson.getEquipments());
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Save this character to an XML file using GSON
     * Object to Json
     * @return
     * @throws Exception
     */
    public boolean save(Context context, String gameName, String roundName)
    {
        try {
            File characterFile = new File(context.getFilesDir(), gameName + "/" + roundName + "/character." + id + ".json");

            Gson gson = new Gson();
            String jsonObject = gson.toJson(this);

            FileWriter fw = new FileWriter(characterFile);
            fw.write(jsonObject.toString());
            fw.close();

            Log.v("Character save()", "Character saved in " + characterFile.getAbsolutePath());

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Save a character as a model
     * @param context
     * @return
     */
    public boolean saveAsModel(Context context){
        try {
            File root = context.getFilesDir();
            File modelDir = new File(root, ".model");
            modelDir.mkdir();
            File characterModelFile = new File(modelDir, "character." + id + ".json");

            Gson gson = new Gson();
            String jsonObject = gson.toJson(this);

            FileWriter fw = new FileWriter(characterModelFile);
            fw.write(jsonObject.toString());
            fw.close();

            Log.v("Character saveAsModel()", "Character saved in " + characterModelFile.getAbsolutePath());

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    // END GSON
}
