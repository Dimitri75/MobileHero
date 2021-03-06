package kei.mobilehero.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import kei.mobilehero.R;
import kei.mobilehero.classes.general.Character;
import kei.mobilehero.classes.utils.persistence.Loader;
import kei.mobilehero.fragments.generic.CharacterListAdapter;

/**
 * Created by Vuzi on 13/07/2015.
 */
public class CharacterSelector {

    public interface CharacterSelectorListener {
        void onCharacterSelected(Character c);
    }

    static public void show(Character character,  final Activity activity, final CharacterSelectorListener selectorListener) {

        // Load list of model characters
        List<Character> characters = Loader.getInstance().loadCharacterModels(activity.getApplicationContext());
        final CharacterListAdapter myAdapter = new CharacterListAdapter(characters, activity.getLayoutInflater());

        // Custom dialog
        final Dialog dialog = new Dialog(activity, R.style.DialogTheme);
        dialog.setContentView(R.layout.alertdialog_character_selection);
        dialog.setTitle(R.string.characterModelSelection);

        final Spinner sp = (Spinner) dialog.findViewById(R.id.spinner_alert_character_model);
        Button save = (Button) dialog.findViewById(R.id.button_alert_character_model_validate);

        sp.setAdapter(myAdapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Character c = (Character) sp.getSelectedItem();

                selectorListener.onCharacterSelected(c);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
