package library.service;

import library.controller.dto.LoanDto;
import library.domain.Book;
import library.domain.Loan;
import library.domain.Reader;
import library.repository.BookRepository;
import library.repository.LoanRepository;
import library.repository.ReaderRepository;
import library.service.mapper.LoanMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService implements CrudService<LoanDto, Long> {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final LoanMapper loanMapper;

    @Override
    public LoanDto create(LoanDto loanDto) {
        Book book = bookRepository.findById(loanDto.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        Reader reader = readerRepository.findById(loanDto.getReaderId())
                .orElseThrow(() -> new IllegalArgumentException("Reader not found"));

        if (!loanRepository.isBookAvailable(book.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "No copies available for borrowing");
        }

        Loan loan = loanMapper.convertToEntity(loanDto);
        loan.setBook(book);
        loan.setReader(reader);
        loanRepository.save(loan);

        return loanMapper.convertToDto(loan);
    }

    @Override
    public LoanDto readById(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Loan not found with id " + id));
        return loanMapper.convertToDto(loan);
    }

    @Override
    public List<LoanDto> readAll() {
        List<Loan> loans = loanRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return loanMapper.convertManyToDto(loans);
    }

    @Override
    public LoanDto update(Long id, LoanDto loanDto) {
        loanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Loan not found with id " + id));
        Loan loan = loanMapper.convertToEntity(loanDto);
        loan.setId(id);
        loanRepository.save(loan);
        return loanMapper.convertToDto(loan);
    }

    @Override
    public void deleteById(Long id) {
        loanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Loan not found with id " + id));
        loanRepository.deleteById(id);
    }
}
