package tourism.model;

import java.util.List;

public class OldTouristAttraction {
    private String name;
    private String description;
    private List<OldTags> tags;
    private OldByer by;





    public OldTouristAttraction(String name, String description, List<OldTags> tags, OldByer by) {
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.by = by;

    }

    public OldTouristAttraction() {
    }

    public List<OldTags> getTags() {
        return tags;
    }


    public OldByer getBy() {
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

    public void setBy(OldByer by) {
        this.by = by;
    }

    public void setTags(List<OldTags> tags) {
        this.tags = tags;
    }
}
