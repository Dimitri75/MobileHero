package kei.mobilehero.fragments.generic;

/**
 * Created by Dimitri on 10/07/2015.
 */
public enum EnumFragment {
    ATTRIBUTE ("attribute"),
    CARACTERISTICS ("caracteristics"),
    SKILLS ("skills"),
    EQUIPMENT ("equipment"),
    CARACTERISTIC_FORM("caracteristic_form"),
    SKILL_FORM("skill_form"),
    EQUIPMENT_FORM("equipment_form");

    private String name;

    EnumFragment(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
