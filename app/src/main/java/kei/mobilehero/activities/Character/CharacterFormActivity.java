package kei.mobilehero.activities.character;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kei.mobilehero.R;
import kei.mobilehero.activities.ActivityBase;
import kei.mobilehero.activities.fragments.generic.ContentProvider;
import kei.mobilehero.activities.fragments.generic.EnumFragment;
import kei.mobilehero.activities.fragments.generic.OnFragmentInteractionListener;
import kei.mobilehero.classes.general.Character;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;
import kei.mobilehero.classes.utils.persistence.Loader;

public class CharacterFormActivity extends ActivityBase implements OnFragmentInteractionListener, ContentProvider, SelectionListener {
    private Game game;
    private Round round;
    private kei.mobilehero.classes.general.Character character;

    HashMap<EnumFragment, Fragment> dictionaryFragments;
    ArrayList<ContentProviderListener> contentProviderListeners = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_form);

        if((game = (Game) getIntent().getExtras().get("game")) == null ||
                (round = (Round) getIntent().getExtras().get("round")) == null){
            Log.v("NewCharacter onCreate()", "Couldn't get the extras.");
            finish();
        }

        // Fragments
        initFragments();

        if (!((character = (kei.mobilehero.classes.general.Character) getIntent().getExtras().get("character")) != null))
            character = new Character("");

        // Signal that data is available
        for(ContentProviderListener listener : contentProviderListeners) {
            listener.onAvailableData();
        }
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
                if (isLandscape()) {
                    hideFragments(dictionaryFragments, null);
                    showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.CARACTERISTICS));
                }
                else if (character.save(getApplicationContext(), game.getName(), round.getName())) {
                        Intent i = new Intent(getApplicationContext(), AttributesActivity.class);
                        i.putExtra("game", game);
                        i.putExtra("round", round);
                        i.putExtra("character", character);
                        i.putExtra("argumentKey", EnumFragment.CARACTERISTICS);
                        startActivity(i);
                }
                break;
            case R.id.button_skills_new_character:
                if (isLandscape()) {
                    hideFragments(dictionaryFragments, null);
                    showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.SKILLS));
                }
                else if (character.save(getApplicationContext(), game.getName(), round.getName())) {
                        Intent i = new Intent(getApplicationContext(), AttributesActivity.class);
                        i.putExtra("game", game);
                        i.putExtra("round", round);
                        i.putExtra("character", character);
                        i.putExtra("argumentKey", EnumFragment.SKILLS);
                        startActivity(i);
                }
                break;
            case R.id.button_equipment_new_character:
                if (isLandscape()) {
                    hideFragments(dictionaryFragments, null);
                    showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.EQUIPMENT));
                }
                else if (character.save(getApplicationContext(), game.getName(), round.getName())) {
                        Intent i = new Intent(getApplicationContext(), AttributesActivity.class);
                        i.putExtra("game", game);
                        i.putExtra("round", round);
                        i.putExtra("character", character);
                        i.putExtra("argumentKey", EnumFragment.EQUIPMENT);
                        startActivity(i);
                }
                break;
            case R.id.button_caracteristic_fragment_caracteristic:
                if (isLandscape()) {
                    hideFragments(dictionaryFragments, null);
                    showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.CARACTERISTIC_FORM));
                }
                break;
            case R.id.button_skill_fragment_skill:
                if (isLandscape()) {
                    hideFragments(dictionaryFragments, null);
                    showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.SKILL_FORM));
                }
                break;
            case R.id.button_equipment_fragment_equipment:
                if (isLandscape()) {
                    hideFragments(dictionaryFragments, null);
                    showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.EQUIPMENT_FORM));
                }
                break;
            case R.id.button_attributes_new_character:
                hideFragments(dictionaryFragments, null);
                showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.ATTRIBUTE));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // Reload the character from the database
        character = Loader.getInstance().loadCharacterById(getApplicationContext(), character.getId(), game, round);

        for(ContentProviderListener listener : contentProviderListeners) {
            listener.onAvailableData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_character, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}

    @Override
    public Object getData() {
        List l =  new ArrayList<>();

        l.add(game);
        l.add(round);
        l.add(character);
        l.add(null);
        l.add(null);
        l.add(null);

        return l;
    }

    @Override
    public void addContentListener(ContentProviderListener listener) {
        contentProviderListeners.add(listener);
    }

    @Override
    public void removeContentListener(ContentProviderListener listener) {
        contentProviderListeners.remove(listener);
    }

    @Override
    public void onSelected(Object o) {

    }
}
