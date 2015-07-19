package kei.mobilehero.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import kei.mobilehero.R;
import kei.mobilehero.activities.character.generic.EnumAttribute;
import kei.mobilehero.classes.general.Character;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;
import kei.mobilehero.classes.utils.DownloadImageTask;
import kei.mobilehero.custom.widgets.MyCustomEditText;
import kei.mobilehero.fragments.generic.FragmentBase;

public class AttributeFragment extends FragmentBase implements OnClickListener {

    private Game game;
    private Round round;
    private Character character;

    private EditText nameText;
    private SeekBar lifeBar;
    private SeekBar manaBar;
    private EditText genderText;
    private EditText alignmentText;
    private EditText raceText;
    private EditText classNameText;
    private EditText levelText;
    private TextView manaText;
    private TextView lifeText;
    private TextView equipmentWeight;
    private ImageView imageAvatar;

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
        lifeBar = (SeekBar) v.findViewById(R.id.seekBar_life_frament_attribute);
        manaBar = (SeekBar) v.findViewById(R.id.seekBar_mana_frament_attribute);
        genderText = (EditText) v.findViewById(R.id.editText_characterGender_fragment_attribute);
        alignmentText = (EditText) v.findViewById(R.id.editText_characterAlignment_fragment_attribute);
        raceText = (EditText) v.findViewById(R.id.editText_characterRace_fragment_attribute);
        classNameText = (EditText) v.findViewById(R.id.editText_characterClassName_fragment_attribute);
        levelText = (MyCustomEditText) v.findViewById(R.id.editText_characterLevel_fragment_attribute);
        manaText = (TextView) v.findViewById(R.id.textView_mana);
        lifeText = (TextView) v.findViewById(R.id.textView_life);
        equipmentWeight = (TextView) v.findViewById(R.id.textView1_characterEquipmentWeight_fragment_attribute);
        Button saveButton = (Button) v.findViewById(R.id.button_saveCharacter_fragment_attribute);
        imageAvatar = (ImageView) v.findViewById(R.id.imageButton_profile);
        imageAvatar.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageAvatar.setImageDrawable(getResources().getDrawable(R.drawable.mystery));

        saveButton.setOnClickListener(this);
        imageAvatar.setOnClickListener(this);

        initSeekBars();

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

    public void initSeekBars(){

        lifeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lifeText.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        manaBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                manaText.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void init(){
        nameText.setText(character.getName());
        lifeBar.setProgress(character.getLife());
        manaBar.setProgress(character.getMana());
        genderText.setText(character.getGender());
        alignmentText.setText(character.getAlignment());
        raceText.setText(character.getRace());
        classNameText.setText(character.getClassName());
        equipmentWeight.setText(String.valueOf(character.getEquipmentWeight()));
        if(character.getLevel() != 0)
            levelText.setText(String.valueOf(character.getLevel()));
        if(character.getAvatar() != null)
            imageAvatar.setImageBitmap(character.getAvatar());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_saveCharacter_fragment_attribute:
                String characterName;
                if (!(characterName = nameText.getText().toString()).isEmpty()) {
                    character.setName(characterName);
                    character.setLife(lifeBar.getProgress());
                    character.setMana(manaBar.getProgress());
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
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.toastTypeACharacterName), Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageButton_profile:
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                final EditText edittext= new EditText(getActivity().getApplicationContext());
                edittext.setTextColor(Color.BLACK);
                alert.setMessage(getActivity().getApplicationContext().getString(R.string.enter_link));
                alert.setTitle(getActivity().getApplicationContext().getString(R.string.character_image));

                alert.setView(edittext);

                alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String YouEditTextValue = edittext.getText().toString();

                        new DownloadImageTask() {
                            @Override
                            public void onImageReady(Bitmap result) {
                                character.setAvatar(result);
                                imageAvatar.setImageBitmap(result);
                            }

                        }.execute(edittext.getText().toString());
                    }
                });
                alert.setNegativeButton(android.R.string.cancel, null);

                alert.show();

                break;
        }
    }
}
