package guru.qa.niffler.test;

import com.github.javafaker.Faker;
import guru.qa.niffler.data.entity.CurrencyValues;
import guru.qa.niffler.data.entity.SpendEntity;

import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserEntity;
import guru.qa.niffler.data.repository.SpendRepositoryJdbc;
import guru.qa.niffler.data.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
/**
 * Условные тесты, для проверки работы методов по изменению пользователя
 */
public class UpdateUserTest {
    UserRepository userRepository = UserRepository.getInstance();
    SpendRepositoryJdbc spendRepositorySpringJdbc = new SpendRepositoryJdbc();

    @BeforeEach
    void createUser() {
        UserAuthEntity userAuth = new UserAuthEntity();
        userAuth.setId(UUID.fromString("f71cc872-169a-11ef-a275-0242ac110011"));
        userAuth.setUsername("jdbc_user2_update12222");
        userAuth.setPassword("12345");
        userAuth.setEnabled(true);
        userAuth.setAccountNonExpired(true);
        userAuth.setAccountNonLocked(true);
        userAuth.setCredentialsNonExpired(true);

        userRepository.updateUserInAuth(userAuth);
    }

    @Test
    void updateUserInAuth () {
        UserAuthEntity userAuth = new UserAuthEntity();
        userAuth.setId(UUID.fromString("f71cc872-169a-11ef-a275-0242ac110002"));
        userAuth.setUsername("jdbc_user2_update" + new Faker().number().digits(3));
        userAuth.setPassword("12345");
        userAuth.setEnabled(true);
        userAuth.setAccountNonExpired(true);
        userAuth.setAccountNonLocked(true);
        userAuth.setCredentialsNonExpired(true);

        userRepository.updateUserInAuth(userAuth);
    }

    @Test
    void updateUserInData () throws SQLException {
        UserEntity userAuth = new UserEntity();
        userAuth.setId(UUID.fromString("1fb1566e-1690-11ef-a335-0242ac110002"));
        userAuth.setUsername("jdbc_user2_туц111");
        userAuth.setCurrency(CurrencyValues.RUB);

        Optional<UserEntity> userDataEntity = userRepository.findUserInUserDataById(UUID.fromString("1fb1566e-1690-11ef-a335-0242ac110002"));
        List<SpendEntity> spendEntity = spendRepositorySpringJdbc.findAllByUsername("DIMA");
        userRepository.updateUserInUserdata(userAuth);

        userRepository.findUserInUserDataById(userDataEntity.get().getId());
    }
}