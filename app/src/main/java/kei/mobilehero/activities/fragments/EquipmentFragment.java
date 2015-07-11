package kei.mobilehero.activities.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import kei.mobilehero.R;
import kei.mobilehero.activities.character.SelectionListener;
import kei.mobilehero.activities.fragments.generic.FragmentBase;
import kei.mobilehero.classes.attributes.Equipment;
import kei.mobilehero.classes.general.*;

public class EquipmentFragment extends FragmentBase {
    View v;
    private Game game;
    private Round round;
    private kei.mobilehero.classes.general.Character character;
    private SelectionListener selectionListener;

    public EquipmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_equipment, container, false);

        return v;
    }

    @Override
    public void onAvailableData() {
        List data = (List) contentProvider.getData();

        game = (Game) data.get(0);
        round = (Round) data.get(1);
        character = (kei.mobilehero.classes.general.Character) data.get(2);

        init();
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            selectionListener = (SelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SelectionListener");
        }
    }

    public void init(){
        if (character.getEquipments() == null || character.getEquipments().isEmpty()) return;

        final ArrayAdapter<Equipment> myAdapter = new ArrayAdapter<>(
                getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new ArrayList<>(character.getEquipments().values()));

        ListView listView = (ListView) v.findViewById(R.id.listView_equipment);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectionListener.onSelected(myAdapter.getItem(position));
            }
        });
    }
}
