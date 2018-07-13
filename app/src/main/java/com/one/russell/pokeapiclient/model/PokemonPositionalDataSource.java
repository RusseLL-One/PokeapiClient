package com.one.russell.pokeapiclient.model;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.one.russell.pokeapiclient.common.Constants;

import java.util.List;

class PokemonPositionalDataSource extends PositionalDataSource<Pokemon> {

    private final PokemonStorage pokemonStorage;

    PokemonPositionalDataSource(PokemonStorage pokemonStorage) {
        this.pokemonStorage = pokemonStorage;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Pokemon> callback) {
        Log.d(Constants.LOG_TAG, "loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);
        PokemonData result = pokemonStorage.getInitialData(params.requestedStartPosition, params.requestedLoadSize);
        callback.onResult(result.getPokeList(), result.getPosition());
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Pokemon> callback) {
        Log.d(Constants.LOG_TAG, "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
        List<Pokemon> result = pokemonStorage.getData(params.startPosition, params.loadSize);
        callback.onResult(result);
    }

}