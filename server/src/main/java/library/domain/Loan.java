package library.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_loan")
    private Long id;
    @NotBlank
    private LocalDate borrowed;
    private LocalDate returned;
    @ManyToOne(targetEntity = Reader.class)
    @JoinColumn(name = "id_reader")
    private Reader reader;
    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name = "id_book")
    private Book book;

    public Loan(LocalDate borrowed, LocalDate returned, Reader reader, Book book) {
        this.borrowed = borrowed;
        this.returned = returned;
        this.reader = reader;
        this.book = book;
    }
}
