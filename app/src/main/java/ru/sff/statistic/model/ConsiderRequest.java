package ru.sff.statistic.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ConsiderRequest implements Serializable {

    private List<Integer> balls;

    public ConsiderRequest( ) {
        this.balls = new LinkedList<>(  );
    }

    public List<Integer> getBalls() {
        return balls;
    }

    public void setBalls(List<Integer> balls) {
        this.balls = balls;
    }
}
