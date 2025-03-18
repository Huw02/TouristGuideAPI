package tourism.service;

import org.springframework.stereotype.Service;
import tourism.model.OldTags;
import tourism.model.OldTouristAttraction;
import tourism.model.TouristAttraction;
import tourism.repository.TouristRepository;

import java.util.List;

@Service
public class TouristService {
    private final TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    public List<OldTags> getTags(String name) {
        return touristRepository.getTags(name);
    }

    public List<TouristAttraction> getAttractions() {
        return touristRepository.getAllTouristAttractions();
    }

    public OldTouristAttraction getAttractionsByName(String name) {
        return touristRepository.getAttractionsByName(name);
    }

    public TouristRepository setAttractions(List<OldTouristAttraction> attractions) {
        touristRepository.setAttractions(attractions);
        return touristRepository;
    }

    public OldTouristAttraction addAttractions(OldTouristAttraction touristAttraction) {
        touristRepository.addAttractions(touristAttraction);
        return touristAttraction;
    }

    public void updateAttraction(OldTouristAttraction touristAttraction) {
        touristRepository.updateAttraction(touristAttraction);
    }

    public OldTouristAttraction getAttractionByName(String name) {
        return touristRepository.getAttractionByName(name);
    }

    public OldTouristAttraction removeAttraction(String name) {
        return touristRepository.removeAttraction(name);
    }


    public List<OldTouristAttraction>getAttractionsDatabase(){
        return touristRepository.getAttractionsDatabase();
    }
}