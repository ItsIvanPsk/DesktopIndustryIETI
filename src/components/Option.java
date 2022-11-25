package src.components;

public class Option extends Controls{
    private int value;
    private String text;

    //Constructor
    public Option(int value, String text) {
        super();
        this.value = value;
        this.text = text;
    }

    //Getters and Setters
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
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
    	return value + "//" + text;
    }
}
