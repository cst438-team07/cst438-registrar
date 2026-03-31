package com.cst438.controller;

<<<<<<< HEAD
=======
import com.cst438.domain.*;
import com.cst438.dto.EnrollmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

>>>>>>> 30603bb011ee7831607b8ab6995991b0b961614f
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.User;
import com.cst438.domain.UserRepository;
import com.cst438.dto.EnrollmentDTO;

@RestController
public class StudentController {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;

    public StudentController(
            EnrollmentRepository enrollmentRepository,
            UserRepository userRepository
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
    }

    // retrieve schedule for student for a term
    @GetMapping("/enrollments")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_STUDENT')")
    public List<EnrollmentDTO> getSchedule(
            @RequestParam("year") int year,
            @RequestParam("semester") String semester,
            Principal principal) {

        // get logged-in student
        User student = userRepository.findByEmail(principal.getName());
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found");
        }

        // get all enrollments for student
        List<Enrollment> enrollments =
                enrollmentRepository.findEnrollmentsByStudentIdOrderByTermId(student.getId());

        // filter by term and convert to DTO
        List<EnrollmentDTO> result = new ArrayList<>();

        for (Enrollment e : enrollments) {
            if (e.getSection().getTerm().getYear() == year &&
                e.getSection().getTerm().getSemester().equals(semester)) {

                result.add(convertToDTO(e));
            }
        }

        return result;
    }

    // return transcript for student
=======
@RestController
public class StudentController {

   private final EnrollmentRepository enrollmentRepository;
   private final UserRepository userRepository;

   public StudentController(
           EnrollmentRepository enrollmentRepository,
           UserRepository userRepository
   ) {
       this.enrollmentRepository = enrollmentRepository;
       this.userRepository = userRepository;
   }

   // retrieve schedule for student for a term
   @GetMapping("/enrollments")
   @PreAuthorize("hasAuthority('SCOPE_ROLE_STUDENT')")
   public List<EnrollmentDTO> getSchedule(
           @RequestParam("year") int year,
           @RequestParam("semester") String semester,
           Principal principal) {
			   
		// use the EnrollmentController findByYearAndSemsterOrderByCourseId
		// method to retrive the enrollments given the year, semester and id 
		// of the logged in student.
		// Return a list of EnrollmentDTO.

      return null;
   }

   // return transcript for student
>>>>>>> 30603bb011ee7831607b8ab6995991b0b961614f
    @GetMapping("/transcripts")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_STUDENT')")
    public List<EnrollmentDTO> getTranscript(Principal principal) {

<<<<<<< HEAD
        // get logged-in student
        User student = userRepository.findByEmail(principal.getName());
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found");
        }

        // get all enrollments
        List<Enrollment> enrollments =
                enrollmentRepository.findEnrollmentsByStudentIdOrderByTermId(student.getId());

        // convert all to DTO
        List<EnrollmentDTO> result = new ArrayList<>();

        for (Enrollment e : enrollments) {
            result.add(convertToDTO(e));
        }

        return result;
    }

    // helper method to convert Enrollment -> EnrollmentDTO
    private EnrollmentDTO convertToDTO(Enrollment e) {
        return new EnrollmentDTO(
                e.getEnrollmentId(),
                e.getGrade(),
                e.getStudent().getId(),
                e.getStudent().getName(),
                e.getStudent().getEmail(),
                e.getSection().getCourse().getCourseId(),
                e.getSection().getCourse().getTitle(),
                e.getSection().getSectionId(),
                e.getSection().getSectionNo(),
                e.getSection().getBuilding(),
                e.getSection().getRoom(),
                e.getSection().getTimes(),
                e.getSection().getCourse().getCredits(),
                e.getSection().getTerm().getYear(),
                e.getSection().getTerm().getSemester()
        );
=======
        // use the EnrollmentController findEnrollmentsByStudentIdOrderByTermId
		// method to retrive the enrollments given the id 
		// of the logged in student.
		// Return a list of EnrollmentDTO.
		
        return null;
>>>>>>> 30603bb011ee7831607b8ab6995991b0b961614f
    }
}