package kei.mobilehero.activities.fragments;

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
import kei.mobilehero.activities.fragments.generic.FragmentBase;
import kei.mobilehero.classes.attributes.Caracteristic;
import kei.mobilehero.classes.general.Character;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;

public class CaracteristicFormFragment extends FragmentBase implements OnClickListener {
    View v;
    private Game game;
    private Round round;
    private kei.mobilehero.classes.general.Character character;
    private Caracteristic actualCaracteristic;

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

        actualCaracteristic = (Caracteristic) data.get(EnumAttribute.CARACTERISTIC);

        init();
    }

    public void init(){
        if(actualCaracteristic != null) {
            caracteristicNameText.setText(actualCaracteristic.getName());
            caracteristicDescriptionText.setText(actualCaracteristic.getDescription());
            if(actualCaracteristic.getValue() != 0)
                caracteristicValueText.setText(String.valueOf(actualCaracteristic.getValue()));
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
                    (actualCaracteristic != null || !character.getCaracteristics().keySet().contains(caracteristicNameText.getText().toString()))) {

                    Double value = caracteristicValueText.getText().toString().isEmpty() ? 0 : Double.valueOf(caracteristicValueText.getText().toString());

                    Caracteristic c = new Caracteristic(
                            caracteristicNameText.getText().toString(),
                            caracteristicDescriptionText.getText().toString(),
                            value
                    );

                    if(actualCaracteristic != null && !character.getCaracteristics().containsKey(c.getName()))
                        character.getCaracteristics().remove(actualCaracteristic.getName());

                    character.getCaracteristics().put(c.getName(), c);

                    // And save
                    if(character.save(getActivity().getApplicationContext(), game.getName(), round.getName()))
                        getActivity().onBackPressed();
                }
                else
                    Toast.makeText(getActivity().getApplicationContext(), "La caractéristique existe déjà ou les champs ne sont pas bien remplis.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
