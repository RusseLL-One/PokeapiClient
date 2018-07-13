package com.one.russell.pokeapiclient.model.retrofit;

import com.one.russell.pokeapiclient.model.PokemonCount;
import com.one.russell.pokeapiclient.model.Pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import io.reactivex.Observable;

public interface UserService {
    @GET("pokemon/{id}")
    Observable<Pokemon> getPokemon(@Path("id") int id);

    @GET("pokemon")
    Call<PokemonCount> getCount();
}
