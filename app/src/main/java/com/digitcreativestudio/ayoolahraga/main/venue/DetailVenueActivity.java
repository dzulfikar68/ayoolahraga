package com.digitcreativestudio.ayoolahraga.main.venue;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.adapter.*;
import com.digitcreativestudio.ayoolahraga.model.Image;
import com.digitcreativestudio.ayoolahraga.model.Operational;
import com.digitcreativestudio.ayoolahraga.model.Rating;
import com.digitcreativestudio.ayoolahraga.model.Venue;
import com.digitcreativestudio.ayoolahraga.network.ClientServices;
import com.digitcreativestudio.ayoolahraga.network.DetailVenueResponse;
import com.digitcreativestudio.ayoolahraga.utils.DatabaseHelper;
import com.digitcreativestudio.ayoolahraga.utils.GlideImageLoadingService;
import com.digitcreativestudio.ayoolahraga.utils.SharedPrefManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ss.com.bannerslider.Slider;

import java.util.ArrayList;

import static com.digitcreativestudio.ayoolahraga.utils.Constant.BASE_URL;

public class DetailVenueActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Venue venue;
    private ClientServices services;
    public static String EXTRA_INTENT = "EXTRA_INTENT_DETAIL";
    private RecyclerView rvFacility, rvOperational;
    private CardView cvRating;
    private TextView tvTitle, tvDescription, tvAddress, tvNameComment, tvMessageComment, tvEmail, tvPhone;
    private FacilityAdapter adapterFacility;
    private OperationalAdapter adapterOperational;
    private ArrayList<String> listFacility = new ArrayList<>();
    private ArrayList<Operational> listOperational = new ArrayList<>();
    private DatabaseHelper db;
    private FloatingActionButton fabFavorite;
    private Slider sliderPhoto;
    private RatingBar ratingVenue, ratingComment;
    private GoogleMap mapDetail;
    private LinearLayout llLastComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_venue);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Detail Venue");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvTitle = findViewById(R.id.tv_title_venue);
        tvDescription = findViewById(R.id.tv_description_venue);
        tvAddress = findViewById(R.id.tv_address_venue);
        tvNameComment = findViewById(R.id.tv_name_comment);
        tvMessageComment = findViewById(R.id.tv_message_comment);
        tvEmail = findViewById(R.id.tv_email_venue);
        tvPhone = findViewById(R.id.tv_phone_venue);
        cvRating = findViewById(R.id.cv_rating);
        sliderPhoto = findViewById(R.id.iv_image_venue);
        ratingVenue = findViewById(R.id.rb_rating_venue);
        ratingComment = findViewById(R.id.rb_rating_comment);
        llLastComment = findViewById(R.id.ll_last_comment);

        LinearLayoutManager layoutManagerHorizontal = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        adapterFacility = new FacilityAdapter(getApplicationContext());
        adapterFacility.setList(listFacility);
        rvFacility = findViewById(R.id.rv_facility_venue);
        rvFacility.setHasFixedSize(true);
        rvFacility.setLayoutManager(layoutManagerHorizontal);
        rvFacility.setAdapter(adapterFacility);

        adapterOperational = new OperationalAdapter(getApplicationContext());
        adapterOperational.setList(listOperational);
        rvOperational = findViewById(R.id.rv_operational_venue);
        rvOperational.setHasFixedSize(true);
        rvOperational.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        rvOperational.setAdapter(adapterOperational);

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            services = retrofit.create(ClientServices.class);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            listFacility.clear();
            listOperational.clear();
            Bundle bundle = getIntent().getExtras();
            if(bundle != null){
                venue = bundle.getParcelable(DetailVenueActivity.EXTRA_INTENT);
                Context ctx = this;
                requestDetail(ctx);
                databaseAccess(venue.getPhoto());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void requestDetail(Context ctx){
        final ProgressDialog dialog = ProgressDialog.show(ctx, "",
                "Loading. Please wait...", true);

        Call<DetailVenueResponse> request = services.detailVenueGET(Integer.toString(venue.getId_venue()));
        request.enqueue(new Callback<DetailVenueResponse>() {
            @Override
            public void onResponse(Call<DetailVenueResponse> call, Response<DetailVenueResponse> response) {
                try{
                    final Venue venueDetail = response.body().getData();
                    venue = venueDetail;
                    tvTitle.setText(venueDetail.getName_venue());
                    tvDescription.setText(venueDetail.getDescription());
                    tvAddress.setText(venueDetail.getAddress_venue());
                    if(!venueDetail.getRating().isEmpty()){
                        llLastComment.setVisibility(View.VISIBLE);
                        Rating rating = venueDetail.getRating().get(0);
                        tvNameComment.setText(rating.getName_user());
                        tvMessageComment.setText(rating.getMessage());
                        ratingComment.setRating(Float.valueOf(rating.getScore()));
                    }
                    if(!venueDetail.getOperational().isEmpty()){
                        listOperational.addAll(venueDetail.getOperational());
                        adapterOperational.notifyDataSetChanged();
                    }
                    if(!venueDetail.getFacility().isEmpty()){
                        listFacility.addAll(venueDetail.getFacility());
                        adapterFacility.notifyDataSetChanged();
                    }
                    if(!venueDetail.getImage().isEmpty()){
                        Slider.init(new GlideImageLoadingService(DetailVenueActivity.this));
                        sliderPhoto.setAdapter(new MainSliderAdapter(venueDetail.getImage()));
                    } else {
                        Image image = new Image();
                        image.setUrl_image("https://www.capebretonpost.com/media/photologue/photos/cache/CB-23112018-Winter-Sports-SUB_large.jpg");
                        ArrayList<Image> images = new ArrayList<>();
                        images.add(image);
                        Slider.init(new GlideImageLoadingService(DetailVenueActivity.this));
                        sliderPhoto.setAdapter(new MainSliderAdapter(images));
                    }
                    ratingVenue.setRating(Float.valueOf(venueDetail.getRating_total()));
                    tvEmail.setText(venueDetail.getEmail_user());
                    tvPhone.setText(venueDetail.getPhone_user());

//                final ArrayList<Rating> listRating = venueDetail.getRating();
                    cvRating.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent moveWithObjectIntent = new Intent(getApplicationContext(), RatingActivity.class);
                            moveWithObjectIntent.putExtra(RatingActivity.EXTRA_INTENT, venueDetail);
//                        moveWithObjectIntent.putParcelableArrayListExtra(RatingActivity.EXTRA_INTENT, listRating);
                            startActivity(moveWithObjectIntent);
                        }
                    });

                    Button btnPhone = findViewById(R.id.btn_telp);
                    Button btnEmail = findViewById(R.id.btn_email);

                    btnPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openWhatsApp(venueDetail.getPhone_user());
                        }
                    });
                    btnEmail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sendEmail(venueDetail.getEmail_user());
                        }
                    });

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.g_map_detil);
                    mapFragment.getMapAsync(DetailVenueActivity.this);

//                ScrollView sv = findViewById(R.id.sv_detail);
//                sv.scrollTo(-1000, -1000);

                } catch (Exception e){
                    e.printStackTrace();
                }

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<DetailVenueResponse> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "Get Detail Venue Failed", Toast.LENGTH_LONG).show();
                Log.e("ERROR: ", t.getMessage());
                dialog.dismiss();
            }
        });
    }

    private void sendEmail(String emailAddress) {
        String[] TO = {emailAddress};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Pertanyaan");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "halo, nama saya " +
                SharedPrefManager.getInstance(getApplicationContext()).getStringPref(SharedPrefManager.KEY_NAME) +
                ". ");

        try {
            startActivity(Intent.createChooser(emailIntent, "Kirim Email lewat Ayoolahraga"));
            finish();
            Log.e("Finished sending", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "Aplikasi email tidak terinstall.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openWhatsApp(String numberPhone){
        PackageManager pm = getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);

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

    private void databaseAccess(final String photo){
        db = new DatabaseHelper(this);
        fabFavorite = findViewById(R.id.fab_favorite);

        if(db.existCheck(venue.getId_venue())){
            fabFavorite.setImageDrawable( getResources().getDrawable(R.drawable.ic_favorite_white_24dp) );
        } else {
            fabFavorite.setImageDrawable( getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp) );
        }

        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!db.existCheck(venue.getId_venue())){
                    db.insertVenue(
                            venue.getId_venue(),
                            venue.getName_venue(),
                            photo,
                            venue.getAddress_venue()
                    );
//                    Toast.makeText(getApplicationContext(), "Favorite: "+venue.getName_venue(), Toast.LENGTH_LONG).show();
                    fabFavorite.setImageDrawable( getResources().getDrawable(R.drawable.ic_favorite_white_24dp) );
                } else {
                    db.deleteVenue(
                            venue
                    );
//                    Toast.makeText(getApplicationContext(), "Un-Favorite: "+venue.getName_venue(), Toast.LENGTH_LONG).show();
                    fabFavorite.setImageDrawable( getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp) );
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try{
            mapDetail = googleMap;

            //seattle coordinates
            LatLng point = new LatLng(Double.valueOf(venue.getLatitude()), Double.valueOf(venue.getLongitude()));

//            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_icon_map_v9);
            mapDetail.addMarker(new MarkerOptions().position(point).title(venue.getAddress_venue()));
            mapDetail.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 17.5f));
            mapDetail.setMapType(GoogleMap.MAP_TYPE_HYBRID);

//            scrollview_detail_property = findViewById(R.id.scrollview_detail_property);
//            scrollview_detail_property.scrollTo(5, 10);
            //            scrollview_detail_property.scrollTo(0, scrollview_detail_property.getBottom());
        } catch (Exception e){
            Log.e("ERROR:", e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
