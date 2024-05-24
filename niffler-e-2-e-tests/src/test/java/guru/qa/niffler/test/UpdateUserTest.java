package guru.qa.niffler.test;

import com.github.javafaker.Faker;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.entity.userAuth.Authority;
import guru.qa.niffler.data.entity.userAuth.UserAuthEntity;
import guru.qa.niffler.data.entity.userData.CurrencyValues;
import guru.qa.niffler.data.entity.userData.UserDataEntity;
import guru.qa.niffler.data.repository.SpendRepositoryJdbc;
import guru.qa.niffler.data.repository.SpendRepositorySpringJdbc;
import guru.qa.niffler.data.repository.UserRepository;
import guru.qa.niffler.data.repository.UserRepositoryJdbc;
import guru.qa.niffler.data.repository.UserRepositorySpringJdbc;
import guru.qa.niffler.pages.WelcomePage;
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
    UserRepository userRepository = new UserRepositoryJdbc();
    SpendRepositoryJdbc spendRepositorySpringJdbc = new SpendRepositoryJdbc();

    @BeforeEach
    void createUser() {
        UserAuthEntity userAuth = new UserAuthEntity();
        userAuth.setId(UUID.fromString("f71cc872-169a-11ef-a275-0242ac110002"));
        userAuth.setUsername("jdbc_user2_update12");
        userAuth.setPassword("12345");
        userAuth.setEnabled(true);
        userAuth.setAccountNonExpired(true);
        userAuth.setAccountNonLocked(true);
        userAuth.setCredentialsNonExpired(true);

        List<String> authorityListForSet = Arrays.asList("read", "write");;
        userRepository.updateUserInAuth(userAuth, authorityListForSet);
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

        List<String> authorityListForSet = Arrays.asList("read", "write");;
        userRepository.updateUserInAuth(userAuth, authorityListForSet);
    }

    @Test
    void updateUserInData () throws SQLException {
        UserDataEntity userAuth = new UserDataEntity();
        userAuth.setId(UUID.fromString("1fb1566e-1690-11ef-a335-0242ac110002"));
        userAuth.setUsername("jdbc_user2_туц");
        userAuth.setCurrency(CurrencyValues.RUB);

        Optional<UserDataEntity> userDataEntity = userRepository.findUserInUserDataById(UUID.fromString("1fb1566e-1690-11ef-a335-0242ac110002"));
        List<SpendEntity> spendEntity = spendRepositorySpringJdbc.findAllByUsername("DIMA");
        userRepository.updateUserInUserdata(userAuth);

        userRepository.findUserInUserDataById(userDataEntity.get().getId());
    }
}