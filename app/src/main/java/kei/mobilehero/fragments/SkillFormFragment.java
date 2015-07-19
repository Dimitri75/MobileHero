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
import kei.mobilehero.classes.attributes.Skill;
import kei.mobilehero.classes.general.Character;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;
import kei.mobilehero.classes.utils.swipe.SwipeDismissListViewTouchListener;
import kei.mobilehero.dialog.EffectCreator;
import kei.mobilehero.fragments.generic.FragmentBase;

public class SkillFormFragment extends FragmentBase implements OnClickListener {
    View v;
    private Game game;
    private Round round;
    private kei.mobilehero.classes.general.Character character;

    private EditText skillNameText;
    private EditText skillDescriptionText;
    private Skill actualSkill;

    private HashMap<String, Effect> effectsList;
    private ArrayAdapter<Effect> myAdapter;

    public SkillFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_skill_form, container, false);

        // Instantiate the views
        skillNameText = (EditText) v.findViewById(R.id.editText_skillName_new_skill);
        skillDescriptionText = (EditText) v.findViewById(R.id.editText_skillDescription_new_skill);

        Button saveButton = (Button) v.findViewById(R.id.button_saveSkill_new_skill);
        Button newEffect = (Button) v.findViewById(R.id.button_effects_new_skill);
        saveButton.setOnClickListener(this);
        newEffect.setOnClickListener(this);

        return v;
    }

    @Override
    public void onAvailableData() {
        HashMap<EnumAttribute, Object> data = (HashMap) contentProvider.getData();

        game = (Game) data.get(EnumAttribute.GAME);
        round = (Round) data.get(EnumAttribute.ROUND);
        character = (Character) data.get(EnumAttribute.CHARACTER);

        actualSkill = (Skill) data.get(EnumAttribute.SKILL);

        init();
    }

    public void init() {
        if(actualSkill != null) {
            skillNameText.setText(actualSkill.getName());
            skillDescriptionText.setText(actualSkill.getDescription());
            effectsList = actualSkill.getEffects();
        } else {
            skillNameText.setText("");
            skillDescriptionText.setText("");
            effectsList = new HashMap<>();
        }


        // Fill listView effects
        //if (effectsList.isEmpty()) return;

        myAdapter = new ArrayAdapter<>(
                getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new ArrayList<>(effectsList.values()));

        ListView listView = (ListView) v.findViewById(R.id.listView_effects_skill);
        listView.setAdapter(myAdapter);

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
                                    effectsList.remove(myAdapter.getItem(position).getName());
                                }
                                updateEffectView();
                            }
                        });
        listView.setOnTouchListener(swipeTouchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        listView.setOnScrollListener(swipeTouchListener.makeScrollListener());
    }

    private void updateEffectView() {
        myAdapter.clear();
        myAdapter.addAll(effectsList.values());
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_effects_new_skill:
                EffectCreator.show(new ArrayList<>(character.getCharacteristics().values()), getActivity(), new EffectCreator.EffectCreationListener() {
                    @Override
                    public void onEffectCreated(Effect e) {
                    effectsList.put(e.getName(), e);
                    updateEffectView();
                    }
                });
                break;
            case R.id.button_saveSkill_new_skill:
                if (!skillNameText.getText().toString().isEmpty() &&
                        (actualSkill != null || !character.getSkills().keySet().contains(skillNameText.getText().toString()))) {

                    Skill skill = new Skill();
                    skill.setName(skillNameText.getText().toString());
                    skill.setDescription(skillDescriptionText.getText().toString());
                    skill.setEffects(effectsList);
                    skill.setValue(0);

                    if(actualSkill != null)
                        character.getSkills().remove(actualSkill.getName());

                    character.getSkills().put(skill.getName(), skill);

                    // And save
                    if (character.save(getActivity().getApplicationContext(), game.getName(), round.getName()))
                        getActivity().onBackPressed();
                } else

                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.toastSkillAlreadyExists), Toast.LENGTH_SHORT).show();
                    break;
        }
    }
}
