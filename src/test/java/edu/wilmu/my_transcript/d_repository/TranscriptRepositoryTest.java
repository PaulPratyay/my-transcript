package edu.wilmu.my_transcript.d_repository;

import edu.wilmu.my_transcript.a_entity.Transcript;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/test-data.sql")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Testing 'TranscriptRepository' class")
public class TranscriptRepositoryTest {

    @Autowired
    private TranscriptRepository transcriptRepository;

    // Testing 'findByCourseId' method
    @Order(1)
    @DisplayName("Testing 'findCourseById' method")
    @Test
    void testFindByCourseId() {
        Optional<Transcript> transcript = transcriptRepository.findByCourseId(6030);
        assertThat(transcript).isPresent();
        assertThat(transcript.get().getCourseName()).isEqualTo("OS and Computer System Sec");
    }

    // Testing 'saveTranscript' method

    @Order(2)
    @DisplayName("Testing 'saveTranscript' method")
    @Test
    void testSaveTranscript() {
        Transcript newTranscript = new Transcript(6040, "Advanced Networking", "A", 3, "Fall 2023");
        Transcript savedTranscript = transcriptRepository.save(newTranscript);
        Optional<Transcript> foundTranscript = transcriptRepository.findById(savedTranscript.getCourseId());
        assertThat(foundTranscript).isPresent();
        assertThat(foundTranscript.get().getCourseName()).isEqualTo("Advanced Networking");

    }
}
