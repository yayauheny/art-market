package by.yayauheny.dto;

import by.yayauheny.enums.Role;
import java.time.LocalDate;
import java.util.UUID;

public record UserResponse(
    UUID id,
    String email,
    String name,
    String lastName,
    Role role,
    String address,
    LocalDate birthDate
) {

}
