package kei.mobilehero.classes.attributes;

import android.test.InstrumentationTestCase;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class CaracteristicTest extends InstrumentationTestCase {
    private final static String CARAC_NAME = "Caracteristic";
    private final static String CARAC_DESCRIPTION = "This is a caracteristic.";
    private final static double CARAC_VALUE = 4.2;

    public void test() throws Exception {
        Caracteristic caracteristic = new Caracteristic(CARAC_NAME, CARAC_DESCRIPTION, CARAC_VALUE);

        assertEquals(caracteristic.getName(), CARAC_NAME);
        assertEquals(caracteristic.getDescription(), CARAC_DESCRIPTION);
        assertEquals(caracteristic.getValue(), CARAC_VALUE);
    }
}
