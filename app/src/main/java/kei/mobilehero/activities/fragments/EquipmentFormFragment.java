package kei.mobilehero.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kei.mobilehero.R;
import kei.mobilehero.activities.fragments.generic.FragmentBase;
import kei.mobilehero.classes.attributes.Equipment;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;

public class EquipmentFormFragment extends FragmentBase implements OnClickListener {
    View v;
    private Game game;
    private Round round;
    private kei.mobilehero.classes.general.Character character;

    private EditText equipmentNameText;
    private EditText equipmentDescriptionText;
    private EditText equipmentWeightText;
    private EditText equipmentPositionText;

    public EquipmentFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_equipment_form, container, false);

        // Instantiate the views
        equipmentNameText = (EditText) v.findViewById(R.id.editText_equipmentName_new_equipment);
        equipmentDescriptionText = (EditText) v.findViewById(R.id.editText_equipmentDescription_new_equipment);
        equipmentWeightText = (EditText) v.findViewById(R.id.editText_equipmentValue_new_equipment);
        equipmentPositionText = (EditText) v.findViewById(R.id.editText_equipmentPosition_new_equipment);

        Button saveButton = (Button) v.findViewById(R.id.button_saveEquipment_new_equipment);
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

    public void init() {
        // TODO set text to the editText
        /*equipmentNameText.setText(equipment.getName());
        equipmentDescriptionText.setText(equipment.getDescription();
        equipmentPositionText.setText(equipment.getPosition();
        if(equipment.getValue() != 0)
            equipmentWeightText.setText(String.valueOf(equipment.getValue()));*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_saveEquipment_new_equipment:
                if (!equipmentNameText.getText().toString().isEmpty()) {

                    Double value = equipmentWeightText.getText().toString().isEmpty() ? 0 : Double.valueOf(equipmentWeightText.getText().toString());

                    Equipment e = new Equipment(
                            equipmentNameText.getText().toString(),
                            equipmentDescriptionText.getText().toString(),
                            value,
                            equipmentPositionText.getText().toString()
                    );

                    character.getEquipments().put(e.getName(), e);

                    // And save
                    if (character.save(getActivity().getApplicationContext(), game.getName(), round.getName()))
                        getActivity().finish();
                } else
                    Toast.makeText(getActivity().getApplicationContext(), "Les champs du formulaire ne sont pas bien remplis.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
