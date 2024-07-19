package edu.wilmu.my_transcript.b_controller;

import edu.wilmu.my_transcript.a_entity.Transcript;
import edu.wilmu.my_transcript.c_service.TranscriptService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("api/v2/my_transcript")
@RestController
public class TranscriptController {

    private final TranscriptService transcriptService;

    @Autowired
    public TranscriptController(TranscriptService transcriptService) {
        this.transcriptService = transcriptService;
    }

    // GetMapping for Full Transcript

    @GetMapping
    public List<Transcript> getFullTranscriptController() {
        return transcriptService.getFullTranscriptService();
    }

    // GetMapping for Transcript Homepage

    @GetMapping("/home")
    public String transcriptHomepage() {
        return "This is not an official transcript.";
    }

    // GetMapping to search results of a specific course by courseId

    @GetMapping(path = "{courseId}")
    Transcript specificCourseResultController(@PathVariable("courseId") Integer courseId) {
        return transcriptService.specificCourseResultService(courseId);
    }

    // PostMapping to update new courses in the PostgresSQL Database

    @PostMapping()
    public void updateNewCourseController(@Valid @RequestBody Transcript transcript) {
        transcriptService.updateNewCourseService(transcript);
    }

    // PutMapping to update Course Information in the PostgresSQL Database

    @PutMapping(path = "{courseId}")
    public void updateCourseInformationController(
            @PathVariable("courseId") Integer courseId,
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) String term,
            @RequestParam(required = false) Integer credit) {
        transcriptService.updateCourseInformationService(courseId, courseName, grade, term, credit);

    }

    // DeleteMapping to delete course from the PostgresSQL Database using courseId

    @DeleteMapping(path = "{courseId}")
    public void deleteCourseController(
            @PathVariable("courseId") Integer courseId) {
        transcriptService.deleteCourseService(courseId);
    }


    // Reduce lines of error message and shows exactly what the error is (Postman)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
