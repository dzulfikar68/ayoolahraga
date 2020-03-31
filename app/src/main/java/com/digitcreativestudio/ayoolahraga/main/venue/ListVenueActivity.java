package com.digitcreativestudio.ayoolahraga.main.venue;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.adapter.VenueAdapter;
import com.digitcreativestudio.ayoolahraga.model.Type;
import com.digitcreativestudio.ayoolahraga.model.Venue;
import com.digitcreativestudio.ayoolahraga.network.ClientServices;
import com.digitcreativestudio.ayoolahraga.network.ListVenueResponse;
import com.digitcreativestudio.ayoolahraga.utils.ItemClickSupport;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.digitcreativestudio.ayoolahraga.utils.Constant.BASE_URL;

public class ListVenueActivity extends AppCompatActivity {

    public static String EXTRA_INTENT = "EXTRA_INTENT_LIST";

    private ClientServices services;
    private VenueAdapter adapter;
    private ArrayList<Venue> list = new ArrayList<>();
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_venue);

        etSearch = findViewById(R.id.et_search_venue);
        ImageButton btnSearch = findViewById(R.id.btn_search_venue);

        adapter = new VenueAdapter(getApplicationContext());
        adapter.setList(list);

        RecyclerView recyclerView = findViewById(R.id.rv_venue_sport);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        services = retrofit.create(ClientServices.class);

        Bundle bundle = getIntent().getExtras();
        final Type type;
        if(bundle != null){
            type = bundle.getParcelable(ListVenueActivity.EXTRA_INTENT);
            assert type != null;
            requestList(String.valueOf(type.getId()),"");

            if(getSupportActionBar() != null){
                getSupportActionBar().setTitle(type.getTitle());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            btnSearch.setOnClickListener(v -> requestList(String.valueOf(type.getId()), etSearch.getText().toString().trim()));
        }

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView1, position, view) -> {
            Intent moveWithObjectIntent = new Intent(getApplicationContext(), DetailVenueActivity.class);
            moveWithObjectIntent.putExtra(DetailVenueActivity.EXTRA_INTENT, list.get(position));
            startActivity(moveWithObjectIntent);
        });
    }

    private void requestList(String type, String query){
        final ProgressBar pgListVenue = findViewById(R.id.pb_list_venue);
        pgListVenue.setVisibility(View.VISIBLE);
        findViewById(R.id.tv_not_found).setVisibility(View.GONE);

        Call<ListVenueResponse> request = services.listVenueGET(type, query);
        request.enqueue(new Callback<ListVenueResponse>() {
            @Override
            public void onResponse(@NotNull Call<ListVenueResponse> call, @NotNull Response<ListVenueResponse> response) {
                list.clear();
                assert response.body() != null;
                list = response.body().getData();
                if(list.isEmpty()) Toast.makeText(getApplicationContext(), "List Kosong/ Pencarian tidak ada", Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
