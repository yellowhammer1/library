package library.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReaderDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long id;
    @Schema(example = "Andrea Rásochová")
    private String name;
    @Schema(example = "rasochova@email.cz")
    private String email;
    @Schema(example = "777654795")
    private String phoneNumber;
    @Schema(example = "2002-10-06")
    private LocalDate birthdate;
    @Schema(example = "2022-10-12")
    private LocalDate registered;

    public ReaderDto(String name, String email, String phoneNumber, LocalDate birthdate, LocalDate registered) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.registered = registered;
    }
}