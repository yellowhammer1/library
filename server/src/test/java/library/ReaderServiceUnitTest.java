package library;

import library.controller.dto.ReaderDto;
import library.domain.Reader;
import library.repository.LoanRepository;
import library.repository.ReaderRepository;
import library.service.ReaderService;
import library.service.mapper.ReaderMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReaderServiceUnitTest {
    @Mock
    private ReaderRepository readerRepository;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private ReaderMapper readerMapper;

    @InjectMocks
    private ReaderService readerService;

    private Reader reader1;
    private Reader reader2;
    private Reader reader3;
    private ReaderDto readerDto1;
    private ReaderDto readerDto2;
    private ReaderDto readerDto3;

    @BeforeEach
    void setUp() {
        reader1 = new Reader(0L, "Karel Novák", "karelnovak@seznam.cz", "777531589", LocalDate.of(1987, 3, 12), LocalDate.of(2020, 5, 4));
        reader2 = new Reader(1L, "Jan Sviták", "svitakjan@email.cz", "743897123", LocalDate.of(1998, 12, 18), LocalDate.of(2021, 3, 21));
        reader3 = new Reader(2L, "Alena Sýkorová", "sykorkaA@seznam.cz", "789561442", LocalDate.of(1975, 6, 1), LocalDate.of(2021, 9, 8));
        readerDto1 = new ReaderDto(0L, "Karel Novák", "karelnovak@seznam.cz", "777531589", LocalDate.of(1987, 3, 12), LocalDate.of(2020, 5, 4));
        readerDto2 = new ReaderDto(1L, "Jan Sviták", "svitakjan@email.cz", "743897123", LocalDate.of(1998, 12, 18), LocalDate.of(2021, 3, 21));
        readerDto3 = new ReaderDto(2L, "Alena Sýkorová", "sykorkaA@seznam.cz", "789561442", LocalDate.of(1975, 6, 1), LocalDate.of(2021, 9, 8));
    }

    @Test
    void create_ShouldSaveAndReturnReaderDto() {
        when(readerMapper.convertToEntity(any())).thenReturn(reader1);
        when(readerRepository.save(any())).thenReturn(reader1);
        when(readerMapper.convertToDto(any())).thenReturn(readerDto1);

        ReaderDto result = readerService.create(readerDto1);

        assertNotNull(result);
        assertEquals(readerDto1.getId(), result.getId());
        assertEquals(readerDto1.getName(), result.getName());
        assertEquals(readerDto1.getEmail(), result.getEmail());
        assertEquals(readerDto1.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(readerDto1.getBirthdate(), result.getBirthdate());
        assertEquals(readerDto1.getRegistered(), result.getRegistered());

        verify(readerRepository).save(reader1);
    }

    @Test
    void readById_ShouldReturnReaderDto_WhenReaderExists() {
        when(readerRepository.findById(anyLong())).thenReturn(Optional.of(reader1));
        when(readerMapper.convertToDto(any())).thenReturn(readerDto1);

        ReaderDto result = readerService.readById(0L);

        assertNotNull(result);
        assertEquals(readerDto1.getId(), result.getId());
        assertEquals(readerDto1.getName(), result.getName());
        assertEquals(readerDto1.getEmail(), result.getEmail());
        assertEquals(readerDto1.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(readerDto1.getBirthdate(), result.getBirthdate());
        assertEquals(readerDto1.getRegistered(), result.getRegistered());
    }

    @Test
    void readById_ShouldThrowException_WhenReaderNotFound() {
        when(readerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> readerService.readById(5L));
    }

    @Test
    void readAll_ShouldReturnAllReaders() {
        when(readerRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(List.of(reader1, reader2, reader3));
        when(readerMapper.convertManyToDto(List.of(reader1, reader2, reader3))).thenReturn(List.of(readerDto1, readerDto2, readerDto3));

        List<ReaderDto> readers = readerService.readAll();

        assertEquals(3, readers.size());

        assertEquals(0L, readers.get(0).getId());
        assertEquals("Karel Novák", readers.get(0).getName());
        assertEquals("karelnovak@seznam.cz", readers.get(0).getEmail());
        assertEquals("777531589", readers.get(0).getPhoneNumber());
        assertEquals(LocalDate.of(1987, 3, 12), readers.get(0).getBirthdate());
        assertEquals(LocalDate.of(2020, 5, 4), readers.get(0).getRegistered());

        assertEquals(1L, readers.get(1).getId());
        assertEquals("Jan Sviták", readers.get(1).getName());
        assertEquals("svitakjan@email.cz", readers.get(1).getEmail());
        assertEquals("743897123", readers.get(1).getPhoneNumber());
        assertEquals(LocalDate.of(1998, 12, 18), readers.get(1).getBirthdate());
        assertEquals(LocalDate.of(2021, 3, 21), readers.get(1).getRegistered());

        assertEquals(2L, readers.get(2).getId());
        assertEquals("Alena Sýkorová", readers.get(2).getName());
        assertEquals("sykorkaA@seznam.cz", readers.get(2).getEmail());
        assertEquals("789561442", readers.get(2).getPhoneNumber());
        assertEquals(LocalDate.of(1975, 6, 1), readers.get(2).getBirthdate());
        assertEquals(LocalDate.of(2021, 9, 8), readers.get(2).getRegistered());
    }

    @Test
    void update_ShouldUpdateAndReturnReaderDto() {
        when(readerRepository.findById(anyLong())).thenReturn(Optional.of(reader1));
        when(readerMapper.convertToEntity(any())).thenReturn(reader1);
        when(readerRepository.save(any())).thenReturn(reader1);
        when(readerMapper.convertToDto(any())).thenReturn(readerDto1);

        ReaderDto result = readerService.update(1L, readerDto1);

        assertNotNull(result);
        assertEquals(readerDto1.getId(), result.getId());
        assertEquals(readerDto1.getName(), result.getName());
        assertEquals(readerDto1.getEmail(), result.getEmail());
        assertEquals(readerDto1.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(readerDto1.getBirthdate(), result.getBirthdate());
        assertEquals(readerDto1.getRegistered(), result.getRegistered());
        verify(readerRepository).save(any());
    }

    @Test
    void update_ShouldThrowException_WhenReaderNotFound() {
        when(readerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> readerService.update(5L, readerDto1));
    }

    @Test
    void deleteById_ShouldDeleteReaderAndLoans() {
        when(readerRepository.findById(anyLong())).thenReturn(Optional.of(reader1));
        when(loanRepository.findByReaderId(anyLong())).thenReturn(List.of());

        readerService.deleteById(1L);

        verify(loanRepository).deleteAll(anyList());
        verify(readerRepository).deleteById(1L);
    }

    @Test
    void deleteById_ShouldThrowException_WhenReaderNotFound() {
        when(readerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> readerService.deleteById(1L));
    }
}
