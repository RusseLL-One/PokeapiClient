package com.one.russell.pokeapiclient.model;

import android.arch.paging.DataSource;

public class PokemonSourceFactory extends DataSource.Factory<Integer, Pokemon> {

    private final PokemonStorage pokemonStorage;

    PokemonSourceFactory(PokemonStorage pokemonStorage) {
        this.pokemonStorage = pokemonStorage;
    }

    @Override
    public DataSource<Integer, Pokemon> create() {
        return new PokemonPositionalDataSource(pokemonStorage);
    }
}
