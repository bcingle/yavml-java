package com.github.bcingle.yavml.yavmlapi.service;

import com.github.bcingle.yavml.yavmlapi.controller.EmailTakenException;
import com.github.bcingle.yavml.yavmlapi.controller.UsernameTakenException;
import com.github.bcingle.yavml.yavmlapi.model.YavmlUser;
import com.github.bcingle.yavml.yavmlapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repo;

    public UserService(UserRepository userRepo) {
        this.repo = userRepo;
    }

    /**
     * Find a user by username or email address.  Use this method
     * when unsure whether the param is a username or an email address.
     * The presence of an at symbol (@) indicates that the param is
     * an email address.  Therefore, usernames should not be allowed to
     * contain at symbols.
     * @param param
     * @return
     */
    public YavmlUser findUserByUsernameOrEmail(String param) {
        if (param.contains("@")) {
            return findByEmail(param);
        } else {
            return findByUsername(param);
        }
    }

    /**
     * Find a user by username
     * @param username
     * @return
     */
    public YavmlUser findByUsername(String username) {
        return repo.findByUsername(username).orElse(null);
    }

    /**
     * Find a user by email address
     * @param email
     * @return
     */
    public YavmlUser findByEmail(String email) {
        return repo.findByEmail(email).orElse(null);
    }

    public boolean isUsernameAvailable(String username) {
        return repo.findByUsername(username).isEmpty();
    }

    public boolean isEmailAvailable(String email) {
        return repo.findByEmail(email).isEmpty();
    }

    /**
     * Save a user in the repository, checking that username and email are unique
     * @param user
     * @return the persisted user, which may be a different object than what was passed as an argument
     * @throws EmailTakenException if the user's email address is already in use
     * @throws UsernameTakenException if the user's username is already in use
     */
    public YavmlUser saveUnique(YavmlUser user) throws EmailTakenException, UsernameTakenException {
        if (!isUsernameAvailable(user.getUsername())) {
            throw new UsernameTakenException(user.getUsername());
        }

        if (!isEmailAvailable(user.getEmail())) {
            throw new EmailTakenException(user.getEmail());
        }

        return repo.save(user);
    }

}
