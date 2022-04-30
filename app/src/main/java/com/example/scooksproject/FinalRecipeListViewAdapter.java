package com.example.scooksproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class FinalRecipeListViewAdapter extends ArrayAdapter<Instruction> {

    private Context context;
    private List<Instruction> items;

    public FinalRecipeListViewAdapter(@NonNull Context context, List<Instruction> instructions) {
        super(context, R.layout.final_recipe_instruction, instructions);
        this.context = context;
        this.items = instructions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.final_recipe_instruction, null);

            TextView instructionsDetails = convertView.findViewById(R.id.finalInstructionTxt);
            CardView background = convertView.findViewById(R.id.finalRecipeSingleItemBackground);
            ImageView timerIcon = convertView.findViewById(R.id.finalTimerIcon);
            ImageView stepNumPressed = convertView.findViewById(R.id.finalInstructionPressedStepNum);
            ImageView stepNumUnPressed = convertView.findViewById(R.id.finalInstructionUnpressedStepNum);

            initComponents(position, background, timerIcon, stepNumPressed, stepNumUnPressed, instructionsDetails);

        }
        return convertView;
    }

    private void initComponents(int position, CardView background, ImageView timerIcon,
                                ImageView stepNumPressed, ImageView stepNumUnPressed,
                                TextView instructionsDetails){

        Instruction currentInstruction = items.get(position);

        // Pressed Step Button initialization
        stepNumUnPressed.setOnClickListener(item -> {
            background.setCardBackgroundColor(context.getResources().getColor(R.color.grey));
            stepNumUnPressed.setVisibility(View.INVISIBLE);
            stepNumPressed.setVisibility(View.VISIBLE);
        });

        // Timer Icon initialization
        if(currentInstruction.getFreeTime() == 0)
            timerIcon.setVisibility(View.INVISIBLE);

        // Instructions Text View initialization
        instructionsDetails.setText(currentInstruction.getContent());
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getPosition(@Nullable Instruction item) {
        return super.getPosition(item);
    }


}
