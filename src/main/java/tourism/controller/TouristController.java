package tourism.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tourism.model.OldByer;
import tourism.model.OldTags;
import tourism.model.OldTouristAttraction;
import tourism.repository.ByerRepository;
import tourism.repository.TagsRepository;
import tourism.service.TouristService;


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
        return "attraction";
    }

    @GetMapping("/add")
    public String addTouristAttraction(Model model) {
        OldTouristAttraction attraction = new OldTouristAttraction();
        attraction.setBy(OldByer.KØBENHAVN);
        model.addAttribute("attraction", attraction);
        model.addAttribute("city", touristService.getAllByer());
        model.addAttribute("tags", touristService.getAllTags());
        return "add";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute OldTouristAttraction touristAttraction) {
        touristService.addAttractions(touristAttraction);
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
        model.addAttribute("touristAttraction", new OldTouristAttraction());
        return "attractionsList";
    }

    @GetMapping("/attractions/edit/{name}")
    public String editAttraction(@PathVariable String name, Model model) {
        OldTouristAttraction touristAttraction = touristService.getAttractionByName(name);
        if (touristAttraction.getName() == null) {
            throw new IllegalArgumentException("Id not found");
        }
        model.addAttribute("attraction", touristAttraction);
        model.addAttribute("city", OldByer.values());
        model.addAttribute("tags", OldTags.values());

        return "editAttraction";
    }

    @PostMapping("/edit")
    public String postEditAttraction(@ModelAttribute OldTouristAttraction touristAttraction) {
        touristService.updateAttraction(touristAttraction);
        return "redirect:/attractionsList";
    }

    @GetMapping("/attractions/tags/{name}")
    public String tags(Model model, @PathVariable String name) {
        model.addAttribute("touristAttraction", touristService.getTags(name));
        return "tags";
    }

    @PostMapping("/attractions/delete/{name}")
    public String removeAttraction(@PathVariable String name) {
        touristService.removeAttraction(name);
        return "redirect:/attractionsList";
    }
}
