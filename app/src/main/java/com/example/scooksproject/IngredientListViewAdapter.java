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
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IngredientListViewAdapter extends ArrayAdapter<Ingredient> {

    private Context context;
    private List<Ingredient> ingredients;
    private boolean viewOnly;

    public IngredientListViewAdapter(@NonNull Context context, List<Ingredient> ingredients, boolean viewOnly) {
        super(context, R.layout.ingredient_list_item, ingredients);
        this.context = context;
        this.ingredients = ingredients;
        this.viewOnly = viewOnly;
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

            if (viewOnly)
                setButtonsForViewOnly(amountIngredient, measureUnit, removeIngredient, ingredientName);

            //Amount Ingredient initialization
            String amount = String.valueOf(ingredients.get(position).getAmount());
            if (!amount.equals("0.0"))
                amountIngredient.setText(String.valueOf(ingredients.get(position).getAmount()));
            else
                amountIngredient.setHint("0");

            //Remove Button initialization
            removeIngredient.setOnClickListener(v -> IngredientsFragment.removeItem(position));

            //Ingredient Name initialization
            ingredientName.setText(ingredients.get(position).getName());

            //Popup menu initialization
            initPopupMenu(convertView);

            //Measure Unit Button initialization
            measureUnit = convertView.findViewById(R.id.measureUnitIngredient);
            measureUnit.setText(ingredients.get(position).getMeasureUnit());
            Button finalMeasureUnit = measureUnit;

            measureUnit.setOnClickListener(v -> {
                if (viewOnly)
                    return;

                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                popupMenu.setOnMenuItemClickListener(item -> {
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
                });
                popupMenu.inflate(R.menu.popup_menu_amount);
                popupMenu.show();
            });
        }
        return convertView;
    }

    private void setButtonsForViewOnly(EditText amountIngredient, Button measureUnit, ImageView removeIngredient, EditText ingredientName) {
        amountIngredient.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        measureUnit.setBackgroundResource(android.R.color.transparent);
        removeIngredient.setVisibility(View.INVISIBLE);
        ingredientName.setEnabled(false);
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
