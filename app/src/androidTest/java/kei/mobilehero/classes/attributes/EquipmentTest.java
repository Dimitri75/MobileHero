package kei.mobilehero.classes.attributes;

import android.test.InstrumentationTestCase;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class EquipmentTest extends InstrumentationTestCase{
    private final static String EQUIP_NAME = "Equipment";
    private final static String EQUIP_DESCRIPTION = "This is an equipment.";
    private final static double EQUIP_WEIGHT = 10.5;
    private final static String EQUIP_POSITION = "Head";

    private final static String CARAC_NAME = "Caracteristic";
    private final static String CARAC_DESCRIPTION = "This is a caracteristic.";
    private final static double CARAC_VALUE = 4.2;

    private final static String EFFECT_NAME = "Effect";
    private final static String EFFECT_DESCRIPTION = "This is an effect.";
    private final static double EFFECT_VALUE = 4.2;

    public void test() throws Exception {
        Effect effect = new Effect(EFFECT_NAME, EFFECT_DESCRIPTION, EFFECT_VALUE, new Caracteristic(CARAC_NAME, CARAC_DESCRIPTION, CARAC_VALUE));

        Equipment equipment = new Equipment(EQUIP_NAME, EQUIP_DESCRIPTION, EQUIP_WEIGHT, EQUIP_POSITION );
        equipment.getEffects().put(effect.getId(), effect);
        assertEquals(equipment.getName(), EQUIP_NAME);
        assertEquals(equipment.getDescription(), EQUIP_DESCRIPTION);
        assertEquals(equipment.getValue(), EQUIP_WEIGHT);
        assertEquals(equipment.getWeight(), EQUIP_WEIGHT);
        assertEquals(equipment.getEquipmentPosition(), EQUIP_POSITION);
        assertEquals(equipment.getWeight(), EQUIP_WEIGHT);
        assertEquals(equipment.getEffects().get(0), effect);
    }
}
