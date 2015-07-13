package kei.mobilehero.fragments.generic;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Spinner;

import java.util.List;

import kei.mobilehero.R;
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

        // custom dialog
        final Dialog dialog = new Dialog(activity, R.style.DialogTheme);
        dialog.setContentView(R.layout.alertdialog_effect_form);
        dialog.setTitle(R.string.addEffect);

        Spinner sp = (Spinner) dialog.findViewById(R.id.spinner_alert_effect_characteristics);

        sp.setAdapter(myAdapter);

        dialog.show();

        /*

       // AlertDialog.Builder builder = new AlertDialog.Builder(activity);
       // builder.set


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

        builder.show();*/

    }
}
