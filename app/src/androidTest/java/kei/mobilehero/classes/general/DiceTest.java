package kei.mobilehero.classes.general;

import android.test.InstrumentationTestCase;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class DiceTest extends InstrumentationTestCase{
    private final static int DICE_NUMBERDICES = 2;
    private final static int DICE_NUMBERSIDES = 6;

    public void test() throws Exception {
        Dice dice = new Dice(DICE_NUMBERDICES, DICE_NUMBERSIDES);

        assertEquals(dice.getNumberDices(), DICE_NUMBERDICES);
        assertEquals(dice.getNumberSides(), DICE_NUMBERSIDES);
    }

    public void testRoll() throws Exception {
        int nbTests = 25;

        Dice dice = new Dice(DICE_NUMBERDICES, DICE_NUMBERSIDES);

        for (int i = 0; i < nbTests; i++) {
            int result = dice.getSumOfRolls(dice.roll());

            if (result > 0 && result <= DICE_NUMBERDICES*DICE_NUMBERSIDES) {
                assertTrue(true);
            } else {
                assertFalse(false);
            }
        }
    }
}
