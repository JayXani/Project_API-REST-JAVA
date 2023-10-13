package br.com.apirest.todolist.users;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user") //This is generally a route main.
public class UserController {
    @PostMapping("/")
    public void create(@RequestBody UserModel user){

    }
}
