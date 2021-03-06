package kei.mobilehero.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import kei.mobilehero.R;
import kei.mobilehero.classes.attributes.Characteristic;
import kei.mobilehero.classes.attributes.Effect;
import kei.mobilehero.fragments.generic.CharacteristicListAdapter;

/**
 * Created by Vuzi on 13/07/2015.
 */
public class EffectCreator {

    public interface EffectCreationListener {
        void onEffectCreated(Effect e);
    }

    static public void show(List<Characteristic> characteristics, final Activity activity, final EffectCreationListener selectorListener) {

        final CharacteristicListAdapter myAdapter = new CharacteristicListAdapter(characteristics, activity.getLayoutInflater());

        // custom dialog
        final Dialog dialog = new Dialog(activity, R.style.DialogTheme);
        dialog.setContentView(R.layout.alertdialog_effect_form);
        dialog.setTitle(R.string.addEffect);

        final Spinner sp = (Spinner) dialog.findViewById(R.id.spinner_alert_effect_characteristics);
        final EditText name = (EditText) dialog.findViewById(R.id.editText_alert_effect_name);
        final EditText value = (EditText) dialog.findViewById(R.id.editText_alert_effect_value);
        Button save = (Button) dialog.findViewById(R.id.button_alert_effect_save);

        sp.setAdapter(myAdapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Characteristic c = (Characteristic) sp.getSelectedItem();

                if (c == null)
                    Toast.makeText(activity, activity.getString(R.string.toastNoSelectedCharacteristic), Toast.LENGTH_SHORT).show();
                else {
                    String n = name.getText().toString();
                    Double v = value.getText().toString().isEmpty() ? 0 : Double.parseDouble(value.getText().toString());

                    if (n == null || n.isEmpty() || v == null) {
                        Toast.makeText(activity, activity.getString(R.string.toastNoSelectedValue), Toast.LENGTH_SHORT).show();
                    } else {
                        selectorListener.onEffectCreated(new Effect(n, "", v, c));
                        dialog.hide();
                    }
                }
            }
        });

        dialog.show();
    }
}
