package edu.icet2.mapper;

import edu.icet2.dto.StudentCreateRequest;
import edu.icet2.dto.StudentDto;
import edu.icet2.dto.StudentUpdateRequest;
import edu.icet2.entity.Student;
import org.mapstruct.*;

import java.util.List;

/**
 * MapStruct mapper for converting between Student entity and DTOs.
 * Provides type-safe and efficient mapping between different representations.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {

    /**
     * Convert Student entity to StudentDto.
     */
    StudentDto toDto(Student student);

    /**
     * Convert list of Student entities to list of StudentDtos.
     */
    List<StudentDto> toDtoList(List<Student> students);

    /**
     * Convert StudentCreateRequest to Student entity.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    Student toEntity(StudentCreateRequest request);

    /**
     * Update existing Student entity with data from StudentUpdateRequest.
     * Only non-null fields from the request will be mapped.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(StudentUpdateRequest request, @MappingTarget Student student);
}