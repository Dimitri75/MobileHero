package kei.mobilehero.fragments.generic;

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
