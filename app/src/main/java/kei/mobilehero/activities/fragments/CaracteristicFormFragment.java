package kei.mobilehero.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import kei.mobilehero.R;
import kei.mobilehero.activities.fragments.generic.FragmentBase;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;
import kei.mobilehero.custom.widgets.MyCustomEditText;

public class CaracteristicFormFragment extends FragmentBase implements OnClickListener {
    View v;
    private Game game;
    private Round round;
    private kei.mobilehero.classes.general.Character character;

    private EditText caracteristicNameText;
    private EditText caracteristicValueText;

    public CaracteristicFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_new_caracteristic, container, false);

        // Instantiate the views
        caracteristicNameText = (EditText) v.findViewById(R.id.editText_characterName_fragment_attribute);
        caracteristicValueText = (MyCustomEditText) v.findViewById(R.id.editText_characterLevel_fragment_attribute);
        Button saveButton = (Button) v.findViewById(R.id.button_saveCaracteristic_new_caracteristic);

        saveButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onAvailableData() {
        game = contentProvider.getGame();
        round = contentProvider.getRound();
        character = contentProvider.getCharacter();

        init();
    }

    public void init(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_saveCaracteristic_new_caracteristic:
                // TODO
                break;
        }
    }
}
