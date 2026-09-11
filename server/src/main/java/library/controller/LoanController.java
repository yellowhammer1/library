package library.controller;

import library.controller.dto.LoanDto;
import library.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @Operation(summary = "Retrieve all loans", description = "Returns a list of all loans in the database")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of loans")
    @GetMapping
    public List<LoanDto> getAll() {
        return loanService.readAll();
    }

    @Operation(summary = "Retrieve a loan by ID", description = "Fetch a single loan by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the loan"),
            @ApiResponse(responseCode = "404", description = "Loan with the given ID not found")
    })
    @GetMapping("/{id}")
    public LoanDto getById(@PathVariable("id") Long id) {
        return loanService.readById(id);
    }

    @Operation(summary = "Create a new loan", description = "Adds a new loan to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Loan successfully created"),
            @ApiResponse(responseCode = "409", description = "No book copies available for borrowing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanDto createLoan(@RequestBody LoanDto loanDto) {
        return loanService.create(loanDto);
    }

    @Operation(summary = "Update an existing loan", description = "Updates the details of an existing loan by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan successfully updated"),
            @ApiResponse(responseCode = "404", description = "Loan with the given ID not found")
    })
    @PutMapping("/{id}")
    public LoanDto updateLoan(
            @PathVariable("id") Long id,
            @RequestBody LoanDto loanDto) {
        return loanService.update(id, loanDto);
    }

    @Operation(summary = "Delete a loan", description = "Deletes a loan from the database by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Loan successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Loan with the given ID not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLoan(@PathVariable("id") Long id) {
        loanService.deleteById(id);
    }
}
