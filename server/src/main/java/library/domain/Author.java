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
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_author")
    private Long id;
    @NotBlank
    private String name;
    private LocalDate birthdate;
    private String nationality;
    @ManyToMany(targetEntity = Book.class)
    @JoinTable(
            name = "Book_author",
            joinColumns = @JoinColumn(name = "id_author"),
            inverseJoinColumns = @JoinColumn(name = "id_book")
    )
    private List<Book> books;

    public Author(String name, LocalDate birthdate, String nationality, List<Book> books) {
        this.name = name;
        this.birthdate = birthdate;
        this.nationality = nationality;
        this.books = books;
    }
}
