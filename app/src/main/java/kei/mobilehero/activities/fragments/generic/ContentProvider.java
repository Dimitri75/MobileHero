package kei.mobilehero.activities.fragments.generic;

import kei.mobilehero.classes.general.*;
import kei.mobilehero.classes.general.Character;

/**
 * Created by Vuzi on 09/07/2015.
 */
public interface ContentProvider {
    interface ContentProviderListener {
        void onAvailableData();
    }

    Game getGame();

    Round getRound();

    Character getCharacter();

    void addContentListener(ContentProviderListener listener);

    void removeContentListener(ContentProviderListener listener);
}
