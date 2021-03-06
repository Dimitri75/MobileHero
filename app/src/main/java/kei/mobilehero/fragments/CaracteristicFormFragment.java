package kei.mobilehero.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import kei.mobilehero.R;
import kei.mobilehero.activities.character.generic.EnumAttribute;
import kei.mobilehero.classes.attributes.Characteristic;
import kei.mobilehero.classes.general.Character;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;
import kei.mobilehero.fragments.generic.FragmentBase;

public class CaracteristicFormFragment extends FragmentBase implements OnClickListener {
    View v;
    private Game game;
    private Round round;
    private kei.mobilehero.classes.general.Character character;
    private Characteristic actualCharacteristic;

    private EditText caracteristicNameText;
    private EditText caracteristicDescriptionText;
    private EditText caracteristicValueText;

    public CaracteristicFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_caracteristic_form, container, false);

        // Instantiate the views
        caracteristicNameText = (EditText) v.findViewById(R.id.editText_caracteristicName_new_caracteristic);
        caracteristicDescriptionText = (EditText) v.findViewById(R.id.editText_caracteristicDescription_new_caracteristic);
        caracteristicValueText = (EditText) v.findViewById(R.id.editText_caracteristicValue_new_caracteristic);

        Button saveButton = (Button) v.findViewById(R.id.button_saveCaracteristic_new_caracteristic);
        saveButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onAvailableData() {
        HashMap<EnumAttribute, Object> data = (HashMap) contentProvider.getData();

        game = (Game) data.get(EnumAttribute.GAME);
        round = (Round) data.get(EnumAttribute.ROUND);
        character = (Character) data.get(EnumAttribute.CHARACTER);

        actualCharacteristic = (Characteristic) data.get(EnumAttribute.CARACTERISTIC);

        init();
    }

    public void init(){
        if(actualCharacteristic != null) {
            caracteristicNameText.setText(actualCharacteristic.getName());
            caracteristicDescriptionText.setText(actualCharacteristic.getDescription());
            if(actualCharacteristic.getValue() != 0)
                caracteristicValueText.setText(String.valueOf(actualCharacteristic.getValue()));
            else
                caracteristicValueText.setText("");
        } else {
            caracteristicNameText.setText("");
            caracteristicDescriptionText.setText("");
            caracteristicValueText.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_saveCaracteristic_new_caracteristic:
                
                if (!caracteristicNameText.getText().toString().isEmpty() &&
                    (actualCharacteristic != null || !character.getCharacteristics().keySet().contains(caracteristicNameText.getText().toString()))) {

                    Double value = caracteristicValueText.getText().toString().isEmpty() ? 0 : Double.valueOf(caracteristicValueText.getText().toString());

                    Characteristic c = new Characteristic(
                            caracteristicNameText.getText().toString(),
                            caracteristicDescriptionText.getText().toString(),
                            value
                    );

                    if(actualCharacteristic != null && !character.getCharacteristics().containsKey(c.getName()))
                        character.getCharacteristics().remove(actualCharacteristic.getName());

                    character.getCharacteristics().put(c.getName(), c);

                    // And save
                    if(character.save(getActivity().getApplicationContext(), game.getName(), round.getName()))
                        getActivity().onBackPressed();
                }
                else
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.toastCharacteristicAlreadyExists), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
