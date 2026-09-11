package library;

import library.controller.dto.ReaderDto;
import library.service.ReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import java.util.concurrent.atomic.AtomicLong;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class ReaderControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        when(readerService.readAll()).thenReturn(
                List.of(
                new ReaderDto(0L, "Karel Novák", "karelnovak@seznam.cz", "777531589", LocalDate.of(1987, 3, 12), LocalDate.of(2020, 5, 4)),
                new ReaderDto(1L, "Jan Sviták", "svitakjan@email.cz", "743897123", LocalDate.of(1998, 12, 18), LocalDate.of(2021, 3, 21)),
                new ReaderDto(2L, "Alena Sýkorová", "sykorkaA@seznam.cz", "789561442", LocalDate.of(1975, 6, 1), LocalDate.of(2021, 9, 8))
                )
        );

        when(readerService.readById(anyLong()))
                .thenAnswer(invocation -> {
                    Long id = invocation.getArgument(0);
                    return Stream.of(
                            new ReaderDto(0L, "Karel Novák", "karelnovak@seznam.cz", "777531589", LocalDate.of(1987, 3, 12), LocalDate.of(2020, 5, 4)),
                            new ReaderDto(1L, "Jan Sviták", "svitakjan@email.cz", "743897123", LocalDate.of(1998, 12, 18), LocalDate.of(2021, 3, 21)),
                            new ReaderDto(2L, "Alena Sýkorová", "sykorkaA@seznam.cz", "789561442", LocalDate.of(1975, 6, 1), LocalDate.of(2021, 9, 8))
                    ).filter(reader -> reader.getId().equals(id-1)).findFirst().orElse(null);
                });

        AtomicLong idCounter = new AtomicLong(3);
        when(readerService.create(any())).thenAnswer(invocation -> {
            ReaderDto input = invocation.getArgument(0);
            return new ReaderDto(idCounter.getAndIncrement(), input.getName(), input.getEmail(), input.getPhoneNumber(), input.getBirthdate(), input.getRegistered());
        });

        when(readerService.update(anyLong(), any(ReaderDto.class)))
                .thenAnswer(invocation -> invocation.getArgument(1));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/readers")).andExpect(status().isOk())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$[0].id").value(0L),
                        MockMvcResultMatchers.jsonPath("$[0].name").value("Karel Novák"),
                        MockMvcResultMatchers.jsonPath("$[0].email").value("karelnovak@seznam.cz"),
                        MockMvcResultMatchers.jsonPath("$[0].phoneNumber").value("777531589"),
                        MockMvcResultMatchers.jsonPath("$[0].birthdate").value("1987-03-12"),
                        MockMvcResultMatchers.jsonPath("$[0].registered").value("2020-05-04")
                ).andExpectAll(
                        MockMvcResultMatchers.jsonPath("$[1].id").value(1L),
                        MockMvcResultMatchers.jsonPath("$[1].name").value("Jan Sviták"),
                        MockMvcResultMatchers.jsonPath("$[1].email").value("svitakjan@email.cz"),
                        MockMvcResultMatchers.jsonPath("$[1].phoneNumber").value("743897123"),
                        MockMvcResultMatchers.jsonPath("$[1].birthdate").value("1998-12-18"),
                        MockMvcResultMatchers.jsonPath("$[1].registered").value("2021-03-21")
                ).andExpectAll(
                        MockMvcResultMatchers.jsonPath("$[2].id").value(2L),
                        MockMvcResultMatchers.jsonPath("$[2].name").value("Alena Sýkorová"),
                        MockMvcResultMatchers.jsonPath("$[2].email").value("sykorkaA@seznam.cz"),
                        MockMvcResultMatchers.jsonPath("$[2].phoneNumber").value("789561442"),
                        MockMvcResultMatchers.jsonPath("$[2].birthdate").value("1975-06-01"),
                        MockMvcResultMatchers.jsonPath("$[2].registered").value("2021-09-08")
                );
    }

    @Test
    public void testGetById() throws Exception {
        mockMvc.perform(get("/readers/1" ))
                .andExpect(status().isOk())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.id" ).value(0L),
                        MockMvcResultMatchers.jsonPath("$.name" ).value("Karel Novák" ),
                        MockMvcResultMatchers.jsonPath("$.email" ).value("karelnovak@seznam.cz" ),
                        MockMvcResultMatchers.jsonPath("$.phoneNumber" ).value("777531589" ),
                        MockMvcResultMatchers.jsonPath("$.birthdate" ).value("1987-03-12" ),
                        MockMvcResultMatchers.jsonPath("$.registered" ).value("2020-05-04" )
                );
    }

    @Test
    public void testCreateReader() throws Exception {
        mockMvc.perform(post("/readers")
                        .contentType("application/json")
                        .content("{\"id\":3,\"name\":\"Petra Zelená\",\"email\":\"petulkaZelena@email.cz\",\"phoneNumber\":\"775124356\",\"birthdate\":\"2000-02-15\",\"registered\":\"2022-11-26\"}"))
                .andExpect(status().isCreated())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.id").value(3L),
                        MockMvcResultMatchers.jsonPath("$.name").value("Petra Zelená"),
                        MockMvcResultMatchers.jsonPath("$.email").value("petulkaZelena@email.cz"),
                        MockMvcResultMatchers.jsonPath("$.phoneNumber").value("775124356"),
                        MockMvcResultMatchers.jsonPath("$.birthdate").value("2000-02-15"),
                        MockMvcResultMatchers.jsonPath("$.registered").value("2022-11-26")
                );
    }

    @Test
    public void testUpdateReader() throws Exception {
        mockMvc.perform(put("/readers/1")
                        .contentType("application/json")
                        .content("{\"id\":1,\"name\":\"Karel Novák\",\"email\":\"newemail@domain.com\",\"phoneNumber\":\"777531589\",\"birthdate\":\"1987-03-12\",\"registered\":\"2020-05-04\"}"))
                .andExpect(status().isOk())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.id").value(1L),
                        MockMvcResultMatchers.jsonPath("$.name").value("Karel Novák"),
                        MockMvcResultMatchers.jsonPath("$.email").value("newemail@domain.com"),
                        MockMvcResultMatchers.jsonPath("$.phoneNumber").value("777531589"),
                        MockMvcResultMatchers.jsonPath("$.birthdate").value("1987-03-12"),
                        MockMvcResultMatchers.jsonPath("$.registered").value("2020-05-04")
                );

    }

    @Test
    public void testDeleteReader() throws Exception {
        mockMvc.perform(delete("/readers/1"))
                .andExpect(status().isNoContent());
    }
}
