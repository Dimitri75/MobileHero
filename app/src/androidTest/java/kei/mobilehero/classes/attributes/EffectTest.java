package kei.mobilehero.classes.attributes;

import android.test.InstrumentationTestCase;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class EffectTest extends InstrumentationTestCase{
    private final static String EFFECT_NAME = "Impact";
    private final static String EFFECT_DESCRIPTION = "This is an impact.";
    private final static double EFFECT_VALUE = 4.2;

    private final static String CARAC_NAME = "Characteristic";
    private final static String CARAC_DESCRIPTION = "This is a caracteristic.";
    private final static double CARAC_VALUE = 4.2;

    public void test() throws Exception {
        Characteristic characteristic = new Characteristic(CARAC_NAME, CARAC_DESCRIPTION, CARAC_VALUE);
        Effect effect = new Effect(EFFECT_NAME, EFFECT_DESCRIPTION, EFFECT_VALUE, characteristic);

        assertEquals(effect.getName(), EFFECT_NAME);
        assertEquals(effect.getDescription(), EFFECT_DESCRIPTION);
        assertEquals(effect.getValue(), EFFECT_VALUE);
        assertEquals(effect.getCharacteristic(), characteristic);
    }
}
