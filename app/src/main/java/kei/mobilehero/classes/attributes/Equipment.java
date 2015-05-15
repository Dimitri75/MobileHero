package kei.mobilehero.classes.attributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Equipment extends AttributeBase {
    private String equipmentPosition;
    private double weight;
    private List<Effect> effects;

    public Equipment(String name, String description, double value, String equipmentPosition, double weight){
        super(name, description, value);
        this.equipmentPosition = equipmentPosition;
        this.weight = weight;
        effects = new ArrayList<>();
    }

    public Equipment(String name, String description, double value, String equipmentPosition, double weight, List<Effect> impacts){
        super(name, description, value);
        this.equipmentPosition = equipmentPosition;
        this.weight = weight;
        this.effects = impacts;
    }

    public String getEquipmentPosition() {
        return equipmentPosition;
    }

    public void setEquipmentPosition(String equipmentPosition) {
        this.equipmentPosition = equipmentPosition;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }
}
