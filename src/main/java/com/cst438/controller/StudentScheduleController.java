package com.cst438.controller;

<<<<<<< HEAD
import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.Section;
import com.cst438.domain.SectionRepository;
import com.cst438.domain.User;
import com.cst438.domain.UserRepository;
import com.cst438.dto.EnrollmentDTO;
import com.cst438.service.GradebookServiceProxy;
=======
import com.cst438.domain.*;
import com.cst438.dto.EnrollmentDTO;
import com.cst438.dto.SectionDTO;
import com.cst438.service.GradebookServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
>>>>>>> 30603bb011ee7831607b8ab6995991b0b961614f

@RestController
public class StudentScheduleController {

    private final EnrollmentRepository enrollmentRepository;
    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;
    private final GradebookServiceProxy gradebook;

    public StudentScheduleController(
            EnrollmentRepository enrollmentRepository,
            SectionRepository sectionRepository,
            UserRepository userRepository,
            GradebookServiceProxy gradebook
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.sectionRepository = sectionRepository;
        this.userRepository = userRepository;
        this.gradebook = gradebook;
    }

<<<<<<< HEAD
=======

>>>>>>> 30603bb011ee7831607b8ab6995991b0b961614f
    @PostMapping("/enrollments/sections/{sectionNo}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_STUDENT')")
    public EnrollmentDTO addCourse(
            @PathVariable int sectionNo,
            Principal principal ) throws Exception  {

<<<<<<< HEAD
        User student = userRepository.findByEmail(principal.getName());
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found");
        }

        Section section = sectionRepository.findById(sectionNo).orElse(null);
        if (section == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "section not found");
        }

        // check if already enrolled (manual loop)
        List<Enrollment> enrollments =
                enrollmentRepository.findEnrollmentsByStudentIdOrderByTermId(student.getId());

        for (Enrollment e : enrollments) {
            if (e.getSection().getSectionNo() == sectionNo) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "already enrolled");
            }
        }

        // date check
        Date now = new Date();
        if (now.before(section.getTerm().getAddDate()) ||
            now.after(section.getTerm().getAddDeadline())) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not in add period");
        }

        // create enrollment
        Enrollment e = new Enrollment();
        e.setStudent(student);
        e.setSection(section);

        enrollmentRepository.save(e);

        gradebook.sendMessage("addEnrollment", e);

        return convertToDTO(e);
=======
        // create and save an EnrollmentEntity
        //  relate enrollment to the student's User entity and to the Section entity
        //  check that student is not already enrolled in the section
        //  check that the current date is not before addDate, not after addDeadline
		//  of the section's term.  Return an EnrollmentDTO with the id of the 
		//  Enrollment and other fields.
		
		return null;
>>>>>>> 30603bb011ee7831607b8ab6995991b0b961614f
    }

    // student drops a course
    @DeleteMapping("/enrollments/{enrollmentId}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_STUDENT')")
    public void dropCourse(@PathVariable("enrollmentId") int enrollmentId, Principal principal) throws Exception {

<<<<<<< HEAD
        User student = userRepository.findByEmail(principal.getName());
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "student not found");
        }

        Enrollment e = enrollmentRepository.findById(enrollmentId).orElse(null);
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "enrollment not found");
        }

        // ownership check
        if (e.getStudent().getId() != student.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not your enrollment");
        }

        // deadline check
        Date now = new Date();
        if (now.after(e.getSection().getTerm().getDropDeadline())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "drop deadline passed");
        }

        enrollmentRepository.delete(e);

        gradebook.sendMessage("dropEnrollment", e);
    }

    // helper method (same as StudentController)
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
    }
}
=======
        // check that enrollment belongs to the logged in student
		// and that today is not after the dropDeadLine for the term.

    }

}
>>>>>>> 30603bb011ee7831607b8ab6995991b0b961614f
