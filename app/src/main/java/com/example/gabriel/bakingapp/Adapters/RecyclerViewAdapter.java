package com.example.gabriel.bakingapp.Adapters;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.gabriel.bakingapp.R;
import com.example.gabriel.bakingapp.Utils.RecipeCards;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private ItemClickListener mClickListener;
    private List<RecipeCards> mRecipeCards;
    private Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextView, sTextView;
        private CardView mCardView;
        private ImageView mImageView;


        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.card_recipe_text_view);
            sTextView = (TextView) itemView.findViewById(R.id.card_recipe_text_view_servings);
            mCardView = (CardView) itemView.findViewById(R.id.card_recipe_card_view);
            mImageView = (ImageView) itemView.findViewById(R.id.card_recipe_imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public RecyclerViewAdapter(Context context, List<RecipeCards> recipes){
        mContext = context;
        mRecipeCards = recipes;
    }

    private Context getContext(){
        return mContext;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View mView = inflater.inflate(R.layout.card_recipe,parent, false);

        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, int position) {

        RecipeCards cards = mRecipeCards.get(position);
        String imageURL = cards.getRecipeImg();
        TextView textView = holder.mTextView;
        TextView stextView = holder.sTextView;
        ImageView image = holder.mImageView;

        if (imageURL.length() > 0){
            Picasso.with(getContext()).setLoggingEnabled(true);
            Picasso.with(getContext()).load(imageURL).into(image);
        }
        String text_name = cards.getRecipe_name();
        String servings = "Servings: " + String.valueOf(cards.getRecipe_servings_size());
            textView.setText(text_name);
            stextView.setText(servings);

    }

    @Override
    public int getItemCount() {
        return mRecipeCards.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
       this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    public void clear(){
        if (mRecipeCards != null) {
            mRecipeCards.clear();
            notifyDataSetChanged();
        }
    }

    public RecipeCards getItem(int id){
        return mRecipeCards.get(id);
    }
}
