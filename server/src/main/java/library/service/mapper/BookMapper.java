package library.service.mapper;

import library.controller.dto.BookDto;
import library.domain.Author;
import library.domain.Book;
import library.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookMapper implements EntityMapper<Book, BookDto> {

    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;

    public BookMapper(ModelMapper modelMapper, AuthorRepository authorRepository) {
        this.modelMapper = modelMapper;
        this.authorRepository = authorRepository;
    }

    @Override
    public Book convertToEntity(BookDto bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);

        book.setName(bookDto.getName());
        book.setNumberOfCopies(bookDto.getNumberOfCopies());
        book.setIsbn(bookDto.getIsbn());
        book.setPublished(bookDto.getPublished());

        List<Author> authors = authorRepository.findAllById(bookDto.getAuthorIDs());
        book.setAuthors(authors);

        for (Author author : authors) {
            if (author.getBooks() == null) {
                author.setBooks(new ArrayList<>());
            }
            if (!author.getBooks().contains(book)) {
                author.getBooks().add(book);
            }
        }

        return book;
    }

    @Override
    public BookDto convertToDto(Book book) {
        BookDto bookDto = modelMapper.map(book, BookDto.class);

        bookDto.setName(book.getName());
        bookDto.setNumberOfCopies(book.getNumberOfCopies());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setPublished(book.getPublished());
        List<Long> authorIDs = book.getAuthors().stream().map(Author::getId).toList();
        bookDto.setAuthorIDs(authorIDs);

        return bookDto;
    }

    @Override
    public List<BookDto> convertManyToDto(List<Book> books) {
        return books.stream().map(this::convertToDto).toList();
    }
}
