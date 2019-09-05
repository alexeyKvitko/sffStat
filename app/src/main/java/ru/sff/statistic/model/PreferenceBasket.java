package ru.sff.statistic.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class PreferenceBasket implements Serializable {

    private Map< String, StoredBallSet > sharedPreferenceBasket;

    public PreferenceBasket( Map<String, StoredBallSet> storedBallSet ) {
        this.sharedPreferenceBasket = new LinkedHashMap<>(  );
        for( String key : storedBallSet.keySet() ){
            this.sharedPreferenceBasket.put( key, storedBallSet.get(key) );
        }
    }

    public PreferenceBasket get() {
        return this;
    }

    public Map<String, StoredBallSet> getSharedPreferenceBasket() {
        return sharedPreferenceBasket;
    }
}