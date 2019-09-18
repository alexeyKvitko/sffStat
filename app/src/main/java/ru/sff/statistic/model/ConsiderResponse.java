package ru.sff.statistic.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ConsiderResponse implements Serializable {

    private List<ConsiderInfo> considerInfos;
    private List<LotoModel> fiveHits;
    private List<LotoModel> sixHits;

    public ConsiderResponse() {
        this.considerInfos = new LinkedList<>();
    }

    public List<ConsiderInfo> getConsiderInfos() {
        return considerInfos;
    }

    public void setConsiderInfos(List<ConsiderInfo> considerInfos) {
        this.considerInfos = considerInfos;
    }

    public List<LotoModel> getFiveHits() {
        return fiveHits;
    }

    public void setFiveHits(List<LotoModel> fiveHits) {
        this.fiveHits = fiveHits;
    }

    public List<LotoModel> getSixHits() {
        return sixHits;
    }

    public void setSixHits(List<LotoModel> sixHits) {
        this.sixHits = sixHits;
    }

}
