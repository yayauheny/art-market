package by.yayauheny.http.rest;

import by.yayauheny.dto.UserSaveRequest;
import by.yayauheny.dto.UserResponse;
import by.yayauheny.dto.UserUpdateRequest;
import by.yayauheny.dto.filter.UserFilter;
import by.yayauheny.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
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
  public Page<UserResponse> findAll(UserFilter filter, Pageable pageable) {
    return userService.findAll(filter, pageable);
  }

  @Operation(
      summary = "Get user by ID",
      description = "Return user details based on the provided ID"
  )
  @GetMapping("/{id}")
  public UserResponse findById(@PathVariable("id") UUID id) {
    return userService.findById(id);
  }

  @Operation(
      summary = "Create user",
      description = "Return a created user"
  )
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponse create(@Valid @RequestBody UserSaveRequest request) {
    return userService.save(request);
  }

  @Operation(
      summary = "Update user",
      description = "Return an updated user"
  )
  @PutMapping("/{id}")
  public UserResponse update(@PathVariable UUID id, @Valid @RequestBody UserUpdateRequest request) {
    return userService.update(id, request);
  }
}
