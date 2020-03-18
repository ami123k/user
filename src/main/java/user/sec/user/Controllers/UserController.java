package user.sec.user.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import user.sec.user.models.User;
import user.sec.user.models.offre;
import user.sec.user.repository.RoleRepository;
import user.sec.user.repository.UserRepository;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @GetMapping(value = "/listuser" )
    public List<User> usersList(){
        return  userRepository.findAll();

    }

    /* ////////////////////////////find////////////////////// */
    @GetMapping(value = "/user/{id}" )
    public User user(@PathVariable(name = "id") Long id ) {
        return userRepository.findById(id).get();
    }


}
