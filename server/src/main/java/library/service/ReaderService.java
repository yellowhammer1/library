package library.service;

import library.controller.dto.ReaderDto;
import library.domain.Loan;
import library.domain.Reader;
import library.repository.LoanRepository;
import library.repository.ReaderRepository;
import library.service.mapper.ReaderMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService implements CrudService<ReaderDto, Long> {

    private final ReaderRepository readerRepository;
    private final LoanRepository loanRepository;
    private final ReaderMapper readerMapper;

    @Override
    public ReaderDto create(ReaderDto readerDto) throws IllegalArgumentException{
        Reader reader = readerMapper.convertToEntity(readerDto);
        readerRepository.save(reader);
        return readerMapper.convertToDto(reader);
    }

    @Override
    public ReaderDto readById(Long id) {
        Reader reader = readerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reader not found with id " + id));
        return readerMapper.convertToDto(reader);
    }

    @Override
    public List<ReaderDto> readAll() {
        List<Reader> readers = readerRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return readerMapper.convertManyToDto(readers);
    }

    @Override
    public ReaderDto update(Long id, ReaderDto readerDto) {
        readerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reader not found with id " + id));
        Reader reader = readerMapper.convertToEntity(readerDto);
        reader.setId(id);
        readerRepository.save(reader);
        return readerMapper.convertToDto(reader);
    }

    @Override
    public void deleteById(Long id) {
        readerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reader not found with id " + id));
        List<Loan> loans = loanRepository.findByReaderId(id);
        loanRepository.deleteAll(loans);
        readerRepository.deleteById(id);
    }
}
