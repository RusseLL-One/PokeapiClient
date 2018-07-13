package com.one.russell.pokeapiclient.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.one.russell.pokeapiclient.common.Constants;
import com.one.russell.pokeapiclient.R;

public class PokemonFragment extends Fragment {
    TextView pokemonDataView;
    Button closeButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pokemon_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if(args == null) return;

        pokemonDataView = view.findViewById(R.id.pokemonData);
        closeButton = view.findViewById(R.id.fragment_but);

        String pokemonData = args.getString(Constants.FRAGMENT_ARG_NAME);
        pokemonDataView.setText(pokemonData);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                if (activity != null) {
                    activity.getSupportFragmentManager().popBackStack();
                }
            }
        });
    }
}
