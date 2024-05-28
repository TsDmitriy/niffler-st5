package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.userAuth.UserAuthEntity;
import guru.qa.niffler.data.entity.userData.UserDataEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryHibernate implements UserRepository{
    @Override
    public UserAuthEntity createUserInAuth(UserAuthEntity user) {
        return null;
    }

    @Override
    public UserDataEntity createUserInUserData(UserDataEntity user) {
        return null;
    }

    @Override
    public UserAuthEntity updateUserInAuth(UserAuthEntity user) {
        return null;
    }

    @Override
    public UserDataEntity updateUserInUserdata(UserDataEntity user) {
        return null;
    }

    @Override
    public Optional<UserDataEntity> findUserInUserDataById(UUID uuid) {
        return Optional.empty();
    }
}