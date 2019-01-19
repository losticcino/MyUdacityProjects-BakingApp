package com.rasjdd.udacity.mybakingapp.utilities;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.rasjdd.udacity.mybakingapp.models.Ingredient;

public class DisplayUtilities {

    public void prettyIngredientView(Ingredient ingredient, TextView textView) {

        String s = String.valueOf(ingredient.getQuantity());

        // Clean the measurement a little
        if (s.endsWith(".0")) s = s.substring(0,s.length() - 2);

        // Don't add the quantifier if it is just "UNIT"
        if (!ingredient.getMeasure().toLowerCase().equals("unit")) s+= " " + ingredient.getMeasure();

        // Create Bold typeface for the measurement
        StyleSpan boldTypeFace = new StyleSpan(Typeface.BOLD);

        // Apply emboldening
        SpannableStringBuilder ingredientBuilder = new SpannableStringBuilder(s);
        ingredientBuilder.setSpan(boldTypeFace, 0, s.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Add the ingredient name
        ingredientBuilder.append(" ").append(ingredient.getIngredient());

        // Set the view.
        textView.setText(ingredientBuilder);
    }
}
