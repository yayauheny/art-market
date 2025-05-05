package by.yayauheny.http.controller;

import by.yayauheny.dto.PageResponse;
import by.yayauheny.dto.UserResponse;
import by.yayauheny.dto.UserSaveRequest;
import by.yayauheny.dto.UserUpdateRequest;
import by.yayauheny.dto.filter.UserFilter;
import by.yayauheny.enums.Role;
import by.yayauheny.service.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public String findAll(Model model, UserFilter filter, Pageable pageable) {
    Page<UserResponse> foundUsersPage = userService.findAll(filter, pageable);
    model.addAttribute("users", PageResponse.of(foundUsersPage));
    model.addAttribute("filter", filter);
    model.addAttribute("roles", Role.values());
    return "user/users";
  }

  @GetMapping("/{id}")
  public String findById(@PathVariable("id") UUID id, Model model) {
    UserResponse foundUser = userService.findById(id);
    model.addAttribute("user", foundUser);
    model.addAttribute("roles", Role.values());
    return "user/user";
  }

  @GetMapping("/register")
  public String register(Model model, UserSaveRequest request) {
    model.addAttribute("user", request);
    model.addAttribute("roles", Role.values());
    return "user/register";
  }

  @PostMapping
  public String create(@ModelAttribute UserSaveRequest request) {
    userService.save(request);
    return "redirect:/login";
  }

  @PostMapping("/{id}/update")
  public String update(@PathVariable("id") UUID id, @ModelAttribute UserUpdateRequest request) {
    userService.update(id, request);
    return "redirect:/users/" + id;
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable("id") UUID id) {
    userService.delete(id);
    return "redirect:/users/";
  }
}
