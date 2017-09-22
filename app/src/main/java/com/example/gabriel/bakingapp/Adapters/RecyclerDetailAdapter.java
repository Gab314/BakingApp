package com.example.gabriel.bakingapp.Adapters;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gabriel.bakingapp.R;
import com.example.gabriel.bakingapp.Utils.Steps;
import com.example.gabriel.bakingapp.fragments.DetailFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerDetailAdapter extends RecyclerView.Adapter<RecyclerDetailAdapter.MyHolder> {


    private ItemClickListener mClickListener;
    private ArrayList<Steps> mStepList;
    private Context mContext;

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mText;
        private CardView mCardView;

        public MyHolder(View itemView) {
            super(itemView);

            mText = (TextView) itemView.findViewById(R.id.card_detailed_text_view_ing);
            mCardView = (CardView) itemView.findViewById(R.id.card_detailed_card_view_ingredients);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public RecyclerDetailAdapter(Context context, ArrayList<Steps> stepsArrayList){


            mContext = context;
            mStepList = stepsArrayList;

    }

    private Context getContext(){
        return mContext;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View mView = inflater.inflate(R.layout.cards_detailed,parent, false);

        return new MyHolder(mView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        Steps steps = mStepList.get(position);


        String description = steps.getDesc();




        TextView rTextView = holder.mText;


            rTextView.setText(description);
    }



    @Override
    public int getItemCount() {
        return mStepList.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void clear(){
        if ( mStepList != null){
            mStepList.clear();
            notifyDataSetChanged();
        }
    }
    public Steps getItemStep(int id){
        return mStepList.get(id);
    }
}
