package by.yayauheny.controller;

import by.yayauheny.dto.UserResponse;
import by.yayauheny.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoint for managing users")
public class UserController {

  private final UserService userService;

  @Operation(
      summary = "Get all users",
      description = "Return a list of all existing users"
  )
  @GetMapping
  public ResponseEntity<List<UserResponse>> findAllUsers() {
    List<UserResponse> responseList = userService.findAll();
    return ResponseEntity.ok(responseList);
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
