package com.jss.abhi.zealicon.recyclerview.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jss.abhi.zealicon.R;
import com.jss.abhi.zealicon.model.Developer;

import java.util.ArrayList;

/**
 * Created by abhi on 19/2/18.
 */

public class SponsorAdapter extends RecyclerView.Adapter<SponsorAdapter.SponsorViewHolder> {

    private Context context;
    private ArrayList<Developer> mSponsorList;
    int[] sponsorResArray =
            new int[]{
                    R.drawable.hdfc,
                    R.drawable.glued,
                    R.drawable.ace,
                    R.drawable.iot_acdemy,
                    R.drawable.primesweet,
                    R.drawable.hutkar,
                    R.drawable.kamat,
                    R.drawable.cafeyuva,
                    R.drawable.sweetcorner,
                    R.drawable.strawsome1,
                    R.drawable.cupcake,
                    R.drawable.rio,
                    R.drawable.bingo,
                    R.drawable.joy,
                    R.drawable.salon,
                    R.drawable.aptron,
                    R.drawable.cetpa,
                    R.drawable.rcpl,
                    R.drawable.pfc,
                    R.drawable.alkceee,
                    R.drawable.panit,
                    R.drawable.honda,
                    R.drawable.grogg,
                    R.drawable.atkt,
                    R.drawable.dubeat
            };

    public class SponsorViewHolder extends RecyclerView.ViewHolder {

        private ImageView profileImg;
        private TextView name, position;

        public SponsorViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            name = itemView.findViewById(R.id.sponsor_name);
            position = itemView.findViewById(R.id.sponsor_category);
            profileImg = itemView.findViewById(R.id.sponsor_image);
        }
    }

    @Override
    public SponsorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sponsor, parent, false);
        return new SponsorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SponsorViewHolder holder, int position) {
        final Developer sponsor = mSponsorList.get(position);
        holder.name.setText(sponsor.getName());
        holder.position.setText(sponsor.getPosition());
        holder.profileImg.setImageResource(sponsorResArray[position]);
//      holder.profileImg.setImageResource(R.drawable.corpbank);


    }

    @Override
    public int getItemCount() {
        return mSponsorList.size();
    }

    public SponsorAdapter(ArrayList<Developer> mTeamList) {
        this.mSponsorList = mTeamList;
    }


}
