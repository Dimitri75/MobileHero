package kei.mobilehero.activities.character;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import kei.mobilehero.R;
import kei.mobilehero.activities.character.generic.ActivityAttributesBase;
import kei.mobilehero.activities.character.generic.EnumAttribute;
import kei.mobilehero.classes.general.Character;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;
import kei.mobilehero.classes.utils.persistence.Loader;
import kei.mobilehero.classes.utils.persistence.PDFExporter;
import kei.mobilehero.dialog.CharacterSelector;
import kei.mobilehero.fragments.generic.EnumFragment;
import kei.mobilehero.fragments.generic.OnFragmentInteractionListener;

import com.itextpdf.text.DocumentException;

public class CharacterFormActivity extends ActivityAttributesBase implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_form);

        if((game = (Game) getIntent().getExtras().get("game")) == null ||
                (round = (Round) getIntent().getExtras().get("round")) == null){
            Log.v("NewCharacter onCreate()", "Couldn't get the extras.");
            finish();
        }

        if (!((character = (kei.mobilehero.classes.general.Character) getIntent().getExtras().get("character")) != null))
            character = new Character("");

        initFragments();
        initData();

        signalAvailableData();
    }

    public void initFragments(){
        Fragment fragment_attribute = fm.findFragmentById(R.id.fragment_attribute_new_character);
        Fragment fragment_caracteristics = fm.findFragmentById(R.id.fragment_caracteristic_new_character);
        Fragment fragment_skills = fm.findFragmentById(R.id.fragment_skill_new_character);
        Fragment fragment_equipment = fm.findFragmentById(R.id.fragment_equipment_new_character);
        Fragment fragment_new_caracteristic = fm.findFragmentById(R.id.fragment_new_caracteristic_new_character);
        Fragment fragment_new_skill = fm.findFragmentById(R.id.fragment_new_skill_new_character);
        Fragment fragment_new_equipment = fm.findFragmentById(R.id.fragment_new_equipment_new_character);

        dictionaryFragments = new HashMap<>();
        dictionaryFragments.put(EnumFragment.ATTRIBUTE, fragment_attribute);
        dictionaryFragments.put(EnumFragment.CARACTERISTICS, fragment_caracteristics);
        dictionaryFragments.put(EnumFragment.SKILLS, fragment_skills);
        dictionaryFragments.put(EnumFragment.EQUIPMENT, fragment_equipment);
        dictionaryFragments.put(EnumFragment.CARACTERISTIC_FORM, fragment_new_caracteristic);
        dictionaryFragments.put(EnumFragment.SKILL_FORM, fragment_new_skill);
        dictionaryFragments.put(EnumFragment.EQUIPMENT_FORM, fragment_new_equipment);

        hideFragments(dictionaryFragments, dictionaryFragments.get(EnumFragment.ATTRIBUTE));
    }

    public void buttonOnClick(View v) {
        switch(v.getId()){
            case R.id.button_caracteristics_new_character:
                if(currentFragment != EnumFragment.CARACTERISTICS) {
                    if (isLandscape()) {
                        hideFragments(dictionaryFragments, null);
                        showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.CARACTERISTICS));
                        currentFragment = EnumFragment.CARACTERISTICS;
                    } else if (character.save(getApplicationContext(), game.getName(), round.getName())) {
                        Intent i = new Intent(getApplicationContext(), AttributesActivity.class);
                        i.putExtra("game", game);
                        i.putExtra("round", round);
                        i.putExtra("character", character);
                        i.putExtra("argumentKey", EnumFragment.CARACTERISTICS);
                        startActivity(i);
                        currentFragment = null;
                    }
                }
                break;
            case R.id.button_skills_new_character:
                if(currentFragment != EnumFragment.SKILLS) {
                    if (isLandscape()) {
                        hideFragments(dictionaryFragments, null);
                        showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.SKILLS));
                        currentFragment = EnumFragment.SKILLS;
                    } else if (character.save(getApplicationContext(), game.getName(), round.getName())) {
                        Intent i = new Intent(getApplicationContext(), AttributesActivity.class);
                        i.putExtra("game", game);
                        i.putExtra("round", round);
                        i.putExtra("character", character);
                        i.putExtra("argumentKey", EnumFragment.SKILLS);
                        startActivity(i);
                        currentFragment = null;
                    }
                }
                break;
            case R.id.button_equipment_new_character:
                if(currentFragment != EnumFragment.EQUIPMENT) {
                    if (isLandscape()) {
                        hideFragments(dictionaryFragments, null);
                        showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.EQUIPMENT));
                        currentFragment = EnumFragment.EQUIPMENT;
                    } else if (character.save(getApplicationContext(), game.getName(), round.getName())) {
                        Intent i = new Intent(getApplicationContext(), AttributesActivity.class);
                        i.putExtra("game", game);
                        i.putExtra("round", round);
                        i.putExtra("character", character);
                        i.putExtra("argumentKey", EnumFragment.EQUIPMENT);
                        startActivity(i);
                        currentFragment = null;
                    }
                }
                break;
            case R.id.button_caracteristic_fragment_caracteristic:
                if (isLandscape() && currentFragment != EnumFragment.CARACTERISTIC_FORM) {
                    hideFragments(dictionaryFragments, null);
                    showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.CARACTERISTIC_FORM));
                    currentFragment = EnumFragment.CARACTERISTIC_FORM;
                    data.put(EnumAttribute.CARACTERISTIC, null);
                }
                break;
            case R.id.button_skill_fragment_skill:
                if (isLandscape()) {
                    hideFragments(dictionaryFragments, null);
                    showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.SKILL_FORM));
                    currentFragment = EnumFragment.SKILL_FORM;
                    data.put(EnumAttribute.SKILL, null);
                }
                break;
            case R.id.button_equipment_fragment_equipment:
                if (isLandscape()) {
                    hideFragments(dictionaryFragments, null);
                    showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.EQUIPMENT_FORM));
                    currentFragment = EnumFragment.EQUIPMENT_FORM;
                    data.put(EnumAttribute.EQUIPMENT, null);
                }
                break;
            case R.id.button_attributes_new_character:
                if (currentFragment !=  EnumFragment.ATTRIBUTE) {
                    hideFragments(dictionaryFragments, null);
                    showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.ATTRIBUTE));
                    currentFragment = EnumFragment.ATTRIBUTE;
                }
        }
    }

    @Override
    public void onBackPressed() {
        if(isLandscape())
            super.onBackPressed();
        else
            finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // Reload the character from the database
        character = Loader.getInstance().loadCharacterById(getApplicationContext(), character.getId(), game, round);
        initData();

        signalAvailableData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_character, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_saveAsModel) {
            if (character != null && !character.getName().isEmpty()){
                character.saveAsModel(getApplicationContext());
                Toast.makeText(getApplicationContext(), getString(R.string.toastCharSavedAsModel), Toast.LENGTH_SHORT).show();
            }
        }
        else if(id == R.id.action_exportpdf) {
            File file = new File(getApplicationContext().getExternalCacheDir(),  "test.pdf");

            try {
                PDFExporter.export(getApplicationContext(), character, file);
            } catch(IOException e) {
                e.printStackTrace();
            } catch(DocumentException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            startActivityForResult(intent, 10);
        }
        else if (id == R.id.action_loadModel) {
            // Open a new dialog window
            // Loader.getInstance().loadCharacterModels(getApplicationContext());
            CharacterSelector.show(this.character, this, new CharacterSelector.CharacterSelectorListener() {
                @Override
                public void onCharacterSelected(Character modelCharacter) {
                    character.setAlignment(modelCharacter.getAlignment());
                    character.setCaracteristics(modelCharacter.getCharacteristics());
                    character.setClassName(modelCharacter.getClassName());
                    character.setEquipments(modelCharacter.getEquipments());
                    character.setGender(modelCharacter.getGender());
                    character.setRace(modelCharacter.getRace());
                    character.setSkills(modelCharacter.getSkills());

                    initData();
                    signalAvailableData();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    private View getContentView() {
        return getWindow().getDecorView().findViewById(android.R.id.content);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}
}
