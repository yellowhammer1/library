package library.service;

import library.controller.dto.AuthorDto;
import library.domain.Author;
import library.repository.AuthorRepository;
import library.service.mapper.AuthorMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService implements CrudService<AuthorDto, Long> {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public AuthorDto create(AuthorDto authorDto) throws IllegalArgumentException {
        Author author = authorMapper.convertToEntity(authorDto);
        authorRepository.save(author);
        return authorMapper.convertToDto(author);
    }

    @Override
    public AuthorDto readById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found with id " + id));
        return authorMapper.convertToDto(author);
    }

    @Override
    public List<AuthorDto> readAll() {
        List<Author> authors = authorRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return authorMapper.convertManyToDto(authors);
    }

    @Override
    public AuthorDto update(Long id, AuthorDto authorDto) {
        authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found with id " + id));
        Author author = authorMapper.convertToEntity(authorDto);
        author.setId(id);
        authorRepository.save(author);
        return authorMapper.convertToDto(author);
    }

    @Override
    public void deleteById(Long id) {
        authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found with id " + id));
        authorRepository.deleteById(id);
    }
}
