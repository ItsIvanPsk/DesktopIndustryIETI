package src.components;


public class Switch extends Controls{
    private int id, blockID;
    private String def,text;

    public Switch(int id, int blockID, String def, String text) {
        this.id = id;
        this.blockID = blockID;
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

    public int getBlockId() {
        return blockID;
    }

    public void setBlockId(int blockID) {
        this.blockID = blockID;
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
        return "SW#" + id + "#" + blockID + "#" + def + "#" + text;
    }
    
}