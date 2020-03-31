package com.digitcreativestudio.ayoolahraga.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digitcreativestudio.ayoolahraga.R;
import com.digitcreativestudio.ayoolahraga.adapter.VenueAdapter;
import com.digitcreativestudio.ayoolahraga.main.venue.DetailVenueActivity;
import com.digitcreativestudio.ayoolahraga.model.Venue;
import com.digitcreativestudio.ayoolahraga.utils.DatabaseHelper;
import com.digitcreativestudio.ayoolahraga.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private VenueAdapter adapter;
    private ArrayList<Venue> list = new ArrayList<>();

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new VenueAdapter(getActivity());
        adapter.setList(list);

        RecyclerView recyclerView = view.findViewById(R.id.rv_favorite_venue);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(adapter);

        //TODO: get favorite list
        getListFavorite();

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView1, position, view1) -> {
            Intent moveWithObjectIntent = new Intent(getActivity(), DetailVenueActivity.class);
            moveWithObjectIntent.putExtra(DetailVenueActivity.EXTRA_INTENT, list.get(position));
            startActivity(moveWithObjectIntent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getListFavorite();
    }

    private void getListFavorite(){
        DatabaseHelper db = new DatabaseHelper(getActivity());
        list.clear();
        list.addAll(db.getAllVenue());
        adapter.notifyDataSetChanged();
        if (list.isEmpty())
            Objects.requireNonNull(getView()).findViewById(R.id.tv_not_found).setVisibility(View.VISIBLE);
    }
}
