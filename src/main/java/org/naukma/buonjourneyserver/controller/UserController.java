package org.naukma.buonjourneyserver.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.dto.ChangePasswordDto;
import org.naukma.buonjourneyserver.dto.UserDto;
import org.naukma.buonjourneyserver.dto.UserUpdateDto;
import org.naukma.buonjourneyserver.exceptions.InvalidOldPasswordException;
import org.naukma.buonjourneyserver.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping
    public UserDto createUser(UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, UserUpdateDto userDto) {
        return userService.updateUser(userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        log.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        log.info("User deleted with ID: {}", id);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        log.info("Retrieving user with ID: {}", userId);
        return userService.getUserById(userId);
    }

    @PutMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordDto request) {
        try {
            userService.changePassword(request);
            return ResponseEntity.ok("The password was changed successfully");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (InvalidOldPasswordException e) {
            e.printStackTrace();
            String errorCode = e.getErrorCode();
            String errorMessage = e.getMessage();
            return ResponseEntity.badRequest().body(errorCode + ": " + errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error to change password.");
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String errorMessage = "ERROR: " + e.getMessage();
        log.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
