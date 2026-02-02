package university.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateStudentRequest {
    @NotBlank
    @Size(max = 255)
    private String fullName;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate admissionDate;
}
