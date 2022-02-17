package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("usersList", userService.listUsers());
        return "users";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model){
        model.addAttribute("user", userService.findUserById(id));
        return "show";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") User user){
        userService.add(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("user", userService.findUserById(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, Model model){
        model.addAttribute("user", userService.change(user, user.getRoles()));
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id){
        userService.delete(id);
        return "redirect:/users";
    }
}
