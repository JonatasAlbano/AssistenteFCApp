package com.cartola.jonatas.assistentefc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cartola.jonatas.assistentefc.R;
import com.cartola.jonatas.assistentefc.interfaces.RecycleViewOnClickListener;
import com.cartola.jonatas.assistentefc.model.Match;
import com.cartola.jonatas.assistentefc.utils.APIUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jonatas on 15/08/2017.
 */

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {
    private List<Match> matches;
    //private LayoutInflater layoutInflater;
    private RecycleViewOnClickListener mRecycleViewOnClickListener;

    public MatchAdapter(List<Match> matches) {
        this.matches = matches;
        //layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageTeam1;
        private ImageView imageTeam2;
        private TextView score1;
        private TextView score2;
        private TextView placeMatch;
        private TextView matchDate;
        private TextView houseTeamName;
        private TextView visitantTeamName;
        private RelativeLayout relativeLayout;
        private Context context;

        public ViewHolder(View view, Context context) {
            super(view);
            score1 = (TextView) itemView.findViewById(R.id.textPlacar1);
            score2 = (TextView) itemView.findViewById(R.id.textPlacar2);
            imageTeam1 = (ImageView) itemView.findViewById(R.id.image_player);
            imageTeam2 = (ImageView) itemView.findViewById(R.id.image_team_2);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_layout_file);
            placeMatch = (TextView) itemView.findViewById(R.id.textLocal);
            matchDate = (TextView) itemView.findViewById(R.id.textDate);
            houseTeamName = (TextView) itemView.findViewById(R.id.textHouseTeamName);
            visitantTeamName = (TextView)itemView.findViewById(R.id.textVisitantTeamName);
            itemView.setOnClickListener(this);
            this.context = context;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecycleViewOnClickListener != null)
                mRecycleViewOnClickListener.onClickListener(v, matches.get(getPosition()));
        }
    }

    @Override
    public MatchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item, parent, false);
        ViewHolder vh = new ViewHolder(v, parent.getContext());
        return vh;
    }

    @Override
    public void onBindViewHolder(MatchAdapter.ViewHolder holder, int position) {
        //holder.nameFile.setText(repositories.get(position).getName());
        //holder..setText(repositories.get(position).getDescription());
        holder.score1.setText(matches.get(position).getScoreLocalTeam() + "");
        holder.score2.setText(matches.get(position).getScoreVisitantTeam() + "");
        if(matches.get(position).getHouseTeam().getTeamShield().getUrlBigShield() != null && matches.get(position).getVisitantTeam().getTeamShield().getUrlBigShield() != null) {
            Picasso.with(holder.context).load(matches.get(position).getHouseTeam().getTeamShield().getUrlBigShield()).into(holder.imageTeam1);
            Picasso.with(holder.context).load(matches.get(position).getVisitantTeam().getTeamShield().getUrlBigShield()).into(holder.imageTeam2);
        }
        holder.placeMatch.setText(matches.get(position).getLocal());
        holder.matchDate.setText(APIUtils.formatDateAPI(matches.get(position).getMatchDate()));
        holder.houseTeamName.setText(matches.get(position).getHouseTeam().getNome());
        holder.visitantTeamName.setText(matches.get(position).getVisitantTeam().getNome());
    }

    public void setmRecycleViewOnClickListener(RecycleViewOnClickListener mRecycleViewOnClickListener) {
        this.mRecycleViewOnClickListener = mRecycleViewOnClickListener;
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }
}
