package com.example.gabriel.bakingapp.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gabriel.bakingapp.Adapters.RecyclerViewAdapter;
import com.example.gabriel.bakingapp.R;
import com.example.gabriel.bakingapp.Utils.RecipeCards;
import com.example.gabriel.bakingapp.activities.DetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;

public class RecipesFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener{


    RecyclerViewAdapter adapter;
    ArrayList<RecipeCards> mCardsList;
    final String LOG_TAG = RecipesFragment.class.getSimpleName();
public RecipesFragment(){

}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){

            if (isOnline(getActivity())) {

                RecipeSyncAdapter recipeSyncAdapter = new RecipeSyncAdapter();
                String mParams = "59121517_baking";
                recipeSyncAdapter.execute(mParams);

            }else Toast.makeText(getActivity(), "No Connection!", Toast.LENGTH_SHORT).show();
        }
        //restore saved state

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootview = inflater.inflate(R.layout.fragment_recipes, container, false);
         RecyclerView   rv = (RecyclerView) rootview.findViewById(R.id.fragments_recipes_recycler_view);

        mCardsList = new ArrayList<>();
        if (savedInstanceState != null){
            mCardsList = savedInstanceState.getParcelableArrayList("Cards");
        }
        rv.setHasFixedSize(true);
        int numberOfColumns = calculateNoOfColumns(getActivity());
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        rv.setLayoutManager(mLayoutManager);

        adapter = new RecyclerViewAdapter(getActivity(), mCardsList);
        rv.setAdapter(adapter);
        adapter.setClickListener(this);

            return rootview;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("Cards", mCardsList);
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    //@Override
    public void onItemClick(View view, int position) {
        try {
               RecipeCards recipes =  adapter.getItem(position);
            Intent intent = new Intent(getActivity(), DetailActivity.class);

            intent.putExtra("recipe", recipes.getJSONString());
            intent.putExtra("position",position);
            startActivity(intent);

        }catch (NullPointerException e){
            Log.e(LOG_TAG,"Error",e);
        }
    }

    private class RecipeSyncAdapter extends AsyncTask<String, Void, ArrayList<RecipeCards>>{
        private ArrayList<RecipeCards> mList;

        private ArrayList<RecipeCards> getDetailsDataFromJson(String mJSonStr)

                throws JSONException {
            final String KEY_ID = "id";
            final String KEY_NAME = "name";
            final String KEY_SERVINGS = "servings";
            mList = new ArrayList<>();


            JSONArray results = new JSONArray(mJSonStr);

            for (int i = 0; i < results.length(); i++) {
                int resultID;
                String resultName;

                int resultServings;



                JSONObject result = results.getJSONObject(i);



                resultID = result.getInt(KEY_ID);
                resultName = result.getString(KEY_NAME);
                resultServings = result.getInt(KEY_SERVINGS);

                RecipeCards recipeCards = new RecipeCards(resultID, resultName, resultServings,mJSonStr);

                mList.add(recipeCards);

            }
            return mList;
        }
        @Override
        protected ArrayList<RecipeCards> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonStr = null;

            try {
                Uri.Builder urlBuilder = new Uri.Builder();
                final String DB_BASE_URL = "d17h27t6h515a5.cloudfront.net";
                final String DB_TOPHER = "topher";
                final String DB_YR = "2017";
                final String DB_MONTH = "May";
                // params = final String DB_IDS = "59121517_baking";
                final String DB_JSON = "baking.json";

                urlBuilder.scheme("http");
                urlBuilder.authority(DB_BASE_URL);
                urlBuilder.appendEncodedPath(DB_TOPHER);
                urlBuilder.appendEncodedPath(DB_YR);
                urlBuilder.appendEncodedPath(DB_MONTH);
                urlBuilder.appendEncodedPath(params[0]);
                urlBuilder.appendEncodedPath(DB_JSON);

                URL url = new URL(urlBuilder.build().toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                jsonStr = buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return getDetailsDataFromJson(jsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<RecipeCards> result) {

                if (adapter != null)
                    adapter.clear();



            if (result != null){
                mCardsList.addAll(result);
                adapter.notifyDataSetChanged();
            }else Toast.makeText(getActivity(),"Can't retrieve resources", Toast.LENGTH_SHORT).show();

        }
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 300;
        return (int) (dpWidth / scalingFactor);
    }
}
