package tourism.model;

import java.util.List;

public class TouristAttraction {
    private String name;
    private String description;
    private List<Tags> tags;
    private Byer by;




    public TouristAttraction(String name, String description, List<Tags> tags, Byer by) {
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.by = by;


    }

    public TouristAttraction() {
    }

    public List<Tags> getTags() {
        return tags;
    }


    public Byer getBy() {
        return by;
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

    public void setBy(Byer by) {
        this.by = by;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }
}
