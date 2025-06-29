package edu.icet2.service.impl;

import edu.icet2.dto.StudentCreateRequest;
import edu.icet2.dto.StudentDto;
import edu.icet2.dto.StudentUpdateRequest;
import edu.icet2.entity.Student;
import edu.icet2.exception.DuplicateResourceException;
import edu.icet2.exception.ResourceNotFoundException;
import edu.icet2.mapper.StudentMapper;
import edu.icet2.repository.StudentRepository;
import edu.icet2.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of StudentService interface.
 * Provides business logic for student management operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    @Cacheable(value = "students", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<StudentDto> getAllStudents(Pageable pageable) {
        log.debug("Fetching all students with pagination: {}", pageable);
        return studentRepository.findAll(pageable)
                .map(studentMapper::toDto);
    }

    @Override
    @Cacheable(value = "activeStudents")
    public List<StudentDto> getAllActiveStudents() {
        log.debug("Fetching all active students");
        return studentMapper.toDtoList(studentRepository.findAllActiveStudents());
    }

    @Override
    @Cacheable(value = "student", key = "#id")
    public StudentDto getStudentById(Long id) {
        log.debug("Fetching student by ID: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        return studentMapper.toDto(student);
    }

    @Override
    public StudentDto getStudentByEmail(String email) {
        log.debug("Fetching student by email: {}", email);
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with email: " + email));
        return studentMapper.toDto(student);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"students", "activeStudents"}, allEntries = true)
    public StudentDto createStudent(StudentCreateRequest request) {
        log.info("Creating new student with email: {}", request.getEmail());
        
        // Check if student with email already exists
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Student already exists with email: " + request.getEmail());
        }

        Student student = studentMapper.toEntity(request);
        Student savedStudent = studentRepository.save(student);
        
        log.info("Successfully created student with ID: {}", savedStudent.getId());
        return studentMapper.toDto(savedStudent);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"student", "students", "activeStudents"}, allEntries = true)
    public StudentDto updateStudent(Long id, StudentUpdateRequest request) {
        log.info("Updating student with ID: {}", id);
        
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

        // Check if email is being updated and if it already exists
        if (request.getEmail() != null && !request.getEmail().equals(existingStudent.getEmail())) {
            if (studentRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateResourceException("Student already exists with email: " + request.getEmail());
            }
        }

        // Update the entity with non-null fields from the request
        studentMapper.updateEntityFromRequest(request, existingStudent);
        
        Student updatedStudent = studentRepository.save(existingStudent);
        
        log.info("Successfully updated student with ID: {}", id);
        return studentMapper.toDto(updatedStudent);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"student", "students", "activeStudents"}, allEntries = true)
    public void deleteStudent(Long id) {
        log.info("Deleting student with ID: {}", id);
        
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with ID: " + id);
        }

        studentRepository.deleteById(id);
        log.info("Successfully deleted student with ID: {}", id);
    }

    @Override
    public Page<StudentDto> searchStudentsByName(String searchTerm, Pageable pageable) {
        log.debug("Searching students by name: {}", searchTerm);
        return studentRepository.searchByName(searchTerm, pageable)
                .map(studentMapper::toDto);
    }

    @Override
    public Page<StudentDto> getStudentsByStatus(Student.StudentStatus status, Pageable pageable) {
        log.debug("Fetching students by status: {}", status);
        return studentRepository.findByStatus(status, pageable)
                .map(studentMapper::toDto);
    }

    @Override
    @Cacheable(value = "studentStatistics")
    public StudentStatistics getStudentStatistics() {
        log.debug("Calculating student statistics");
        
        long totalStudents = studentRepository.count();
        long activeStudents = studentRepository.countByStatus(Student.StudentStatus.ACTIVE);
        long inactiveStudents = studentRepository.countByStatus(Student.StudentStatus.INACTIVE);
        long graduatedStudents = studentRepository.countByStatus(Student.StudentStatus.GRADUATED);
        long suspendedStudents = studentRepository.countByStatus(Student.StudentStatus.SUSPENDED);

        return new StudentStatistics(
                totalStudents,
                activeStudents,
                inactiveStudents,
                graduatedStudents,
                suspendedStudents
        );
    }
}