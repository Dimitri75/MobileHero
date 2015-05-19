package kei.mobilehero.classes.general;

import android.test.InstrumentationTestCase;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class GameTest extends InstrumentationTestCase {
    private final static String GAME_NAME = "Game";

    private final static String ROUND_NAME = "Round";

    public void test() throws Exception {
        Game game = new Game(GAME_NAME);
        Round round = new Round(ROUND_NAME);
        game.getRounds().add(round);

        assertEquals(game.getName(), GAME_NAME);
        assertEquals(game.getRounds().get(0), round);
    }
}
