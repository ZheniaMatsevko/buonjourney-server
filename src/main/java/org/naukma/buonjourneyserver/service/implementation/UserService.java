package org.naukma.buonjourneyserver.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.dto.UserDto;
import org.naukma.buonjourneyserver.dto.updateDto.ChangePasswordDto;
import org.naukma.buonjourneyserver.dto.updateDto.UserUpdateDto;
import org.naukma.buonjourneyserver.entity.UserEntity;
import org.naukma.buonjourneyserver.exceptions.InvalidOldPasswordException;
import org.naukma.buonjourneyserver.exceptions.InvalidUserDataException;
import org.naukma.buonjourneyserver.mapper.IUserMapper;
import org.naukma.buonjourneyserver.repository.IUserRepository;
import org.naukma.buonjourneyserver.service.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto createUser(UserDto user) throws InvalidUserDataException{
        log.info("Creating user");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPlacesToVisit(new ArrayList<>());
        user.setTrips(new ArrayList<>());

        checkNewEmail(user.getEmail());
        checkNewUsername(user.getUsername());

        UserEntity createdUser = userRepository.save(IUserMapper.INSTANCE.dtoToEntity(user));

        log.info("User created successfully.");
        return IUserMapper.INSTANCE.entityToDto(createdUser);
    }

    @Override
    public UserDto updateUser(UserUpdateDto user) throws InvalidUserDataException, EntityNotFoundException{
        Optional<UserEntity> optional = userRepository.findById(user.getId());
        if (optional.isPresent()) {
            UserEntity userEntity = optional.get();

            if(user.getEmail() != null && !userEntity.getEmail().equals(user.getEmail())) {
                checkNewEmail(user.getEmail());
                userEntity.setEmail(user.getEmail());
            }

            if(user.getUsername() != null && !userEntity.getUsername().equals(user.getUsername())) {
                checkNewUsername(user.getUsername());
                userEntity.setUsername(user.getUsername());
            }

            userEntity.setName(user.getName() == null ? userEntity.getName() : user.getName());

            UserEntity editedUser = userRepository.save(userEntity);
            log.info("Updated user with id " + editedUser.getId());
            return IUserMapper.INSTANCE.entityToDto(editedUser);
        } else {
            throw new EntityNotFoundException("User not found for editing");
        }
    }

    private void checkNewUsername(String username) throws InvalidUserDataException {
        if (userRepository.findByUsername(username).isPresent())
            throw new InvalidUserDataException("This username already exists");
    }

    private void checkNewEmail(String email) throws InvalidUserDataException {
        if (userRepository.findByEmail(email).isPresent())
            throw new InvalidUserDataException("This email already exists");
    }

    @Override
    public void deleteUser(Long userId) throws EntityNotFoundException{
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            log.info("Deleted user with ID: {}", userId);
        } else {
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public UserDto getUserById(Long userId) throws EntityNotFoundException{
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            log.info("Retrieved user with ID: {}", user.getId());
        } else {
            log.warn("User not found with ID: {}", userId);
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }
        return IUserMapper.INSTANCE.entityToDto(user);
    }

    public UserDto getUserByUsername(String username) throws EntityNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            log.info("Retrieved user with username: {}", user.getUsername());
        } else {
            log.warn("User not found with username: {}", username);
            throw new EntityNotFoundException("User with username " + username + " not found");
        }
        return IUserMapper.INSTANCE.entityToDto(user);
    }

    public UserDto getUserByEmail(String email) throws EntityNotFoundException {
        UserEntity user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            log.info("Retrieved user with email: {}", user.getEmail());
        } else {
            log.warn("User not found with email: {}", email);
            throw new EntityNotFoundException("User with email " + email + " not found");
        }
        return IUserMapper.INSTANCE.entityToDto(user);
    }

    @Override
    public void changePassword(ChangePasswordDto changePassword) throws EntityNotFoundException, InvalidOldPasswordException{
        Optional<UserEntity> userO = userRepository.findById(changePassword.getUserId());
        if (userO.isPresent()) {
            UserDto user = IUserMapper.INSTANCE.entityToDto(userO.get());
            checkOldPassword(changePassword.getOldPassword(), user.getPassword());
            setNewPassword(userO.get(), changePassword.getNewPassword());
        } else {
            log.warn("UserEntity not found with ID: {}", changePassword.getUserId());
            throw new EntityNotFoundException("User with id " + changePassword.getUserId() + " not found");
        }
    }

    private void checkOldPassword(String oldPassword, String passwordInDb) throws InvalidOldPasswordException{
        if (!passwordEncoder.matches(oldPassword, passwordInDb))
            throw new InvalidOldPasswordException("Invalid old password");
    }

    private void setNewPassword(UserEntity user, String newPassword) {
        String encryptedNewPassword = encryptPassword(newPassword);
        user.setPassword(encryptedNewPassword);
        userRepository.save(user);
    }

    private String encryptPassword(String newPassword) {
        return passwordEncoder.encode(newPassword);
    }

}
