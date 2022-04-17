package com.jwt;

import com.jwt.api.User;
import com.jwt.api.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {
    @Autowired
    UserRepository respository;


    @Test
    void testUserRepository() {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password= "abdo1020";
        String encodedPassword=passwordEncoder.encode(password);
        User user= User.builder().email("abdo@gmail.com").password(encodedPassword).build();
        User userSave=respository.save(user);
        Assertions.assertThat(userSave).isNotNull();
        Assertions.assertThat(userSave.getId()).isGreaterThan(0);
    }
}
