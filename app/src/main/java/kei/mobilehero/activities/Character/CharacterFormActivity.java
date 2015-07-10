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

import kei.mobilehero.R;
import kei.mobilehero.activities.ActivityBase;
import kei.mobilehero.activities.fragments.generic.ContentProvider;
import kei.mobilehero.activities.fragments.generic.EnumFragment;
import kei.mobilehero.activities.fragments.generic.FragmentBase;
import kei.mobilehero.activities.fragments.generic.OnFragmentInteractionListener;
import kei.mobilehero.classes.general.Character;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;

public class CharacterFormActivity extends ActivityBase implements OnFragmentInteractionListener, ContentProvider {
    private Game game;
    private Round round;
    private kei.mobilehero.classes.general.Character character;

    HashMap<EnumFragment, Fragment> dictionaryFragments;
    ArrayList<ContentProviderListener> contentProviderListeners = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_character);

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
        dictionaryFragments.put(EnumFragment.NEW_CARACTERISTIC, fragment_new_caracteristic);
        dictionaryFragments.put(EnumFragment.NEW_SKILL, fragment_new_skill);
        dictionaryFragments.put(EnumFragment.NEW_EQUIPMENT, fragment_new_equipment);

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
                    showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.NEW_CARACTERISTIC));
                }
                break;
            case R.id.button_skill_fragment_skill:
                if (isLandscape()) {
                    hideFragments(dictionaryFragments, null);
                    showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.NEW_SKILL));
                }
                break;
            case R.id.button_equipment_fragment_equipment:
                if (isLandscape()) {
                    hideFragments(dictionaryFragments, null);
                    showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.NEW_EQUIPMENT));
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

        // Reload fragments with the new data
        for (Fragment frag : dictionaryFragments.values())
            ((FragmentBase) frag).onAvailableData();

        /*for(ContentProviderListener listener : contentProviderListeners) {
            listener.onAvailableData();
        }*/
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
    public Game getGame() {
        return game;
    }

    @Override
    public Round getRound() {
        return round;
    }

    @Override
    public Character getCharacter() {
        return character;
    }

    @Override
    public void addContentListener(ContentProviderListener listener) {
        contentProviderListeners.add(listener);
    }

    @Override
    public void removeContentListener(ContentProviderListener listener) {
        contentProviderListeners.remove(listener);
    }
}
