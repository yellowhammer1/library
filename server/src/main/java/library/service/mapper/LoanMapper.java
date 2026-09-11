package library.service.mapper;

import library.controller.dto.LoanDto;
import library.domain.Book;
import library.domain.Loan;
import library.domain.Reader;
import library.repository.BookRepository;
import library.repository.LoanRepository;
import library.repository.ReaderRepository;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoanMapper implements EntityMapper<Loan, LoanDto> {

    private final ModelMapper modelMapper;
    public LoanMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Loan convertToEntity(LoanDto loanDto) {
        Loan loan = modelMapper.map(loanDto, Loan.class);

        loan.setBorrowed(loanDto.getBorrowed());
        loan.setReturned(loanDto.getReturned());

        return loan;
    }

    @Override
    public LoanDto convertToDto(Loan loan) {
        LoanDto loanDto = modelMapper.map(loan, LoanDto.class);

        loanDto.setBookId(loan.getBook().getId());
        loanDto.setReaderId(loan.getReader().getId());
        loanDto.setBorrowed(loan.getBorrowed());
        loanDto.setReturned(loan.getReturned());

        return loanDto;
    }

    @Override
    public List<LoanDto> convertManyToDto(List<Loan> loans) {
        return loans.stream().map(this::convertToDto).toList();
    }
}
