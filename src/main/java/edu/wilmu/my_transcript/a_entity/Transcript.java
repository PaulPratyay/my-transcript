package edu.wilmu.my_transcript.a_entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Transcript {
    public static void main(String[] args) {
        System.out.println("\"Hare Krishna Hare Krishna, Krishna Krishna Hare Hare, Hare Rama Hare Rama, Rama Rama Hare Hare || Om Nama Bhagabatey Bashudevaya || Om Ganeshaya Nama\"");
    }

    @Id
    @Column(name = "courseId", nullable = false)
    private Integer courseId;

    @Valid
    @NotNull(message = "Course Name is mandatory")
    @NotBlank(message = "Course Name is mandatory")
    @Size(max = 50)
    private String courseName;

    @Valid
    @NotNull(message = "Grade is mandatory")
    @NotBlank(message = "Grade is mandatory")
    @Size(max = 20)
    private String grade;

    @Valid
    @NotNull(message = "Credit is mandatory")
    @Min(value = 3, message = "Credit must be exactly 3")
    @Max(value = 3, message = "Credit must be exactly 3")
    private Integer credit;

    @Valid
    @NotNull(message = "Term is mandatory")
    @NotBlank(message = "Term is mandatory")
    @Size(max = 25)
    private String term;

    // Constructor

    public Transcript(Integer courseId, String courseName, String grade, Integer credit, String term) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.grade = grade;
        this.credit = credit;
        this.term = term;
    }

    // Default Constructor

    public Transcript() {

    }
}
