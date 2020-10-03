package com.digitcreativestudio.ayoolahragaid.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digitcreativestudio.ayoolahragaid.R;
import com.digitcreativestudio.ayoolahragaid.model.Type;
import com.digitcreativestudio.ayoolahragaid.model.UnsplashResults;
import com.digitcreativestudio.ayoolahragaid.network.ClientServices;
import com.digitcreativestudio.ayoolahragaid.network.UnsplashResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.digitcreativestudio.ayoolahragaid.utils.Constant.UNSPLASH_URL;

public class TypeAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private Context context;
    private ArrayList<Type> list;

    public TypeAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Type> getList() {
        return list;
    }

    public void setList(ArrayList<Type> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_type, viewGroup, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder movieViewHolder, int i) {
        getUnsplash(i, movieViewHolder.imageType);
        movieViewHolder.titleType.setText(getList().get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    private void getUnsplash(final int i, final ImageView image){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UNSPLASH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientServices services = retrofit.create(ClientServices.class);

        String fullString = getList().get(i).getTitle().trim();
        String[] splited = fullString.split("\\s+");

        Call<UnsplashResponse> request = services.getPhoto(
                splited[0],
                "1",
                "1",
                "d46362b486aff9800feb3b2ed7a0d684568127eae064b9c1166ae9328a30c263"
        );
        request.enqueue(new Callback<UnsplashResponse>() {
            @Override
            public void onResponse(@NotNull Call<UnsplashResponse> call, @NotNull Response<UnsplashResponse> response) {
                assert response.body() != null;
                List<UnsplashResults> photos = response.body().getResults();
                if(!photos.isEmpty()) {
                    Glide.with(context)
                            .load(photos.get(0).getUrls().getThumb())
                            .placeholder(context.getDrawable(R.drawable.ayoolahraga_placeholder))
                            .into(image);
                }
            }

            @Override
            public void onFailure(Call<UnsplashResponse> call, Throwable t) {
                Log.e("ERROR", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}
