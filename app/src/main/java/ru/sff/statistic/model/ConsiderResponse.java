package ru.sff.statistic.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ConsiderResponse implements Serializable {

    private List<ConsiderInfo> considerInfos;

    public ConsiderResponse() {
        this.considerInfos = new LinkedList<>();
    }

    public List<ConsiderInfo> getConsiderInfos() {
        return considerInfos;
    }

    public void setConsiderInfos(List<ConsiderInfo> considerInfos) {
        this.considerInfos = considerInfos;
    }
}
