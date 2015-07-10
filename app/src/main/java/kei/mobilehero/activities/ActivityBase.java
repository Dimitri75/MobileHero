package kei.mobilehero.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.Surface;

import java.util.HashMap;

import kei.mobilehero.R;
import kei.mobilehero.activities.dice.DicesActivity;
import kei.mobilehero.activities.fragments.EnumFragment;

/**
 * Created by Dimitri on 10/07/2015.
 */
public abstract class ActivityBase extends ActionBarActivity {
    protected FragmentManager fm = getFragmentManager();

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

    public void showFragmentWithAnimation(Fragment fragment){
        if(fragment.isVisible())
            fm.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).hide(fragment).commit();
        else
            fm.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).show(fragment).commit();
    }

    public void hideFragments(HashMap<EnumFragment, Fragment> dictionaryFragments, Fragment fragmentNotToHide){
        for (Fragment fragment : dictionaryFragments.values())
            if(fragment != fragmentNotToHide)
                fm.beginTransaction().hide(fragment).commit();
    }

    public boolean isLandscape(){
        return (getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_90 ||
                getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_270);
    }
}
