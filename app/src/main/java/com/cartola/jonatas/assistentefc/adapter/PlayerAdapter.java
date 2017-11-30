package com.cartola.jonatas.assistentefc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cartola.jonatas.assistentefc.R;
import com.cartola.jonatas.assistentefc.interfaces.RecycleViewOnClickListener;
import com.cartola.jonatas.assistentefc.model.PlayerScore;
import com.cartola.jonatas.assistentefc.utils.APIUtils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by jonatas on 15/08/2017.
 */

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    private List<PlayerScore> listPlayers;
    //private LayoutInflater layoutInflater;
    private RecycleViewOnClickListener mRecycleViewOnClickListener;

    public PlayerAdapter(List<PlayerScore> listPlayers) {
        this.listPlayers = listPlayers;
        //layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imagePlayer;
        private TextView namePlayer;
        private TextView positionPlayer;
        private TextView score;
        private RelativeLayout relativeLayout;
        private Context context;

        public ViewHolder(View view, Context context) {
            super(view);
            imagePlayer = (ImageView) itemView.findViewById(R.id.image_player);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_layout_file);
            namePlayer = (TextView) itemView.findViewById(R.id.text_player_name);
            positionPlayer = (TextView) itemView.findViewById(R.id.text_player_position);
            score = (TextView)itemView.findViewById(R.id.text_score);
            itemView.setOnClickListener(this);
            this.context = context;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecycleViewOnClickListener != null)
                mRecycleViewOnClickListener.onClickListener(v, listPlayers.get(getPosition()));
        }
    }

    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_partial_score_item, parent, false);
        ViewHolder vh = new ViewHolder(v, parent.getContext());
        return vh;
    }

    @Override
    public void onBindViewHolder(PlayerAdapter.ViewHolder holder, int position) {
        //holder.nameFile.setText(repositories.get(position).getName());
        //holder..setText(repositories.get(position).getDescription());
        holder.namePlayer.setText(listPlayers.get(position).getNickName() + "");
        holder.positionPlayer.setText(APIUtils.getPositionName(listPlayers.get(position).getPosition()));
        if(listPlayers.get(position).getFoto() != null) {
            Picasso.with(holder.context).load(listPlayers.get(position).getFoto().replace("FORMATO", "140x140")).into(holder.imagePlayer);
        }
        if(listPlayers.get(position).getMedia() < 0 ) {
            holder.score.setTextColor(Color.RED);
        } else {
            holder.score.setTextColor(ContextCompat.getColor(holder.context, R.color.greenScore));
        }
        DecimalFormat df = new DecimalFormat("#.##");
        holder.score.setText(df.format(listPlayers.get(position).getMedia()));
    }

    public void setmRecycleViewOnClickListener(RecycleViewOnClickListener mRecycleViewOnClickListener) {
        this.mRecycleViewOnClickListener = mRecycleViewOnClickListener;
    }

    @Override
    public int getItemCount() {
        return listPlayers.size();
    }
}
