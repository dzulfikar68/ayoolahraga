package com.digitcreativestudio.ayoolahraga.main.community;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.adapter.MainSliderAdapter;
import com.digitcreativestudio.ayoolahraga.adapter.OperationalAdapter;
import com.digitcreativestudio.ayoolahraga.main.venue.DetailVenueActivity;
import com.digitcreativestudio.ayoolahraga.model.Community;
import com.digitcreativestudio.ayoolahraga.model.Image;
import com.digitcreativestudio.ayoolahraga.model.Operational;
import com.digitcreativestudio.ayoolahraga.model.Venue;
import com.digitcreativestudio.ayoolahraga.network.ClientServices;
import com.digitcreativestudio.ayoolahraga.network.DetailCommunityResponse;
import com.digitcreativestudio.ayoolahraga.network.DetailVenueResponse;
import com.digitcreativestudio.ayoolahraga.utils.GlideImageLoadingService;
import com.digitcreativestudio.ayoolahraga.utils.ImageViewDialog;
import com.digitcreativestudio.ayoolahraga.utils.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ss.com.bannerslider.Slider;

import static com.digitcreativestudio.ayoolahraga.utils.Constant.BASE_URL;

public class DetailCommunityActivity extends AppCompatActivity {

    private Community community;
    private ClientServices services;
    public static String EXTRA_INTENT = "EXTRA_INTENT_DETAIL_C";
    private CardView cvVenue;
    public ArrayList<String> listFacility = new ArrayList<>();
    private OperationalAdapter adapterOperational;
    private TextView tvTitle, tvDescription;
    private ArrayList<Operational> listOperational = new ArrayList<>();
    private Slider sliderPhoto;
    private LinearLayout llVenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_community);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Detail Community");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvTitle = findViewById(R.id.tv_title_community);
        tvDescription = findViewById(R.id.tv_description_community);
        sliderPhoto = findViewById(R.id.iv_image_community);
        cvVenue = findViewById(R.id.cv_venue);
        llVenue = findViewById(R.id.ll_venue);

        adapterOperational = new OperationalAdapter();
        adapterOperational.setList(listOperational);
        RecyclerView rvOperational = findViewById(R.id.rv_operational_community);
        rvOperational.setHasFixedSize(true);
        rvOperational.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        rvOperational.setAdapter(adapterOperational);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        services = retrofit.create(ClientServices.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listFacility.clear();
        listOperational.clear();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            community = bundle.getParcelable(DetailCommunityActivity.EXTRA_INTENT);
            Context ctx = this;
            requestDetail(ctx);
        }
    }

    private void requestDetail(Context ctx){
        final ProgressDialog dialog = ProgressDialog.show(ctx, "",
                "Loading. Please wait...", true);

        Call<DetailCommunityResponse> request = services.detailCommunityGET(Integer.toString(community.getId_community()));
        request.enqueue(new Callback<DetailCommunityResponse>() {
            @Override
            public void onResponse(@NotNull Call<DetailCommunityResponse> call, @NotNull Response<DetailCommunityResponse> response) {
                assert response.body() != null;
                final Community communityDetail = response.body().getData();
                tvTitle.setText(communityDetail.getName_community());
                tvDescription.setText(communityDetail.getDescription_community());

                if(communityDetail.getId_venue() > 0){
                    getVenue(communityDetail);
                }
                if(!communityDetail.getOperational().isEmpty()){
                    listOperational.addAll(communityDetail.getOperational());
                    adapterOperational.notifyDataSetChanged();
                }

                Slider.init(new GlideImageLoadingService(DetailCommunityActivity.this));
                if(!communityDetail.getImage().isEmpty()){
                    sliderPhoto.setAdapter(new MainSliderAdapter(communityDetail.getImage()));
                    sliderPhoto.setOnSlideClickListener(position -> new ImageViewDialog(
                            DetailCommunityActivity.this, communityDetail.getImage().get(position).getUrl_image()).dialog()
                    );
                } else {
                    Image image = new Image();
                    image.setUrl_image("https://www.capebretonpost.com/media/photologue/photos/cache/CB-23112018-Winter-Sports-SUB_large.jpg");
                    ArrayList<Image> images = new ArrayList<Image>();
                    images.add(image);
                    sliderPhoto.setAdapter(new MainSliderAdapter(images));
                }

                Button btnPhone = findViewById(R.id.btn_telp);
                Button btnEmail = findViewById(R.id.btn_email);
                btnPhone.setOnClickListener(v -> openWhatsApp(communityDetail.getContact_community()));
                btnEmail.setOnClickListener(v -> {
                    if (communityDetail.getMedsos_community() != null &&
                            (communityDetail.getMedsos_community().contains("http://") ||
                                    communityDetail.getMedsos_community().contains("https://"))) {
                        goToUrl(communityDetail.getMedsos_community());
                    }
                });

                FloatingActionButton fabPhone = findViewById(R.id.fab_phone);
                if (communityDetail.getContact_community() != null)
                    fabPhone.setVisibility(View.VISIBLE);
                else fabPhone.setVisibility(View.GONE);
                fabPhone.setOnClickListener(v -> dialUpNumber(communityDetail.getContact_community()));

                dialog.dismiss();
            }

            @Override
            public void onFailure(@NotNull Call<DetailCommunityResponse> call, @NotNull Throwable t) {
                Log.e("ERROR: ", Objects.requireNonNull(t.getMessage()));
                dialog.dismiss();
            }
        });
    }

    private void getVenue(Community data){
        Call<DetailVenueResponse> request = services.detailVenueGET(Integer.toString(data.getId_venue()));
        request.enqueue(new Callback<DetailVenueResponse>() {
            @Override
            public void onResponse(@NotNull Call<DetailVenueResponse> call, @NotNull Response<DetailVenueResponse> response) {
                assert response.body() != null;
                final Venue venueDetail = response.body().getData();
                if(venueDetail!=null){
                    TextView tvTitleVenue = findViewById(R.id.tv_title_venue);
                    TextView tvAddressVenue = findViewById(R.id.tv_address_venue);
                    RatingBar ratingVenue = findViewById(R.id.rb_rating_venue);
                    ImageView ivPhotoVenue = findViewById(R.id.iv_photo_venue);

                    tvTitleVenue.setText(venueDetail.getName_venue());
                    tvAddressVenue.setText(venueDetail.getAddress_venue());
                    ratingVenue.setRating(Float.valueOf(venueDetail.getRating_total()));
                    if(!venueDetail.getImage().isEmpty()){
                        //TODO GLIDE
                        Glide.with(getApplicationContext())
                                .load(venueDetail.getImage().get(0).getUrl_image().replace("https://", "http://"))
                                .placeholder(getDrawable(R.drawable.ayoolahraga_placeholder))
                                .into(ivPhotoVenue);
                    }

                    cvVenue.setOnClickListener(v -> {
                        Intent moveWithObjectIntent = new Intent(getApplicationContext(), DetailVenueActivity.class);
                        moveWithObjectIntent.putExtra(DetailVenueActivity.EXTRA_INTENT, venueDetail);
                        startActivity(moveWithObjectIntent);
                    });
                    llVenue.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<DetailVenueResponse> call, @NotNull Throwable t) {
                Log.e("ERROR: ", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    private void openWhatsApp(String numberPhone){
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_VIEW);
            String url =    "https://api.whatsapp.com/send?phone=+62" +
                    numberPhone +
                    "&text=" +
                    "halo, nama saya " +
                    SharedPrefManager.getInstance(getApplicationContext()).getStringPref(SharedPrefManager.KEY_NAME) +
                    ". ";
            sendIntent.setData(Uri.parse(url));

            startActivity(sendIntent);
        } catch (PackageManager.NameNotFoundException e) {
            dialUpNumber(numberPhone);
        }
    }

    private void dialUpNumber(String dial){
        Uri number = Uri.parse("tel:"+dial);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
