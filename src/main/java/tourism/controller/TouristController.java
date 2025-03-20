package tourism.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tourism.DTO.DTOTouristAttraction;
import tourism.model.*;
import tourism.service.TouristService;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("")
public class TouristController {
    private final TouristService touristService;


    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping("/attractions")
    public String attractions(Model model) {
        model.addAttribute("attractions", touristService.getAttractions());
        return "index";
    }

    @GetMapping("attractions/{name}")
    public String getAttractionByName(@PathVariable String name, Model model) {
        model.addAttribute("attractionsByName", touristService.getAttractionsByName(name));
        model.addAttribute("tags", touristService.getTagsByName(name));
        return "attraction";
    }

    @GetMapping("/add")
    public String addTouristAttraction(Model model) {
        model.addAttribute("dto", new DTOTouristAttraction());
        model.addAttribute("city", touristService.getAllByer());
        model.addAttribute("tags", touristService.getAllTags());
        return "add";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute DTOTouristAttraction dtotouristAttraction) {
        TouristAttraction nytouristAttraction = new TouristAttraction();
        nytouristAttraction.setName(dtotouristAttraction.getName());
        nytouristAttraction.setDescription(dtotouristAttraction.getDescription());
        Byer tempBy = touristService.getByerById(dtotouristAttraction.getById());
        nytouristAttraction.setBy(tempBy);

        List<Integer> tempList = dtotouristAttraction.getTagsId();
        List<Tags> tagsList = new ArrayList<>();

        if (tempList != null) {
            for (int i : tempList) {
                tagsList.add(touristService.getTagsById(i));
            }
        }
        nytouristAttraction.setTags(tagsList);
        touristService.addTouristAttraction(nytouristAttraction);

        return "redirect:/save";
    }

    @GetMapping("/save")
    public String savedAttractions(Model model) {
        model.addAttribute("attractions", touristService.getAttractions());
        return "saveAttractions";
    }

    @GetMapping("/attractionsList")
    public String attractionsList(Model model) {
        model.addAttribute("attractionsList", touristService.getAttractions());
        return "attractionsList";
    }

    @GetMapping("/attractions/edit/{name}")
    public String editAttraction(@PathVariable String name, Model model) {
        TouristAttraction touristAttraction = touristService.getTouristAttractionByName(name);
        if (touristAttraction.getName() == null) {
            throw new IllegalArgumentException("Id not found");
        }
        model.addAttribute("attraction", touristAttraction);
        model.addAttribute("dto", new DTOTouristAttraction());
        model.addAttribute("city", touristService.getAllByer());
        model.addAttribute("tags", touristService.getAllTags());

        return "editAttraction";
    }

    @PostMapping("/edit")
    public String postEditAttraction(@ModelAttribute DTOTouristAttraction dtotouristAttraction) {
        TouristAttraction touristAttraction = new TouristAttraction();
        touristAttraction.setId(dtotouristAttraction.getId());
        touristAttraction.setName(dtotouristAttraction.getName());
        touristAttraction.setDescription(dtotouristAttraction.getDescription());
        Byer tempBy = touristService.getByerById(dtotouristAttraction.getId());
        touristAttraction.setBy(tempBy);


        List<Integer> tempList = dtotouristAttraction.getTagsId();
        List<Tags> tagsList = new ArrayList<>();

        if (tempList != null) {
            for (int i : tempList) {
                tagsList.add(touristService.getTagsById(i));
            }
        }

        touristAttraction.setTags(tagsList);

        touristService.updateTouristAttraction(touristAttraction);

        return "redirect:/attractionsList";
    }

    @GetMapping("/attractions/tags/{name}")
    public String tags(Model model, @PathVariable String name) {
        model.addAttribute("touristAttraction", touristService.getTagsByName(name));
        return "tags";
    }

    @PostMapping("/attractions/delete/{name}")
    public String removeAttraction(@PathVariable String name) {
        touristService.removeAttraction(name);
        return "redirect:/attractionsList";
    }
}
