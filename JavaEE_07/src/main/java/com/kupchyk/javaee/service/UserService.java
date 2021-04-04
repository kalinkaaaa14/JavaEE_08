package com.kupchyk.javaee.service;

import com.kupchyk.javaee.dto.UserDto;
import com.kupchyk.javaee.model.Book;
import com.kupchyk.javaee.model.Role;
import com.kupchyk.javaee.model.User;
import com.kupchyk.javaee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepository.findUserByUsernameIgnoreCase(username);
        if (!foundUser.isPresent()) {
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
        return foundUser.get();
    }

    @Transactional
    public List<String> signup(UserDto userDto) {

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());

        List<String> errors = new ArrayList<>();

        Optional<User> usrByUsrnam = userRepository.findUserByUsernameIgnoreCase(user.getUsername());
        Optional<User> usrByMail = userRepository.findUserByEmailIgnoreCase(user.getEmail());

        if (usrByUsrnam.isPresent()) {
            errors.add("Username is taken. Try again");
        }

        if (usrByMail.isPresent()) {
            errors.add("Mail is taken. Try again");
        }
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setRoles(Collections.singleton(new Role(1L, "USER")));
        if(errors.isEmpty()){
            userRepository.save(user);
        }
        return errors;
    }

    @Transactional
    public List<String> login(String username, String password) {
        List<String> errors = new ArrayList<>();
        Optional<User> foundUser = userRepository.findUserByUsernameIgnoreCase(username);
        if (!foundUser.isPresent()) {
            errors.add("Such username doesn't exist");
        }else {
            if(!bCryptPasswordEncoder.matches(password,foundUser.get().getPassword())){
                errors.add("Password is incorrect");
            }
        }
        return errors;
    }

    @Transactional
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsernameIgnoreCase(username);
    }

    @Transactional
    public void fav(Optional<User> user, Book book) {
        user.get().getFavoriteBooks().add(book);
        book.getLikedByUsers().add(user.get());
        userRepository.save(user.get());
    }

    @Transactional
    public void favDel(Optional<User> user, Book book) {
        user.get().getFavoriteBooks().remove(book);
        book.getLikedByUsers().remove(user.get());
        userRepository.save(user.get());
    }
}