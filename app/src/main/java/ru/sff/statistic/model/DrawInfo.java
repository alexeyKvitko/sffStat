package ru.sff.statistic.model;

import java.io.Serializable;

public class DrawInfo implements Serializable {

    private Integer id;
    private Integer drawId;
    private Integer twoGuessedWin;
    private Integer twoGuessedAmount;
    private Integer twoGuessedTotal;
    private Integer threeGuessedWin;
    private Integer threeGuessedAmount;
    private Integer threeGuessedTotal;
    private Integer fourGuessedWin;
    private Integer fourGuessedAmount;
    private Integer fourGuessedTotal;
    private Integer fiveGuessedWin;
    private Integer fiveGuessedAmount;
    private Integer fiveGuessedTotal;
    private Integer sixGuessedWin;
    private Integer sixGuessedAmount;
    private Integer sixGuessedTotal;
    private Integer personCount;
    private Integer paidAmount;
    private Integer superPrize;

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

    public Integer getTwoGuessedWin() {
        return twoGuessedWin;
    }

    public void setTwoGuessedWin( Integer twoGuessedWin ) {
        this.twoGuessedWin = twoGuessedWin;
    }

    public Integer getTwoGuessedAmount() {
        return twoGuessedAmount;
    }

    public void setTwoGuessedAmount( Integer twoGuessedAmount ) {
        this.twoGuessedAmount = twoGuessedAmount;
    }

    public Integer getTwoGuessedTotal() {
        return twoGuessedTotal;
    }

    public void setTwoGuessedTotal( Integer twoGuessedTotal ) {
        this.twoGuessedTotal = twoGuessedTotal;
    }

    public Integer getThreeGuessedWin() {
        return threeGuessedWin;
    }

    public void setThreeGuessedWin( Integer threeGuessedWin ) {
        this.threeGuessedWin = threeGuessedWin;
    }

    public Integer getThreeGuessedAmount() {
        return threeGuessedAmount;
    }

    public void setThreeGuessedAmount( Integer threeGuessedAmount ) {
        this.threeGuessedAmount = threeGuessedAmount;
    }

    public Integer getThreeGuessedTotal() {
        return threeGuessedTotal;
    }

    public void setThreeGuessedTotal( Integer threeGuessedTotal ) {
        this.threeGuessedTotal = threeGuessedTotal;
    }

    public Integer getFourGuessedWin() {
        return fourGuessedWin;
    }

    public void setFourGuessedWin( Integer fourGuessedWin ) {
        this.fourGuessedWin = fourGuessedWin;
    }

    public Integer getFourGuessedAmount() {
        return fourGuessedAmount;
    }

    public void setFourGuessedAmount( Integer fourGuessedAmount ) {
        this.fourGuessedAmount = fourGuessedAmount;
    }

    public Integer getFourGuessedTotal() {
        return fourGuessedTotal;
    }

    public void setFourGuessedTotal( Integer fourGuessedTotal ) {
        this.fourGuessedTotal = fourGuessedTotal;
    }

    public Integer getFiveGuessedWin() {
        return fiveGuessedWin;
    }

    public void setFiveGuessedWin( Integer fiveGuessedWin ) {
        this.fiveGuessedWin = fiveGuessedWin;
    }

    public Integer getFiveGuessedAmount() {
        return fiveGuessedAmount;
    }

    public void setFiveGuessedAmount( Integer fiveGuessedAmount ) {
        this.fiveGuessedAmount = fiveGuessedAmount;
    }

    public Integer getFiveGuessedTotal() {
        return fiveGuessedTotal;
    }

    public void setFiveGuessedTotal( Integer fiveGuessedTotal ) {
        this.fiveGuessedTotal = fiveGuessedTotal;
    }

    public Integer getSixGuessedWin() {
        return sixGuessedWin;
    }

    public void setSixGuessedWin( Integer sixGuessedWin ) {
        this.sixGuessedWin = sixGuessedWin;
    }

    public Integer getSixGuessedAmount() {
        return sixGuessedAmount;
    }

    public void setSixGuessedAmount( Integer sixGuessedAmount ) {
        this.sixGuessedAmount = sixGuessedAmount;
    }

    public Integer getSixGuessedTotal() {
        return sixGuessedTotal;
    }

    public void setSixGuessedTotal( Integer sixGuessedTotal ) {
        this.sixGuessedTotal = sixGuessedTotal;
    }

    public Integer getPersonCount() {
        return personCount;
    }

    public void setPersonCount( Integer personCount ) {
        this.personCount = personCount;
    }

    public Integer getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount( Integer paidAmount ) {
        this.paidAmount = paidAmount;
    }

    public Integer getSuperPrize() {
        return superPrize;
    }

    public void setSuperPrize( Integer superPrize ) {
        this.superPrize = superPrize;
    }
}
