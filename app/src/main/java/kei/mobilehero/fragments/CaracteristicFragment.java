package kei.mobilehero.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import kei.mobilehero.R;
import kei.mobilehero.activities.character.generic.EnumAttribute;
import kei.mobilehero.activities.character.generic.SelectionListener;
import kei.mobilehero.fragments.generic.FragmentBase;
import kei.mobilehero.classes.attributes.Caracteristic;
import kei.mobilehero.classes.general.Character;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;
import kei.mobilehero.classes.utils.swipe.SwipeDismissListViewTouchListener;

public class CaracteristicFragment extends FragmentBase {
    View v;
    private Game game;
    private Round round;
    private kei.mobilehero.classes.general.Character character;
    private SelectionListener selectionListener;

    public CaracteristicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_caracteristic, container, false);
        return v;
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

    @Override
    public void onAvailableData() {
        HashMap<EnumAttribute, Object> data = (HashMap) contentProvider.getData();

        game = (Game) data.get(EnumAttribute.GAME);
        round = (Round) data.get(EnumAttribute.ROUND);
        character = (Character) data.get(EnumAttribute.CHARACTER);

        init();
    }

    public void init(){
        if (character.getCaracteristics() == null || character.getCaracteristics().isEmpty()) return;

        final ArrayAdapter<Caracteristic> myAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new ArrayList<>(character.getCaracteristics().values()));

        ListView listView = (ListView) v.findViewById(R.id.listView_caracteristic);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectionListener.onSelected(myAdapter.getItem(position));
            }
        });

        SwipeDismissListViewTouchListener swipeTouchListener =
                new SwipeDismissListViewTouchListener(
                        listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    character.getCaracteristics().remove(myAdapter.getItem(position).getName());
                                    character.save(getActivity().getApplicationContext(), game.getName(), round.getName());
                                    onAvailableData();
                                }
                                myAdapter.notifyDataSetChanged();
                            }
                        });
        listView.setOnTouchListener(swipeTouchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        listView.setOnScrollListener(swipeTouchListener.makeScrollListener());
    }
}
