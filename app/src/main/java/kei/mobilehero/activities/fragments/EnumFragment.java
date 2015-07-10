package kei.mobilehero.activities.fragments;

/**
 * Created by Dimitri on 10/07/2015.
 */
public enum EnumFragment {
    ATTRIBUTE ("attribute"),
    CARACTERISTICS ("caracteristics"),
    SKILLS ("skills"),
    EQUIPMENT ("equipment"),
    NEW_CARACTERISTIC ("new_caracteristic"),
    NEW_SKILL ("new_skill"),
    NEW_EQUIPMENT ("new_equipment");

    private String name;

    EnumFragment(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
