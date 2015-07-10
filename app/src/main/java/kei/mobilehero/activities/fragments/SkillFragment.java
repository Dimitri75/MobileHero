package kei.mobilehero.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import kei.mobilehero.R;
import kei.mobilehero.classes.attributes.Skill;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;

public class SkillFragment extends FragmentBase implements OnClickListener {
    View v;
    private Game game;
    private Round round;
    private kei.mobilehero.classes.general.Character character;

    public SkillFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_skill, container, false);

        // Instantiate the views
        Button newCaracteristic = (Button) v.findViewById(R.id.button_skill_fragment_skill);

        newCaracteristic.setOnClickListener(this);

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
        if (character.getSkills() == null || character.getSkills().isEmpty()) return;

        ArrayAdapter<Skill> myAdapter = new ArrayAdapter<>(
                getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new ArrayList<>(character.getSkills().values()));

        ListView listView = (ListView) v.findViewById(R.id.listView_skill);
        listView.setAdapter(myAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_skill_fragment_skill:
                // TODO
                break;
        }
    }
}

