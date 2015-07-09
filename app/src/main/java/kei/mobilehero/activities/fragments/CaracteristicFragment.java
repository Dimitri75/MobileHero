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

public class CaracteristicFragment extends FragmentBase {

    public CaracteristicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_caracteristic, container, false);
    }

    @Override
    public void onAvailableData() {
        // TODO : init view
        Log.i("TEST", contentProvider.getGame().getName());
        Log.i("TEST", contentProvider.getRound().getName());
        Log.i("TEST", contentProvider.getCharacter().getName());
    }
}
