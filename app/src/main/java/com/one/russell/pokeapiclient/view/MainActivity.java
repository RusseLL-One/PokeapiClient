package com.one.russell.pokeapiclient.view;

import android.arch.paging.PagedList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.one.russell.pokeapiclient.model.Pokemon;
import com.one.russell.pokeapiclient.presenter.Presenter;
import com.one.russell.pokeapiclient.presenter.PresenterInterface;
import com.one.russell.pokeapiclient.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewInterface {

    PresenterInterface<ViewInterface> presenter;
    CheckBox attackCB;
    CheckBox defenceCB;
    CheckBox hpCB;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new Presenter();
        presenter.attachView(this);

        initViews();

        presenter.viewIsReady();
    }

    public void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        attackCB = findViewById(R.id.attack_cb);
        defenceCB = findViewById(R.id.defence_cb);
        hpCB = findViewById(R.id.hp_cb);

        attackCB.setOnCheckedChangeListener(presenter.getCheckBoxListener());
        attackCB.setOnCheckedChangeListener(presenter.getCheckBoxListener());
        defenceCB.setOnCheckedChangeListener(presenter.getCheckBoxListener());
        hpCB.setOnCheckedChangeListener(presenter.getCheckBoxListener());

        Button refreshButton = findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.initModel(true);
            }
        });
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @SuppressWarnings (value="unchecked")
    public void fillList(List<?> list) {
        final PokemonListAdapter adapter = new PokemonListAdapter(Pokemon.DIFF_CALLBACK);
        recyclerView.removeAllViews();
        recyclerView.setAdapter(adapter);
        adapter.submitList((PagedList<Pokemon>) list);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
