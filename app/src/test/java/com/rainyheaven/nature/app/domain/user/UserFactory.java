package com.rainyheaven.nature.app.domain.user;

import com.rainyheaven.nature.core.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final UserRepository userRepository;



}
