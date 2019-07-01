package ru.sff.statistic.model;

import java.io.Serializable;

public class DrawInfo implements Serializable {

    private Integer id;
    private Integer drawId;
    private Long twoGuessedWin;
    private Long twoGuessedAmount;
    private Long twoGuessedTotal;
    private Long threeGuessedWin;
    private Long threeGuessedAmount;
    private Long threeGuessedTotal;
    private Long fourGuessedWin;
    private Long fourGuessedAmount;
    private Long fourGuessedTotal;
    private Long fiveGuessedWin;
    private Long fiveGuessedAmount;
    private Long fiveGuessedTotal;
    private Long sixGuessedWin;
    private Long sixGuessedAmount;
    private Long sixGuessedTotal;
    private Long personCount;
    private Long paidAmount;
    private Long superPrize;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public Integer getDrawId() {
        return drawId;
    }

    public void setDrawId( Integer drawId ) {
        this.drawId = drawId;
    }

    public Long getTwoGuessedWin() {
        return twoGuessedWin;
    }

    public void setTwoGuessedWin( Long twoGuessedWin ) {
        this.twoGuessedWin = twoGuessedWin;
    }

    public Long getTwoGuessedAmount() {
        return twoGuessedAmount;
    }

    public void setTwoGuessedAmount( Long twoGuessedAmount ) {
        this.twoGuessedAmount = twoGuessedAmount;
    }

    public Long getTwoGuessedTotal() {
        return twoGuessedTotal;
    }

    public void setTwoGuessedTotal( Long twoGuessedTotal ) {
        this.twoGuessedTotal = twoGuessedTotal;
    }

    public Long getThreeGuessedWin() {
        return threeGuessedWin;
    }

    public void setThreeGuessedWin( Long threeGuessedWin ) {
        this.threeGuessedWin = threeGuessedWin;
    }

    public Long getThreeGuessedAmount() {
        return threeGuessedAmount;
    }

    public void setThreeGuessedAmount( Long threeGuessedAmount ) {
        this.threeGuessedAmount = threeGuessedAmount;
    }

    public Long getThreeGuessedTotal() {
        return threeGuessedTotal;
    }

    public void setThreeGuessedTotal( Long threeGuessedTotal ) {
        this.threeGuessedTotal = threeGuessedTotal;
    }

    public Long getFourGuessedWin() {
        return fourGuessedWin;
    }

    public void setFourGuessedWin( Long fourGuessedWin ) {
        this.fourGuessedWin = fourGuessedWin;
    }

    public Long getFourGuessedAmount() {
        return fourGuessedAmount;
    }

    public void setFourGuessedAmount( Long fourGuessedAmount ) {
        this.fourGuessedAmount = fourGuessedAmount;
    }

    public Long getFourGuessedTotal() {
        return fourGuessedTotal;
    }

    public void setFourGuessedTotal( Long fourGuessedTotal ) {
        this.fourGuessedTotal = fourGuessedTotal;
    }

    public Long getFiveGuessedWin() {
        return fiveGuessedWin;
    }

    public void setFiveGuessedWin( Long fiveGuessedWin ) {
        this.fiveGuessedWin = fiveGuessedWin;
    }

    public Long getFiveGuessedAmount() {
        return fiveGuessedAmount;
    }

    public void setFiveGuessedAmount( Long fiveGuessedAmount ) {
        this.fiveGuessedAmount = fiveGuessedAmount;
    }

    public Long getFiveGuessedTotal() {
        return fiveGuessedTotal;
    }

    public void setFiveGuessedTotal( Long fiveGuessedTotal ) {
        this.fiveGuessedTotal = fiveGuessedTotal;
    }

    public Long getSixGuessedWin() {
        return sixGuessedWin;
    }

    public void setSixGuessedWin( Long sixGuessedWin ) {
        this.sixGuessedWin = sixGuessedWin;
    }

    public Long getSixGuessedAmount() {
        return sixGuessedAmount;
    }

    public void setSixGuessedAmount( Long sixGuessedAmount ) {
        this.sixGuessedAmount = sixGuessedAmount;
    }

    public Long getSixGuessedTotal() {
        return sixGuessedTotal;
    }

    public void setSixGuessedTotal( Long sixGuessedTotal ) {
        this.sixGuessedTotal = sixGuessedTotal;
    }

    public Long getPersonCount() {
        return personCount;
    }

    public void setPersonCount( Long personCount ) {
        this.personCount = personCount;
    }

    public Long getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount( Long paidAmount ) {
        this.paidAmount = paidAmount;
    }

    public Long getSuperPrize() {
        return superPrize;
    }

    public void setSuperPrize( Long superPrize ) {
        this.superPrize = superPrize;
    }
}
