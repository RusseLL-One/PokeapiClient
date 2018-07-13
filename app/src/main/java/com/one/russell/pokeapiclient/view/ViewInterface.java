package com.one.russell.pokeapiclient.view;

import android.support.v7.widget.RecyclerView;

import com.one.russell.pokeapiclient.model.Pokemon;

import java.util.List;

public interface ViewInterface {
    void fillList(List<?> list);
    RecyclerView getRecyclerView();
}
