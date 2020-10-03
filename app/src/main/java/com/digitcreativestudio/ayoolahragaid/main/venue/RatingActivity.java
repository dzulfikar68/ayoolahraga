package com.digitcreativestudio.ayoolahragaid.main.venue;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digitcreativestudio.ayoolahragaid.R;
import com.digitcreativestudio.ayoolahragaid.adapter.RatingAdapter;
import com.digitcreativestudio.ayoolahragaid.model.Venue;
import com.digitcreativestudio.ayoolahragaid.network.BasicResponse;
import com.digitcreativestudio.ayoolahragaid.network.ClientServices;
import com.digitcreativestudio.ayoolahragaid.utils.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orhanobut.dialogplus.DialogPlus;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.digitcreativestudio.ayoolahragaid.utils.Constant.BASE_URL;

public class RatingActivity extends AppCompatActivity {

    public static String EXTRA_INTENT = "EXTRA_INTENT_RATING";

    private Venue venue;
    private FloatingActionButton fabRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        fabRating = findViewById(R.id.fab_rating);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            venue = bundle.getParcelable(RatingActivity.EXTRA_INTENT);

            if(getSupportActionBar() != null){
                getSupportActionBar().setTitle("Rating: " + venue.getName_venue());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            RatingAdapter adapter = new RatingAdapter();
            adapter.setList(venue.getRating());

            RecyclerView recyclerView = findViewById(R.id.rv_rating_venue);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(adapter);

            if (venue.getRating().isEmpty()) findViewById(R.id.tv_not_found).setVisibility(View.VISIBLE);
        }

        //TODO checkingRating
        checkRating();

        final Activity act = this;
        fabRating.setOnClickListener(v -> {
            DialogPlus dialog = DialogPlus.newDialog(act)
                    .setAdapter(new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return 1;
                        }

                        @Override
                        public Object getItem(int position) {
                            return 1;
                        }

                        @Override
                        public long getItemId(int position) {
                            return 1;
                        }

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            assert inflater != null;
                            @SuppressLint({"ViewHolder", "InflateParams"}) View view = inflater.inflate(R.layout.layout_add_rating, null);

                            final RatingBar rbRate = view.findViewById(R.id.rb_rate_rating);
                            final EditText etComment = view.findViewById(R.id.et_comment_rating);
                            Button btnGiveRate = view.findViewById(R.id.btn_add_rating);

                            btnGiveRate.setOnClickListener(v1 -> doGiveRate(rbRate, etComment));

                            return view;
                        }
                    })
                    .setOnItemClickListener((dialog1, item, view, position) -> {

                    })
                    .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                    .create();
            dialog.show();
        });
    }

    private void doGiveRate(RatingBar rbRate, EditText etComment){
        final ProgressDialog dialog = ProgressDialog.show(RatingActivity.this, "",
                "Loading. Please wait...", true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientServices services = retrofit.create(ClientServices.class);

        String rate = String.valueOf(rbRate.getRating());
        String comment = etComment.getText().toString().trim();

        HashMap<String, String> params = new HashMap<>();
        params.put("id_user", SharedPrefManager.getInstance(getApplicationContext()).getStringPref(SharedPrefManager.KEY_ID));
        params.put("score", rate);
        params.put("message", comment);

        Call<BasicResponse> request = services.giveRatePOST(String.valueOf(venue.getId_venue()), params);
        request.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NotNull Call<BasicResponse> call, @NotNull Response<BasicResponse> response) {
                BasicResponse data = response.body();
                assert data != null;
                if (data.getStatus().equals("true")){
                    finish();
                }
                Toast.makeText(getApplicationContext(), data.getMessage(), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Beri Rating Gagal", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
    }

    private void checkRating(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientServices services = retrofit.create(ClientServices.class);

        HashMap<String, String> params = new HashMap<>();
        params.put("id_user", SharedPrefManager.getInstance(getApplicationContext()).getStringPref(SharedPrefManager.KEY_ID));

        Call<BasicResponse> request = services.checkRatePOST(String.valueOf(venue.getId_venue()), params);
        request.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                BasicResponse data = response.body();
                assert data != null;
                if (data.getStatus().equals("false")){
                    fabRating.show();
                } else if(data.getStatus().equals("true")){
                    fabRating.hide();
                }
            }

            @Override
            public void onFailure(@NotNull Call<BasicResponse> call, @NotNull Throwable t) {
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
