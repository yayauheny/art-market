package by.yayauheny.http.rest;

import by.yayauheny.dto.UserResponse;
import by.yayauheny.dto.filter.UserFilter;
import by.yayauheny.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoint for managing users")
public class UserRestController {

  private final UserService userService;

  @Operation(
      summary = "Get all users",
      description = "Return a page of all existing users"
  )
  @GetMapping
  public Page<UserResponse> findAllUsers(UserFilter filter, Pageable pageable) {
    return userService.findAll(filter, pageable);
  }

  @Operation(
      summary = "Get user by ID",
      description = "Return user details based on the provided ID"
  )
  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> findUserById(@PathVariable("id") UUID id) {
    UserResponse userResponse = userService.findById(id);
    return ResponseEntity.ok(userResponse);
  }
}
