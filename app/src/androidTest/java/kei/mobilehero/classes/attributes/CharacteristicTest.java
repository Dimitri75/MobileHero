package kei.mobilehero.classes.attributes;

import android.test.InstrumentationTestCase;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class CharacteristicTest extends InstrumentationTestCase {
    private final static String CARAC_NAME = "Characteristic";
    private final static String CARAC_DESCRIPTION = "This is a caracteristic.";
    private final static double CARAC_VALUE = 4.2;

    public void test() throws Exception {
        Characteristic characteristic = new Characteristic(CARAC_NAME, CARAC_DESCRIPTION, CARAC_VALUE);

        assertEquals(characteristic.getName(), CARAC_NAME);
        assertEquals(characteristic.getDescription(), CARAC_DESCRIPTION);
        assertEquals(characteristic.getValue(), CARAC_VALUE);
    }
}
