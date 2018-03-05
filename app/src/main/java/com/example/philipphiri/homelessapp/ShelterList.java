package com.example.philipphiri.homelessapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static com.example.philipphiri.homelessapp.R.layout.activity_shelter_list;

/**
 * Created by philipphiri on 2/24/18.
 */

public class ShelterList extends ArrayAdapter<Shelter> {
    private Activity context;
    List<Shelter> shelterList;

    public ShelterList(Activity context, List<Shelter> shelterList) {
        super(context, R.layout.layout_shelter_list, shelterList);
        this.context = context;
        this.shelterList = shelterList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_shelter_list, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewAddress = (TextView) listViewItem.findViewById(R.id.textViewAddress);

        Shelter shelter = shelterList.get(position);
        textViewName.setText(shelter.getShelterName());
        textViewAddress.setText(shelter.getShelterAddress());

        return listViewItem;
    }
}
