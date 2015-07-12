package kei.mobilehero.activities.character;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.HashMap;

import kei.mobilehero.R;
import kei.mobilehero.activities.character.generic.ActivityAttributesBase;
import kei.mobilehero.activities.character.generic.EnumAttribute;
import kei.mobilehero.fragments.generic.EnumFragment;
import kei.mobilehero.fragments.generic.OnFragmentInteractionListener;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;

import static kei.mobilehero.fragments.generic.EnumFragment.CARACTERISTIC_FORM;
import static kei.mobilehero.fragments.generic.EnumFragment.EQUIPMENT_FORM;

public class AttributesActivity extends ActivityAttributesBase implements OnFragmentInteractionListener {

    private EnumFragment argumentKey;

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
        initData();

        hideSoftKeyboard();

        signalAvailableData();
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!= null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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
                setTitle(getString(R.string.characteristics));
                break;
            case EQUIPMENT:
                findViewById(R.id.layout_attribute_activity).setBackgroundResource(R.drawable.mobile_ogre);
                setTitle(getString(R.string.equipment));
                break;
            case SKILLS:
                findViewById(R.id.layout_attribute_activity).setBackgroundResource(R.drawable.mobile_gobelin);
                setTitle(getString(R.string.skill));
                break;
            default:
                finish();
        }
    }

    public void buttonOnClick(View v) {
        switch(v.getId()){
            case R.id.button_caracteristic_fragment_caracteristic:
                currentFragment = CARACTERISTIC_FORM;
                data.put(EnumAttribute.CARACTERISTIC, null);

                hideFragments(dictionaryFragments, null);
                showFragmentWithAnimation(dictionaryFragments.get(CARACTERISTIC_FORM));
                break;
            case R.id.button_skill_fragment_skill:
                currentFragment = EnumFragment.SKILL_FORM;
                data.put(EnumAttribute.SKILL, null);

                hideFragments(dictionaryFragments, null);
                showFragmentWithAnimation(dictionaryFragments.get(EnumFragment.SKILL_FORM));
                break;
            case R.id.button_equipment_fragment_equipment:
                currentFragment = EQUIPMENT_FORM;
                data.put(EnumAttribute.EQUIPMENT, null);

                hideFragments(dictionaryFragments, null);
                showFragmentWithAnimation(dictionaryFragments.get(EQUIPMENT_FORM));
                break;
        }

        signalAvailableData();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attributes, menu);
        return true;
    }
}
