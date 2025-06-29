package edu.icet2.repository;

import edu.icet2.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Student entity operations.
 * Extends JpaRepository to provide standard CRUD operations and custom queries.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Find student by email address.
     */
    Optional<Student> findByEmail(String email);

    /**
     * Check if a student exists with the given email.
     */
    boolean existsByEmail(String email);

    /**
     * Find students by status.
     */
    List<Student> findByStatus(Student.StudentStatus status);

    /**
     * Find students by status with pagination.
     */
    Page<Student> findByStatus(Student.StudentStatus status, Pageable pageable);

    /**
     * Search students by name (first name or last name) containing the search term.
     */
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Student> searchByName(@Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Find students by first name and last name.
     */
    List<Student> findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Count students by status.
     */
    long countByStatus(Student.StudentStatus status);

    /**
     * Find all active students.
     */
    @Query("SELECT s FROM Student s WHERE s.status = 'ACTIVE' ORDER BY s.lastName, s.firstName")
    List<Student> findAllActiveStudents();
}