package kei.mobilehero.classes.attributes;

import android.test.InstrumentationTestCase;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class EffectTest extends InstrumentationTestCase{
    private final static String EFFECT_NAME = "Effect";
    private final static String EFFECT_DESCRIPTION = "This is an effect.";
    private final static double EFFECT_VALUE = 4.2;

    private final static String CARAC_NAME = "Caracteristic";
    private final static String CARAC_DESCRIPTION = "This is a caracteristic.";
    private final static double CARAC_VALUE = 4.2;

    public void test() throws Exception {
        Caracteristic caracteristic = new Caracteristic(CARAC_NAME, CARAC_DESCRIPTION, CARAC_VALUE);
        Effect effect = new Effect(EFFECT_NAME, EFFECT_DESCRIPTION, EFFECT_VALUE, caracteristic);

        assertEquals(effect.getName(), EFFECT_NAME);
        assertEquals(effect.getDescription(), EFFECT_DESCRIPTION);
        assertEquals(effect.getValue(), EFFECT_VALUE);
        assertEquals(effect.getCaracteristic(), caracteristic);
    }
}
