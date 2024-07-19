package edu.wilmu.my_transcript.d_repository;

import edu.wilmu.my_transcript.a_entity.Transcript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, Integer> {

    Optional<Transcript> findByCourseId(Integer courseId);


}
