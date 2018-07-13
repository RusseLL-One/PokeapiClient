package com.one.russell.pokeapiclient.model;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.one.russell.pokeapiclient.common.Constants;
import com.one.russell.pokeapiclient.model.retrofit.okHttpInterceptors;
import com.one.russell.pokeapiclient.model.retrofit.UserService;
import com.one.russell.pokeapiclient.presenter.PresenterInterface;
import com.one.russell.pokeapiclient.view.ViewInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonStorage implements ModelInterface {

    private PresenterInterface<ViewInterface> presenter;
    private UserService userService;
    private int pokemonCount = 0;

    public PokemonStorage(PresenterInterface<ViewInterface> presenter) {
        this.presenter = presenter;
    }

    @SuppressWarnings (value="all")
    public void init(AppCompatActivity activity, boolean randomStartPosition) {
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(activity.getCacheDir(), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new okHttpInterceptors(activity).REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .retryOnConnectionFailure(true)
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userService = retrofit.create(UserService.class);
        if(pokemonCount == 0) {
            try {
                Response<PokemonCount> resp = userService.getCount().execute();
                    if (resp.body() != null) {
                        pokemonCount = resp.body().getCount();
                    }
            } catch (IOException e) {
                Log.d(Constants.LOG_TAG, "ERROR: Can't get items count");
            }
        }

        PokemonSourceFactory sourceFactory = new PokemonSourceFactory(this);

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(30)
                .setPrefetchDistance(10)
                .setInitialLoadSizeHint(30)
                .build();

        Random rnd = new Random();
        LiveData<PagedList<Pokemon>> pagedListLiveData = new LivePagedListBuilder<>(sourceFactory, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .setInitialLoadKey(randomStartPosition ? rnd.nextInt(pokemonCount) : Constants.DEFAULT_START_POSITION)
                .build();

        pagedListLiveData.observe(activity, new android.arch.lifecycle.Observer<PagedList<Pokemon>>() {
            @Override
            public void onChanged(@Nullable PagedList<Pokemon> pokemons) {
                presenter.setList(pokemons);
            }
        });
    }

   public PokemonData getInitialData(int startPosition, int loadSize) {
       if (pokemonCount > 0 && startPosition + loadSize > pokemonCount) {
           startPosition = pokemonCount - loadSize;
       }
       List<Pokemon> pokemonList = getData(startPosition, loadSize);

       if(pokemonList.isEmpty()) startPosition = 0;

       return new PokemonData(pokemonList, startPosition);
   }

    public List<Pokemon> getData(int startPosition, int loadSize) {
        final List<Pokemon> pokemonList = new ArrayList<>();

        final List<Observable<Pokemon>> requests = new ArrayList<>();
        for(int i = startPosition; i<startPosition+loadSize; i++) {
            requests.add(userService.getPokemon(i+1));
        }

       Observable.zip(requests, new Function<Object[], Object>() {
            @Override
            public Object apply(Object[] objects) {
                for(Object o : objects) {
                    pokemonList.add((Pokemon) o);
                }
                return pokemonList;
            }
        }).subscribe(
                new Consumer<Object>() {
                    @Override
                    public void accept(Object o) {
                        Log.d(Constants.LOG_TAG, "List has been downloaded");
                    }
                },
               new Consumer<Throwable>() {
                   @Override
                   public void accept(Throwable o) {
                       Log.d(Constants.LOG_TAG, "ERROR: " + o.getMessage());
                   }
               }
        );
        return pokemonList;
    }
}

class PokemonData {
    private List<Pokemon> pokeList;
    private int position;

    PokemonData(List<Pokemon> pokeList, int position) {
        this.pokeList = pokeList;
        this.position = position;
    }

    public List<Pokemon> getPokeList() {
        return pokeList;
    }

    public int getPosition() {
        return position;
    }
}
