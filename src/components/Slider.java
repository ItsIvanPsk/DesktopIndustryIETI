package src.components;
import java.io.Serializable;

public class Slider implements Serializable{
    private String id;
    private String def;
    private String min;
    private String max;
    private String step;
    private String text;
    
    //Constructor
    public Slider(String id, String def, String min, String max, String step, String text) {
        super();
        this.id = id;
        this.def = def;
        this.min = min;
        this.max = max;
        this.step = step;
        this.text = text;
    }
    
    //Getters and Setters
    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getValue() {
        return text;
    }

    public void setValue(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    //ToString
    public String toString() {
        return "SL#"+id+"#"+def+"#"+min+"#"+ max+"#"+step+"#"+text ;
    }
    
    
}