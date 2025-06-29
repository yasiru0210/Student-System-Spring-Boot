package edu.icet2.service;

import edu.icet2.dto.StudentCreateRequest;
import edu.icet2.dto.StudentDto;
import edu.icet2.dto.StudentUpdateRequest;
import edu.icet2.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for Student operations.
 * Defines the contract for student management business logic.
 */
public interface StudentService {

    /**
     * Retrieve all students with pagination.
     */
    Page<StudentDto> getAllStudents(Pageable pageable);

    /**
     * Retrieve all active students.
     */
    List<StudentDto> getAllActiveStudents();

    /**
     * Retrieve a student by ID.
     */
    StudentDto getStudentById(Long id);

    /**
     * Retrieve a student by email.
     */
    StudentDto getStudentByEmail(String email);

    /**
     * Create a new student.
     */
    StudentDto createStudent(StudentCreateRequest request);

    /**
     * Update an existing student.
     */
    StudentDto updateStudent(Long id, StudentUpdateRequest request);

    /**
     * Delete a student by ID.
     */
    void deleteStudent(Long id);

    /**
     * Search students by name.
     */
    Page<StudentDto> searchStudentsByName(String searchTerm, Pageable pageable);

    /**
     * Get students by status.
     */
    Page<StudentDto> getStudentsByStatus(Student.StudentStatus status, Pageable pageable);

    /**
     * Get student statistics.
     */
    StudentStatistics getStudentStatistics();

    /**
     * Inner class for student statistics.
     */
    record StudentStatistics(
            long totalStudents,
            long activeStudents,
            long inactiveStudents,
            long graduatedStudents,
            long suspendedStudents
    ) {}
}