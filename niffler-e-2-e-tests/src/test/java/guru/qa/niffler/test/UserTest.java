package guru.qa.niffler.test;

import guru.qa.niffler.data.entity.Authority;
import guru.qa.niffler.data.entity.AuthorityEntity;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.CurrencyValues;
import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.data.repository.UserRepository;
import org.junit.jupiter.api.Test;

/**
 * Условные тесты, для проверки работы методов по созданию пользователя
 */
public class UserTest {
    private final UserRepository userRepository = UserRepository.getInstance();
    private final SpendRepository spendRepository = SpendRepository.getInstance();

    @Test
    void createUser() {
        AuthorityEntity read = new AuthorityEntity();
        read.setAuthority(Authority.read);
        AuthorityEntity write = new AuthorityEntity();
        write.setAuthority(Authority.write);

        UserAuthEntity userAuth = new UserAuthEntity();
        userAuth.setUsername("jdbc_user999991");
        userAuth.setPassword("12345");
        userAuth.setEnabled(true);
        userAuth.setAccountNonExpired(true);
        userAuth.setAccountNonLocked(true);
        userAuth.setCredentialsNonExpired(true);
        userAuth.addAuthorities(read, write);
        userRepository.createUserInAuth(userAuth);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("jdbc_user999991");
        userEntity.setCurrency(CurrencyValues.RUB);
        userRepository.createUserInUserData(userEntity);
    }

    @Test
    void findCategoryAndUser() {
        CategoryEntity categoryEntity =  spendRepository.findCategory("Обучение2222");
        UserAuthEntity userAuthEntity =  userRepository.findUserInAuth("jdbc_user2_update12222");
        UserEntity user = userRepository.findUserInUserData("jdbc_user12211");
        System.out.println("");
    }
}
