package kei.mobilehero.fragments.generic;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import kei.mobilehero.R;
import kei.mobilehero.classes.attributes.Effect;
import kei.mobilehero.classes.general.Character;
import kei.mobilehero.classes.utils.persistence.Loader;

/**
 * Created by Vuzi on 13/07/2015.
 */
public class CharacterSelector {

    public interface CharacterSelectorListener {
        void onCharacterSelected(Effect e);
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

                Toast.makeText(activity.getApplicationContext(), c.getName(), Toast.LENGTH_SHORT).show();

                /*
                if (c == null)
                    Toast.makeText(activity, activity.getString(R.string.toastNoSelectedCharacteristic), Toast.LENGTH_SHORT).show();
                else {
                    String n = name.getText().toString();
                    Double v = Double.valueOf(value.getText().toString());

                    if (n == null || n.isEmpty() || v == null) {
                        Toast.makeText(activity, activity.getString(R.string.toastNoSelectedValue), Toast.LENGTH_SHORT).show();
                    } else {
                        selectorListener.onCharacterSelected(new Effect(n, "", Double.valueOf(v), c));
                        dialog.hide();
                    }
                }*/
            }
        });

        dialog.show();
    }
}
