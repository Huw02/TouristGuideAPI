package tourism.model;

public class Byer {
    private String byName;
    private int byId;

    public Byer(String byName, int byId) {
        this.byName = byName;
        this.byId = byId;
    }


    public int getById() {
        return byId;
    }

    public void setById(int byId) {
        this.byId = byId;
    }

    public String getByName() {
        return byName;
    }

    public void setByName(String byName) {
        this.byName = byName;
    }
}
