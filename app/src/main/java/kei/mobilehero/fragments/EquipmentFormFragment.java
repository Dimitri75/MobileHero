package kei.mobilehero.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import kei.mobilehero.R;
import kei.mobilehero.activities.character.generic.EnumAttribute;
import kei.mobilehero.classes.attributes.Effect;
import kei.mobilehero.classes.attributes.Equipment;
import kei.mobilehero.classes.general.Character;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;
import kei.mobilehero.classes.utils.swipe.SwipeDismissListViewTouchListener;
import kei.mobilehero.fragments.generic.EffectCreator;
import kei.mobilehero.fragments.generic.FragmentBase;

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
    private HashMap<String, Effect> effectsList;

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

        Button newEffect = (Button) v.findViewById(R.id.button_effects_new_equipment);
        Button saveButton = (Button) v.findViewById(R.id.button_saveEquipment_new_equipment);
        saveButton.setOnClickListener(this);
        newEffect.setOnClickListener(this);

        effectsList = new HashMap<>();

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
            effectsList = actualEquipment.getEffects();
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

        // Fill listView effects
        if (effectsList.isEmpty()) return;

        final ArrayAdapter<Effect> myAdapter = new ArrayAdapter<>(
                getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new ArrayList<>(effectsList.values()));

        ListView listView = (ListView) v.findViewById(R.id.listView_effects_equipment);
        listView.setAdapter(myAdapter);

        SwipeDismissListViewTouchListener swipeTouchListener =
                new SwipeDismissListViewTouchListener(
                        listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return false;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    effectsList.remove(myAdapter.getItem(position).getName());
                                    character.getEquipments().get(actualEquipment.getId()).setEffects(effectsList);
                                    character.save(getActivity().getApplicationContext(), game.getName(), round.getName());
                                }
                                myAdapter.notifyDataSetChanged();
                            }
                        });
        listView.setOnTouchListener(swipeTouchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        listView.setOnScrollListener(swipeTouchListener.makeScrollListener());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_effects_new_equipment:
                EffectCreator.show(new ArrayList<>(character.getCaracteristics().values()), getActivity(), new EffectCreator.EffectCreationListener() {
                    @Override
                    public void onEffectCreated(Effect e) {
                        effectsList.put(e.getName(), e);
                        init();
                    }
                });
                break;
            case R.id.button_saveEquipment_new_equipment:
                if (!equipmentNameText.getText().toString().isEmpty()) {

                    Double value = equipmentWeightText.getText().toString().isEmpty() ? 0 : Double.valueOf(equipmentWeightText.getText().toString());

                    Equipment equipment = new Equipment();
                    equipment.setName(equipmentNameText.getText().toString());
                    equipment.setDescription(equipmentDescriptionText.getText().toString());
                    equipment.setValue(value);
                    equipment.setEquipmentPosition(equipmentPositionText.getText().toString());
                    equipment.setEffects(effectsList);

                    if(actualEquipment != null)
                        character.getEquipments().remove(actualEquipment.getId());

                    character.getEquipments().put(equipment.getId(), equipment);

                    // And save
                    if (character.save(getActivity().getApplicationContext(), game.getName(), round.getName())){
                        effectsList = new HashMap<>();
                        getActivity().onBackPressed();
                    }
                } else
                    Toast.makeText(getActivity().getApplicationContext(), "Les champs du formulaire ne sont pas bien remplis.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
