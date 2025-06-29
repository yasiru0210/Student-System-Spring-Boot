package edu.icet2.dto;

import edu.icet2.entity.Student;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for updating an existing student.
 * All fields are optional to support partial updates.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to update an existing student")
public class StudentUpdateRequest {

    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Schema(description = "Student's first name", example = "John")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Schema(description = "Student's last name", example = "Doe")
    private String lastName;

    @Email(message = "Email should be valid")
    @Schema(description = "Student's email address", example = "john.doe@example.com")
    private String email;

    @Size(max = 15, message = "Phone number cannot exceed 15 characters")
    @Schema(description = "Student's phone number", example = "+1234567890")
    private String phoneNumber;

    @Size(max = 200, message = "Address cannot exceed 200 characters")
    @Schema(description = "Student's address", example = "123 Main St, City, State 12345")
    private String address;

    @Schema(description = "Student's status", example = "ACTIVE")
    private Student.StudentStatus status;

    @Schema(description = "Version for optimistic locking", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long version;
}