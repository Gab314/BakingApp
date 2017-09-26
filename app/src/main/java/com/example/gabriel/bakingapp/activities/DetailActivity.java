package com.example.gabriel.bakingapp.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.Window;

import com.example.gabriel.bakingapp.R;
import com.example.gabriel.bakingapp.fragments.DetailFragment;
import com.example.gabriel.bakingapp.fragments.RecipesFragment;
import com.example.gabriel.bakingapp.fragments.StepByStepFragment;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detailed_recipe);

        if (savedInstanceState == null) {
            if (findViewById(R.id.container_detail) != null) {
                DetailFragment fragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("SMALL",true);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_detail, fragment);
                fragmentTransaction.commit();
            }
        }
    }
}
