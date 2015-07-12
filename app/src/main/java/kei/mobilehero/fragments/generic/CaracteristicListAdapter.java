package kei.mobilehero.fragments.generic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import kei.mobilehero.R;
import kei.mobilehero.classes.attributes.Caracteristic;

/**
 * Created by Vuzi on 13/07/2015.
 */
public class CaracteristicListAdapter extends BaseAdapter {

    private final List<Caracteristic> characteristics;
    private final LayoutInflater inflater;

    public CaracteristicListAdapter(List<Caracteristic> characteristics, LayoutInflater inflater) {
        this.characteristics = characteristics;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return characteristics.size();
    }

    @Override
    public Object getItem(int position) {
        return characteristics.get(position);
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

        Caracteristic c = characteristics.get(position);

        ((TextView)convertView.findViewById(R.id.text1)).setText(c.getName() );
        ((TextView)convertView.findViewById(R.id.text2)).setText(c.getDescription());

        return convertView;
    }

}