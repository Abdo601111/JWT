package com.jwt;

import com.jwt.api.Role;
import com.jwt.api.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTest {
    @Autowired
    RoleRepository roleRepository;

    @Test
    void testRoleRepository() {
        Role role= new Role("ROLE_ADMIN");
        Role role1= new Role("ROLE_MANGER");
        Role role2= new Role("ROLE_EDITOR");
        Role role3= new Role("ROLE_CUSTOMER");

        roleRepository.saveAll(List.of(role,role1,role2,role3));
        long count= roleRepository.count();
        Assertions.assertEquals(4,count);


    }

    @Test
    void testFindAllRoles() {


        List<Role> roles = roleRepository.findAll();
        org.assertj.core.api.Assertions.assertThat(roles.size()).isGreaterThan(0);
        roles.forEach(System.out::println);

    }


}
