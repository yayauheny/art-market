package by.yayauheny.dto;

import by.yayauheny.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public record UserSaveRequest(
    @Email
    @NotBlank
    String email,

    @Size(min = 3, max = 64)
    String name,

    @Size(max = 64)
    String lastName,

    @Size(min = 6)
    @NotBlank
    String rawPassword,

    Role role,

    @Size(max = 256)
    String address,

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate
) {

}
