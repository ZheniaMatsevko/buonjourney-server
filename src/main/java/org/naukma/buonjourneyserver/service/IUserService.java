package org.naukma.buonjourneyserver.service;

import org.naukma.buonjourneyserver.dto.UserDto;
import org.naukma.buonjourneyserver.dto.updateDto.ChangePasswordDto;
import org.naukma.buonjourneyserver.dto.updateDto.UserUpdateDto;

import java.util.List;

public interface IUserService {
    UserDto createUser(UserDto user);

    UserDto updateUser(UserUpdateDto user);

    void deleteUser(Long userId);

    UserDto getUserById(Long userId);

    UserDto getUserByEmail(String email);

    void changePassword(ChangePasswordDto changePassword);

    List<UserDto> getAllUsers();
}
