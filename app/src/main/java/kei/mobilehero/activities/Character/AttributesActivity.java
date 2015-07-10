package kei.mobilehero.activities.character;

import android.app.Fragment;
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
import kei.mobilehero.activities.fragments.generic.OnFragmentInteractionListener;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;

public class AttributesActivity extends ActivityBase implements OnFragmentInteractionListener, ContentProvider {
    private Game game;
    private Round round;
    private kei.mobilehero.classes.general.Character character;

    private EnumFragment argumentKey;

    HashMap<EnumFragment, Fragment> dictionaryFragments;
    ArrayList<ContentProviderListener> contentProviderListeners = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attributes);

        if((game = (Game) getIntent().getExtras().get("game")) == null ||
                (round = (Round) getIntent().getExtras().get("round")) == null ||
                (character = (kei.mobilehero.classes.general.Character) getIntent().getExtras().get("character")) == null ||
                (argumentKey = (EnumFragment) getIntent().getExtras().get("argumentKey")) == null){
            Log.v("Attributes onCreate()", "Couldn't get the extras.");
            finish();
        }

        initBackground();
        initFragments();
    }

    public void initFragments(){
        Fragment fragment_caracteristics = fm.findFragmentById(R.id.fragment_caracteristic_attribute);
        Fragment fragment_skills = fm.findFragmentById(R.id.fragment_skill_attribute);
        Fragment fragment_equipment = fm.findFragmentById(R.id.fragment_equipment_attribute);
        Fragment fragment_new_caracteristic = fm.findFragmentById(R.id.fragment_new_caracteristic_attribute);
        Fragment fragment_new_skill = fm.findFragmentById(R.id.fragment_new_skill_attribute);
        Fragment fragment_new_equipment = fm.findFragmentById(R.id.fragment_new_equipment_attribute);

        dictionaryFragments = new HashMap<>();
        dictionaryFragments.put(EnumFragment.CARACTERISTICS, fragment_caracteristics);
        dictionaryFragments.put(EnumFragment.SKILLS, fragment_skills);
        dictionaryFragments.put(EnumFragment.EQUIPMENT, fragment_equipment);
        dictionaryFragments.put(EnumFragment.NEW_CARACTERISTIC, fragment_new_caracteristic);
        dictionaryFragments.put(EnumFragment.NEW_SKILL, fragment_new_skill);
        dictionaryFragments.put(EnumFragment.NEW_EQUIPMENT, fragment_new_equipment);

        if (!dictionaryFragments.keySet().contains(argumentKey))
            finish();

        hideFragments(dictionaryFragments, dictionaryFragments.get(argumentKey));
    }

    public void initBackground(){
        switch (argumentKey){
            case CARACTERISTICS:
                findViewById(R.id.layout_attribute_activity).setBackgroundResource(R.drawable.mobile_morse);
                break;
            case EQUIPMENT:
                findViewById(R.id.layout_attribute_activity).setBackgroundResource(R.drawable.mobile_ogre);
                break;
            case SKILLS:
                findViewById(R.id.layout_attribute_activity).setBackgroundResource(R.drawable.mobile_gobelin);
                break;
            default:
                finish();
        }
    }

    public void buttonOnClick(View v) {
        switch(v.getId()){
            case R.id.button_caracteristic_fragment_caracteristic:
                hideFragments(dictionaryFragments, null);
                showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.NEW_CARACTERISTIC));
                break;
            case R.id.button_skill_fragment_skill:
                hideFragments(dictionaryFragments, null);
                showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.NEW_SKILL));
                break;
            case R.id.button_equipment_fragment_equipment:
                hideFragments(dictionaryFragments, null);
                showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.NEW_EQUIPMENT));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attributes, menu);
        return true;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public Round getRound() {
        return round;
    }

    @Override
    public kei.mobilehero.classes.general.Character getCharacter() {
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
