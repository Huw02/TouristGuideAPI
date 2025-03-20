package tourism.DTO;


import java.util.List;

public class DTOTouristAttraction {
    private int id;
    private String name;
    private String description;
    private int byId;
    private List<Integer> tagsId;

    public DTOTouristAttraction(int id, String name, String description, int byId, List<Integer> tagsId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.byId = byId;
        this.tagsId = tagsId;
    }

    public DTOTouristAttraction() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getTagsId() {
        return tagsId;
    }

    public void setTagsId(List<Integer> tagsId) {
        this.tagsId = tagsId;
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

    public int getById() {
        return byId;
    }

    public void setById(int byId) {
        this.byId = byId;
    }


}
