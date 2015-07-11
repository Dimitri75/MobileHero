package kei.mobilehero.activities.fragments.generic;

import kei.mobilehero.classes.general.*;
import kei.mobilehero.classes.general.Character;

/**
 * Created by Vuzi on 09/07/2015.
 */
public interface ContentProvider<T> {

    interface ContentProviderListener {
        void onAvailableData();
    }

    T getData();

    void addContentListener(ContentProviderListener listener);

    void removeContentListener(ContentProviderListener listener);
}
