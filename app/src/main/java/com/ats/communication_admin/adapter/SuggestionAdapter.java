package com.ats.communication_admin.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import com.ats.communication_admin.R;
import com.ats.communication_admin.activity.AddSuggestionActivity;
import com.ats.communication_admin.activity.ImageZoomActivity;
import com.ats.communication_admin.activity.InnerTabActivity;
import com.ats.communication_admin.activity.SuggestionDetailActivity;
import com.ats.communication_admin.activity.ViewImageActivity;
import com.ats.communication_admin.bean.Info;
import com.ats.communication_admin.bean.SuggestionData;
import com.ats.communication_admin.common.CommonDialog;
import com.ats.communication_admin.constants.Constants;
import com.ats.communication_admin.db.DatabaseHandler;
import com.ats.communication_admin.fragment.SuggestionFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MAXADMIN on 30/1/2018.
 */

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.MyViewHolder> {

    private ArrayList<SuggestionData> suggestionList;
    private Context context;
    DatabaseHandler db;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, desc, date, count, franchise;
        public LinearLayout llBack, llMenu;
        public ImageView ivImage;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tvSuggestionItem_Title);
            desc = view.findViewById(R.id.tvSuggestionItem_Desc);
            date = view.findViewById(R.id.tvSuggestionItem_Date);
            llBack = view.findViewById(R.id.llSuggestionItem);
            ivImage = view.findViewById(R.id.ivSuggestionItem_Image);
            count = view.findViewById(R.id.tvSuggestionItem_Count);
            franchise = view.findViewById(R.id.tvSuggestionItem_Fr);

        }
    }

    public SuggestionAdapter(ArrayList<SuggestionData> suggestionList, Context context) {
        this.suggestionList = suggestionList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_suggestion_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final SuggestionData suggestion = suggestionList.get(position);
        holder.title.setText(suggestion.getTitle());
        holder.desc.setText(suggestion.getDescription());
        holder.date.setText(suggestion.getDate());
        holder.franchise.setText(suggestion.getFrName());

        Log.e("Suggestion Adapter", "----------" + suggestion);
        db = new DatabaseHandler(context);

        final String image = Constants.SUGGESTION_IMAGE_URL + suggestion.getPhoto();
        try {
            Picasso.with(context)
                    .load(image)
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo)
                    .into(holder.ivImage);
        } catch (Exception e) {
        }

        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //  Intent intent = new Intent(context, ViewImageActivity.class);
                 Intent intent = new Intent(context, ImageZoomActivity.class);
                intent.putExtra("image", image);
                context.startActivity(intent);
            }
        });

        holder.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SuggestionDetailActivity.class);
                intent.putExtra("suggestionId", suggestion.getSuggestionId());
                intent.putExtra("refresh", 0);
                context.startActivity(intent);
            }
        });

        if (db.getSuggestionDetailUnreadCount(suggestion.getSuggestionId()) > 0) {
            holder.count.setVisibility(View.VISIBLE);
            holder.count.setText("" + db.getSuggestionDetailUnreadCount(suggestion.getSuggestionId()));
        } else {
            holder.count.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return suggestionList.size();
    }




}
