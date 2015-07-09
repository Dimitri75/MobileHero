package kei.mobilehero.activities.character;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import kei.mobilehero.R;
import kei.mobilehero.activities.fragments.ContentProvider;
import kei.mobilehero.activities.fragments.OnFragmentInteractionListener;
import kei.mobilehero.activities.dice.DicesActivity;
import kei.mobilehero.classes.general.Character;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;

public class NewCharacterActivity extends ActionBarActivity implements OnFragmentInteractionListener, ContentProvider {
    private Game game;
    private Round round;
    private Character character;

    FragmentManager fm;
    HashMap<String, Fragment> dictionaryFragments;
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
        fm = getFragmentManager();
        Fragment fragment_attribute = fm.findFragmentById(R.id.fragment_attribute_new_character);
        Fragment fragment_caracteristics = fm.findFragmentById(R.id.fragment_caracteristic_new_character);
        Fragment fragment_skills = fm.findFragmentById(R.id.fragment_skill_new_character);
        Fragment fragment_equipment = fm.findFragmentById(R.id.fragment_equipment_new_character);
        Fragment fragment_new_caracteristic = fm.findFragmentById(R.id.fragment_new_caracteristic_new_character);
        Fragment fragment_new_skill = fm.findFragmentById(R.id.fragment_new_skill_new_character);
        Fragment fragment_new_equipment = fm.findFragmentById(R.id.fragment_new_equipment_new_character);

        dictionaryFragments = new HashMap<>();
        dictionaryFragments.put("attribute", fragment_attribute);
        dictionaryFragments.put("caracteristics", fragment_caracteristics);
        dictionaryFragments.put("skills", fragment_skills);
        dictionaryFragments.put("equipment", fragment_equipment);
        dictionaryFragments.put("new_caracteristic", fragment_new_caracteristic);
        dictionaryFragments.put("new_skill", fragment_new_skill);
        dictionaryFragments.put("new_equipment", fragment_new_equipment);

        hideFragments(dictionaryFragments, dictionaryFragments.get("attribute"));

        if (!((character = (Character) getIntent().getExtras().get("character")) != null))
            character = new Character("");

        // Signal that data is available
        for(ContentProviderListener listener : contentProviderListeners) {
            listener.onAvailableData();
        }
    }

    public void buttonOnClick(View v) {
        switch(v.getId()){
            case R.id.button_caracteristics_new_character:
                if (isLandscape()) hideFragments(dictionaryFragments, null);
                else hideFragments(dictionaryFragments, dictionaryFragments.get("attribute"));

                showFragmentWithAnimation(dictionaryFragments.get("caracteristics"));
                break;
            case R.id.button_skills_new_character:
                if (isLandscape()) hideFragments(dictionaryFragments, null);
                else hideFragments(dictionaryFragments, dictionaryFragments.get("attribute"));

                showFragmentWithAnimation(dictionaryFragments.get("skills"));
                break;
            case R.id.button_equipment_new_character:
                if (isLandscape()) hideFragments(dictionaryFragments, null);
                else hideFragments(dictionaryFragments, dictionaryFragments.get("attribute"));

                showFragmentWithAnimation(dictionaryFragments.get("equipment"));
                break;
            case R.id.button_caracteristic_fragment_caracteristic:
                if (isLandscape()) hideFragments(dictionaryFragments, null);
                else hideFragments(dictionaryFragments, dictionaryFragments.get("attribute"));

                showFragmentWithAnimation(dictionaryFragments.get("new_caracteristic"));
                break;
            case R.id.button_skill_fragment_skill:
                if (isLandscape()) hideFragments(dictionaryFragments, null);
                else hideFragments(dictionaryFragments, dictionaryFragments.get("attribute"));

                showFragmentWithAnimation(dictionaryFragments.get("new_skill"));
                break;
            case R.id.button_equipment_fragment_equipment:
                if (isLandscape()) hideFragments(dictionaryFragments, null);
                else hideFragments(dictionaryFragments, dictionaryFragments.get("attribute"));

                showFragmentWithAnimation(dictionaryFragments.get("new_equipment"));
                break;
            case R.id.button_attributes_new_character:
                hideFragments(dictionaryFragments, null);
                showFragmentWithAnimation(dictionaryFragments.get("attribute"));
        }
    }

    public void showFragmentWithAnimation(Fragment fragment){
        if(fragment.isVisible())
            fm.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).hide(fragment).commit();
        else
            fm.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).show(fragment).commit();
    }

    public void hideFragments(HashMap<String, Fragment> dictionaryFragments, Fragment fragmentNotToHide){
        for (Fragment fragment : dictionaryFragments.values())
            if(fragment != fragmentNotToHide)
                fm.beginTransaction().hide(fragment).commit();
    }

    public boolean isLandscape(){
        return (getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_90 ||
                getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_270);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_character, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_dices) {
            Intent i = new Intent(getApplicationContext(), DicesActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
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
