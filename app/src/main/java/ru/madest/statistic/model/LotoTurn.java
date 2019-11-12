package ru.madest.statistic.model;

import java.io.Serializable;
import java.util.Map;

public class LotoTurn implements Serializable {

    private SimpleDraw startDraw;
    private SimpleDraw endDraw;
    private SimpleDraw leftFiveDigitDraw;
    private SimpleDraw leftThreeDigitDraw;
    private SimpleDraw leftOneDigitDraw;
    private SimpleDraw borderDraw;
    private Integer borderZero;
    private Integer drawCount;
    private Map<Integer,Integer> fallouts;

    public SimpleDraw getStartDraw() {
        return startDraw;
    }

    public void setStartDraw( SimpleDraw startDraw ) {
        this.startDraw = startDraw;
    }

    public SimpleDraw getEndDraw() {
        return endDraw;
    }

    public void setEndDraw( SimpleDraw endDraw ) {
        this.endDraw = endDraw;
    }

    public SimpleDraw getLeftFiveDigitDraw() {
        return leftFiveDigitDraw;
    }

    public void setLeftFiveDigitDraw( SimpleDraw leftFiveDigitDraw ) {
        this.leftFiveDigitDraw = leftFiveDigitDraw;
    }

    public SimpleDraw getLeftThreeDigitDraw() {
        return leftThreeDigitDraw;
    }

    public void setLeftThreeDigitDraw( SimpleDraw leftThreeDigitDraw ) {
        this.leftThreeDigitDraw = leftThreeDigitDraw;
    }

    public SimpleDraw getLeftOneDigitDraw() {
        return leftOneDigitDraw;
    }

    public void setLeftOneDigitDraw( SimpleDraw leftOneDigitDraw ) {
        this.leftOneDigitDraw = leftOneDigitDraw;
    }

    public SimpleDraw getBorderDraw() {
        return borderDraw;
    }

    public void setBorderDraw( SimpleDraw borderDraw ) {
        this.borderDraw = borderDraw;
    }

    public Integer getBorderZero() {
        return borderZero;
    }

    public void setBorderZero( Integer borderZero ) {
        this.borderZero = borderZero;
    }

    public Integer getDrawCount() {
        return drawCount;
    }

    public void setDrawCount( Integer drawCount ) {
        this.drawCount = drawCount;
    }

    public Map< Integer, Integer > getFallouts() {
        return fallouts;
    }

    public void setFallouts( Map< Integer, Integer > fallouts ) {
        this.fallouts = fallouts;
    }
}
