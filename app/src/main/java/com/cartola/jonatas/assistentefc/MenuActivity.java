package com.cartola.jonatas.assistentefc;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RadioButton;
import android.widget.TextView;

import com.cartola.jonatas.assistentefc.fragment.FragmentList;
import com.cartola.jonatas.assistentefc.fragment.SuggestionFragment;
import com.cartola.jonatas.assistentefc.interfaces.CartolaAPIService;
import com.cartola.jonatas.assistentefc.interfaces.OnBackPressedListener;
import com.cartola.jonatas.assistentefc.model.Authentication;
import com.cartola.jonatas.assistentefc.model.Payload;
import com.cartola.jonatas.assistentefc.model.PayloadWrapped;
import com.cartola.jonatas.assistentefc.model.PlayerScore;
import com.cartola.jonatas.assistentefc.model.PlayersWrapped;
import com.cartola.jonatas.assistentefc.model.Team;
import com.cartola.jonatas.assistentefc.utils.StatisticUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    protected OnBackPressedListener onBackPressedListener;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        loadPlayers2();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(FragmentList.recyclerState.equals("Teams"))
            super.onBackPressed();
        else
            onBackPressedListener.doBack();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FragmentList();
                default:
                    return new SuggestionFragment();

            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "JOGOS";
                case 1:
                    return "SUGESTÃO";
            }
            return null;
        }
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    public void loadPlayers2() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CartolaAPIService.BASE_LOGIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CartolaAPIService service = retrofit.create(CartolaAPIService.class);
        Payload payload = new Payload();
        payload.setEmail("joaraujoalb@gmail.com");
        payload.setPassword("jowalbano123");
        payload.setServiceId(4728);

        PayloadWrapped payloadWrapped = new PayloadWrapped();
        payloadWrapped.setPayload(payload);

        Call<Authentication> requestPartialScorePlayers = service.cartolaLogin(payloadWrapped);
        Log.e("testando", "Teste");
        requestPartialScorePlayers.enqueue(new Callback<Authentication>() {
            @Override
            public void onResponse( Call<Authentication> call, Response<Authentication> response) {
                Authentication authentication = response.body();
                Log.e("auth", authentication.getId());
            }

            @Override
            public void onFailure(Call<Authentication> call, Throwable t) {
                Log.e("falhou", "falhou");
            }
        });
    }

}
