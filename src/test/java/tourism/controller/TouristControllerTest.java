package tourism.controller;

import tourism.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tourism.model.TouristAttraction;
import tourism.service.TouristService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TouristController.class)
class TouristControllerTest {

    private List<TouristAttraction> mockList;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TouristService touristService;

    @BeforeEach
    void setup() {
        TouristAttraction attraction1 = new TouristAttraction("Tivoli", "Forlystelsespark", Arrays.asList(Tags.KULTUR, Tags.FORLYSTELSESPARK, Tags.UNDERHOLDNING), Byer.KØBENHAVN);
        TouristAttraction attraction2 = new TouristAttraction("DR Byen", "Blå bygning, idk", Arrays.asList(Tags.KONCERT, Tags.KULTUR), Byer.KØBENHAVN);
        mockList = List.of(attraction1, attraction2);
    }

    @Test
    void attractions() throws Exception {
        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void testAttractionList() throws Exception {
        when(touristService.getAttractions()).thenReturn(mockList);
        mockMvc.perform(get("/attractionsList"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractionsList"))
                .andExpect(model().attributeExists("attractionsList"))
                .andExpect(model().attribute("attractionsList", mockList));
        verify(touristService, times(1)).getAttractions();
    }

    @Test
    void postEditAttraction() throws Exception {
    }
}
