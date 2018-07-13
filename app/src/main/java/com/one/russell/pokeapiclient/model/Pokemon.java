package com.one.russell.pokeapiclient.model;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.one.russell.pokeapiclient.common.Constants;

import java.util.List;

@SuppressWarnings (value="unused")
public class Pokemon {
    private int id;
    private String name;
    private int weight;
    private SpritesBean sprites;
    private int height;
    private java.util.List<StatsBean> stats;
    private java.util.List<TypesBean> types;
    public boolean isHighlighted;

    public Pokemon(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static class SpritesBean {

        private String front_default;

        public String getFront_default() {
            return front_default;
        }

        public void setFront_default(String front_default) {
            this.front_default = front_default;
        }
    }

    public static class StatsBean {

        private int base_stat;

        private StatBean stat;

        public static class StatBean {

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public int getBase_stat() {
            return base_stat;
        }

        public void setBase_stat(int base_stat) {
            this.base_stat = base_stat;
        }

        public StatBean getStat() {
            return stat;
        }

        public void setStat(StatBean stat) {
            this.stat = stat;
        }
    }

    public static class TypesBean {

        private TypeBean type;

        public static class TypeBean {

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public TypeBean getType() {
            return type;
        }

        public void setType(TypeBean type) {
            this.type = type;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public SpritesBean getSprites() {
        return sprites;
    }

    public void setSprites(SpritesBean sprites) {
        this.sprites = sprites;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<StatsBean> getStats() {
        return stats;
    }

    public void setStats(List<StatsBean> stats) {
        this.stats = stats;
    }

    public List<TypesBean> getTypes() {
        return types;
    }

    public void setTypes(List<TypesBean> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "ID: " +
                id +
                "\nName: " +
                name +
                "\nWeight: " +
                weight +
                "\nHeight: " +
                height +
                "\nType: " +
                types.get(0).type.name +
                "\nStats: " +
                "\n\t\t" +
                stats.get(Constants.DEFENCE_STAT_ID).stat.name +
                ": " +
                stats.get(Constants.DEFENCE_STAT_ID).base_stat +
                "\n\t\t" +
                stats.get(Constants.ATTACK_STAT_ID).stat.name +
                ": " +
                stats.get(Constants.ATTACK_STAT_ID).base_stat +
                "\n\t\t" +
                stats.get(Constants.HP_STAT_ID).stat.name +
                ": " +
                stats.get(Constants.HP_STAT_ID).base_stat;
    }

    public static final DiffUtil.ItemCallback<Pokemon> DIFF_CALLBACK = new DiffUtil.ItemCallback<Pokemon>() {
        @Override
        public boolean areItemsTheSame(@NonNull Pokemon oldPokemon, @NonNull Pokemon newPokemon) {
            return oldPokemon.id == newPokemon.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Pokemon oldPokemon, @NonNull Pokemon newPokemon) {
            return oldPokemon.name.equals(newPokemon.name);
        }
    };
}