package com.example.synchrony.service;

import com.example.synchrony.dto.RegisterRequest;
import com.example.synchrony.entity.UserAccount;
import com.example.synchrony.repo.UserAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {
    private final UserAccountRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserAccountRepository repo, PasswordEncoder encoder) {
        this.repo = repo; this.encoder = encoder;
    }

    @Transactional
    public UserAccount register(RegisterRequest req) {
        if (repo.existsByUsername(req.username())) {
            throw new IllegalArgumentException("Username already taken");
        }
        UserAccount ua = new UserAccount();
        ua.setUsername(req.username());
        ua.setPassword(encoder.encode(req.password()));
        ua.setFullName(req.fullName());
        return repo.save(ua);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
    }
}
