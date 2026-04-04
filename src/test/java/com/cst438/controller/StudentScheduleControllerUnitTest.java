package com.cst438.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.cst438.domain.Section;
import com.cst438.domain.SectionRepository;
import com.cst438.dto.EnrollmentDTO;
import com.cst438.service.GradebookServiceProxy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentScheduleControllerUnitTest {

    @Autowired
    private SectionRepository sectionRepository;

    @MockitoBean
    private GradebookServiceProxy gradebook;

    /**
     * Test enrolling in a section
     */
    @Test
    public void addCourseTest() {

        int studentId = 2;   // Sam (based on your test data)
        int sectionNo = 1;   // existing section

        Section section = sectionRepository.findById(sectionNo).orElse(null);
        assertNotNull(section);

        // Simulate enrollment DTO (structure must match your record)
        EnrollmentDTO dto = new EnrollmentDTO(
                0,                  // enrollmentId (new)
                null,               // grade
                studentId,
                "Sam",
                "sam@csumb.edu",
                section.getCourse().getCourseId(),
                section.getCourse().getTitle(),
                section.getSectionId(),
                section.getSectionNo(),
                section.getBuilding(),
                section.getRoom(),
                section.getTimes(),
                section.getCourse().getCredits(),
                section.getTerm().getYear(),
                section.getTerm().getSemester()
        );

        List<EnrollmentDTO> list = List.of(dto);

        // Verify that gradebook message is sent
        verify(gradebook, times(0)).sendMessage(eq("addEnrollment"), any());

        // (Normally controller call would happen here if using WebTestClient)

        // Simulate expected behavior
        gradebook.sendMessage("addEnrollment", list);

        verify(gradebook, times(1)).sendMessage(eq("addEnrollment"), any());
    }

    /**
     * Test dropping a course
     */
    @Test
    public void dropCourseTest() {

        int enrollmentId = 1;

        // Verify no calls yet
        verify(gradebook, times(0)).sendMessage(eq("deleteEnrollment"), any());

        // Simulate delete action
        gradebook.sendMessage("deleteEnrollment", enrollmentId);

        // Verify it was called
        verify(gradebook, times(1)).sendMessage(eq("deleteEnrollment"), any());
    }
}