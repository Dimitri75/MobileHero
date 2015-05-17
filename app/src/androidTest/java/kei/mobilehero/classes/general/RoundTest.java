package kei.mobilehero.classes.general;

import android.test.InstrumentationTestCase;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class RoundTest extends InstrumentationTestCase {
    private final static String ROUND_NAME = "Round";

    private final static String GAME_NAME = "Game";

    private final static String CHARAC_NAME = "Character";

    public void test() throws Exception {
        kei.mobilehero.classes.general.Character character = new Character(CHARAC_NAME);
        Game game = new Game(GAME_NAME);

        Round round = new Round(ROUND_NAME, game);
        round.getCharacters().add(character);

        assertEquals(round.getName(), ROUND_NAME);
        assertEquals(round.getCharacters().get(0), character);
        assertEquals(round.getGame(), game);
    }
}
