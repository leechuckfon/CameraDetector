package be.kdg.processor.web.controllers;

import be.kdg.processor.model.users.DatabaseUser;
import be.kdg.processor.web.dto.UserCreationDTO;
import be.kdg.processor.web.services.UserException;
import be.kdg.processor.web.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usapi")
public class UserRestController {
    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserDetails> createUser(@RequestBody UserCreationDTO userCreationDTO) throws UserException {
        UserDetails createdUser = userService.saveNewUser(new DatabaseUser(userCreationDTO.getUsername(),userCreationDTO.getPassword(),userCreationDTO.getRoles()));
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<UserDetails> updateUser(@RequestBody UserCreationDTO userCreationDTO) throws UserException {
        UserDetails updatedUser = userService.updateUser(new DatabaseUser(userCreationDTO.getUsername(),userCreationDTO.getPassword(),userCreationDTO.getRoles()));
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    @PostMapping("/deleteUser/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) throws UserException {
        int amountOfRowsDeleted = userService.deleteUser(username);
        return new ResponseEntity<>(String.valueOf(amountOfRowsDeleted), HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserDetails> getUser(@PathVariable String username){
        UserDetails requestedUser = userService.loadUserByUsername(username);
        return new ResponseEntity<>(requestedUser, HttpStatus.OK);
    }
}
