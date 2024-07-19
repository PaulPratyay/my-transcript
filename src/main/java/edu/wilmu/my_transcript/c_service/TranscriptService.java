package edu.wilmu.my_transcript.c_service;

import edu.wilmu.my_transcript.a_entity.Transcript;
import edu.wilmu.my_transcript.d_repository.TranscriptRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TranscriptService {

    private static final Logger log = LoggerFactory.getLogger(TranscriptService.class);
    private final TranscriptRepository transcriptRepository;

    @Autowired
    public TranscriptService(TranscriptRepository transcriptRepository) {
        this.transcriptRepository = transcriptRepository;
    }

    // Method to get the full transcript

    public List<Transcript> getFullTranscriptService() {
        return transcriptRepository.findAll();
    }

    // Method to get result of a specific course using courseId

    public Transcript specificCourseResultService(Integer courseId) {
        return transcriptRepository.findAll()
                .stream()
                .filter(transcript -> transcript.getCourseId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Course Id: " + courseId + " is not present in the database."));
    }

    // Method to update new course in PostgresSQL Database

    public void updateNewCourseService(Transcript transcript) {
        Optional<Transcript> coursePresent = transcriptRepository.findByCourseId(transcript.getCourseId());

        if (coursePresent.isPresent()) {
            log.error("Course Id: " + transcript.getCourseId() + " is already present in the database");
            throw new IllegalStateException("Course Id: " + transcript.getCourseId() + " is already present in the database");
        } else {
            log.info("Course Id: " + transcript.getCourseId() + " is updated in the database");
            transcriptRepository.save(transcript);
        }
    }

    // Method to update Course information in PostgresSQL Database

    @Transactional
    public void updateCourseInformationService(Integer courseId, String courseName, String grade, String term, Integer credit) {
        Transcript transcript = transcriptRepository.findByCourseId(courseId).orElseThrow(() -> new IllegalStateException("Course Id: " + courseId + " is not present in the database"));
        if (courseName != null && !courseName.isEmpty() && !Objects.equals(transcript.getCourseName(), courseName)) {
            log.info("Your course name is successfully updated in the database.");
            transcript.setCourseName(courseName);
        }
        if (grade != null && !grade.isEmpty() && !Objects.equals(transcript.getGrade(), grade)) {
            log.info("Your grade is successfully updated in the database");
            transcript.setGrade(grade);
        }
        if (term != null && !term.isEmpty() && !Objects.equals(transcript.getTerm(), term)) {
            log.info("Your term is successfully updated in the database");
            transcript.setTerm(term);
        }
        if (credit != null && !Objects.equals(transcript.getCredit(), credit)) {
            log.info("Your credit is successfully updated in the database");
            transcript.setCredit(credit);
        }
    }

    // Method to delete course from PostgresSQL Database using courseId

    public void deleteCourseService(Integer courseId) {
        boolean courseIdExists = transcriptRepository.existsById(courseId);
        if (!courseIdExists) {
            log.error("Course Id: " + courseId + " is not present in the database.");
            return;
        }
        log.info("Course Id: " + courseId + " is successfully deleted from the database.");
        transcriptRepository.deleteById(courseId);
    }


}
