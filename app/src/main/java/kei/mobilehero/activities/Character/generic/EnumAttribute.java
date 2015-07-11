package kei.mobilehero.activities.character.generic;

/**
 * Created by Dimitri on 10/07/2015.
 */
public enum EnumAttribute {
    GAME ("game"),
    ROUND ("round"),
    CHARACTER ("character"),
    CARACTERISTIC ("caracteristic"),
    SKILL ("skill"),
    EQUIPMENT ("equipment");

    private String name;

    EnumAttribute(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
