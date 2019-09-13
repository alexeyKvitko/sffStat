package ru.sff.statistic.model;

import java.io.Serializable;

public class EvenOdd implements Serializable {

    private int evenCount;
    private int oddCount;

    public EvenOdd() {
        this.evenCount = 0;
        this.oddCount = 0;
    }

    public int getEvenCount() {
        return evenCount;
    }

    public void setEvenCount(int evenCount) {
        this.evenCount = evenCount;
    }

    public int getOddCount() {
        return oddCount;
    }

    public void setOddCount(int oddCount) {
        this.oddCount = oddCount;
    }

}
