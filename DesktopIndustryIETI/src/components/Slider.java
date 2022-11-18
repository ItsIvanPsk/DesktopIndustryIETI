package src.components;

public class Slider extends Controls{
    private int id;
    private int def;
    private int min;
    private int max;
    private int step;
    private String text;
    
    //Constructor
    public Slider(int id, int def, int min, int max, int step, String text) {
        super();
        this.id = id;
        this.def = def;
        this.min = min;
        this.max = max;
        this.step = step;
        this.text = text;
    }
    
    public Slider() {
    }

    //Getters and Setters
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

    public int getId() {
        return id;
    }

    //ToString
    public String toString() {
        return "SL#"+id+"#"+def+"#"+min+"#"+ max+"#"+step+"#"+text ;
    }
    
    public void setId(int id) {
		this.id = id;
    }
    
}