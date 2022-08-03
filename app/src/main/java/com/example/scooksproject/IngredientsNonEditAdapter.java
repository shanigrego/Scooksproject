package com.example.scooksproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IngredientsNonEditAdapter extends ArrayAdapter<Ingredient> {

    private List<Ingredient> items;
    private Context context;

    public IngredientsNonEditAdapter(@NonNull Context context, @NonNull List<Ingredient> items) {
        super(context, R.layout.single_ingredient_non_edit, items);
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.single_ingredient_non_edit, parent, false);

            TextView amount = convertView.findViewById(R.id.ingredientsNonEditAmount);
//            TextView measureUnit = convertView.findViewById(R.id.ingredientsNonEditMeasureUnit);
            TextView name = convertView.findViewById(R.id.ingredientsNonEditName);

            amount.setText(String.valueOf(items.get(position).getAmount()) + '\n' + items.get(position).getMeasureUnit());
//            measureUnit.setText(items.get(position).getMeasureUnit());
            name.setText(items.get(position).getName());
        }
        return convertView;
    }
}
