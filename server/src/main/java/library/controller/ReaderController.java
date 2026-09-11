package library.controller;

import library.controller.dto.ReaderDto;
import library.service.ReaderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/readers")
public class ReaderController {
    private final ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @Operation(summary = "Retrieve all readers", description = "Returns a list of all readers in the database")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of readers")
    @GetMapping
    public List<ReaderDto> getAll() {
        return readerService.readAll();
    }

    @Operation(summary = "Retrieve a reader by ID", description = "Fetch a single reader by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the reader"),
            @ApiResponse(responseCode = "404", description = "Reader with the given ID not found")
    })
    @GetMapping("/{id}")
    public ReaderDto getById(@PathVariable("id") Long id) {
        return readerService.readById(id);
    }

    @Operation(summary = "Create a new reader", description = "Adds a new reader to the database")
    @ApiResponse(responseCode = "201", description = "Reader successfully created")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReaderDto createReader(@RequestBody ReaderDto readerDto) {
        return readerService.create(readerDto);
    }

    @Operation(summary = "Update an existing reader", description = "Updates the details of an existing reader by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reader successfully updated"),
            @ApiResponse(responseCode = "404", description = "Reader with the given ID not found")
    })
    @PutMapping("/{id}")
    public ReaderDto updateReader(
            @PathVariable("id") Long id,
            @RequestBody ReaderDto readerDto) {
        return readerService.update(id, readerDto);
    }

    @Operation(summary = "Delete a reader", description = "Deletes a reader from the database by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reader successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Reader with the given ID not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReader(@PathVariable("id") Long id) {
        readerService.deleteById(id);
    }
}
