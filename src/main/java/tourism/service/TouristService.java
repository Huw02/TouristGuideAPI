package tourism.service;

import org.springframework.stereotype.Service;
import tourism.model.*;
import tourism.repository.ByerRepository;
import tourism.repository.TagsRepository;
import tourism.repository.TouristRepository;

import java.util.List;

@Service
public class TouristService {
    private final TouristRepository touristRepository;
    private final TagsRepository tagsRepository;
    private final ByerRepository byerRepository;

    public TouristService(TouristRepository touristRepository, TagsRepository tagsRepository, ByerRepository byerRepository) {
        this.touristRepository = touristRepository;
        this.tagsRepository = tagsRepository;
        this.byerRepository = byerRepository;
    }

    public List<OldTags> getTags(String name) {
        return touristRepository.getTags(name);
    }

    public List<TouristAttraction> getAttractions() {
        return touristRepository.getAllTouristAttractions();
    }

    public TouristAttraction getAttractionsByName(String name) {
        return touristRepository.getTouristAttractionByName(name);
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

    public void removeAttraction(String name) {
        touristRepository.deleteTouristAttraction(name);
    }


    public List<OldTouristAttraction>getAttractionsDatabase(){
        return touristRepository.getAttractionsDatabase();
    }

    public List<Tags>getAllTags(){
        return tagsRepository.getAllTags();
    }

    public List<Byer>getAllByer(){
        return byerRepository.getAllbyer();
    }

}