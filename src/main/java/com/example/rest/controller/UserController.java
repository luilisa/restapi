package com.example.rest.controller;

import com.example.rest.entity.Portfolio;
import com.example.rest.entity.UserEntity;
import com.example.rest.exception.ResourceNotFoundException;
import com.example.rest.repository.PortfolioRepository;
import com.example.rest.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;

    public UserController(UserRepository userRepository, PortfolioRepository portfolioRepository) {
        this.userRepository = userRepository;
        this.portfolioRepository = portfolioRepository;
    }


    @GetMapping("/users")
    public List<? extends Object> getAllUsers(@RequestParam (value = "login", required = false) String login) {
        if (login != null) {
            return userRepository.findAllByLoginEquals(login);
        }
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
        return ResponseEntity.ok().body(userEntity);
    }

    @PostMapping("/users")
    public UserEntity createUser(@RequestBody UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable(value = "id") Long userId,
                                                    @RequestBody UserEntity userDetails) throws ResourceNotFoundException {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        userEntity.setLogin(userDetails.getLogin());
        userEntity.setPassword(userDetails.getPassword());
        final UserEntity updatedUser = userRepository.save(userEntity);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/users/{id}/portfolio/{portfolio_id}")
    public UserEntity assignPortfolioToUser(@PathVariable(value = "id") Long userId,
                                                 @PathVariable Long portfolio_id) throws ResourceNotFoundException {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
        Portfolio portfolio = portfolioRepository.findById(portfolio_id)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found for this id :: " + portfolio_id));
        portfolio.assignUser(userEntity);
        return userRepository.save(userEntity);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        userRepository.delete(userEntity);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
