package com.cartola.jonatas.assistentefc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.cartola.jonatas.assistentefc.fragment.FragmentList;
import com.cartola.jonatas.assistentefc.interfaces.CartolaAPIService;
import com.cartola.jonatas.assistentefc.interfaces.OnBackPressedListener;
import com.cartola.jonatas.assistentefc.model.MarketStatus;
import com.cartola.jonatas.assistentefc.model.Match;
import com.cartola.jonatas.assistentefc.model.MatchesWrapped;
import com.cartola.jonatas.assistentefc.model.PlayerScore;
import com.cartola.jonatas.assistentefc.model.PlayersScoreWrapped;
import com.cartola.jonatas.assistentefc.model.Team;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    protected OnBackPressedListener onBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragmentList();
        Log.d("Teste1", "olar");
        carregarTeste();
        carregarTeste2();
        carregarTeste3();
        carregarTest();
    }

    @Override
    public void onBackPressed() {
        if(FragmentList.recyclerState.equals("Teams"))
            super.onBackPressed();
        else
            onBackPressedListener.doBack();
    }

    public void loadFragmentList() {
        FragmentList filesListFragment = new FragmentList();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, filesListFragment).commit();
    }

    public void carregarTeste() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CartolaAPIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CartolaAPIService service = retrofit.create(CartolaAPIService.class);
        Call<MatchesWrapped> requestTeam = service.listMatches("23");
        Log.e("testando", "Teste");

        requestTeam.enqueue(new Callback<MatchesWrapped>() {
            @Override
            public void onResponse(Call<MatchesWrapped> call, Response<MatchesWrapped> response) {
                MatchesWrapped matchesWrapped = response.body();

                for (Match team : matchesWrapped.getMatches()) {
                    Log.i("jonatas2", team.getLocal());
                }
            }

            @Override
            public void onFailure(Call<MatchesWrapped> call, Throwable t) {

            }

            /*
            @Override
            public void onResponse(Response<MatchesWrapped> response, Retrofit retrofit) {
                if(!response.isSuccess()) {
                    Log.e("jonatas1", response.toString());
                    Log.e("jonatas1", "" +response.code());
                } else {
                    MatchesWrapped  matches = response.body();

                    for(Match team : matches.getMatches()) {
                        Log.i("jonatas2", team.getLocal());
                    }
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("jonatas1", t.getMessage());
            }
            */
        });


    }

    public void carregarTeste2() {
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

    public void carregarTeste3() {
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
                Map<String, Team>  status = response.body();
                for(Team team : status.values()) {
                    Log.e("logandoTesteTeam", team.getNome());
                    if(team.getNome().equals("Flamengo"))
                        Log.i("logTeamShield",team.getTeamShield().getUrlBigShield());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Team>> call, Throwable t) {

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

    public void carregarTest() {
        Gson gson = new Gson();
        PlayersScoreWrapped playersScoreWrapped = gson.fromJson("{\"rodada\": 23, \"atletas\": { \"37281\": { \"apelido\": \"Mano Menezes\", \"pontuacao\": 4.31, \"scout\": {}, \"foto\": \"https://s.glbimg.com/es/sde/f/2016/08/09/246730e80d7ef5af15c8b59b8491c289_FORMATO.png\", \"posicao_id\": 6, \"clube_id\": 283 }, \"37306\": { \"apelido\": \"Gilson Kleina\", \"pontuacao\": 3.03, \"scout\": {}, \"foto\": \"https://s.glbimg.com/es/sde/f/2017/04/25/170c293b8b336044ef9d06f703796fcc_FORMATO.png\", \"posicao_id\": 6, \"clube_id\": 303}}}", new TypeToken<PlayersScoreWrapped>(){}.getType());
        for(PlayerScore playerScore : playersScoreWrapped.getPlayers().values()) {
            Log.e("logPlayersScore", playerScore.getNickName());
        }
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }
}
