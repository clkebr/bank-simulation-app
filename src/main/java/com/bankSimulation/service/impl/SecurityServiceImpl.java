package com.bankSimulation.service.impl;

import com.bankSimulation.entity.User;
import com.bankSimulation.entity.common.UserPrincipal;
import com.bankSimulation.repository.UserRepository;
import com.bankSimulation.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //retrieve user from DB
        User user = userRepository.findByUsername(username);

        //throw exception if user does not exist
        if (user == null) {
            throw new UsernameNotFoundException("This user does not exist");
        }
        // return user information in UserDetails
        return new UserPrincipal(user);
    }
}
