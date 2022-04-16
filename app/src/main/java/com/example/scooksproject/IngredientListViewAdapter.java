package com.example.scooksproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupMenu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IngredientListViewAdapter extends ArrayAdapter<Ingredient> {

    private Context context;
    private LinkedList<Ingredient> ingredients;

    public IngredientListViewAdapter(@NonNull Context context, LinkedList<Ingredient> ingredients) {
        super(context, R.layout.ingredient_list_item, ingredients);
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.ingredient_list_item, parent, false);

            EditText ingredientName = convertView.findViewById(R.id.nameIngredient);
            EditText amountIngredient = convertView.findViewById(R.id.amountIngredient);
            ImageView removeIngredient = convertView.findViewById(R.id.removeIngredient);
            Button measureUnit = convertView.findViewById(R.id.measureUnitIngredient);

            //Amount Ingredient initialization
            String amount = String.valueOf(ingredients.get(position).getAmount());
            if(!amount.equals("0.0"))
                amountIngredient.setText(String.valueOf(ingredients.get(position).getAmount()));
            else
                amountIngredient.setHint("0");

            //Remove Button initialization
            removeIngredient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IngredientsFragment.removeItem(position);
                }
            });

            //Ingredient Name initialization
            ingredientName.setText(ingredients.get(position).getName());

            //Popup menu initialization
            initPopupMenu(convertView);

            //Measure Unit Button initialization
            measureUnit = convertView.findViewById(R.id.measureUnitIngredient);
            measureUnit.setText(ingredients.get(position).getMeasureUnit());
            Button finalMeasureUnit = measureUnit;
            measureUnit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(getContext(), v);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            String measureUnitChosen;
                            switch (item.getItemId()) {
                                case R.id.gram:
                                    measureUnitChosen = "גרם";
                                    break;
                                case R.id.cups:
                                    measureUnitChosen = "כוסות";
                                    break;
                                default:
                                    measureUnitChosen = "כפיות";
                                    break;
                            }
                            finalMeasureUnit.setText(measureUnitChosen);
                            ingredients.get(position).setMeasureUnit(measureUnitChosen);
                            return true;
                        }
                    });
                    popupMenu.inflate(R.menu.popup_menu_amount);
                    popupMenu.show();
                }
            });
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

    private void initPopupMenu(View view) {

    }
}
