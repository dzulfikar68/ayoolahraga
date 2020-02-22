package com.digitcreativestudio.ayoolahraga.main.community;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.adapter.CommunityAdapter;
import com.digitcreativestudio.ayoolahraga.model.Community;
import com.digitcreativestudio.ayoolahraga.model.Type;
import com.digitcreativestudio.ayoolahraga.network.ClientServices;
import com.digitcreativestudio.ayoolahraga.network.ListCommunityResponse;
import com.digitcreativestudio.ayoolahraga.utils.ItemClickSupport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

import static com.digitcreativestudio.ayoolahraga.utils.Constant.BASE_URL;

public class ListCommunityActivity extends AppCompatActivity {

    public static String EXTRA_INTENT = "EXTRA_INTENT_LIST_C";

    private Bundle bundle;
    private ClientServices services;
    private RecyclerView recyclerView;
    private CommunityAdapter adapter;
    private ArrayList<Community> list = new ArrayList<>();
    private ImageButton btnSearch;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_community);

        etSearch = findViewById(R.id.et_search_community);
        btnSearch = findViewById(R.id.btn_search_community);

        adapter = new CommunityAdapter(getApplicationContext());
        adapter.setList(list);

        recyclerView = findViewById(R.id.rv_list_community);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        services = retrofit.create(ClientServices.class);

        bundle = getIntent().getExtras();
        final Type type;
        if(bundle != null){
            type = bundle.getParcelable(ListCommunityActivity.EXTRA_INTENT);
            requestList(String.valueOf(type.getId()),"");

            if(getSupportActionBar() != null){
                getSupportActionBar().setTitle(type.getTitle());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestList(String.valueOf(type.getId()), etSearch.getText().toString().trim());
                }
            });
        }

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
//                Toast.makeText(getApplicationContext(), "Kamu memilih " + list.get(position).getName_community(), Toast.LENGTH_LONG).show();
                Intent moveWithObjectIntent = new Intent(getApplicationContext(), DetailCommunityActivity.class);
                moveWithObjectIntent.putExtra(DetailCommunityActivity.EXTRA_INTENT, list.get(position));
                startActivity(moveWithObjectIntent);
            }
        });
    }

    private void requestList(String type, String query){
        final ProgressBar pgListCommunity = findViewById(R.id.pb_list_community);
        pgListCommunity.setVisibility(View.VISIBLE);
        findViewById(R.id.tv_not_found).setVisibility(View.GONE);

        Call<ListCommunityResponse> request = services.listCommunityGET(type, query);
        request.enqueue(new Callback<ListCommunityResponse>() {
            @Override
            public void onResponse(Call<ListCommunityResponse> call, Response<ListCommunityResponse> response) {
                list.clear();
                list = response.body().getData();
                if(list.isEmpty()) Toast.makeText(getApplicationContext(), "List Kosong/ Pencarian tidak ada", Toast.LENGTH_LONG).show();
                adapter.setList(list);
                adapter.notifyDataSetChanged();
                pgListCommunity.setVisibility(View.GONE);
                if (list.isEmpty()) findViewById(R.id.tv_not_found).setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<ListCommunityResponse> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),"Get List Community Failed", Toast.LENGTH_LONG).show();
                pgListCommunity.setVisibility(View.GONE);
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
