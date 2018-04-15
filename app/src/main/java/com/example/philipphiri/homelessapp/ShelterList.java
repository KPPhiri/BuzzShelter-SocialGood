package com.example.philipphiri.homelessapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * ShelterList class that creates a list
 * of appropriate shelters
 */

public class ShelterList extends ArrayAdapter<Shelter>{
    private Activity context;
    List<Shelter> shelterList;
    private ArrayList<Shelter> arraylist=null;

    /**
     * Shelterlist Activity
     * @param context context
     * @param shelterList list of Shelter objects
     */
    public ShelterList(Activity context, List<Shelter> shelterList) {
        super(context, R.layout.layout_shelter_list, shelterList);
        this.context = context;
        this.shelterList = shelterList;

        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(shelterList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_shelter_list, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewCapacity = (TextView) listViewItem.findViewById(R.id.textViewCapacity);

        Shelter shelter = shelterList.get(position);
        textViewName.setText(shelter.getShelterName());
        textViewCapacity.setText(shelter.getShelterCapacity());

        return listViewItem;
    }

    /**
     * filter method
     * @param charText filter text
     */
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        shelterList.clear();
        if (charText.length() == 0) {
            shelterList.addAll(arraylist);
        }
        else
        {
            for (Shelter wp : arraylist) {
                if (wp.getShelterName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    shelterList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void genFilter(String option) {
        shelterList.clear();
        {
            for (Shelter wp : arraylist) {
                if (wp.getShelterRestrictions().contains(option)) {
                    shelterList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void ageFilter(String option) {
        shelterList.clear();
        {
            for (Shelter wp : arraylist) {
                if ((wp.getShelterRestrictions().contains(option))) {
                    shelterList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void noFilter() {
        shelterList.clear();
        {
            for (Shelter wp : arraylist) {
                    shelterList.add(wp);
            }
        }
        notifyDataSetChanged();
    }


}
