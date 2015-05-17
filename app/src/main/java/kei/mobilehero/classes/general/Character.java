package kei.mobilehero.classes.general;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
    private String gender;
    private String alignment;
    private String race;
    private String className;
    private String picture;
    private ArrayList<Skill> skills;
    private ArrayList<Caracteristic> caracteristics;
    private ArrayList<Equipment> equipments;

    public Character(String name){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        skills = new ArrayList<>();
        caracteristics = new ArrayList<>();
        equipments = new ArrayList<>();
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

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public ArrayList<Caracteristic> getCaracteristics() {
        return caracteristics;
    }

    public void setCaracteristics(ArrayList<Caracteristic> caracteristics) {
        this.caracteristics = caracteristics;
    }

    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(ArrayList<Equipment> equipments) {
        this.equipments = equipments;
    }

    public HashMap<String, Double> getCalculatedCaracteristics(){
        HashMap<String, Double> calculatedCaracteristics = new HashMap<>();
        for (Caracteristic c : caracteristics){
            calculatedCaracteristics.put(c.getName(), c.getValue());
        }

        for (Equipment e : equipments){
            if (!e.getEffects().isEmpty()){
                for (Effect i : e.getEffects()){
                    Double value;
                    if ((value = calculatedCaracteristics.get(i.getCaracteristic().getName())) != null){
                        calculatedCaracteristics.put(i.getCaracteristic().getName(), value+i.getValue());
                    }
                }
            }
        }
        return calculatedCaracteristics;
    }

    public double getEquipmentWeight(){
        double sum = 0;
        for (Equipment e : equipments){
            sum += e.getWeight();
        }
        return sum;
    }

    public boolean delete(Context context, String gameName, String roundName){
        File file = new File (context.getFilesDir() + gameName+"/"+roundName+"/character."+name+"."+id+".xml");
        if (file.exists()){
            if (file.delete()) return true;
        }
        else Log.v("Character delete():", "Directory doesn't exist.");
        return false;
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
        dest.writeString(gender);
        dest.writeString(alignment);
        dest.writeString(race);
        dest.writeString(className);
        dest.writeString(picture);

        Bundle b = new Bundle();
        b.putParcelableArrayList("skills", skills);
        b.putParcelableArrayList("caracteristics", caracteristics);
        b.putParcelableArrayList("equipments", equipments);
        dest.writeBundle(b);
    }

    /**
     * Instanciate a game using Parcelable
     * @param in
     */
    public Character(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.gender = in.readString();
        this.alignment = in.readString();
        this.race = in.readString();
        this.className = in.readString();
        this.picture = in.readString();

        Bundle b = in.readBundle(Skill.class.getClassLoader());
        skills = b.getParcelableArrayList("skills");

        b = in.readBundle(Caracteristic.class.getClassLoader());
        caracteristics = b.getParcelableArrayList("caracteristics");

        b = in.readBundle(Equipment.class.getClassLoader());
        equipments = b.getParcelableArrayList("equipments");
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


    // JAXB
    /**
     * Instanciate a character from an xml file using JAXB
     * @param xmlFile
     */
    public Character(File xmlFile){
        try {
            if (xmlFile.exists() && xmlFile.isFile()) {
                JAXBContext jc = JAXBContext.newInstance(this.getClass());
                Unmarshaller u = jc.createUnmarshaller();
                Object object = u.unmarshal(xmlFile);

                if (object != null){
                    Character c = (Character) object;
                    this.setId(c.getId());
                    this.setName(c.getName());
                    this.setGender(c.getGender());
                    this.setAlignment(c.getAlignment());
                    this.setRace(c.getRace());
                    this.setClassName(c.getClassName());
                    this.setPicture(c.getPicture());
                    this.setSkills(c.getSkills());
                    this.setCaracteristics(c.getCaracteristics());
                    this.setEquipments(c.getEquipments());
                }
            }
        }
        catch(JAXBException e){
            e.printStackTrace();
        }
    }

    /**
     * Save this character to an XML file using JAXB
     * MarshallToXML
     * @return
     * @throws JAXBException
     */
    public boolean save(Context context, String gameName, String roundName) throws JAXBException
    {
        //TODO Ajouter StaX jar aux libs
        try {
            File characterFile = new File(context.getFilesDir(), gameName + "/" + roundName + "/character." + name + "." + id + ".hero");

            JAXBContext jaxbContext = JAXBContext.newInstance(this.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            marshaller.marshal(this, baos);

            FileWriter fw = new FileWriter(characterFile);
            fw.write(baos.toString());
            fw.close();

            Log.v("Character save()", "Character saved in " + characterFile.getAbsolutePath());

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    // END JAXB
}
