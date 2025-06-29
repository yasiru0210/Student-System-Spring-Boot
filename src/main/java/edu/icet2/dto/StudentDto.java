package edu.icet2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.icet2.entity.Student;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Student entity.
 * Used for API requests and responses to provide a clean interface
 * and hide internal entity structure.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Student information")
public class StudentDto {

    @Schema(description = "Student ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Schema(description = "Student's first name", example = "John", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Schema(description = "Student's last name", example = "Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Schema(description = "Student's email address", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Size(max = 15, message = "Phone number cannot exceed 15 characters")
    @Schema(description = "Student's phone number", example = "+1234567890")
    private String phoneNumber;

    @Size(max = 200, message = "Address cannot exceed 200 characters")
    @Schema(description = "Student's address", example = "123 Main St, City, State 12345")
    private String address;

    @Schema(description = "Student's current status", example = "ACTIVE")
    private Student.StudentStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "When the student record was created", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "When the student record was last updated", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Schema(description = "Version for optimistic locking", accessMode = Schema.AccessMode.READ_ONLY)
    private Long version;
}