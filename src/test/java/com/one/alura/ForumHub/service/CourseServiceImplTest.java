package com.one.alura.ForumHub.service;


import com.one.alura.ForumHub.dto.CourseRequestDTO;
import com.one.alura.ForumHub.entity.Course;
import com.one.alura.ForumHub.exception.DuplicatedContentException;
import com.one.alura.ForumHub.exception.ResourceNotFoundException;
import com.one.alura.ForumHub.mapper.CourseMapper;
import com.one.alura.ForumHub.repository.CourseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {


    @Mock
    private CourseRepository courseRepo;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    @DisplayName("should create a new course successfully with a valid data")
    void createCourse_shouldCallSaveOneTime() {
        var requestDTO = new CourseRequestDTO("Java", "backend");
        var course = new Course();

        when(courseRepo.existsByNameIgnoreCase(requestDTO.name())).thenReturn(false);
        when(courseMapper.toEntity(requestDTO)).thenReturn(course);

        courseService.createCourse(requestDTO);

        verify(courseRepo, times(1)).save(course);
        verify(courseMapper, times(1)).toEntity(requestDTO);
    }


    @Test
    @DisplayName("should throw DuplicatedContentException when creating a new course with duplicated name")
    void createCourse_withDuplicatedName_shouldThrowDuplicatedContentException() {
        var requestDTO = new CourseRequestDTO("Java", "backend");
        when(courseRepo.existsByNameIgnoreCase(requestDTO.name())).thenReturn(true);

        assertThrows(DuplicatedContentException.class,  () -> courseService.createCourse(requestDTO));

        verify(courseRepo, times(1)).existsByNameIgnoreCase(requestDTO.name());
        verify(courseRepo, never()).save(any());
    }


    @Test
    @DisplayName("Should update course successfuly with a valid data")
    void updateCourse_shouldCallSaveOneTime() {

        var courseId = UUID.randomUUID();
        var requestDTO = new CourseRequestDTO("Spring", "backend");

        //simulando que o curso existe
        var existingCourse = new Course();
        existingCourse.setId(courseId);
        existingCourse.setName("Java");
        existingCourse.setCategory("backend");

        //curso atualizado
        var updatedCourse = new Course();
        updatedCourse.setName(requestDTO.name());
        updatedCourse.setCategory(requestDTO.category());

        //chamo o metodo findById que retorna opcional do curso existente (sem ser o atualizado)
        when(courseRepo.findById(courseId)).thenReturn(Optional.of(existingCourse));
        //aqui sim no metodo do mapper deve retornar o objeto atualizado
        when(courseMapper.toEntity(requestDTO)).thenReturn(updatedCourse);

        courseService.updateCourse(requestDTO,  courseId);

        //verifico se o metodo findById foi chamado uma vez usando courseId como parâmetro
        verify(courseRepo, times(1)).findById(courseId);

        //verifica se o mapper foi chamado uma vez passando o requestDTO como parametro
        verify(courseMapper, times(1)).toEntity(requestDTO);

        //verifico se o save foi chamado 1 vez passando o curso atualizado;
        verify(courseRepo, times(1)).save(updatedCourse);

    }

    @Test
    @DisplayName("should throw ResourceNotFoundException when course doesn't exist")
    void updateCourse_shouldThrowResourceNotFoundException() {
        var courseId =  UUID.randomUUID();
        var requestDTO = new CourseRequestDTO("Spring", "backend");
        when(courseRepo.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.updateCourse(requestDTO, courseId));

        verify(courseRepo, times(1)).findById(courseId);
        verify(courseRepo, never()).save(any());
        verify(courseMapper, never()).toEntity(any());
    }

    @Test
    @DisplayName("should throw DuplicatedContentException")
    void updateCourse_withDuplicatedName_shouldThrowDuplicatedContentException() {
        var courseId = UUID.randomUUID();
        var requestDTO = new CourseRequestDTO("Spring", "backend");

        var existingCourse = new Course();
        existingCourse.setId(courseId);
        existingCourse.setName("Spring");
        existingCourse.setCategory("backend");


        when(courseRepo.findById(courseId)).thenReturn(Optional.of(existingCourse));

        assertThrows(DuplicatedContentException.class,  () -> courseService.updateCourse(requestDTO,  courseId));

        verify(courseRepo, times(1)).findById(courseId);
        verify(courseRepo, never()).save(any());

    }


}
