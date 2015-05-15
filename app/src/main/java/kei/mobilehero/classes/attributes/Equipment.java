package kei.mobilehero.classes.attributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Equipment extends AttributeBase {
    private String equipmentPosition;
    private List<Effect> effects;

    public Equipment(String name, String description, double weight, String equipmentPosition){
        super(name, description, weight);
        this.equipmentPosition = equipmentPosition;
        effects = new ArrayList<>();
    }

    public Equipment(String name, String description, double value, String equipmentPosition, List<Effect> impacts){
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

    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }
}
