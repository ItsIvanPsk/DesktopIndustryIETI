package src.components;
 
public class Option {
    private String value, text;
    
    //Constructor
    public Option(String value, String text) {
        super();
        this.value = value;
        this.text = text;
    }
    
    //Getters and Setters
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    //ToSting
    public String toString() {
    	return "value#" + value + "#" + text;
    }
}    
