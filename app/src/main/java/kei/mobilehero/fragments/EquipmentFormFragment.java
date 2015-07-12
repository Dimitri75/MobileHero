package kei.mobilehero.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import kei.mobilehero.R;
import kei.mobilehero.activities.character.generic.EnumAttribute;
import kei.mobilehero.classes.attributes.Caracteristic;
import kei.mobilehero.fragments.generic.CharacteristicSelector;
import kei.mobilehero.fragments.generic.FragmentBase;
import kei.mobilehero.classes.attributes.Equipment;
import kei.mobilehero.classes.general.Character;
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
    private Equipment actualEquipment;

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
        HashMap<EnumAttribute, Object> data = (HashMap) contentProvider.getData();

        game = (Game) data.get(EnumAttribute.GAME);
        round = (Round) data.get(EnumAttribute.ROUND);
        character = (Character) data.get(EnumAttribute.CHARACTER);

        actualEquipment = (Equipment) data.get(EnumAttribute.EQUIPMENT);

        init();
    }

    public void init() {
        if(actualEquipment != null) {
            equipmentNameText.setText(actualEquipment.getName());
            equipmentDescriptionText.setText(actualEquipment.getDescription());
            equipmentPositionText.setText(actualEquipment.getEquipmentPosition());
            if (actualEquipment.getValue() != 0)
                equipmentWeightText.setText(String.valueOf(actualEquipment.getValue()));
            else
                equipmentWeightText.setText("");
        } else {
            equipmentNameText.setText("");
            equipmentDescriptionText.setText("");
            equipmentPositionText.setText("");
            equipmentWeightText.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_effects_new_equipment:
                CharacteristicSelector.show(new ArrayList<>(character.getCaracteristics().values()), getActivity(), new CharacteristicSelector.CharacteristicListener() {
                    @Override
                    public void onCharacteristicSelected(Caracteristic c) {
                        Toast.makeText(getActivity().getApplicationContext(), c.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.button_saveEquipment_new_equipment:
                if (!equipmentNameText.getText().toString().isEmpty()) {

                    Double value = equipmentWeightText.getText().toString().isEmpty() ? 0 : Double.valueOf(equipmentWeightText.getText().toString());

                    Equipment e = new Equipment(
                            equipmentNameText.getText().toString(),
                            equipmentDescriptionText.getText().toString(),
                            value,
                            equipmentPositionText.getText().toString()
                    );

                    if(actualEquipment != null)
                        character.getEquipments().remove(actualEquipment.getId());

                    character.getEquipments().put(e.getId(), e);

                    // And save
                    if (character.save(getActivity().getApplicationContext(), game.getName(), round.getName()))
                        getActivity().onBackPressed();
                } else
                    Toast.makeText(getActivity().getApplicationContext(), "Les champs du formulaire ne sont pas bien remplis.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
