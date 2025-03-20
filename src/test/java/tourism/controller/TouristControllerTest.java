package tourism.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tourism.model.TouristAttraction;
import tourism.service.TouristService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    }

    @Test
    void attractions() throws Exception {
        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }


    @Test
    void postEditAttraction() throws Exception {
    }

}