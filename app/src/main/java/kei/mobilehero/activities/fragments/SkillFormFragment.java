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
import kei.mobilehero.classes.attributes.Skill;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;

public class SkillFormFragment extends FragmentBase implements OnClickListener {
    View v;
    private Game game;
    private Round round;
    private kei.mobilehero.classes.general.Character character;

    private EditText skillNameText;
    private EditText skillDescriptionText;

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
        /*skillNameText.setText(skill.getName());
        skillDescriptionText.setText(skill.getDescription();*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_saveSkill_new_skill:
                if (!skillNameText.getText().toString().isEmpty() &&
                        !character.getSkills().keySet().contains(skillNameText.getText().toString())) {

                    Skill s = new Skill(
                            skillNameText.getText().toString(),
                            skillDescriptionText.getText().toString(),
                            0
                    );

                    character.getSkills().put(s.getName(), s);

                    // And save
                    if (character.save(getActivity().getApplicationContext(), game.getName(), round.getName()))
                        getActivity().finish();
                } else
                    Toast.makeText(getActivity().getApplicationContext(), "La compétence existe déjà ou les champs ne sont pas bien remplis.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
