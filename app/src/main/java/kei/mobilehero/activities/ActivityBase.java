package kei.mobilehero.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.Surface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kei.mobilehero.R;
import kei.mobilehero.activities.character.SelectionListener;
import kei.mobilehero.activities.dice.DicesActivity;
import kei.mobilehero.activities.fragments.generic.ContentProvider;
import kei.mobilehero.activities.fragments.generic.EnumFragment;
import kei.mobilehero.classes.attributes.Caracteristic;
import kei.mobilehero.classes.attributes.Equipment;
import kei.mobilehero.classes.attributes.Skill;
import kei.mobilehero.classes.general.Game;
import kei.mobilehero.classes.general.Round;

import static kei.mobilehero.activities.fragments.generic.EnumFragment.CARACTERISTIC_FORM;
import static kei.mobilehero.activities.fragments.generic.EnumFragment.EQUIPMENT_FORM;

/**
 * Created by Dimitri on 10/07/2015.
 */
public abstract class ActivityBase extends ActionBarActivity implements ContentProvider, SelectionListener {

    protected FragmentManager fm = getFragmentManager();

    protected HashMap<EnumFragment, Fragment> dictionaryFragments;
    protected ArrayList<ContentProviderListener> contentProviderListeners = new ArrayList<>();

    protected List<Object> data;
    protected Game game;
    protected Round round;
    protected kei.mobilehero.classes.general.Character character;

    protected EnumFragment currentFragment;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_dices) {
            Intent i = new Intent(getApplicationContext(), DicesActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Display a given fragment with a fade in animation
     * @param fragment The fragment to show
     */
    public void showFragmentWithAnimation(Fragment fragment){
        if(fragment.isVisible())
            fm.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).hide(fragment).commit();
        else
            fm.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).show(fragment).commit();
    }

    /**
     * Hide all the fragments of the given dictionary, except the specified one
     * @param dictionaryFragments
     * @param fragmentNotToHide
     */
    public void hideFragments(HashMap<EnumFragment, Fragment> dictionaryFragments, Fragment fragmentNotToHide){
        for (Fragment fragment : dictionaryFragments.values())
            if(fragment != fragmentNotToHide)
                fm.beginTransaction().hide(fragment).commit();
    }

    /**
     * Return true if in landscape mode, false otherwise
     * @return
     */
    public boolean isLandscape(){
        return (getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_90 ||
                getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_270);
    }

    @Override
    public void onSelected(Object o) {
        // Generic event based on selected characteristic
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

        signalAvailableData();
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

    protected void initData() {
        data =  new ArrayList<>();
        data.add(game);
        data.add(round);
        data.add(character);
        data.add(null);
        data.add(null);
        data.add(null);
    }

    @Override
    public Object getData() {
        // Return the contained data
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

    protected void signalAvailableData() {
        // Signal that data is available to all the listener
        for(ContentProviderListener listener : contentProviderListeners) {
            listener.onAvailableData();
        }
    }
}
