package library.controller;

import library.controller.dto.AuthorDto;
import library.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Operation(summary = "Retrieve all authors", description = "Returns a list of all authors in the database")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of authors")
    @GetMapping
    public List<AuthorDto> getAll() {
        return authorService.readAll();
    }

    @Operation(summary = "Retrieve an author by ID", description = "Fetch a single author by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the author"),
            @ApiResponse(responseCode = "404", description = "Author with the given ID not found")
    })
    @GetMapping("/{id}")
    public AuthorDto getById(@PathVariable("id") Long id) {
        return authorService.readById(id);
    }

    @Operation(summary = "Create a new author", description = "Adds a new author to the database")
    @ApiResponse(responseCode = "201", description = "Author successfully created")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto createAuthor(@RequestBody AuthorDto authorDto) {
        return authorService.create(authorDto);
    }

    @Operation(summary = "Update an existing author", description = "Updates the details of an existing author by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author successfully updated"),
            @ApiResponse(responseCode = "404", description = "Author with the given ID not found")
    })
    @PutMapping("/{id}")
    public AuthorDto updateAuthor(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto authorDto) {
        return authorService.update(id, authorDto);
    }

    @Operation(summary = "Delete an author", description = "Deletes an author from the database by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Author successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Author with the given ID not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable("id") Long id) {
        authorService.deleteById(id);
    }
}
