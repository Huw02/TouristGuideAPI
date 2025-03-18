package tourism.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tourism.model.*;
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
        TouristAttraction attraction = new TouristAttraction();
        attraction.setBy(new Byer("København", 33));
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
        TouristAttraction touristAttraction = touristService.getTouristAttractionByName(name);
        if (touristAttraction.getName() == null) {
            throw new IllegalArgumentException("Id not found");
        }
        model.addAttribute("attraction", touristAttraction);
        model.addAttribute("city", touristService.getAllByer());
        model.addAttribute("tags", touristService.getAllTags());

        return "editAttraction";
    }

    @PostMapping("/edit")
    public String postEditAttraction(@ModelAttribute TouristAttraction touristAttraction) {
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
        touristService.getTagsByName(name);
        return "redirect:/attractionsList";
    }
}
