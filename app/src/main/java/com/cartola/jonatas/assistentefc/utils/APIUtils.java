package com.cartola.jonatas.assistentefc.utils;

import android.graphics.drawable.Drawable;

import com.cartola.jonatas.assistentefc.model.PlayerScore;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jonatas on 12/09/2017.
 */

public class APIUtils {

    public static Drawable loadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable drawable = Drawable.createFromStream(is, "src name");
            return drawable;
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatDateAPI(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfResult = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Date matchDate = null;
        try {
            matchDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdfResult.format(matchDate);
    }

    public static List<PlayerScore> getPlayersByTeam(List<PlayerScore> allPlayers, long teamID) {
        List<PlayerScore> listPlayerTeam = new ArrayList<>();

        for(PlayerScore player : allPlayers) {
            if(player.getTeamID() == teamID) {
                listPlayerTeam.add(player);
            }
        }
        return listPlayerTeam;
    }

    public static String getPositionName(int position) {
        if(position == 1) {
            return "Goleiro";
        } else if(position == 2) {
            return "Lateral";
        } else if(position == 3) {
            return "Zagueiro";
        } else if(position == 4) {
            return "Meia";
        } else if(position == 5) {
            return "Atacante";
        } else if(position == 6) {
            return "Técnico";
        } else {
            return "";
        }
    }


}
