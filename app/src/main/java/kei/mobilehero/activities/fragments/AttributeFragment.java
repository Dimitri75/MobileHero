package kei.mobilehero.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import kei.mobilehero.R;
import kei.mobilehero.classes.general.*;
import kei.mobilehero.classes.general.Character;
import kei.mobilehero.custom.widgets.MyCustomEditText;

public class AttributeFragment extends FragmentBase implements View.OnClickListener {

    private Game game;
    private Round round;
    private Character character;

    private EditText nameText;
    private EditText genderText;
    private EditText alignmentText;
    private EditText raceText;
    private EditText classNameText;
    private EditText levelText;

    public AttributeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_attribute, container, false);

        // Instantiate the views
        nameText = (EditText) v.findViewById(R.id.editText_characterName_new_character);
        genderText = (EditText) v.findViewById(R.id.editText_characterGender_new_character);
        alignmentText = (EditText) v.findViewById(R.id.editText_characterAlignment_new_character);
        raceText = (EditText) v.findViewById(R.id.editText_characterRace_new_character);
        classNameText = (EditText) v.findViewById(R.id.editText_characterClassName_new_character);
        levelText = (MyCustomEditText) v.findViewById(R.id.editText_characterLevel_new_character);
        Button saveButton = (Button) v.findViewById(R.id.button_saveCharacter_new_character);

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

    public void init(){
        nameText.setText(character.getName());
        genderText.setText(character.getGender());
        alignmentText.setText(character.getAlignment());
        raceText.setText(character.getRace());
        classNameText.setText(character.getClassName());
        if(character.getLevel() != 0)
            levelText.setText(String.valueOf(character.getLevel()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_saveCharacter_new_character:
                String characterName;
                if (!(characterName = nameText.getText().toString()).isEmpty()) {
                    character.setName(characterName);
                    character.setGender(genderText.getText().toString());
                    character.setAlignment(alignmentText.getText().toString());
                    character.setRace(raceText.getText().toString());
                    character.setClassName(classNameText.getText().toString());
                    character.setLevel(levelText.getText().toString().isEmpty() ? 1 : Integer.parseInt(levelText.getText().toString()));

                    // And save
                    if(character.save(getActivity().getApplicationContext(), game.getName(), round.getName()))
                        getActivity().finish();
                }
                break;
        }
    }
}
