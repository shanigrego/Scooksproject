package com.example.scooksproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RecipeInstructionsListAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> items;
    private TextView stepNum;
    private ImageView removeBtn;
    private boolean viewOnly;

    public RecipeInstructionsListAdapter(@NonNull Context context, List<String> items, boolean viewOnly) {
        super(context, R.layout.recipe_instructions_single_item, items);
        this.context = context;
        this.items = items;
        this.viewOnly = viewOnly;
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

            //Step Number initialization
            stepNum.setText(Integer.toString(position + 1));

            //Remove Button initialization
            removeBtn.setOnClickListener(v -> AddRecipeFragment.removeInstruction(position));
            if(position != items.size() - 1)
                removeBtn.setVisibility(View.INVISIBLE);

            if(viewOnly){
                removeBtn.setVisibility(View.INVISIBLE);
                txtDetails.setEnabled(false);
                txtDetails.setTextColor(context.getResources().getColor(R.color.black));
            }

            //Instruction Details initialization
            txtDetails.setText(items.get(position));
            txtDetails.setHint(" הזן את מהלך שלב " + (position + 1) + "\n בהכנת המתכון ");
            txtDetails.setOnFocusChangeListener((v, hasFocus) -> {
                if(!hasFocus) {
                    items.set(position, txtDetails.getText().toString());
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
