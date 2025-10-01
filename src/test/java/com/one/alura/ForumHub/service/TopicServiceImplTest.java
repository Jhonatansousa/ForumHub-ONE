package com.one.alura.ForumHub.service;


import com.one.alura.ForumHub.dto.topic.TopicRequestDTO;
import com.one.alura.ForumHub.dto.topic.TopicResponseDTO;
import com.one.alura.ForumHub.dto.topic.TopicWithAnswerResponseDTO;
import com.one.alura.ForumHub.entity.Course;
import com.one.alura.ForumHub.entity.Topic;
import com.one.alura.ForumHub.entity.User;
import com.one.alura.ForumHub.exception.DuplicatedContentException;
import com.one.alura.ForumHub.exception.ResourceNotFoundException;
import com.one.alura.ForumHub.mapper.TopicMapper;
import com.one.alura.ForumHub.repository.CourseRepository;
import com.one.alura.ForumHub.repository.TopicRepository;
import com.one.alura.ForumHub.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TopicServiceImplTest {

    @Mock
    private TopicRepository topicRepo;
    @Mock
    private  UserRepository userRepo;
    @Mock
    private  CourseRepository courseRepo;
    @Mock
    private  TopicMapper topicMapper;

    @InjectMocks
    private TopicServiceImpl topicService;




    @Test
    @DisplayName("Should create a new Topic successfully when the data is valid")
    void createNewTopicWithValidData_ShouldReturnTopicWithAnswerResponseDTO() {
        //arrange (arrumar)
        // 1. criamos os dados de entrada
        var authorId = UUID.randomUUID();
        var requestDTO = new TopicRequestDTO("Titulo do tópico", "Mensagem do tópico", authorId, "Java Best Practices");

        //2. cria-se instancias da entity que será usada
        var user = new User();
        user.setId(authorId);
        var course = new Course();
        var topic = new Topic();
        var expectedResponseDTO = new TopicWithAnswerResponseDTO(UUID.randomUUID(), requestDTO.title(), requestDTO.message(), null, null, null);


        //3. definindo comportamentos ds mocks
        // QUANDo topic.repo.existsByTitleAndMessage for chamado Com esses params, deve ser retornado false
        when(topicRepo.existsByTitleAndMessage(requestDTO.title(),requestDTO.message())).thenReturn(false);
        //quando userRepo.findById for chamado com o ID do autor, deve ser retornado um optional com o usuário "encontrado"
        when(userRepo.findById(authorId)).thenReturn(Optional.of(user));
        //quando courseRepo.findByNameIgnoreCase for chamado, deve retornar o Course
        when(courseRepo.findByNameIgnoreCase(requestDTO.courseName())).thenReturn(course);
        //quando o mapper for chamado para converter o dto em uma entidade, deve retornar o tópic
        when(topicMapper.toEntity(requestDTO, user, course)).thenReturn(topic);
        //quando o repositorio salvar o tópico, deve retornar o tópico salvo
        when(topicRepo.save(topic)).thenReturn(topic);
        //quando o mapper for chamado para converter a entidade em um dto de resposta, deve retornar o DTO esperado
        when(topicMapper.toResponseWithAnswerDTO(topic)).thenReturn(expectedResponseDTO);

        //======= ACT - agir
        //chamamos o metodo que estamos testando
        TopicWithAnswerResponseDTO actualResponse = topicService.createNewTopic(requestDTO);

        //====== ASSERT - afirmar
        //verificando se os resultados é o esperado
        assertNotNull(actualResponse); //o resultado não pode ser nulo
        assertEquals(expectedResponseDTO.title(), actualResponse.title()); //o título deve ser o mesmo
        assertEquals(expectedResponseDTO.message(), actualResponse.message());// a mensagem também deve ser igual

        //====== boas práticas
        //verificar se os métodos importantes foram chamados
        //verificar se o topicRepo.save() foi chamado exatamente 1 vez com o objeto "topic"
        verify(topicRepo, times(1)).save(topic);
        //verificar se userRepo.findById() foi chamado 1 vez
        verify(userRepo, times(1)).findById(authorId);

    }


    @Test
    @DisplayName("Should throw (DuplicatedContentException) when creating a topic with an existing title and message")
    void createNewTopicWithDuplicateTitle_ShouldThrowDuplicatedContentException() {
        //arrange (arrumar)
        var requestDTO = new TopicRequestDTO("titulo repetido", "mensagem repetida", UUID.randomUUID(), "Java Best Practices");
        //a unica coisa que é preciso mockar é a verificação de duplicidade
        //dessa vez retornando true quando verificar se o tópico ja existe
        when(topicRepo.existsByTitleAndMessage(requestDTO.title(),requestDTO.message())).thenReturn(true);

        //act (agir e afirmar)
        //usamos assertThroews para verificar se o metodo lança a exceção esperada
        //primeiro argumento -> classe da exceção
        //sgundo é um lambda com a chamada do metodo que deve lançar a exceção.
        assertThrows(DuplicatedContentException.class, () -> topicService.createNewTopic(requestDTO));

        //boa prática: veiricar que, se a exceção foi lançada, o metodo salvar NUNcA foi chamado
        verify(topicRepo, never()).save(any());
    }

    @Test
    @DisplayName("Shoud throw ResourceNotFoundException when creating a topic with author that doesn't exist")
    void createNewTopicWithAuthorThatDoesNotExist_ShouldThrowResourceNotFoundException() {
        var authorId = UUID.randomUUID();
        var requestDTO = new TopicRequestDTO("titulo qualquer", "mensagem qualquer", authorId, "Java Best Practices");

        //mock da verificação de dulicidade que retorna false, para passar dessa etapa ok
        when(topicRepo.existsByTitleAndMessage(requestDTO.title(),requestDTO.message())).thenReturn(false);
        //mock da busca do usuário para retornar um Optional vazio, simulando que não foi encontrado
        when(userRepo.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> topicService.createNewTopic(requestDTO));

        verify(topicRepo, never()).save(any());

    }

    @Test
    @DisplayName("should throw ResourceNotFoundException when creating a topic with course not found")
    void createNewTopicWithCourseNameNotFound_ShouldThrowResourceNotFoundException() {
        var authorId = UUID.randomUUID();
        var requestDTO = new TopicRequestDTO("titulo qualquer", "mensagem qualquer", authorId, "Java Best Practices");

        when(topicRepo.existsByTitleAndMessage(requestDTO.title(),requestDTO.message())).thenReturn(false);
        when(userRepo.findById(authorId)).thenReturn(Optional.of(new User()));
        when(courseRepo.findByNameIgnoreCase(requestDTO.courseName())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> topicService.createNewTopic(requestDTO));
        verify(topicRepo, never()).save(any());
    }


    @Test
    @DisplayName("should return a page of topics with answers successfully")
    void getAllTopicsWithAnswers_shouldReturnPageOfTopicWithAnswerResponseDTO() {
        var pageable = Pageable.ofSize(2);
        var topic1 = new Topic();
        var topic2 = new Topic();
        var topicsPage = new PageImpl<>(List.of(topic1, topic2));

        var responseDTO1 = new TopicWithAnswerResponseDTO(UUID.randomUUID(), "title 1", "Message 1", null, null, null);
        var responseDTO2 = new TopicWithAnswerResponseDTO(UUID.randomUUID(), "title 2", "Message 2", null, null, null);

        when(topicRepo.findAllWithAnswers(pageable)).thenReturn(topicsPage);
        when(topicMapper.toResponseWithAnswerDTO(topic1)).thenReturn(responseDTO1);
        when(topicMapper.toResponseWithAnswerDTO(topic2)).thenReturn(responseDTO2);

        //act
        Page<TopicWithAnswerResponseDTO> result = topicService.getAllTopicsWithAnswers(pageable);

        //assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("title 1", result.getContent().get(0).title());
        assertEquals("title 2", result.getContent().get(1).title());

        //verificar e o repo e mapper foram chamados corretamente
        verify(topicRepo, times(1)).findAllWithAnswers(pageable);
        verify(topicMapper, times(1)).toResponseWithAnswerDTO(topic1);
        verify(topicMapper, times(1)).toResponseWithAnswerDTO(topic2);
    }


    @Test
    @DisplayName("should return a page with topics successfully")
    void getAllTopicsWithAnswers_shouldReturnPageOfTopicResponseDTO() {
        var pageable = Pageable.ofSize(2);
        var topic1 = new Topic();
        var topic2 = new Topic();

        var topicsPage = new PageImpl<>(List.of(topic1, topic2));
        var responseDTO1 = new TopicResponseDTO(UUID.randomUUID(), "title 1", "Message 1", null, null);
        var responseDTO2 = new TopicResponseDTO(UUID.randomUUID(), "title 2", "Message 2", null, null);

        when(topicRepo.findAll(pageable)).thenReturn(topicsPage);
        when(topicMapper.toResponseDTO(topic1)).thenReturn(responseDTO1);
        when(topicMapper.toResponseDTO(topic2)).thenReturn(responseDTO2);

        Page<TopicResponseDTO> result = topicService.getAllTopics(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("title 1", result.getContent().get(0).title());
        assertEquals("title 2", result.getContent().get(1).title());

        verify(topicRepo, times(1)).findAll(pageable);
        verify(topicMapper, times(1)).toResponseDTO(topic1);
        verify(topicMapper, times(1)).toResponseDTO(topic2);
    }



}
