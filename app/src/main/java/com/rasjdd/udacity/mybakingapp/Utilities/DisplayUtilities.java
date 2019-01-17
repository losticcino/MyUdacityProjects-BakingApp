package com.rasjdd.udacity.mybakingapp.Utilities;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.TextView;

public class DisplayUtilities {

    public void prettyIngredientView(int quantity, String measurement, String ingredient, TextView textView) {
        String s = String.valueOf(quantity);
        if (!measurement.toLowerCase().equals("unit")) s+= " " + measurement;
        StyleSpan boldTypeFace = new StyleSpan(Typeface.BOLD);
        SpannableStringBuilder ingredientBuilder = new SpannableStringBuilder(s);
        ingredientBuilder.setSpan(boldTypeFace, 0, s.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        ingredientBuilder.append(" ").append(ingredient);
        textView.setText(ingredientBuilder);
    }
}
