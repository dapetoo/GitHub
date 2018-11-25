package com.iamdeejay.github.controller;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iamdeejay.github.R;
import com.iamdeejay.github.adapter.ItemAdapter;
import com.iamdeejay.github.api.Client;
import com.iamdeejay.github.api.Service;
import com.iamdeejay.github.model.Item;
import com.iamdeejay.github.model.ItemResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView disconnected;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressDialog;
    private Item item;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialie Variables
        disconnected = findViewById(R.id.disconnected);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_container);
        recyclerView = findViewById(R.id.recyclerView);

        initViews();

        //Setup SwipeRefreshLayout
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJSON();
                Toast.makeText(MainActivity.this, "Github Users Refreshed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Github Users");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        loadJSON();
    }

    private void loadJSON(){
        disconnected = findViewById(R.id.disconnected);
        try {
            Client client = new Client();
            Service apiService = client.getClient().create(Service.class);
            Call<ItemResponse> itemResponseCall = apiService.getItems();
            itemResponseCall.enqueue(new Callback<ItemResponse>() {
                @Override
                public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                    List<Item> items = response.body().getItems();
                    recyclerView.setAdapter(new ItemAdapter(getApplicationContext(), items));
                    recyclerView.smoothScrollToPosition(0);
                    swipeRefreshLayout.setRefreshing(false);
                    progressDialog.hide();
                }

                @Override
                public void onFailure(Call<ItemResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MainActivity.this, "Error Fetching Data", Toast.LENGTH_SHORT).show();
                    disconnected.setVisibility(View.VISIBLE);
                    progressDialog.hide();
                }
            });
        } catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
