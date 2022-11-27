package src.components;

import src.utils.ServerUtils;

public class Sensor extends Controls implements ServerUtils{
    int id,thresholdlow,thresholdhigh;
    private String units,text,blockName;

    public Sensor(int id, String blockName, String units, int thresholdlow,int thresholdhigh,String text) {
        this.id = id;
        this.blockName = blockName;
        this.units = units;
        this.thresholdlow = thresholdlow;
        this.thresholdhigh= thresholdhigh;
        this.text = text;
    }
    //getters and setters

    public Sensor() {
    }

    public int getId() {
        return id;
    }

    public String getBlockId() {
        return blockName;
    }

    public void setBlockId(String blockName) {
        this.blockName = blockName;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int getThresholdlow() {
        return thresholdlow;
    }

    public void setThresholdlow(int thresholdlow) {
        this.thresholdlow = thresholdlow;
    }

    public int getThresholdhigh() {
        return thresholdhigh;
    }

    public void setThresholdhigh(int thresholdhigh) {
        this.thresholdhigh = thresholdhigh;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(int id){
        this.id = id;
    }
    @Override
    public String toString() {
        return 
        "SS" + componentAttr
        + id + componentAttr
        + blockName + componentAttr
        + units + componentAttr
        + thresholdlow + componentAttr
        + thresholdhigh + componentAttr
        + text;
    }



}
