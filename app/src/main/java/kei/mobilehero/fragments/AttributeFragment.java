package kei.mobilehero.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.HashMap;

import kei.mobilehero.R;
import kei.mobilehero.activities.character.generic.EnumAttribute;
import kei.mobilehero.classes.general.Character;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;
import kei.mobilehero.custom.widgets.MyCustomEditText;
import kei.mobilehero.fragments.generic.FragmentBase;

public class AttributeFragment extends FragmentBase implements OnClickListener {

    private Game game;
    private Round round;
    private Character character;

    private EditText nameText;
    private SeekBar lifeText;
    private SeekBar manaText;
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
        nameText = (EditText) v.findViewById(R.id.editText_characterName_fragment_attribute);
        lifeText = (SeekBar) v.findViewById(R.id.seekBar_life_frament_attribute);
        manaText = (SeekBar) v.findViewById(R.id.seekBar_mana_frament_attribute);
        genderText = (EditText) v.findViewById(R.id.editText_characterGender_fragment_attribute);
        alignmentText = (EditText) v.findViewById(R.id.editText_characterAlignment_fragment_attribute);
        raceText = (EditText) v.findViewById(R.id.editText_characterRace_fragment_attribute);
        classNameText = (EditText) v.findViewById(R.id.editText_characterClassName_fragment_attribute);
        levelText = (MyCustomEditText) v.findViewById(R.id.editText_characterLevel_fragment_attribute);
        Button saveButton = (Button) v.findViewById(R.id.button_saveCharacter_fragment_attribute);

        saveButton.setOnClickListener(this);

        lifeText.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
        lifeText.getThumb().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);



        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        return v;
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
        nameText.setText(character.getName());
        lifeText.setProgress(character.getLife());
        manaText.setProgress(character.getMana());
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
            case R.id.button_saveCharacter_fragment_attribute:
                String characterName;
                if (!(characterName = nameText.getText().toString()).isEmpty()) {
                    character.setName(characterName);
                    character.setLife(lifeText.getProgress());
                    character.setMana(manaText.getProgress());
                    character.setGender(genderText.getText().toString());
                    character.setAlignment(alignmentText.getText().toString());
                    character.setRace(raceText.getText().toString());
                    character.setClassName(classNameText.getText().toString());
                    character.setLevel(levelText.getText().toString().isEmpty() ? 0 : Integer.parseInt(levelText.getText().toString()));

                    // And save
                    if(character.save(getActivity().getApplicationContext(), game.getName(), round.getName()))
                        getActivity().finish();
                }
                else
                    Toast.makeText(getActivity().getApplicationContext(), "Veuillez saisir un nom pour votre personnage.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
