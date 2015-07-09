package kei.mobilehero.activities.fragments;

import kei.mobilehero.classes.general.*;
import kei.mobilehero.classes.general.Character;

/**
 * Created by Vuzi on 09/07/2015.
 */
public interface ContentProvider {

    public interface ContentProviderListener {
        public void onAvailableData();
    }

    public Game getGame();

    public Round getRound();

    public Character getCharacter();

    public void addContentListener(ContentProviderListener listener);
}
