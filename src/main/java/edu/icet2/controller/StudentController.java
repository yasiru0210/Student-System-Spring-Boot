package edu.icet2.controller;

import edu.icet2.dto.*;
import edu.icet2.entity.Student;
import edu.icet2.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Student management operations.
 * Provides comprehensive CRUD operations with proper error handling,
 * validation, and API documentation.
 */
@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Student Management", description = "APIs for managing student records")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    @Operation(summary = "Get all students", description = "Retrieve all students with pagination and sorting")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved students",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    public ResponseEntity<ApiResponse<Page<StudentDto>>> getAllStudents(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field", example = "lastName")
            @RequestParam(defaultValue = "lastName") String sortBy,
            @Parameter(description = "Sort direction", example = "asc")
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        log.info("Fetching all students - page: {}, size: {}, sortBy: {}, sortDir: {}", 
                page, size, sortBy, sortDir);

        Sort sort = sortDir.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<StudentDto> students = studentService.getAllStudents(pageable);
        
        return ResponseEntity.ok(ApiResponse.success(students, "Students retrieved successfully"));
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active students", description = "Retrieve all students with ACTIVE status")
    public ResponseEntity<ApiResponse<List<StudentDto>>> getAllActiveStudents() {
        log.info("Fetching all active students");
        List<StudentDto> students = studentService.getAllActiveStudents();
        return ResponseEntity.ok(ApiResponse.success(students, "Active students retrieved successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID", description = "Retrieve a specific student by their ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Student found",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", 
            description = "Student not found"
        )
    })
    public ResponseEntity<ApiResponse<StudentDto>> getStudentById(
            @Parameter(description = "Student ID", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("Fetching student by ID: {}", id);
        StudentDto student = studentService.getStudentById(id);
        return ResponseEntity.ok(ApiResponse.success(student, "Student retrieved successfully"));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get student by email", description = "Retrieve a specific student by their email address")
    public ResponseEntity<ApiResponse<StudentDto>> getStudentByEmail(
            @Parameter(description = "Student email", required = true, example = "john.doe@example.com")
            @PathVariable String email) {
        
        log.info("Fetching student by email: {}", email);
        StudentDto student = studentService.getStudentByEmail(email);
        return ResponseEntity.ok(ApiResponse.success(student, "Student retrieved successfully"));
    }

    @PostMapping
    @Operation(summary = "Create new student", description = "Create a new student record")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201", 
            description = "Student created successfully",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400", 
            description = "Invalid input data"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "409", 
            description = "Student with email already exists"
        )
    })
    public ResponseEntity<ApiResponse<StudentDto>> createStudent(
            @Parameter(description = "Student creation request", required = true)
            @Valid @RequestBody StudentCreateRequest request) {
        
        log.info("Creating new student with email: {}", request.getEmail());
        StudentDto createdStudent = studentService.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(createdStudent, "Student created successfully"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student", description = "Update an existing student record")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Student updated successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", 
            description = "Student not found"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "409", 
            description = "Email already exists for another student"
        )
    })
    public ResponseEntity<ApiResponse<StudentDto>> updateStudent(
            @Parameter(description = "Student ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Student update request", required = true)
            @Valid @RequestBody StudentUpdateRequest request) {
        
        log.info("Updating student with ID: {}", id);
        StudentDto updatedStudent = studentService.updateStudent(id, request);
        return ResponseEntity.ok(ApiResponse.success(updatedStudent, "Student updated successfully"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student", description = "Delete a student record")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Student deleted successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", 
            description = "Student not found"
        )
    })
    public ResponseEntity<ApiResponse<Void>> deleteStudent(
            @Parameter(description = "Student ID", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("Deleting student with ID: {}", id);
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Student deleted successfully"));
    }

    @GetMapping("/search")
    @Operation(summary = "Search students by name", description = "Search students by first name or last name")
    public ResponseEntity<ApiResponse<Page<StudentDto>>> searchStudents(
            @Parameter(description = "Search term", required = true, example = "John")
            @RequestParam String q,
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        
        log.info("Searching students with term: {}", q);
        Pageable pageable = PageRequest.of(page, size, Sort.by("lastName", "firstName"));
        Page<StudentDto> students = studentService.searchStudentsByName(q, pageable);
        return ResponseEntity.ok(ApiResponse.success(students, "Search completed successfully"));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get students by status", description = "Retrieve students filtered by their status")
    public ResponseEntity<ApiResponse<Page<StudentDto>>> getStudentsByStatus(
            @Parameter(description = "Student status", required = true, example = "ACTIVE")
            @PathVariable Student.StudentStatus status,
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        
        log.info("Fetching students by status: {}", status);
        Pageable pageable = PageRequest.of(page, size, Sort.by("lastName", "firstName"));
        Page<StudentDto> students = studentService.getStudentsByStatus(status, pageable);
        return ResponseEntity.ok(ApiResponse.success(students, "Students retrieved successfully"));
    }

    @GetMapping("/statistics")
    @Operation(summary = "Get student statistics", description = "Retrieve statistical information about students")
    public ResponseEntity<ApiResponse<StudentService.StudentStatistics>> getStudentStatistics() {
        log.info("Fetching student statistics");
        StudentService.StudentStatistics statistics = studentService.getStudentStatistics();
        return ResponseEntity.ok(ApiResponse.success(statistics, "Statistics retrieved successfully"));
    }
}