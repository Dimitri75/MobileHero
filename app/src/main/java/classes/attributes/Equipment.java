package classes.attributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Equipment extends AttributeBase {
    private String equipmentPosition;
    private List<Impact> impacts;

    public Equipment(String name, String description, double value){
        super(name, description, value);
        impacts = new ArrayList<>();
    }

    public Equipment(String name, String description, double value, List<Impact> impacts){
        super(name, description, value);
        this.impacts = impacts;
    }

    public String getEquipmentPosition() {
        return equipmentPosition;
    }

    public void setEquipmentPosition(String equipmentPosition) {
        this.equipmentPosition = equipmentPosition;
    }

    public List<Impact> getImpacts() {
        return impacts;
    }

    public void setImpacts(List<Impact> impacts) {
        this.impacts = impacts;
    }
}
