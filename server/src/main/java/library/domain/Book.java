package library.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book")
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private Integer numberOfCopies;
    private String isbn;
    private LocalDate published;
    @ManyToMany(mappedBy = "books")
    private List<Author> authors;
    @OneToMany(mappedBy = "book")
    private List<Loan> loans;

    public Book(String name, Integer numberOfCopies, String isbn, LocalDate published, List<Author> authors) {
        this.name = name;
        this.numberOfCopies = numberOfCopies;
        this.isbn = isbn;
        this.published = published;
        this.authors = authors;
    }
}
