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
public class BookDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long id;
    @Schema(example = "Psychology of murderers")
    private String name;
    @Schema(example = "10")
    private Integer numberOfCopies;
    @Schema(example = "978-0743273565")
    private String isbn;
    @Schema(example = "1925-04-10")
    private LocalDate published;
    @Schema(example = "[5, 7]")
    private List<Long> authorIDs;
}
