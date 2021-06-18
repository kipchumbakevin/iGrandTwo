package com.igrandbusiness.mybusinessplans;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.igrandbusiness.mybusinessplans.adapters.MagazineAdapter;
import com.igrandbusiness.mybusinessplans.adapters.VideosAdapter;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;
import com.igrandbusiness.mybusinessplans.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    VideosAdapter videosAdapter;
    TextView novideos,network_error;
    CardView progress, reload, network_error_card;
    RecyclerView recyclerView;
    private ArrayList<ReceiveData> mContentArrayList = new ArrayList<>();
    public VideosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideosFragment newInstance(String param1, String param2) {
        VideosFragment fragment = new VideosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        reload = view.findViewById(R.id.reload);
        network_error = view.findViewById(R.id.network_error);
        network_error_card = view.findViewById(R.id.network_error_card);
        progress = view.findViewById(R.id.progress);
        novideos = view.findViewById(R.id.novideos);
        videosAdapter = new VideosAdapter(getActivity(),mContentArrayList);
        recyclerView.setAdapter(videosAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fetchVideos();
        reload.setOnClickListener(view1 -> {
            fetchVideos();
        });
        return view;
    }
    private void fetchVideos() {
        progress.setVisibility(View.VISIBLE);
        network_error_card.setVisibility(View.GONE);
        mContentArrayList.clear();
        Call<List<ReceiveData>> call = RetrofitClient.getInstance(getActivity())
                .getApiConnector()
                .getVids();
        call.enqueue(new Callback<List<ReceiveData>>() {
            @Override
            public void onResponse(Call<List<ReceiveData>> call, Response<List<ReceiveData>> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    if (response.body().size()>0){
                        mContentArrayList.addAll(response.body());
                        videosAdapter.notifyDataSetChanged();
                    }else {
                        novideos.setVisibility(View.VISIBLE);
                    }
                } else {
                    network_error.setText("Oh no!\nA server error has occurred.Ensure you are connected then retry.");
                    network_error_card.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<ReceiveData>> call, Throwable t) {
                progress.setVisibility(View.GONE);
                network_error.setText("Oh no!\nA network error has occurred.Ensure you are connected then retry.");
                network_error_card.setVisibility(View.VISIBLE);
            }

        });
    }
}