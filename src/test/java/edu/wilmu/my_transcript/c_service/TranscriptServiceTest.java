package edu.wilmu.my_transcript.c_service;

import edu.wilmu.my_transcript.a_entity.Transcript;
import edu.wilmu.my_transcript.d_repository.TranscriptRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Testing 'TranscriptService' class")
public class TranscriptServiceTest {

    // Fields
    @Mock
    private TranscriptRepository transcriptRepository;

    @InjectMocks
    private TranscriptService transcriptService;

    // Setup Method
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test Methods

    // Testing getFullTranscriptService() method

    @Order(1)
    @DisplayName("Testing getFullTranscriptService() method")
    @Test
    void getFullTranscriptService() {
        transcriptService.getFullTranscriptService();
        verify(transcriptRepository, times(1)).findAll();
    }

    // Testing specificCourseResultService() method

    @Order(2)
    @DisplayName("Testing specificCourseResultService() method")
    @Test
    void specificCourseResultService() {
        Transcript transcript = new Transcript(6030, "OS and Computer System Sec", "TR", 3, "Spring 2023");

        when(transcriptRepository.findAll()).thenReturn(List.of(transcript));

        Transcript result = transcriptService.specificCourseResultService(6030);

        assertEquals(transcript, result);
    }

    // Testing updateNewCourseService() method

    @Order(3)
    @DisplayName("Testing updateNewCourseService() method")
    @Test
    void updateNewCourseService() {
        Transcript transcript = new Transcript(6030, "OS and Computer System Sec", "TR", 3, "Spring 2023");

        when(transcriptRepository.findByCourseId(6030)).thenReturn(Optional.empty());

        transcriptService.updateNewCourseService(transcript);

        verify(transcriptRepository, times(1)).save(transcript);
    }

    // Testing updateCourseInformationService() method

    @Order(4)
    @DisplayName("Testing updateCourseInformationService() method")
    @Test
    void updateCourseInformationService() {
        Transcript transcript = new Transcript(6030, "OS and Computer System Sec", "TR", 3, "Spring 2023");

        when(transcriptRepository.findByCourseId(6030)).thenReturn(Optional.of(transcript));

        transcriptService.updateCourseInformationService(6030, "Advanced OS", null, null, null);

        assertEquals("Advanced OS", transcript.getCourseName());
        verify(transcriptRepository, times(1)).findByCourseId(6030);

    }

    // Testing deleteCourseService() method

    @Order(5)
    @DisplayName("Testing deleteCourseService() method")
    @Test
    void deleteCourseService() {
        when(transcriptRepository.existsById(6030)).thenReturn(true);

        transcriptService.deleteCourseService(6030);

        verify(transcriptRepository, times(1)).deleteById(6030);
    }
}
