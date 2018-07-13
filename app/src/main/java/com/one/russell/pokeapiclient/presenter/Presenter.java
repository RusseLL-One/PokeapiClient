package com.one.russell.pokeapiclient.presenter;

import android.arch.paging.PagedList;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;

import com.one.russell.pokeapiclient.common.Constants;
import com.one.russell.pokeapiclient.model.ModelInterface;
import com.one.russell.pokeapiclient.model.Pokemon;
import com.one.russell.pokeapiclient.view.PokemonListAdapter;
import com.one.russell.pokeapiclient.model.PokemonStorage;
import com.one.russell.pokeapiclient.R;
import com.one.russell.pokeapiclient.view.ViewInterface;

import java.util.List;

public class Presenter extends BasePresenter<ViewInterface> {

    private ModelInterface model;

    @Override
    public void viewIsReady() {
        initModel(false);
    }

    @Override
    public void initModel(final boolean randomStartPosition) {
        model = new PokemonStorage(this);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                model.init((AppCompatActivity) getView(), randomStartPosition);
            }
        };
        new Thread(runnable).start();
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public void setList(List<?> list) {
        PagedList<Pokemon> pList = (PagedList<Pokemon>) list;
        getView().fillList(pList);
    }

    @Override
    public CompoundButton.OnCheckedChangeListener getCheckBoxListener() {
        return cbListener;
    }

    private CompoundButton.OnCheckedChangeListener cbListener = new CompoundButton.OnCheckedChangeListener() {
        boolean isAttackChecked;
        boolean isDefenceChecked;
        boolean isHPChecked;
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.attack_cb:
                    isAttackChecked = isChecked;
                    break;
                case R.id.defence_cb:
                    isDefenceChecked = isChecked;
                    break;
                case R.id.hp_cb:
                    isHPChecked = isChecked;
                    break;
            }
            PagedList<Pokemon> pl = ((PokemonListAdapter) getView().getRecyclerView().getAdapter()).getCurrentList();
            if (pl != null) {
                int maxValue = 0;
                Pokemon bestPokemon = null;
                for (Pokemon pokemon : pl) {
                    pokemon.isHighlighted = false;
                    int value = 0;
                    if (isDefenceChecked)
                        value += pokemon.getStats().get(Constants.DEFENCE_STAT_ID).getBase_stat();
                    if (isAttackChecked)
                        value += pokemon.getStats().get(Constants.ATTACK_STAT_ID).getBase_stat();
                    if (isHPChecked)
                        value += pokemon.getStats().get(Constants.HP_STAT_ID).getBase_stat();

                    if (value > maxValue) {
                        maxValue = value;
                        bestPokemon = pokemon;
                    }
                }
                if (maxValue != 0) {
                    bestPokemon.isHighlighted = true;
                    Log.d(Constants.LOG_TAG, "Max value ID: " + String.valueOf(bestPokemon.getId() - 1));
                    getView().getRecyclerView().smoothScrollToPosition(bestPokemon.getId() - 1);
                }
                getView().getRecyclerView().getAdapter().notifyDataSetChanged();
            }
        }
    };
}
