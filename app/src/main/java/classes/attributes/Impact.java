package classes.attributes;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Impact extends AttributeBase{
    private Caracteristic caracteristic;

    public Impact(String name, String description, double value, Caracteristic caracteristic) {
        super(name, description, value);
        this.caracteristic = caracteristic;
    }

    public Caracteristic getCaracteristic() {
        return caracteristic;
    }

    public void setCaracteristic(Caracteristic caracteristic) {
        this.caracteristic = caracteristic;
    }
}
