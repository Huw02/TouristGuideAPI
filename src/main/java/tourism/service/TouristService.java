package tourism.service;

import org.springframework.stereotype.Service;
import tourism.DTO.DTOTouristAttraction;
import tourism.model.*;
import tourism.repository.TouristRepository;

import java.util.List;

@Service
public class TouristService {
    private final TouristRepository touristRepository;


    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;

    }


    public List<TouristAttraction> getAttractions() {
        return touristRepository.getAllTouristAttractions();
    }

    public TouristAttraction getAttractionsByName(String name) {
        return touristRepository.getTouristAttractionByName(name);
    }

    public TouristAttraction updateTouristAttraction(TouristAttraction touristAttraction) {
        return touristRepository.updateTouristAttraction(touristAttraction);
    }


    public void removeAttraction(String name) {
        touristRepository.deleteTouristAttraction(name);
    }


    public List<Tags> getAllTags() {
        return touristRepository.getAllTags();
    }

    public List<Byer> getAllByer() {
        return touristRepository.getAllbyer();
    }


    public int getById(String name) {
        return touristRepository.getById(name);
    }

    public Byer getByerById(int id) {
        return touristRepository.getByerById(id);
    }

    public Byer getByWithName(String name) {
        return touristRepository.getByWithName(name);
    }


    public List<Tags> getTagsByName(String name) {
        return touristRepository.getTagsForAttraction(touristRepository.getTouristAttractionByName(name).getId());
    }

    public Tags getTagsById(int id) {
        return touristRepository.getTagById(id);
    }


    public TouristAttraction getTouristAttractionByName(String name) {
        return touristRepository.getTouristAttractionByName(name);
    }

    public TouristAttraction addTouristAttraction(TouristAttraction touristAttraction) {
        return touristRepository.addTouristAttraction(touristAttraction);
    }


}