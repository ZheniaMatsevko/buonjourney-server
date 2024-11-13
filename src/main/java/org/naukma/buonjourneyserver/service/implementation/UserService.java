package org.naukma.buonjourneyserver.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.dto.ChangePasswordDto;
import org.naukma.buonjourneyserver.dto.UserDto;
import org.naukma.buonjourneyserver.dto.UserUpdateDto;
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

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto createUser(UserDto user) {

        log.info("Creating user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity createdUser = userRepository.save(IUserMapper.INSTANCE.dtoToEntity(user));

        log.info("User created successfully.");
        return IUserMapper.INSTANCE.entityToDto(createdUser);
    }

    @Override
    public UserDto updateUser(UserUpdateDto user) {
        Optional<UserEntity> optional = userRepository.findById(user.getId());
        /*if (optional.isPresent()) {
            UserEntity userEntity = optional.get();
            if (!userEntity.getEmail().equals(user.getEmail())) {
                checkNewEmail(user.getEmail());
            }
            userEntity.setEmail(user.getEmail());
            userEntity.setLastname(user.getLastname());
            userEntity.setFirstname(user.getFirstname());
            if (!userEntity.getUsername().equals(user.getUsername())) {
                checkNewUsername(user.getUsername());
            }
            userEntity.setUsername(user.getUsername());
            UserEntity editedUser = userRepository.save(userEntity);
            log.info("Updated user with id " + editedUser.getId());
            try {
                if (file != null) {
                    String imagePath = ImagesManager.saveProfileImage(file, editedUser.getId());
                    userRepository.updateImageUrl(editedUser.getId(), imagePath);
                    editedUser.setProfileImageUrl(imagePath);
                }
                log.info("User edited successfully.");
                return IUserMapper.INSTANCE.entityToDto(editedUser);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else {
            throw new EntityNotFoundException("User not found for editing");
        }*/
        return null;
    }

    private void checkNewUsername(String username) {
        if (userRepository.findByUsername(username).isPresent())
            throw new InvalidUserDataException("This username already exists");
    }

    private void checkNewEmail(String email) {
        if (userRepository.findByEmail(email).isPresent())
            throw new InvalidUserDataException("This email already exists");
    }

    @Override
    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            log.info("Deleted user with ID: {}", userId);
        } else {
            log.warn("User not found for deletion with ID: {}", userId);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public UserDto getUserById(Long userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            log.info("Retrieved user with ID: {}", user.getId());
        } else {
            log.warn("User not found with ID: {}", userId);
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }
        return IUserMapper.INSTANCE.entityToDto(user);
    }

    public UserDto getUserByUsername(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        return user.map(IUserMapper.INSTANCE::entityToDto).orElse(null);
    }

    public UserDto getUserByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.map(IUserMapper.INSTANCE::entityToDto).orElse(null);
    }

    @Override
    public void changePassword(ChangePasswordDto changePassword) {
        Optional<UserEntity> userO = userRepository.findById(changePassword.getId());
        if (userO.isPresent()) {
            UserDto user = IUserMapper.INSTANCE.entityToDto(userO.get());
            checkOldPassword(changePassword.getOldPassword(), user.getPassword());
            setNewPassword(userO.get(), changePassword.getNewPassword());
        } else {
            log.warn("UserEntity not found with ID: {}", changePassword.getId());
            throw new EntityNotFoundException("User with id " + changePassword.getId() + " not found");
        }
    }

    private void checkOldPassword(String oldPassword, String passwordInDb) {
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
