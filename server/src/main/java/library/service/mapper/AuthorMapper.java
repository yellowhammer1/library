package library.service.mapper;

import library.controller.dto.AuthorDto;
import library.controller.dto.AuthorDto;
import library.domain.Author;
import library.domain.Author;
import library.domain.Book;
import library.repository.AuthorRepository;
import library.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorMapper implements EntityMapper<Author, AuthorDto>{

    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;

    public AuthorMapper(ModelMapper modelMapper, BookRepository bookRepository) {
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public Author convertToEntity(AuthorDto authorDto) {
        Author author = modelMapper.map(authorDto, Author.class);

        author.setName(authorDto.getName());
        author.setBirthdate(authorDto.getBirthdate());
        author.setNationality(authorDto.getNationality());

        List<Book> books = bookRepository.findAllById(authorDto.getBookIDs());
        author.setBooks(books);

        for (Book book : books) {
            if (book.getAuthors() == null) {
                book.setAuthors(new ArrayList<>());
            }
            if (!book.getAuthors().contains(author)) {
                book.getAuthors().add(author);
            }
        }

        return author;
    }

    @Override
    public AuthorDto convertToDto(Author author) {
        AuthorDto authorDto = modelMapper.map(author, AuthorDto.class);

        authorDto.setName(author.getName());
        authorDto.setBirthdate(author.getBirthdate());
        authorDto.setNationality(author.getNationality());
        List<Long> bookIDs = author.getBooks().stream().map(Book::getId).toList();
        authorDto.setBookIDs(bookIDs);

        return authorDto;
    }

    @Override
    public List<AuthorDto> convertManyToDto(List<Author> authors) {
        return authors.stream().map(this::convertToDto).toList();
    }
}
