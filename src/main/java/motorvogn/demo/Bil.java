package motorvogn.demo;

public class Bil {
    private int id;
    private String merke;
    private String type;


    public Bil (int id, String merke, String type){
        this.merke = merke;
        this.type = type;
        this.id = id;
    }
    public Bil(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMerke() {
        return merke;
    }

    public void setMerke(String merke) {
        this.merke = merke;
    }
}
