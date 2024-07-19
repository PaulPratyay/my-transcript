package edu.wilmu.my_transcript.b_controller;

import edu.wilmu.my_transcript.a_entity.Transcript;
import edu.wilmu.my_transcript.c_service.TranscriptService;

import org.junit.jupiter.api.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Testing 'TranscriptController' class")
public class TranscriptControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TranscriptService transcriptService;

    @InjectMocks
    private TranscriptController transcriptController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transcriptController).build();
    }

    // Testing Methods

    // Testing getFullTranscriptController() method

    @Order(1)
    @DisplayName("Testing 'getFullTranscriptController()' method")
    @Test
    void getFullTranscriptController() throws Exception {
        when(transcriptService.getFullTranscriptService()).thenReturn(List.of(new Transcript()));

        mockMvc.perform(get("/api/v2/my_transcript"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(transcriptService, times(1)).getFullTranscriptService();

    }

    // Testing transcriptHomepage() method

    @Order(2)
    @DisplayName("Testing 'transcriptHomepage()' method")
    @Test
    void transcriptHomepage() throws Exception {
        mockMvc.perform(get("/api/v2/my_transcript/home"))
                .andExpect(status().isOk())
                .andExpect(content().string("This is not an official transcript."));

    }

    // Testing specificCourseResultController() method

    @Order(3)
    @DisplayName("Testing 'specificCourseResultController()' method")
    @Test
    void specificCourseResultController() throws Exception {
        Transcript transcript = new Transcript(6030, "OS and Computer System Sec", "TR", 3, "Spring 2023");
        when(transcriptService.specificCourseResultService(6030)).thenReturn(transcript);

        mockMvc.perform(get("/api/v2/my_transcript/6030"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseId").value(6030));

        verify(transcriptService, times(1)).specificCourseResultService(6030);

    }

    // Testing updateNewCourseController() method

    @Order(4)
    @DisplayName("Testing 'updateNewCourseController()' method")
    @Test
    void updateNewCourseController() throws Exception {
        Transcript transcript = new Transcript(6030, "OS and Computer System Sec", "TR", 3, "Spring 2023");

        mockMvc.perform(post("/api/v2/my_transcript")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"courseId\":6030,\"courseName\":\"OS and Computer System Sec\",\"grade\":\"TR\",\"credit\":3,\"term\":\"Spring 2023\"}"))
                .andExpect(status().isOk());

        verify(transcriptService, times(1)).updateNewCourseService(any(Transcript.class));
    }

    // Testing updateCourseInformationController() method

    @Order(5)
    @DisplayName("Testing 'updateCourseInformationController()' method")
    @Test
    void updateCourseInformationController() throws Exception {
        mockMvc.perform(put("/api/v2/my_transcript/6030")
                        .param("courseName", "Advanced OS"))
                .andExpect(status().isOk());

        verify(transcriptService, times(1)).updateCourseInformationService(6030, "Advanced OS", null, null, null);
    }

    // Testing deleteCourseController() method

    @Order(6)
    @DisplayName("Testing 'deleteCourseController()' method")
    @Test
    void deleteCourseController() throws Exception {
        mockMvc.perform(delete("/api/v2/my_transcript/6030"))
                .andExpect(status().isOk());

        verify(transcriptService, times(1)).deleteCourseService(6030);
    }
}
