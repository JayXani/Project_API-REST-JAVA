package br.com.apirest.todolist.users;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user") //This is generally a route main.
public class UserController {
    @Autowired
    private IUserRepository userRepository;
    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel user){
        var userExist = this.userRepository.findByUserName(user.getUserName());
        if(userExist != null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists in Data base");
        if(user.getPassword().isEmpty()) return ResponseEntity.status(HttpStatus.CONFLICT).body("Password cannot be empty !");

        var passwordHash = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(passwordHash);

        var userCreated = this.userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
