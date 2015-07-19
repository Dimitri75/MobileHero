package kei.mobilehero.fragments.generic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import kei.mobilehero.R;
import kei.mobilehero.classes.general.Character;

/**
 * Created by Vuzi on 13/07/2015.
 */
public class CharacterListAdapter extends BaseAdapter {

    private final List<Character> characters;
    private final LayoutInflater inflater;

    public CharacterListAdapter(List<Character> characteristics, LayoutInflater inflater) {
        this.characters = characteristics;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return characters.size();
    }

    @Override
    public Object getItem(int position) {
        return characters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.alert_list_item, null);
        }

        Character c = characters.get(position);

        ((TextView)convertView.findViewById(R.id.text1)).setText(c.getName() );
        ((TextView)convertView.findViewById(R.id.text2)).setText(c.getLevel() + " " + c.getRace() + " " + c.getClassName());

        return convertView;
    }

}