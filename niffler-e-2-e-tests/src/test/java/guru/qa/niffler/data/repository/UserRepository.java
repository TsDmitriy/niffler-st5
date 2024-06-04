package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    static UserRepository getInstance(){
        final String repo = System.getProperty("repo");

        if("sjdbc".equals(repo)){
            return new UserRepositorySpringJdbc();
        }
        if("sjdbc".equals(repo)){
            return new UserRepositoryJdbc();
        }
        return new UserRepositoryHibernate();
    }
    Optional<UserAuthEntity> findUserInAuth(String userName);

    UserAuthEntity createUserInAuth(UserAuthEntity user);

    UserEntity createUserInUserData(UserEntity user);

    UserAuthEntity updateUserInAuth (UserAuthEntity user);

    UserEntity updateUserInUserdata (UserEntity user);

    Optional<Object> findUserInUserData(String userName);

    Optional<UserEntity> findUserInUserDataById(UUID uuid);
}