package src.components;


public class Switch extends Controls{
    private int id;
    private String def,text;

    public Switch(int id, String def, String text) {
        this.id = id;
        this.def = def;
        this.text = text;
    }
    public Switch() {
    }
    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "SW#"+id+"#"+def+"#"+text;
    }
    
    
    
}