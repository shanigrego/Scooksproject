package com.example.scooksproject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RecipeInstructionsListAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> items;
    private TextView stepNum;
    private ImageView removeBtn;

    public RecipeInstructionsListAdapter(@NonNull Context context, ArrayList<String> items) {
        super(context, R.layout.recipe_instructions_single_item, items);
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.recipe_instructions_single_item, null);

            EditText txtDetails =convertView.findViewById(R.id.txtDetails);
            stepNum = convertView.findViewById(R.id.stepNum);
            removeBtn = convertView.findViewById(R.id.removeRecipeStep);

            stepNum.setText(Integer.toString(position + 1));
            txtDetails.setText(items.get(position));
            if(position != items.size() - 1){
                    removeBtn.setVisibility(View.INVISIBLE);
            }
            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RecipeInstructionsFragment.removeItem(position);
                 }
            });
            txtDetails.setHint(" הזן את מהלך שלב " + (position + 1) + "\n בהכנת המתכון ");
            txtDetails.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        items.set(position, txtDetails.getText().toString());
                    }
                }
            });
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if(getCount() > 0)
            return getCount();
        else
            return super.getViewTypeCount();
    }
}
