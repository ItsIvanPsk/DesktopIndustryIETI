package src.components;


public class Slider extends Controls {
    private int id, def, min, max, step;
    private String text, blockName;

    //Constructor
    public Slider(int id, String blockName, int def, int min, int max, int step, String text) {
        super();
        this.id = id;
        this.blockName = blockName;
        this.def = def;
        this.min = min;
        this.max = max;
        this.step = step;
        this.text = text;
    }

    public Slider() {
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBlockId() {
        return blockName;
    }

    public void setBlockId(String blockName) {
        this.blockName = blockName;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getValue() {
        return text;
    }

    public void setValue(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    //ToString
    public String toString() {
        return "SL#" + id + "#" + blockName + "#" + def + "#" + min + "#" + max + "#" + step + "#" + text;
    }

}