package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.userAuth.Authority;
import guru.qa.niffler.data.entity.userAuth.UserAuthEntity;
import guru.qa.niffler.data.entity.userData.UserDataEntity;
import guru.qa.niffler.data.sjdbc.AuthorityEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    static UserRepository getInstance(){
        if("sjdbc".equals(System.getProperty("repo"))){
            return new UserRepositorySpringJdbc();
        }
        if("sjdbc".equals(System.getProperty("repo"))){
            return new UserRepositoryHybernate();
        }
        return new UserRepositoryJdbc();
    }

    Object createUserInAuth(UserAuthEntity user);
    Object createUserInUserData(UserDataEntity user);

    Object updateUserInAuth (UserAuthEntity user, List<String> authorityListForSet);

    Object updateUserInUserdata (UserDataEntity user);

    Optional<UserDataEntity> findUserInUserDataById(UUID uuid);
}