package com.sonsenim.sonsenimbackend.service;

import com.sonsenim.sonsenimbackend.api.model.RegistrationBody;
import com.sonsenim.sonsenimbackend.exception.UserAlreadyExistsException;
import com.sonsenim.sonsenimbackend.mappers.LocalUserMapper;
import com.sonsenim.sonsenimbackend.model.LocalUser;
import com.sonsenim.sonsenimbackend.api.model.LoginBody;
import com.sonsenim.sonsenimbackend.model.dao.LocalUserDAO;
import com.sonsenim.sonsenimbackend.model.dto.LocalUserDTO;
import com.sonsenim.sonsenimbackend.repositories.CardsRepository;
import com.sonsenim.sonsenimbackend.repositories.DecksRepository;
import com.sonsenim.sonsenimbackend.repositories.LocalUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final LocalUserDAO localUserDAO;
    private final EncryptionService encryptionService;
    private final JWTService jwtService;
    private final LocalUserRepository localUserRepository;
    private final DecksRepository decksRepository;
    private final CardsRepository cardsRepository;

    public UserService(LocalUserDAO localUserDAO, EncryptionService encryptionService, JWTService jwtService,
                       LocalUserRepository localUserRepository, DecksRepository decksRepository, CardsRepository cardsRepository) {
        this.localUserDAO = localUserDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
        this.localUserRepository = localUserRepository;
        this.decksRepository = decksRepository;
        this.cardsRepository = cardsRepository;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
        if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        LocalUser user = new LocalUser();
        user.setUsername(registrationBody.getUsername());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setEmail(registrationBody.getEmail());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        return localUserDAO.save(user);
    }

    public String loginUser(LoginBody loginBody) {
        Optional<LocalUser> optionalUser = localUserDAO.findByUsernameIgnoreCase(loginBody.getUsername());
        if (optionalUser.isPresent()) {
            LocalUser user = optionalUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                return jwtService.createToken(user);
            }
        }
        return null;
    }

    public LocalUserDTO getUserWithDTO(Long id) {
        try {
            Optional<LocalUser> localUser = localUserRepository.findById(id);
            long totalDecks = decksRepository.countByGroups_LocalUser_Id(id);
            long totalCards = cardsRepository.countByDeck_Groups_LocalUser_Id(id);
            return LocalUserMapper.toDTO(localUser, totalCards, totalDecks);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
