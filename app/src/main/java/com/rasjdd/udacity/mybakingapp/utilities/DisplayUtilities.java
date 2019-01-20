package com.rasjdd.udacity.mybakingapp.utilities;

import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.rasjdd.udacity.mybakingapp.models.Ingredient;

public class DisplayUtilities {


    public static void prettyIngredientView(Ingredient ingredient, TextView textView, int charsWide) {

        String s = String.valueOf(ingredient.getQuantity());
        String t = ingredient.getIngredient();

        // Clean the measurement a little
        if (s.endsWith(".0")) s = s.substring(0, s.length() - 2);

        // Don't add the quantifier if it is just "UNIT"
        if (!ingredient.getMeasure().toLowerCase().equals("unit"))
            s += " " + ingredient.getMeasure();

        // Create Bold typeface for the measurement
        StyleSpan boldTypeFace = new StyleSpan(Typeface.BOLD);

        // Apply emboldening
        SpannableStringBuilder ingredientBuilder = new SpannableStringBuilder(s);
        ingredientBuilder.setSpan(boldTypeFace, 0, s.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        //Test for character-limit

        if (charsWide > 0){
            if (t.length() > charsWide) {
                t = t.substring(0,charsWide) + "\n\t" + t.substring(charsWide);
            }
        }

        // Add the ingredient name
        ingredientBuilder.append(" ").append(t);

        // Set the view.
        textView.append(ingredientBuilder);
    }

}
