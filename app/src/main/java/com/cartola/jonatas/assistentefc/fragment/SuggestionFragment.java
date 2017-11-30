package com.cartola.jonatas.assistentefc.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cartola.jonatas.assistentefc.MenuActivity;
import com.cartola.jonatas.assistentefc.R;
import com.cartola.jonatas.assistentefc.SuggestionTeamActivity;
import com.cartola.jonatas.assistentefc.interfaces.CartolaAPIService;
import com.cartola.jonatas.assistentefc.model.PlayerScore;
import com.cartola.jonatas.assistentefc.model.PlayersWrapped;
import com.cartola.jonatas.assistentefc.model.Team;
import com.cartola.jonatas.assistentefc.utils.StatisticUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SuggestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SuggestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuggestionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<PlayerScore> listPlayers;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SuggestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuggestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SuggestionFragment newInstance(String param1, String param2) {
        SuggestionFragment fragment = new SuggestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        loadPlayers2();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestion, container, false);
        Button buttonGenerateTeam = (Button) view.findViewById(R.id.button_generate_team);
        buttonGenerateTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<PlayerScore> bestPlayers = null;
                TextView textQuantCartoletas = (TextView) getActivity().findViewById(R.id.text_cartoletas);
                int quantCartoletas = -1;
                if(textQuantCartoletas.getText().toString() != null && textQuantCartoletas.getText().toString().length() > 0) {
                    quantCartoletas = Integer.parseInt(textQuantCartoletas.getText().toString());
                }
                int formation = -1;
                int positionToSpendMore = -1;
                RadioGroup groupFormation = (RadioGroup) getActivity().findViewById(R.id.group_formation);
                RadioGroup groupPosition = (RadioGroup) getActivity().findViewById(R.id.group_position);

                if(groupPosition.getCheckedRadioButtonId() == R.id.radio_goleiro) {
                    positionToSpendMore = 1;
                } else if(groupPosition.getCheckedRadioButtonId() == R.id.radio_lateral) {
                    positionToSpendMore = 2;
                } else if(groupPosition.getCheckedRadioButtonId() == R.id.radio_zagueiro) {
                    positionToSpendMore = 3;
                } else if(groupPosition.getCheckedRadioButtonId() == R.id.radio_meia) {
                    positionToSpendMore = 4;
                } else if(groupPosition.getCheckedRadioButtonId() == R.id.radio_atacante) {
                    positionToSpendMore = 5;
                }

                    if (groupFormation.getCheckedRadioButtonId() == R.id.radio_343) {
                        bestPlayers = StatisticUtils.getBestPlayers(listPlayers, quantCartoletas, 3, 0, 4, 3, positionToSpendMore);
                        formation = 1;
                    } else if (groupFormation.getCheckedRadioButtonId() == R.id.radio_352) {
                        bestPlayers = StatisticUtils.getBestPlayers(listPlayers, quantCartoletas, 3, 0, 5, 2, positionToSpendMore);
                        formation = 2;
                    } else if (groupFormation.getCheckedRadioButtonId() == R.id.radio_433) {
                        bestPlayers = StatisticUtils.getBestPlayers(listPlayers, quantCartoletas, 2, 2, 3, 3, positionToSpendMore);
                        formation = 3;
                    } else if (groupFormation.getCheckedRadioButtonId() == R.id.radio_442) {
                        bestPlayers = StatisticUtils.getBestPlayers(listPlayers, quantCartoletas, 2, 2, 4, 2, positionToSpendMore);
                        formation = 4;
                    } else if (groupFormation.getCheckedRadioButtonId() == R.id.radio_451) {
                        bestPlayers = StatisticUtils.getBestPlayers(listPlayers, quantCartoletas, 2, 2, 5, 1, positionToSpendMore);
                        formation = 5;
                    } else if (groupFormation.getCheckedRadioButtonId() == R.id.radio_532) {
                        bestPlayers = StatisticUtils.getBestPlayers(listPlayers, quantCartoletas, 3, 2, 3, 2, positionToSpendMore);
                        formation = 6;
                    } else if (groupFormation.getCheckedRadioButtonId() == R.id.radio_541) {
                        bestPlayers = StatisticUtils.getBestPlayers(listPlayers, quantCartoletas, 3, 2, 4, 1, positionToSpendMore);
                        formation = 6;
                    }

                if(textQuantCartoletas.getText().toString().length() > 0 && formation != -1 && positionToSpendMore != -1) {


                    Collections.sort(bestPlayers, new Comparator<PlayerScore>() {
                        public int compare(PlayerScore one, PlayerScore two) {
                            if (one.getPosition() > two.getPosition())
                                return 1;
                            else if (one.getPosition() < two.getPosition())
                                return -1;
                            else
                                return 0;
                        }
                    });
                    Intent intent = new Intent(getActivity(), SuggestionTeamActivity.class);
                    intent.putExtra("listPlayers", (Serializable) bestPlayers);
                    intent.putExtra("formation", formation);

                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(),"Campo obrigatório não preenchido", Toast.LENGTH_SHORT).show();

                }
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void loadPlayers2() {
        final Map<String, Team> teams;
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
                listPlayers = response.body().getPlayers();
                Collections.sort(listPlayers);
            }

            @Override
            public void onFailure(Call<PlayersWrapped> call, Throwable t) {
                Log.e("falhou", "falhou");
            }
        });
    }

}
