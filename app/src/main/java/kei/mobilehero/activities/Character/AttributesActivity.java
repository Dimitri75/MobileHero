package kei.mobilehero.activities.character;

import android.app.Fragment;
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
import kei.mobilehero.classes.attributes.Caracteristic;
import kei.mobilehero.classes.attributes.Equipment;
import kei.mobilehero.classes.attributes.Skill;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;

import static kei.mobilehero.activities.fragments.generic.EnumFragment.CARACTERISTIC_FORM;
import static kei.mobilehero.activities.fragments.generic.EnumFragment.EQUIPMENT_FORM;

public class AttributesActivity extends ActivityBase implements OnFragmentInteractionListener, ContentProvider, SelectionListener {

    @Override
    public void onSelected(Object o) {
        if(o instanceof Caracteristic) {
            data.set(3, o);
            data.set(4, null);
            data.set(5, null);
            hideFragments(dictionaryFragments, null);
            currentFragment = CARACTERISTIC_FORM;
            showFragmentWithAnimation(dictionaryFragments.get(CARACTERISTIC_FORM));
        } else if (o instanceof Skill) {
            data.set(3, null);
            data.set(4, o);
            data.set(5, null);
            hideFragments(dictionaryFragments, null);
            currentFragment = EnumFragment.SKILL_FORM;
            showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.SKILL_FORM));
        } else if (o instanceof Equipment) {
            data.set(3, null);
            data.set(4, null);
            data.set(5, o);
            hideFragments(dictionaryFragments, null);
            currentFragment = EQUIPMENT_FORM;
            showFragmentWithAnimation(dictionaryFragments.get(EQUIPMENT_FORM));
        }

        // Signal that data is available
        for(ContentProviderListener listener : contentProviderListeners) {
            listener.onAvailableData();
        }
    }

    private List<Object> data;
    private Game game;
    private Round round;
    private kei.mobilehero.classes.general.Character character;

    private EnumFragment currentFragment;
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

        data =  new ArrayList<Object>();
        data.add(game);
        data.add(round);
        data.add(character);
        data.add(null);
        data.add(null);
        data.add(null);

        Log.i("TEST", "" + data.size());

        // Signal that data is available
        for(ContentProviderListener listener : contentProviderListeners) {
            listener.onAvailableData();
        }
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
        dictionaryFragments.put(CARACTERISTIC_FORM, fragment_new_caracteristic);
        dictionaryFragments.put(EnumFragment.SKILL_FORM, fragment_new_skill);
        dictionaryFragments.put(EQUIPMENT_FORM, fragment_new_equipment);

        if (!dictionaryFragments.keySet().contains(argumentKey))
            finish();

        currentFragment = argumentKey;

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
                currentFragment = CARACTERISTIC_FORM;
                data.set(3, null);

                hideFragments(dictionaryFragments, null);
                showFragmentWithAnimation(dictionaryFragments.get(CARACTERISTIC_FORM));
                break;
            case R.id.button_skill_fragment_skill:
                currentFragment = EnumFragment.SKILL_FORM;
                data.set(4, null);

                hideFragments(dictionaryFragments, null);
                showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.SKILL_FORM));
                break;
            case R.id.button_equipment_fragment_equipment:
                currentFragment = EQUIPMENT_FORM;
                data.set(5, null);

                hideFragments(dictionaryFragments, null);
                showFragmentWithAnimation(dictionaryFragments.get(EQUIPMENT_FORM));
                break;
        }

        // Signal that data is available
        for(ContentProviderListener listener : contentProviderListeners) {
            listener.onAvailableData();
        }
    }

    @Override
    public void onBackPressed() {
        switch(currentFragment){
            case CARACTERISTIC_FORM:
                currentFragment = EnumFragment.CARACTERISTICS;

                hideFragments(dictionaryFragments, null);
                showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.CARACTERISTICS));
                break;
            case SKILL_FORM:
                currentFragment = EnumFragment.SKILLS;

                hideFragments(dictionaryFragments, null);
                showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.SKILLS));
                break;
            case EQUIPMENT_FORM:
                currentFragment = EnumFragment.EQUIPMENT;

                hideFragments(dictionaryFragments, null);
                showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.EQUIPMENT));
                break;
            default:
                finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attributes, menu);
        return true;
    }

    @Override
    public Object getData() {
        return data;
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
