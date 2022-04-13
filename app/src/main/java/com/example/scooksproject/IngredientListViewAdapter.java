package com.example.scooksproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IngredientListViewAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> items;

    public IngredientListViewAdapter(@NonNull Context context, ArrayList<String> items) {
        super(context, R.layout.ingredient_list_item, items);
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.ingredient_list_item, null);

            TextView ingredientName = convertView.findViewById(R.id.nameIngredient);
            Button amountBtn = convertView.findViewById(R.id.amountIngredient);
            NumberPicker numberPicker = convertView.findViewById(R.id.numberPickerIngredient);
            ImageView removeIngredient = convertView.findViewById(R.id.removeIngredient);

            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(10);
            removeIngredient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IngredientsFragment.removeItem(position);
                }
            });
            ingredientName.setText(items.get(position));
        }
        return convertView;
    }
}
