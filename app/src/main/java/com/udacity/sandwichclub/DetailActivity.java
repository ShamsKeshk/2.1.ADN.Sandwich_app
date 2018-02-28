package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    @BindView(R.id.main_name_of_sandwich_tv)
    TextView mMainNameTextView;
    @BindView(R.id.image_iv)
    ImageView mSandwichImageView;
    @BindView(R.id.origin_tv)
    TextView mOriginTextView;
    @BindView(R.id.also_known_tv)
    TextView mAlsoKnownAsTextView;
    @BindView(R.id.ingredients_tv)
    TextView mIngredientsTextView;
    @BindView(R.id.description_tv)
    TextView mDescriptionTextView;
    @BindView(R.id.also_known_as_layout_id)
    LinearLayout mKnownAsLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }


        int position = DEFAULT_POSITION;
        if (intent != null) {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
            if (position == DEFAULT_POSITION) {
                // EXTRA_POSITION not found in intent
                closeOnError();
                return;
            }
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (sandwich.getImage() != null) {
            Picasso.with(getApplicationContext())
                    .load(sandwich.getImage())
                    .into(mSandwichImageView);
        }

        if (sandwich.getMainName() != null && !sandwich.getMainName().isEmpty()) {
            mMainNameTextView.setText(sandwich.getMainName());
        } else {
            mMainNameTextView.setText(getString(R.string.not_provided));
        }

        if ((sandwich.getAlsoKnownAs() != null && !sandwich.getAlsoKnownAs().isEmpty())) {
            for (String singleName : sandwich.getAlsoKnownAs()) {
                mAlsoKnownAsTextView.append(singleName + "\n");
            }
        } else {
            mKnownAsLinearLayout.setVisibility(View.GONE);
        }


        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().isEmpty() && !sandwich.getPlaceOfOrigin().contains("")) {
            mOriginTextView.setText(sandwich.getPlaceOfOrigin());
        } else {
            mOriginTextView.setText(getString(R.string.not_provided));
        }


        if (sandwich.getIngredients() != null && !sandwich.getIngredients().isEmpty()) {
            for (String singleIngredients : sandwich.getIngredients()) {
                mIngredientsTextView.append(singleIngredients + "\n");
            }
        } else {
            mIngredientsTextView.setText(getString(R.string.not_provided));
        }

        if (sandwich.getDescription() != null && !sandwich.getDescription().isEmpty()) {
            mDescriptionTextView.setText(sandwich.getDescription());
        } else {
            mDescriptionTextView.setText(getString(R.string.not_provided));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(DetailActivity.this);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
