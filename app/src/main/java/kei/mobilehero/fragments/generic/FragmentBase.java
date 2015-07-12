package kei.mobilehero.fragments.generic;

import android.app.Activity;
import android.app.Fragment;

/**
 * Created by Vuzi on 09/07/2015.
 */
public abstract class FragmentBase extends Fragment implements ContentProvider.ContentProviderListener {
    protected OnFragmentInteractionListener mListener;
    protected ContentProvider contentProvider;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            contentProvider = (ContentProvider) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener and ContentProvider");
        }

        contentProvider.addContentListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
        contentProvider.removeContentListener(this);
    }

}
