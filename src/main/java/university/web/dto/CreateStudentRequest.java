package university.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class CreateStudentRequest {
  @NotBlank
  @Size(max = 255)
  private String fullName;

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  @PastOrPresent(message = "Дата поступления не может быть в будущем")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate admissionDate;
}
