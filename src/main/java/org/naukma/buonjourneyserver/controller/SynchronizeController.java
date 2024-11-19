package org.naukma.buonjourneyserver.controller;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.dto.UserDto;
import org.naukma.buonjourneyserver.exceptions.ExceptionHelper;
import org.naukma.buonjourneyserver.service.ISynchronizeService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/synchronize")
@RequiredArgsConstructor
public class SynchronizeController {
    private final ISynchronizeService synchronizeService;

    @PostMapping
    public UserDto createUserWithAllData(@RequestBody @Valid UserDto userRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        UserDto userDto = synchronizeService.createUserWithAllData(userRequestDto);
        log.info("User created with ID: {}", userDto.getId());
        return userDto;
    }
}
