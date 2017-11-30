package com.cartola.jonatas.assistentefc.interfaces;

import com.cartola.jonatas.assistentefc.model.Authentication;
import com.cartola.jonatas.assistentefc.model.MarketStatus;
import com.cartola.jonatas.assistentefc.model.MatchesWrapped;
import com.cartola.jonatas.assistentefc.model.Message;
import com.cartola.jonatas.assistentefc.model.Payload;
import com.cartola.jonatas.assistentefc.model.PayloadWrapped;
import com.cartola.jonatas.assistentefc.model.PlayerScore;
import com.cartola.jonatas.assistentefc.model.PlayersScoreWrapped;
import com.cartola.jonatas.assistentefc.model.PlayersWrapped;
import com.cartola.jonatas.assistentefc.model.Team;
import com.cartola.jonatas.assistentefc.model.TeamLineUp;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by jonatas on 16/08/2017.
 */

public interface CartolaAPIService {

    public static final String BASE_URL = "https://api.cartolafc.globo.com/";
    public static final String BASE_LOGIN = "https://login.globo.com/";

    @GET("partidas/{rodada}")
    Call<MatchesWrapped> listMatches(@Path("rodada") String rodada);

    @GET("mercado/status")
    Call<MarketStatus> getMarketStatus();

    @GET("clubes")
    Call<Map<String, Team>> listTeams();

    @GET("atletas/pontuados")
    Call<PlayersScoreWrapped> listPartialScorePlayers();

    @GET("atletas/mercado")
    Call<PlayersWrapped> listScorePlayers();

    @POST("api/authentication")
    Call<Authentication> cartolaLogin(@Body PayloadWrapped payload);

    @POST("auth/time/salvar")
    Call<Message> teamLineUp(@Header("X-GLB-Token") String token, @Body TeamLineUp lineUp);
}
