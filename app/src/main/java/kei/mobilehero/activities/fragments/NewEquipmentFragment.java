package kei.mobilehero.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import kei.mobilehero.R;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;

public class NewEquipmentFragment extends FragmentBase implements OnClickListener {
    View v;
    private Game game;
    private Round round;
    private kei.mobilehero.classes.general.Character character;

    public NewEquipmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_new_equipment, container, false);

        /*// Instantiate the views
        Button newCaracteristic = (Button) v.findViewById(R.id.button_saveEquipment_fragment_new_skill);

        newCaracteristic.setOnClickListener(this);*/

        return v;
    }

    @Override
    public void onAvailableData() {
        game = contentProvider.getGame();
        round = contentProvider.getRound();
        character = contentProvider.getCharacter();

        init();
    }

    public void init(){}

    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.button_saveEquipment_fragment_new_skill:
                // TODO
                break;
        }*/
    }
}
