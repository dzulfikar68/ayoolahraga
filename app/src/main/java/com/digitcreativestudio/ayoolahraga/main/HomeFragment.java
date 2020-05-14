package com.digitcreativestudio.ayoolahraga.main;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.adapter.BlogAdapter;
import com.digitcreativestudio.ayoolahraga.adapter.TypeAdapter;
import com.digitcreativestudio.ayoolahraga.main.blog.DetailBlogActivity;
import com.digitcreativestudio.ayoolahraga.main.community.ListCommunityActivity;
import com.digitcreativestudio.ayoolahraga.main.venue.ListVenueActivity;
import com.digitcreativestudio.ayoolahraga.model.Blog;
import com.digitcreativestudio.ayoolahraga.model.Type;
import com.digitcreativestudio.ayoolahraga.network.ClientServices;
import com.digitcreativestudio.ayoolahraga.network.NewBlogResponse;
import com.digitcreativestudio.ayoolahraga.network.TypeResponse;
import com.digitcreativestudio.ayoolahraga.network.xml.Item;
import com.digitcreativestudio.ayoolahraga.utils.ItemClickSupport;
import com.digitcreativestudio.ayoolahraga.utils.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import static com.digitcreativestudio.ayoolahraga.utils.Constant.BASE_URL;
import static com.digitcreativestudio.ayoolahraga.utils.Constant.BLOG_NEW_URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ClientServices services;
    private TypeAdapter adapterType;
    private TypeAdapter adapterCommunity;
    private BlogAdapter adapterBlog;
    private ArrayList<Type> listType = new ArrayList<>();
    private ArrayList<Type> listCommunity = new ArrayList<>();
    //    private ArrayList<Blog> listBlog = new ArrayList<>();
    private ArrayList<Blog> listNewBlog = new ArrayList<>();
    private TextView tvName;
    private CardView cvName;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvName = view.findViewById(R.id.tv_name_user);
        cvName = view.findViewById(R.id.cv_name);

        RecyclerView rvType = view.findViewById(R.id.rv_type_sport);
        RecyclerView rvCommunity = view.findViewById(R.id.rv_list_community);
        RecyclerView rvBlog = view.findViewById(R.id.rv_blog);

        adapterType = new TypeAdapter(getActivity());
        adapterCommunity = new TypeAdapter(getActivity());
        adapterBlog = new BlogAdapter(getActivity());

        adapterType.setList(listType);
        adapterCommunity.setList(listCommunity);
//        adapterBlog.setList(listBlog);
        adapterBlog.setList(listNewBlog);

        rvType.setHasFixedSize(true);
        rvCommunity.setHasFixedSize(true);
        rvBlog.setHasFixedSize(true);

        LinearLayoutManager layoutManagerHorizontal = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        rvType.setLayoutManager(new GridLayoutManager(getActivity(),3));
        rvCommunity.setLayoutManager(new GridLayoutManager(getActivity(),3));
        rvBlog.setLayoutManager(layoutManagerHorizontal);

        rvType.setAdapter(adapterType);
        rvCommunity.setAdapter(adapterCommunity);
        rvBlog.setAdapter(adapterBlog);

        adapterType.notifyDataSetChanged();
        adapterCommunity.notifyDataSetChanged();
        adapterBlog.notifyDataSetChanged();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        services = retrofit.create(ClientServices.class);

        ItemClickSupport.addTo(rvType).setOnItemClickListener((recyclerView, position, view13) -> {
            Intent moveWithObjectIntent = new Intent(getActivity(), ListVenueActivity.class);
            moveWithObjectIntent.putExtra(ListVenueActivity.EXTRA_INTENT, listType.get(position));
            startActivity(moveWithObjectIntent);
        });

        ItemClickSupport.addTo(rvCommunity).setOnItemClickListener((recyclerView, position, view1) -> {
            Intent moveWithObjectIntent = new Intent(getActivity(), ListCommunityActivity.class);
            moveWithObjectIntent.putExtra(ListCommunityActivity.EXTRA_INTENT, listCommunity.get(position));
            startActivity(moveWithObjectIntent);
        });

        ItemClickSupport.addTo(rvBlog).setOnItemClickListener((recyclerView, position, view12) -> {
            Intent moveWithObjectIntent = new Intent(getActivity(), DetailBlogActivity.class);
//            moveWithObjectIntent.putExtra(DetailBlogActivity.EXTRA_INTENT, listBlog.get(position));
            moveWithObjectIntent.putExtra(DetailBlogActivity.EXTRA_INTENT, listNewBlog.get(position));
            startActivity(moveWithObjectIntent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        final boolean isLogin = SharedPrefManager.getInstance(getActivity()).isLoggedIn();
        final String welcome = SharedPrefManager.getInstance(getActivity()).getStringPref(SharedPrefManager.KEY_NAME);
        if (welcome == null) {
            tvName.setVisibility(View.GONE);
        } else {
            tvName.setText(welcome);
        }

        requestListType();
        requestListCommunity();
        requestListBlog();

        cvName.setOnClickListener(v -> {
            if (isLogin) {
                Toast.makeText(getActivity(), "Halo " + welcome, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void requestListType(){
        final ProgressBar pgType = Objects.requireNonNull(getView()).findViewById(R.id.pb_sport_type);
        pgType.setVisibility(View.VISIBLE);

        Call<TypeResponse> requestType = services.typeGET();
        requestType.enqueue(new Callback<TypeResponse>() {
            @Override
            public void onResponse(@NotNull Call<TypeResponse> call, @NotNull Response<TypeResponse> response) {
                assert response.body() != null;
                listType = response.body().getData();
                adapterType.setList(listType);
                adapterType.notifyDataSetChanged();
                pgType.setVisibility(View.GONE);
                if (listType.isEmpty())
                    Objects.requireNonNull(getView()).findViewById(R.id.tv_sport_type).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NotNull Call<TypeResponse> call, @NotNull Throwable t) {
                pgType.setVisibility(View.GONE);
                if (listType.isEmpty())
                    Objects.requireNonNull(getView()).findViewById(R.id.tv_sport_type).setVisibility(View.GONE);
            }
        });
    }

    private void requestListCommunity(){
        final ProgressBar pgCommunity = Objects.requireNonNull(getView()).findViewById(R.id.pb_list_community);
        pgCommunity.setVisibility(View.VISIBLE);

        Call<TypeResponse> requestCommunity = services.communityGET();
        requestCommunity.enqueue(new Callback<TypeResponse>() {
            @Override
            public void onResponse(@NotNull Call<TypeResponse> call, @NotNull Response<TypeResponse> response) {
                assert response.body() != null;
                listCommunity = response.body().getData();
                adapterCommunity.setList(listCommunity);
                adapterCommunity.notifyDataSetChanged();
                pgCommunity.setVisibility(View.GONE);
                if (listCommunity.isEmpty())
                    Objects.requireNonNull(getView()).findViewById(R.id.tv_list_community).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NotNull Call<TypeResponse> call, @NotNull Throwable t) {
                pgCommunity.setVisibility(View.GONE);
                if (listCommunity.isEmpty())
                    Objects.requireNonNull(getView()).findViewById(R.id.tv_list_community).setVisibility(View.GONE);
            }
        });
    }

    private void requestListBlog(){
        final ProgressBar pgBlog = Objects.requireNonNull(getView()).findViewById(R.id.pb_blog_news);
        pgBlog.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BLOG_NEW_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        ClientServices services = retrofit.create(ClientServices.class);
//        Call<BlogResponse> requestBlog = services.listBlogGET();
        Call<NewBlogResponse> requestBlog = services.listNewBlogGET();
//        requestBlog.enqueue(new Callback<BlogResponse>() {
        requestBlog.enqueue(new Callback<NewBlogResponse>() {
            @Override
//            public void onResponse(@NotNull Call<BlogResponse> call, @NotNull Response<BlogResponse> response) {
            public void onResponse(@NotNull Call<NewBlogResponse> call, @NotNull Response<NewBlogResponse> response) {
                if(response.body()!=null){
                    if (response.body().getChannel().getItemList() != null) {
//                        List<Entry> entries = response.body().getEntry();
//                        for (int i=0; i<entries.size(); i++){
//                            Blog blog = new Blog();
//                            blog.setId_blog(0);
//                            blog.setTitle(entries.get(i).getTitle());
//                            blog.setImage(entries.get(i).getThumbnail().get(0).getUrl());
//                            for (int j=0; j<entries.get(i).getLink().size(); j++){
//                                if (entries.get(i).getLink().get(j).getRel().equals(Link.VAL_REL)){
//                                    blog.setLink(entries.get(i).getLink().get(j).getHref());
//                                }
//                            }
//                            listBlog.add(blog);
//                        }
                        List<Item> itemList = response.body().getChannel().getItemList();
                        for (int i = 0; i < itemList.size(); i++) {
                            Blog blog = new Blog();
                            blog.setId_blog(0);
                            blog.setTitle(itemList.get(i).getTitle());
                            blog.setImage(itemList.get(i).getEnclosure().getUrl());
                            blog.setLink(itemList.get(i).getLink());
                            listNewBlog.add(blog);
                        }

//                        adapterBlog.setList(listBlog);
                        adapterBlog.setList(listNewBlog);
                        adapterBlog.notifyDataSetChanged();
//                        if (listBlog.isEmpty())
                        if (listNewBlog.isEmpty())
                            Objects.requireNonNull(getView()).findViewById(R.id.tv_blog_news).setVisibility(View.GONE);
                    } else {
                        Log.e("BLOG", "NULL");
                        Objects.requireNonNull(getView()).findViewById(R.id.tv_blog_news).setVisibility(View.GONE);
                    }
                    pgBlog.setVisibility(View.GONE);
                } else {
                    Objects.requireNonNull(getView()).findViewById(R.id.tv_blog_news).setVisibility(View.GONE);
                }
            }

            @Override
//            public void onFailure(Call<BlogResponse> call, Throwable t) {
            public void onFailure(@NotNull Call<NewBlogResponse> call, Throwable t) {
                Log.e("ERROR BLOG", Objects.requireNonNull(t.getMessage()));
                pgBlog.setVisibility(View.GONE);
            }
        });
    }

}
