package library.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long id;
    @Schema(example = "2020-05-10")
    private LocalDate borrowed;
    @Schema(example = "2020-05-17")
    private LocalDate returned;
    @Schema(example = "3")
    private Long readerId;
    @Schema(example = "200")
    private Long bookId;
}
