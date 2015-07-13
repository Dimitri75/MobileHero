package kei.mobilehero.fragments.generic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.List;

import kei.mobilehero.classes.attributes.Caracteristic;

/**
 * Created by Vuzi on 13/07/2015.
 */
public class CharacteristicSelector {

    public interface CharacteristicListener {
        void onCharacteristicSelected(Caracteristic c);
    }

    static public void show(List<Caracteristic> characteristics, Activity activity, final CharacteristicListener selectorListener) {

        final CaracteristicListAdapter myAdapter = new CaracteristicListAdapter(characteristics, activity.getLayoutInflater());

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setAdapter(myAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectorListener.onCharacteristicSelected((Caracteristic) myAdapter.getItem(which));
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });

        builder.show();

    }
}
