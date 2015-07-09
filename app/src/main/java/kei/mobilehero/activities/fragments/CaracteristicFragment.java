package kei.mobilehero.activities.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kei.mobilehero.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CaracteristicFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CaracteristicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaracteristicFragment extends Fragment implements ContentProvider.ContentProviderListener {

    private OnFragmentInteractionListener mListener;
    private ContentProvider contentProvider;

    public static CaracteristicFragment newInstance() {
        CaracteristicFragment fragment = new CaracteristicFragment();
        return fragment;
    }

    public CaracteristicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_caracteristic, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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
    }

    @Override
    public void onAvailableData() {
        // TODO : init view
        Log.i("TEST", contentProvider.getGame().getName());
        Log.i("TEST", contentProvider.getRound().getName());
        //Log.i("TEST", contentProvider.getCharacter().getName());
    }
}
