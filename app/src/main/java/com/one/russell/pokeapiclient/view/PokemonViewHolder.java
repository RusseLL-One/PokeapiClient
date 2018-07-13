package com.one.russell.pokeapiclient.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.one.russell.pokeapiclient.R;
import com.one.russell.pokeapiclient.common.Constants;
import com.one.russell.pokeapiclient.model.Pokemon;
import com.squareup.picasso.Picasso;

class PokemonViewHolder extends RecyclerView.ViewHolder {

    private TextView pokemonIdTextView;
    private ImageView pokemonSpriteImageView;
    private TextView pokemonNameTextView;

    PokemonViewHolder(View itemView) {
        super(itemView);
        pokemonIdTextView = itemView.findViewById(R.id.list_item_id);
        pokemonNameTextView = itemView.findViewById(R.id.list_item_text);
        pokemonSpriteImageView = itemView.findViewById(R.id.iv_pokemon);
    }

    public void bind(final Pokemon pokemon) {
        if (pokemon.isHighlighted) itemView.setBackgroundColor(Color.CYAN);
        else itemView.setBackgroundColor(Color.TRANSPARENT);

        itemView.setTag(pokemon.getId());
        pokemonIdTextView.setText(String.valueOf(pokemon.getId()));
        pokemonNameTextView.setText(pokemon.getName());
        Picasso.get()
                .load(pokemon.getSprites().getFront_default())
                .into(pokemonSpriteImageView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PokemonFragment pokeFragment = new PokemonFragment();
                FragmentManager fm = ((MainActivity)v.getContext()).getSupportFragmentManager();
                Bundle args = new Bundle();
                args.putString(Constants.FRAGMENT_ARG_NAME, pokemon.toString());
                pokeFragment.setArguments(args);
                fm.beginTransaction().replace(R.id.pokemon_fragment, pokeFragment).addToBackStack(null).commit();
            }
        });
    }
}