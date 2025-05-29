package com.java.Travel.controller;


import com.java.Travel.controller.dto.ApiError;
import com.java.Travel.controller.dto.UserDTO;
import com.java.Travel.exception.UserNotFoundException;
import com.java.Travel.service.RoleService;
import com.java.Travel.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    private UserService userService;
    private RoleService roleService;

    public UserController(UserService userService,
                          RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'WORKER')")
    @GetMapping()
    public String getAllUsersByRole(@RequestParam(value = "role", required = false) String role, Model model) {
        role = role == null ? "ROLE_CLIENT" : role;
        List<UserDTO> users = roleService.getUsersByRole(role);
        model.addAttribute("role", role);
        model.addAttribute("users", users.size() == 0 ? null : users);
        return "user/showUsers";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'WORKER')")
    @GetMapping(path = "/delete")
    public String deleteEmployeeById(Model model,
                                     @RequestParam("id") Long id,
                                     @RequestParam(value = "role") String role) throws UserNotFoundException {
        userService.deleteUserById(id);
        String redirect = "redirect:/users?&role=" + role;
        return redirect;
    }

    @GetMapping(path = {"/edit", "/edit/{id}"})
    public String getAddOrEditUserView(
            Model model,
            @RequestParam(value = "role", required = false, defaultValue = "ROLE_CLIENT") String role,
            @PathVariable(value = "id") Optional<Long> id) {


        if (id.isPresent()) {
            UserDTO userDTO = userService.findUserById(id.get());
            if (userDTO != null) {
                model.addAttribute("user", userDTO);
            } else {
                throw new UserNotFoundException("User with id=" + id + " not found");
            }
            return "user/addEditUser";
        } else {
            UserDTO userDTO = new UserDTO();
            userDTO.setRole(role);
            model.addAttribute("user", userDTO);
            return "user/addEditUser";
        }
    }

    @PostMapping(path = "/edit")
    public String addOrEditUser(@Valid @ModelAttribute("user") UserDTO user,
                                @RequestParam(value = "role", required = false, defaultValue = "ROLE_CLIENT") String role,
                                BindingResult result, Model model) {

        if (result.hasErrors()) {
            ApiError apiError = new ApiError();
            String message = "";
            for (FieldError str : result.getFieldErrors()) {
                message += str.getDefaultMessage();
                apiError.setMessage(message);
            }
            model.addAttribute("user", user);
            model.addAttribute("apiError", apiError);
            return "user/addEditUser";
        }

        if (user.getId() != null) {
            userService.update(user);
        } else {
            userService.save(user, role);
        }

        return "redirect:/home";
    }


}
