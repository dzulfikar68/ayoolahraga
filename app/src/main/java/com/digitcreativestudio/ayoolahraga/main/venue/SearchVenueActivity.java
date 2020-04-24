package com.digitcreativestudio.ayoolahraga.main.venue;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.adapter.VenueAdapter;
import com.digitcreativestudio.ayoolahraga.model.Venue;
import com.digitcreativestudio.ayoolahraga.network.ClientServices;
import com.digitcreativestudio.ayoolahraga.network.ListVenueResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.digitcreativestudio.ayoolahraga.utils.Constant.BASE_URL;

public class SearchVenueActivity extends AppCompatActivity {

    public static String EXTRA_INTENT = "EXTRA_INTENT_SEARCH";
    private String query;
    private ClientServices services;
    private VenueAdapter adapter;
    private ArrayList<Venue> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_venue);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            query = bundle.getString(EXTRA_INTENT);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(query);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            services = retrofit.create(ClientServices.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final ProgressBar pgListVenue = findViewById(R.id.pb_list_venue);
        pgListVenue.setVisibility(View.VISIBLE);
        Call<ListVenueResponse> request = services.listVenueGET(null, query);
        request.enqueue(new Callback<ListVenueResponse>() {
            @Override
            public void onResponse(@NotNull Call<ListVenueResponse> call, @NotNull Response<ListVenueResponse> response) {
                list.clear();
                assert response.body() != null;
                list = response.body().getData();
                if (list.isEmpty())
                    Toast.makeText(getApplicationContext(), "List Kosong/ Pencarian tidak ada", Toast.LENGTH_LONG).show();
                adapter.setList(list);
                adapter.notifyDataSetChanged();
                pgListVenue.setVisibility(View.GONE);
                if (list.isEmpty()) findViewById(R.id.tv_not_found).setVisibility(View.VISIBLE);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NotNull Call<ListVenueResponse> call, @NotNull Throwable t) {
                pgListVenue.setVisibility(View.GONE);
                TextView error = findViewById(R.id.tv_not_found);
                error.setVisibility(View.VISIBLE);
                error.setText("maaf, sedang terjadi gangguan");
            }
        });
    }
}
