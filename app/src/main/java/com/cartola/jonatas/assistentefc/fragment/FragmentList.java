package com.cartola.jonatas.assistentefc.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cartola.jonatas.assistentefc.ChartActivity;
import com.cartola.jonatas.assistentefc.MainActivity;
import com.cartola.jonatas.assistentefc.MenuActivity;
import com.cartola.jonatas.assistentefc.R;
import com.cartola.jonatas.assistentefc.adapter.MatchAdapter;
import com.cartola.jonatas.assistentefc.adapter.PlayerScoreAdapter;
import com.cartola.jonatas.assistentefc.interfaces.CartolaAPIService;
import com.cartola.jonatas.assistentefc.interfaces.OnBackPressedListener;
import com.cartola.jonatas.assistentefc.interfaces.RecycleViewOnClickListener;
import com.cartola.jonatas.assistentefc.model.MarketStatus;
import com.cartola.jonatas.assistentefc.model.Match;
import com.cartola.jonatas.assistentefc.model.MatchesWrapped;
import com.cartola.jonatas.assistentefc.model.PlayerScore;
import com.cartola.jonatas.assistentefc.model.PlayersScoreWrapped;
import com.cartola.jonatas.assistentefc.model.PlayersWrapped;
import com.cartola.jonatas.assistentefc.model.Team;
import com.cartola.jonatas.assistentefc.utils.APIUtils;
import com.cartola.jonatas.assistentefc.utils.StatisticUtils;
import com.github.mikephil.charting.charts.Chart;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jonatas on 14/08/2017.
 */

public class FragmentList extends Fragment implements RecycleViewOnClickListener, OnBackPressedListener {

    public static String recyclerState = "";
    RecyclerView recyclerView;
    private MatchesWrapped matchesWrapped;
    private int actualRound;
    private int marketStatus;
    private GridLayoutManager gridLayoutManager;
    private Map<String, Team> listTeams;
    private Map<String, PlayerScore> listPlayersScore;
    private int contador = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadPartialScorePlayers();
    }

    private void loadPartialScorePlayers() {
        loadActuaRound();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Match match = new Match();
        match.setScoreLocalTeam(7);
        match.setScoreVisitantTeam(0);
        List<Match> matches = new ArrayList<>();
        matches.add(match);
        Activity activity = getActivity();
        ((MenuActivity)activity).setOnBackPressedListener(this);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_files);

        //verifyOrientation();
        //MatchAdapter repoAdapter = new MatchAdapter(matches);
        //repoAdapter.setmRecycleViewOnClickListener(FragmentList.this);
        //recyclerView.setLayoutManager(gridLayoutManager);
        //recyclerView.setAdapter(repoAdapter);

        loadTeams();
        loadPlayers2();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentListView = inflater.inflate(R.layout.fragment_list, container, false);
        return fragmentListView;
    }

    @Override
    public void onClickListener(View view, Object object) {
        if(marketStatus != 1) {
            if (recyclerState.equals("Teams")) {
                Match match = (Match) object;
                verifyOrientation();
                List<PlayerScore> playerScoreList = new ArrayList<>();

                List<PlayerScore> houseTeam = APIUtils.getPlayersByTeam(new ArrayList<PlayerScore>(listPlayersScore.values()), match.getHouseTeamID());
                List<PlayerScore> visitantTeam = APIUtils.getPlayersByTeam(new ArrayList<PlayerScore>(listPlayersScore.values()), match.getVisitantTeamID());

                Collections.sort(houseTeam);
                Collections.sort(visitantTeam);

                playerScoreList.addAll(houseTeam);
                playerScoreList.addAll(visitantTeam);

                List<PlayerScore> listPlayersScoreAux = new ArrayList<PlayerScore>(playerScoreList);
                PlayerScoreAdapter repoAdapter = new PlayerScoreAdapter(listPlayersScoreAux);
                repoAdapter.setmRecycleViewOnClickListener(FragmentList.this);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(repoAdapter);
                recyclerState = "Players";
            } else {
                //Intent intent = new Intent(getActivity(), ChartActivity.class);
                //startActivity(intent);
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager = new GridLayoutManager(getContext(), 5);
        }  else {
            gridLayoutManager = new GridLayoutManager(getContext(), 1);
        }
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void verifyOrientation() {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            gridLayoutManager = new GridLayoutManager(getContext(), 5);
        else
            gridLayoutManager = new GridLayoutManager(getContext(), 1);
    }

    public void loadMatches() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CartolaAPIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CartolaAPIService service = retrofit.create(CartolaAPIService.class);
        Call<MatchesWrapped> requestTeam = service.listMatches(actualRound + "");

        requestTeam.enqueue(new Callback<MatchesWrapped>() {
            @Override
            public void onResponse(Call<MatchesWrapped> call, Response<MatchesWrapped> response) {
                matchesWrapped = response.body();
                if(matchesWrapped != null) {
                    for (Match match : matchesWrapped.getMatches()) {
                        match.setHouseTeam(listTeams.get(match.getHouseTeamID() + ""));
                        match.setVisitantTeam(listTeams.get(match.getVisitantTeamID() + ""));
                    }

                    verifyOrientation();
                    MatchAdapter repoAdapter = new MatchAdapter(matchesWrapped.getMatches());
                    repoAdapter.setmRecycleViewOnClickListener(FragmentList.this);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(repoAdapter);
                } else {
                    if(contador > 0) {
                        loadMatches();
                        contador--;
                    }
                }
            }

            @Override
            public void onFailure(Call<MatchesWrapped> call, Throwable t) {

            }
        });
    }

    public void loadTeams() {
        final Map<String, Team>  teams;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CartolaAPIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CartolaAPIService service = retrofit.create(CartolaAPIService.class);
        Call<Map<String, Team>> requestTeam = service.listTeams();
        Log.e("testando", "Teste");
        requestTeam.enqueue(new Callback<Map<String, Team>>() {
            @Override
            public void onResponse(Call<Map<String, Team>> call, Response<Map<String, Team>> response) {
                listTeams = response.body();
                for(Team team : listTeams.values()) {
                    Log.e("teste", team.getNome());
                    if(team.getNome().equals("Flamengo"))
                        Log.e("logTeamShield",team.getTeamShield().getUrlBigShield());
                }
                loadMatches();
                recyclerState = "Teams";
            }

            @Override
            public void onFailure(Call<Map<String, Team>> call, Throwable t) {

            }
        });
    }



    public void carregarTest() {
        Gson gson = new Gson();
        PlayersScoreWrapped playersScoreWrapped = gson.fromJson("{\"rodada\": 23, \"atletas\": { \"37281\": { \"apelido\": \"Mano Menezes\", \"pontuacao\": 4.31, \"scout\": {}, \"foto\": \"https://s.glbimg.com/es/sde/f/2016/08/09/246730e80d7ef5af15c8b59b8491c289_FORMATO.png\", \"posicao_id\": 6, \"clube_id\": 283 }, \"37306\": { \"apelido\": \"Gilson Kleina\", \"pontuacao\": 3.03, \"scout\": {}, \"foto\": \"https://s.glbimg.com/es/sde/f/2017/04/25/170c293b8b336044ef9d06f703796fcc_FORMATO.png\", \"posicao_id\": 6, \"clube_id\": 303}}}", new TypeToken<PlayersScoreWrapped>(){}.getType());
        for(PlayerScore playerScore : playersScoreWrapped.getPlayers().values()) {
            Log.e("logPlayersScore", playerScore.getNickName());
        }

        listPlayersScore = playersScoreWrapped.getPlayers();
    }

    public void loadPlayers() {
        final Map<String, Team>  teams;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CartolaAPIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CartolaAPIService service = retrofit.create(CartolaAPIService.class);
        Call<PlayersScoreWrapped> requestPartialScorePlayers = service.listPartialScorePlayers();
        Log.e("testando", "Teste");
        requestPartialScorePlayers.enqueue(new Callback<PlayersScoreWrapped>() {
            @Override
            public void onResponse(Call<PlayersScoreWrapped> call, Response<PlayersScoreWrapped> response) {
                listPlayersScore = response.body().getPlayers();
            }

            @Override
            public void onFailure(Call<PlayersScoreWrapped> call, Throwable t) {

            }
        });
    }

    public void loadPlayers2() {
        final Map<String, Team>  teams;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CartolaAPIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CartolaAPIService service = retrofit.create(CartolaAPIService.class);
        Call<PlayersWrapped> requestPartialScorePlayers = service.listScorePlayers();
        Log.e("testando", "Teste");
        requestPartialScorePlayers.enqueue(new Callback<PlayersWrapped>() {
            @Override
            public void onResponse( Call<PlayersWrapped> call, Response<PlayersWrapped> response) {
                List<PlayerScore> listPlayers = response.body().getPlayers();
                Collections.sort(listPlayers);
            }

            @Override
            public void onFailure(Call<PlayersWrapped> call, Throwable t) {
                Log.e("falhou", "falhou");
            }
        });
    }

    public void loadActuaRound() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CartolaAPIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CartolaAPIService service = retrofit.create(CartolaAPIService.class);
        Call<MarketStatus> requestTeam = service.getMarketStatus();
        Log.e("testando", "Teste");
        requestTeam.enqueue(new Callback<MarketStatus>() {
            @Override
            public void onResponse(Call<MarketStatus> call, Response<MarketStatus> response) {
                MarketStatus  status = response.body();

                Log.i("jonatas2","" + status.getActualRound());
                actualRound = status.getActualRound();
                marketStatus = status.getStatusMarket();
                if(marketStatus != 1) {
                    loadPlayers();
                }
            }

            @Override
            public void onFailure(Call<MarketStatus> call, Throwable t) {

            }

           /* @Override
            public void onResponse(Response<MarketStatus> response, Retrofit retrofit) {
                if(!response.isSuccess()) {
                    Log.e("jonatas1", response.toString());
                    Log.e("jonatas1", "" +response.code());
                } else {
                    MarketStatus  status = response.body();

                    Log.i("jonatas2","" + status.getActualRound());

                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("jonatas1", t.getMessage());
            }
            */
        });
    }

    @Override
    public void doBack() {
        loadTeams();
    }
}
