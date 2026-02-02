package university.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGroupRequest {
  @NotBlank
  @Size(max = 20)
  @Pattern(
      regexp = "\\d+(?:-\\d+)?",
      message = "Номер группы должен содержать цифры и/или один дефис (например 19-03)")
  private String number;
}
