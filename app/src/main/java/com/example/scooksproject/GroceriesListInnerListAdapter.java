package com.example.scooksproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GroceriesListInnerListAdapter extends ArrayAdapter<Ingredient> {

    private final Context context;
    private final ArrayList<Ingredient> items;
    TextView groceryName;

    public GroceriesListInnerListAdapter(@NonNull Context context, ArrayList<Ingredient> items) {
        super(context, R.layout.groceries_list_fragment, items);
        this.context = context;
        this.items = items;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.groceries_list_single_item, null);
            groceryName = convertView.findViewById(R.id.groceryName);
            groceryName.setText(items.get(position).getName());
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (getCount() > 0)
            return getCount();
        else
            return super.getViewTypeCount();
    }
}
