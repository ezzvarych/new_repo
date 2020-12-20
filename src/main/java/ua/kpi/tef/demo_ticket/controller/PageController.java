package ua.kpi.tef.demo_ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.kpi.tef.demo_ticket.entity.Ticket;
import ua.kpi.tef.demo_ticket.entity.Trip;
import ua.kpi.tef.demo_ticket.entity.enums.RoleType;
import ua.kpi.tef.demo_ticket.entity.User;
import ua.kpi.tef.demo_ticket.service.UserService;

import java.util.List;


@Controller
public class PageController {
    private final UserService userService;

    @Autowired
    public PageController(UserService userService) {
        this.userService = userService;
    }

    /*@RequestMapping("/")
    public String getMainPage(Model model){
        //model.addAttribute("editions", editionService.getAllEditions());
        return "index.html";
    }*/


    @RequestMapping("/cab")
    public String getUserPage(Model model){
        return "cab.html";
    }

    @RequestMapping("/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login.html";
    }


    @GetMapping("/users")
    public String findAll(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/user-create")
    public String createUserForm(User user){
        return "user-create";
    }

    @PostMapping("/user-create")
    public String createUser(User user){
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping("/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model){
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user-update";
    }

    @PostMapping("/user-update")
    public String updateUser(User user){
        userService.saveUser(user);
        return "redirect:/users";
    }


    @RequestMapping("/registration")
    public String regForm(){
        return "registration.html";
    }

    @GetMapping("/user-tickets")
    public String getUserTickets(Model model) {
        List<Ticket> tickets = userService.getUserTickets(getCurrent().getId());
        model.addAttribute("tickets", tickets);
        return "user-profile";
    }

    private User getCurrent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user;
    }
}