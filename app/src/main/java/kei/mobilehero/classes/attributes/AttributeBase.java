package kei.mobilehero.classes.attributes;

/**
 * Created by Dimitri on 15/05/2015.
 */

import java.util.UUID;

public abstract class AttributeBase {
    private String id;
    private String name;
    private String description;
    private double value;

    public AttributeBase(String name, String description, double value){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.value = value;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name;
    }
}
