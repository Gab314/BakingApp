package com.example.gabriel.bakingapp.fragments;


import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gabriel.bakingapp.Adapters.RecyclerDetailAdapter;
import com.example.gabriel.bakingapp.Adapters.RecyclerViewAdapter;
import com.example.gabriel.bakingapp.R;
import com.example.gabriel.bakingapp.Utils.RecipeCards;
import com.example.gabriel.bakingapp.Utils.Steps;
import com.example.gabriel.bakingapp.Widget.IngredientsIntentService;
import com.example.gabriel.bakingapp.activities.StepByStepActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailFragment extends Fragment implements RecyclerDetailAdapter.ItemClickListener{
    Boolean small_sc;
    RecyclerDetailAdapter adapter;
    ArrayAdapter<String> ingAdapter;
    ArrayList<String> mIngList;
    ArrayList<Steps> mStepsList;
    String mIngText;
    final String LOG_TAG = DetailFragment.class.getSimpleName();

    public DetailFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            small_sc = getArguments().getBoolean("SMALL");
            }

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detailed_recpies, container, false);
            ListView ingredients = (ListView) rootView.findViewById(R.id.ingredient_row_ListView);
            RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.fragments_detailed_recipes_recycler_view);

            mIngList = new ArrayList<>();
            mStepsList = new ArrayList<>();
            mIngText = "";

            rv.setHasFixedSize(true);
            int numberOfColumns = 1;
            GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
            rv.setLayoutManager(mLayoutManager);

            if (savedInstanceState != null){
                mStepsList =   savedInstanceState.getParcelableArrayList("STEPS");
                mIngList = savedInstanceState.getStringArrayList("ING");
            }
            adapter = new RecyclerDetailAdapter(getActivity(),mStepsList);
            ingAdapter = new ArrayAdapter<>(getActivity(),R.layout.ingredient_row,R.id.ingredient_row_TextView, mIngList);


            rv.setAdapter(adapter);
            ingredients.setAdapter(ingAdapter);

            adapter.setClickListener(this);

            Intent intent = getActivity().getIntent();
              String recipes = intent.getStringExtra("recipe");
                int position = intent.getIntExtra("position",0);

            try {
                extractJSON(recipes,position);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            IngredientsIntentService.startActionPutIngredients(getActivity(), mIngList);
            return rootView;
        }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("STEPS", mStepsList);
        savedInstanceState.putStringArrayList("ING", mIngList);

    }
    public void onItemClick(View view, int position) {

        if (small_sc != null){
            Intent intent = new Intent(getActivity(), StepByStepActivity.class);
            intent.putParcelableArrayListExtra("StepsArray",mStepsList );
            intent.putExtra("position", position);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }else startActivity(intent);
        }
        if (small_sc == null){
            StepByStepFragment fragment = new StepByStepFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("StepsArray", mStepsList);
            bundle.putInt("position", position);
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.detailed_steps, fragment);
            fragmentTransaction.commit();
        }
    }

        public void extractJSON(String object, int position) throws JSONException {

            JSONArray results = new JSONArray(object);
            JSONObject result = results.getJSONObject(position);
            JSONArray ingredientsArray = result.getJSONArray("ingredients");
            JSONArray stepsArray = result.getJSONArray("steps");

            final String KEY_QTT = "quantity";
            final String KEY_MEASURE = "measure";
            final String KEY_INGREDIENT = "ingredient";

            final String KEY_ID = "id";
            final String KEY_SDESC = "shortDescription";
            final String KEY_DESC = "description";
            final String KEY_VIDEO = "videoURL";
            ArrayList<String> ingList = new ArrayList<>();
            ArrayList<Steps> stepsList = new ArrayList<Steps>();
            String ingredientRow;
            Steps steps;

            try {
                for (int i = 0; i < ingredientsArray.length(); i++){
                    int qtt;
                    String  msure, ing;
                    JSONObject ingredientResults =  ingredientsArray.getJSONObject(i);

                    qtt = ingredientResults.getInt(KEY_QTT);
                    msure = ingredientResults.getString(KEY_MEASURE);
                    ing = ingredientResults.getString(KEY_INGREDIENT);


                    ingredientRow = String.valueOf(qtt) + " x " + msure + " " + ing;
                        ingList.add(ingredientRow);
                }

                for (int i = 0; i < stepsArray.length(); i++){
                    int mId;
                    String mSDesc, mDesc, mVideoURl;

                    JSONObject stepsResults =  stepsArray.getJSONObject(i);

                    mId = stepsResults.getInt(KEY_ID);
                    mSDesc = stepsResults.getString(KEY_SDESC);
                    mDesc = stepsResults.getString(KEY_DESC);
                    mVideoURl = stepsResults.getString(KEY_VIDEO);

                    steps = new Steps(mId,mSDesc,mDesc,mVideoURl);

                    stepsList.add(steps);

                }

                if (adapter != null && ingAdapter != null) {
                    adapter.clear();
                    ingAdapter.clear();
                }
                    mIngList.addAll(ingList);
                mStepsList.addAll(stepsList);
                    ingAdapter.notifyDataSetChanged();
                    adapter.notifyDataSetChanged();


            }catch (Exception e){
                Log.e(LOG_TAG,"Error extracting JSON Objects",e);
            }


        }





    }


