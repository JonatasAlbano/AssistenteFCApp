package com.cartola.jonatas.assistentefc;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cartola.jonatas.assistentefc.adapter.PlayerAdapter;
import com.cartola.jonatas.assistentefc.adapter.PlayerScoreAdapter;
import com.cartola.jonatas.assistentefc.fragment.FragmentList;
import com.cartola.jonatas.assistentefc.interfaces.CartolaAPIService;
import com.cartola.jonatas.assistentefc.model.Authentication;
import com.cartola.jonatas.assistentefc.model.Message;
import com.cartola.jonatas.assistentefc.model.Payload;
import com.cartola.jonatas.assistentefc.model.PayloadWrapped;
import com.cartola.jonatas.assistentefc.model.PlayerScore;
import com.cartola.jonatas.assistentefc.model.TeamLineUp;
import com.cartola.jonatas.assistentefc.utils.StatisticUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SuggestionTeamActivity extends AppCompatActivity {

    private List<PlayerScore> listPlayers;
    private int formation;
    String token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listPlayers = (List<PlayerScore>) getIntent().getSerializableExtra("listPlayers");
        formation = getIntent().getIntExtra("formation", 0);
        setContentView(R.layout.activity_suggestion_team);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_players);
        PlayerAdapter repoAdapter = new PlayerAdapter(listPlayers);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(repoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.suggestion_team_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_suggestion_item:
                showLoginDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showLoginDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_login, null);
        dialogBuilder.setView(dialogView);

        final EditText username = (EditText) dialogView.findViewById(R.id.username);
        final EditText password = (EditText) dialogView.findViewById(R.id.password);

        dialogBuilder.setTitle("Login no Cartola FC");
        dialogBuilder.setMessage("Coloque seu email e senha para escalar o time gerado");
        dialogBuilder.setPositiveButton("Logar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(username.getText().toString().length() > 0 && password.getText().toString().length() > 0) {
                    loginCartolaFC(username.getText().toString(), password.getText().toString());
                } else {
                    Toast.makeText(SuggestionTeamActivity.this,"Usuario e/ou senha inválidos", Toast.LENGTH_SHORT).show();

                }
            }
        });
        dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void loginCartolaFC(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CartolaAPIService.BASE_LOGIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CartolaAPIService service = retrofit.create(CartolaAPIService.class);
        Payload payload = new Payload();
        payload.setEmail(email);
        payload.setPassword(password);
        Log.e("password", password);
        payload.setServiceId(4728);

        PayloadWrapped payloadWrapped = new PayloadWrapped();
        payloadWrapped.setPayload(payload);

        Call<Authentication> requestPartialScorePlayers = service.cartolaLogin(payloadWrapped);
        requestPartialScorePlayers.enqueue(new Callback<Authentication>() {
            @Override
            public void onResponse( Call<Authentication> call, Response<Authentication> response) {
                Authentication authentication = null;
                if(response.body() != null) {
                    authentication = response.body();
                    token = authentication.getGlbId();
                    postLineUp(listPlayers, formation, token);
                } else {
                    Toast.makeText(SuggestionTeamActivity.this,"Erro ao efetuar o login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Authentication> call, Throwable t) {
                Log.e("authentication", "falhou");
                Toast.makeText(SuggestionTeamActivity.this,"Erro ao efetuar o login", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void postLineUp(List<PlayerScore> listPlayers, int formation, String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CartolaAPIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CartolaAPIService service = retrofit.create(CartolaAPIService.class);
        TeamLineUp teamLineUp = new TeamLineUp();
        teamLineUp.setEsquema(formation);
        teamLineUp.setAtleta(teamLineupToIdList(listPlayers));

        Call<Message> requestPartialScorePlayers = service.teamLineUp(token, teamLineUp);
        Log.e("testando", "Teste");
        requestPartialScorePlayers.enqueue(new Callback<Message>() {
            @Override
            public void onResponse( Call<Message> call, Response<Message> response) {
                Message message = null;
                if(response.body() != null) {
                    message = response.body();
                    if(message.getMensagem().contains("Time Escalado")) {
                        Toast.makeText(SuggestionTeamActivity.this,"Time escalado com sucesso!", Toast.LENGTH_SHORT).show();

                    } else if(message.getMensagem().contains("game nesse estado")) {
                        Toast.makeText(SuggestionTeamActivity.this,"Não foi possivel escalar o time! O mercado está fora", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(SuggestionTeamActivity.this,"Ocorreu algum erro ao escalar time. Verifique se o mercado está aberto", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SuggestionTeamActivity.this,"Ocorreu algum erro ao escalar time. Verifique se o mercado está aberto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e("authentication", "falhou");
            }
        });
    }

    private List<Long> teamLineupToIdList(List<PlayerScore> listPlayers) {
        List<Long> listTeamId = new ArrayList<>();
        for(PlayerScore player : listPlayers) {
            listTeamId.add(player.getId());
        }
        return listTeamId;
    }
}
