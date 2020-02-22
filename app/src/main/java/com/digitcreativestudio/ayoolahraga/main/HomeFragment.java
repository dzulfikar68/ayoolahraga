package com.digitcreativestudio.ayoolahraga.main;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.adapter.TypeAdapter;
import com.digitcreativestudio.ayoolahraga.adapter.BlogAdapter;
import com.digitcreativestudio.ayoolahraga.main.blog.DetailBlogActivity;
import com.digitcreativestudio.ayoolahraga.main.community.ListCommunityActivity;
import com.digitcreativestudio.ayoolahraga.main.venue.ListVenueActivity;
import com.digitcreativestudio.ayoolahraga.model.Blog;
import com.digitcreativestudio.ayoolahraga.model.Type;
import com.digitcreativestudio.ayoolahraga.network.BlogResponse;
import com.digitcreativestudio.ayoolahraga.network.ClientServices;
import com.digitcreativestudio.ayoolahraga.network.TypeResponse;
import com.digitcreativestudio.ayoolahraga.network.xml.Entry;
import com.digitcreativestudio.ayoolahraga.network.xml.Link;
import com.digitcreativestudio.ayoolahraga.utils.ItemClickSupport;
import com.digitcreativestudio.ayoolahraga.utils.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import java.util.ArrayList;
import java.util.List;

import static com.digitcreativestudio.ayoolahraga.utils.Constant.BASE_URL;
import static com.digitcreativestudio.ayoolahraga.utils.Constant.GLADY_URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ClientServices services;
    private TextView tvName;
    private CardView cvName;
    private RecyclerView rvType;
    private RecyclerView rvCommunity;
    private RecyclerView rvBlog;
    private TypeAdapter adapterType;
    private TypeAdapter adapterCommunity;
    private BlogAdapter adapterBlog;
    private ArrayList<Type> listType = new ArrayList<>();
    private ArrayList<Type> listCommunity = new ArrayList<>();
    private ArrayList<Blog> listBlog = new ArrayList<>();
    Call<TypeResponse> requestType;
    Call<TypeResponse> requestCommunity;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvName  = view.findViewById(R.id.tv_name_user);
        cvName = view.findViewById(R.id.cv_name);

        rvType = view.findViewById(R.id.rv_type_sport);
        rvCommunity = view.findViewById(R.id.rv_list_community);
        rvBlog = view.findViewById(R.id.rv_blog);

        final String welcome = SharedPrefManager.getInstance(getActivity()).getStringPref(SharedPrefManager.KEY_NAME);
        tvName.setText(welcome);

        adapterType = new TypeAdapter(getActivity());
        adapterCommunity = new TypeAdapter(getActivity());
        adapterBlog = new BlogAdapter(getActivity());

        adapterType.setList(listType);
        adapterCommunity.setList(listCommunity);
        adapterBlog.setList(listBlog);

        rvType.setHasFixedSize(true);
        rvCommunity.setHasFixedSize(true);
        rvBlog.setHasFixedSize(true);

//        list.addAll(MoviesData.getListdata());
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

        requestListType();
        requestListCommunity();
        requestListBlog();

        ItemClickSupport.addTo(rvType).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
//                Toast.makeText(getActivity(), "Kamu memilih " + listType.get(position).getTitle(), Toast.LENGTH_LONG).show();
                Intent moveWithObjectIntent = new Intent(getActivity(), ListVenueActivity.class);
                moveWithObjectIntent.putExtra(ListVenueActivity.EXTRA_INTENT, listType.get(position));
                startActivity(moveWithObjectIntent);
            }
        });

        ItemClickSupport.addTo(rvCommunity).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
//                Toast.makeText(getActivity(), "Kamu memilih " + listCommunity.get(position).getTitle(), Toast.LENGTH_LONG).show();
                Intent moveWithObjectIntent = new Intent(getActivity(), ListCommunityActivity.class);
                moveWithObjectIntent.putExtra(ListCommunityActivity.EXTRA_INTENT, listCommunity.get(position));
                startActivity(moveWithObjectIntent);
            }
        });

        ItemClickSupport.addTo(rvBlog).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                Intent moveWithObjectIntent = new Intent(getActivity(), DetailBlogActivity.class);
                moveWithObjectIntent.putExtra(DetailBlogActivity.EXTRA_INTENT, listBlog.get(position));
                startActivity(moveWithObjectIntent);
            }
        });

        cvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setFragment(new AccountFragment());
                Toast.makeText(getActivity(), "Halo " + welcome, Toast.LENGTH_LONG).show();
            }
        });
    }

//    private void setFragment(Fragment fragment){
//        if(getFragmentManager() != null){
//            getFragmentManager().beginTransaction()
//                    .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
//                    .commit();
//        }
//    }

    private void requestListType(){
        final ProgressBar pgType = getView().findViewById(R.id.pb_sport_type);
        pgType.setVisibility(View.VISIBLE);

        requestType = services.typeGET();
        requestType.enqueue(new Callback<TypeResponse>() {
            @Override
            public void onResponse(Call<TypeResponse> call, Response<TypeResponse> response) {
                listType = response.body().getData();
                adapterType.setList(listType);
                adapterType.notifyDataSetChanged();
                pgType.setVisibility(View.GONE);
                if (listType.isEmpty()) getView().findViewById(R.id.tv_sport_type).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TypeResponse> call, Throwable t) {
//                Toast.makeText(getActivity(), "Get Type Failed", Toast.LENGTH_LONG).show();
                pgType.setVisibility(View.GONE);
                if (listType.isEmpty()) getView().findViewById(R.id.tv_sport_type).setVisibility(View.GONE);
            }
        });
    }

    private void requestListCommunity(){
        final ProgressBar pgCommunity = getView().findViewById(R.id.pb_list_community);
        pgCommunity.setVisibility(View.VISIBLE);

        requestCommunity = services.communityGET();
        requestCommunity.enqueue(new Callback<TypeResponse>() {
            @Override
            public void onResponse(Call<TypeResponse> call, Response<TypeResponse> response) {
                listCommunity = response.body().getData();
                adapterCommunity.setList(listCommunity);
                adapterCommunity.notifyDataSetChanged();
                pgCommunity.setVisibility(View.GONE);
                if (listCommunity.isEmpty()) getView().findViewById(R.id.tv_list_community).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TypeResponse> call, Throwable t) {
//                Toast.makeText(getActivity(), "Get Type Community Failed", Toast.LENGTH_LONG).show();
                pgCommunity.setVisibility(View.GONE);
                if (listCommunity.isEmpty()) getView().findViewById(R.id.tv_list_community).setVisibility(View.GONE);
            }
        });
    }

    private void requestListBlog(){
        final ProgressBar pgBlog = getView().findViewById(R.id.pb_blog_news);
        pgBlog.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GLADY_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        ClientServices services = retrofit.create(ClientServices.class);
        Call<BlogResponse> requestBlog = services.listBlogGET();
        requestBlog.enqueue(new Callback<BlogResponse>() {
            @Override
            public void onResponse(Call<BlogResponse> call, Response<BlogResponse> response) {
                if(response.body()!=null){
                    if(response.body().getEntry()!=null){
                        List<Entry> entries = response.body().getEntry();
                        for (int i=0; i<entries.size(); i++){
                            Blog blog = new Blog();
                            blog.setId_blog(0);
                            blog.setTitle(entries.get(i).getTitle());
                            blog.setImage(entries.get(i).getThumbnail().get(0).getUrl());
                            for (int j=0; j<entries.get(i).getLink().size(); j++){
                                if (entries.get(i).getLink().get(j).getRel().equals(Link.VAL_REL)){
                                    blog.setLink(entries.get(i).getLink().get(j).getHref());
                                }
                            }
                            listBlog.add(blog);
                        }
                        adapterBlog.setList(listBlog);
                        adapterBlog.notifyDataSetChanged();
                        if (listBlog.isEmpty()) getView().findViewById(R.id.tv_blog_news).setVisibility(View.GONE);
//                        Log.e("TITLE", response.body().getEntry().get(0).getTitle());
//                        Log.e("IMAGE", response.body().getEntry().get(0).getThumbnail().get(0).getUrl());
//                        Log.e("LINK", response.body().getEntry().get(0).getLink().get(0).getRel());
                    } else {
                        Log.e("BLOG", "NULL");
                        getView().findViewById(R.id.tv_blog_news).setVisibility(View.GONE);
                    }
                    pgBlog.setVisibility(View.GONE);
                } else {
                    getView().findViewById(R.id.tv_blog_news).setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BlogResponse> call, Throwable t) {
                Log.e("ERROR BLOG", t.getMessage());
                pgBlog.setVisibility(View.GONE);
//                Toast.makeText(getActivity(), "Get Blog Failed: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

//        String urlBlog = "https://stackoverflow.com/feeds";
//        Parser parser = new Parser();
//        parser.onFinish(new OnTaskCompleted() {
//
//            //what to do when the parsing is done
//            @Override
//            public void onTaskCompleted(List<Article> list) {
//                // The list contains all article's data. For example you can use it for your adapter.
//                Log.e("BLOG", list.get(0).getTitle());
//            }
//
//            //what to do in case of error
//            @Override
//            public void onError(Exception e) {
//                // Handle the exception
//            }
//        });
//        parser.execute(urlBlog);
    }

    @Override
    public void onPause() {
        super.onPause();
//        requestType.cancel();
    }
}
