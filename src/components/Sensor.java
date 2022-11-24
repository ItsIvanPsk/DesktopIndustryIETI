package src.components;

public class Sensor extends Controls{
    int id,blockID,thresholdlow,thresholdhigh;
    private String units,text;

    public Sensor(int id, int blockID, String units, int thresholdlow,int thresholdhigh,String text) {
        this.id = id;
        this.blockID = blockID;
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

    public int getBlockId() {
        return blockID;
    }

    public void setBlockId(int blockID) {
        this.blockID = blockID;
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
        return "SS#" + id + "#" + blockID + "#" + units + "#" + thresholdlow + "#" + thresholdhigh + "#" + text;
    }
    
    
    
}
