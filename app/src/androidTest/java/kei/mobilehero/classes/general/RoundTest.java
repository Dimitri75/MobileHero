package kei.mobilehero.classes.general;

import android.test.InstrumentationTestCase;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class RoundTest extends InstrumentationTestCase {
    private final static String ROUND_NAME = "Round";

    private final static String CHARAC_NAME = "Character";

    private final static int DICE_NUMBERDICES = 2;
    private final static int DICE_NUMBERSIDES = 6;

    public void test() throws Exception {
        kei.mobilehero.classes.general.Character character = new Character(CHARAC_NAME);
        Dice dice = new Dice(DICE_NUMBERDICES, DICE_NUMBERSIDES);

        Round round = new Round(ROUND_NAME);
        round.getCharacters().add(character);
        round.setDice(dice);

        assertEquals(round.getName(), ROUND_NAME);
        assertEquals(round.getCharacters().get(0), character);
        assertEquals(round.getDice(), dice);
    }
}
