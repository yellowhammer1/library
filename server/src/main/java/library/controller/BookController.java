package library.controller;

import library.controller.dto.BookDto;
import library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Retrieve all books", description = "Returns a list of all books in the database")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of books")
    @GetMapping
    public List<BookDto> getAll() {
        return bookService.readAll();
    }

    @Operation(summary = "Retrieve a book by ID", description = "Fetch a single book by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the book"),
            @ApiResponse(responseCode = "404", description = "Book with the given ID not found")
    })
    @GetMapping("/{id}")
    public BookDto getById(@PathVariable("id") Long id) {
        return bookService.readById(id);
    }

    @Operation(summary = "Create a new book", description = "Adds a new book to the database")
    @ApiResponse(responseCode = "201", description = "Book successfully created")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody BookDto bookDto) {
        return bookService.create(bookDto);
    }

    @Operation(summary = "Update an existing book", description = "Updates the details of an existing book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book successfully updated"),
            @ApiResponse(responseCode = "404", description = "Book with the given ID not found")
    })
    @PutMapping("/{id}")
    public BookDto updateBook(
            @PathVariable("id") Long id,
            @RequestBody BookDto bookDto) {
        return bookService.update(id, bookDto);
    }

    @Operation(summary = "Delete a book", description = "Deletes a book from the database by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Book with the given ID not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
    }
}
