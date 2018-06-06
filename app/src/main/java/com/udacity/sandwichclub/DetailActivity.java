package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.image_iv) ImageView imageIv ;
    @BindView(R.id.also_known_tv) TextView alsoKnownIv ;
    @BindView(R.id.origin_tv) TextView placeOfOrigin ;
    @BindView(R.id.description_tv) TextView descriptionIv ;
    @BindView(R.id.ingredients_tv) TextView ingredientsIv ;

    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        setTitle(sandwich.getMainName());

        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_error_black_24dp)
                .into(imageIv);

        if (sandwich.getPlaceOfOrigin().isEmpty()){
            placeOfOrigin.setText("Origin not found");
        }else{
            placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
        }


        descriptionIv.setText(sandwich.getDescription());
        //ingredientsIv.setText(sandwich.getIngredients());

//        String alsoKnownString = "";
//        for (int i = 0 ; i<sandwich.getAlsoKnownAs().size();i++){
//            alsoKnownString += sandwich.getAlsoKnownAs().get(i) + " \n";
//        }
//        alsoKnownIv.setText(alsoKnownString);

        if (sandwich.getAlsoKnownAs().isEmpty()){
            alsoKnownIv.setText("Not have other name");
        }else{
            alsoKnownIv.setText(TextUtils.join("\n ", sandwich.getAlsoKnownAs()));
        }



//        String ingredients = "";
//        for (int i = 0 ; i<sandwich.getIngredients().size();i++){
//            ingredients += sandwich.getIngredients().get(i) + " \n";
//        }
//        ingredientsIv.setText(ingredients);

        ingredientsIv.setText(TextUtils.join("\n ", sandwich.getIngredients()));
    }
}
