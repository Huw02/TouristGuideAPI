package tourism.model;

import java.util.List;

public class TouristAttraction {
    private int id;
    private String name;
    private String description;
    private List<Tags> tags;
    private Byer by;





    public TouristAttraction(int id, String name, String description, List<Tags> tags, Byer by) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.by = by;

    }

    public TouristAttraction() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public Byer getBy() {
        return by;
    }

    public void setBy(Byer by) {
        this.by = by;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
