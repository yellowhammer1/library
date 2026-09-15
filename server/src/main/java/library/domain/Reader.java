package library.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reader")
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private LocalDate birthdate;
    @NotBlank
    private LocalDate registered;
    @OneToMany(mappedBy = "reader")
    private List<Loan> loans;

    public Reader(String name, String email, String phoneNumber, LocalDate birthdate, LocalDate registered) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.registered = registered;
    }

    public Reader(Long id, String name, String email, String phoneNumber, LocalDate birthdate, LocalDate registered) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.registered = registered;
    }
}
