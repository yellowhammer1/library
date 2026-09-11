package library.service;

import library.controller.dto.BookDto;
import library.domain.Book;
import library.domain.Loan;
import library.repository.BookRepository;
import library.repository.LoanRepository;
import library.service.mapper.BookMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService implements CrudService<BookDto, Long> {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto create(BookDto bookDto) throws IllegalArgumentException {
        Book book = bookMapper.convertToEntity(bookDto);
        bookRepository.save(book);
        return bookMapper.convertToDto(book);
    }

    @Override
    public BookDto readById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found with id " + id));
        return bookMapper.convertToDto(book);
    }

    @Override
    public List<BookDto> readAll() {
        List<Book> books = bookRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return bookMapper.convertManyToDto(books);
    }

    @Override
    public BookDto update(Long id, BookDto bookDto) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found with id " + id));

        existingBook.getAuthors().forEach(author -> author.getBooks().remove(existingBook));
        existingBook.getAuthors().clear();

        Book book = bookMapper.convertToEntity(bookDto);
        book.setId(id);

        bookRepository.save(book);
        return bookMapper.convertToDto(book);
    }

    @Override
    public void deleteById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found with id " + id));

        book.getAuthors().forEach(author -> author.getBooks().remove(book));
        book.getAuthors().clear();
        bookRepository.save(book);

        List<Loan> loans = loanRepository.findByBookId(id);
        loanRepository.deleteAll(loans);

        bookRepository.deleteById(id);
    }
}
