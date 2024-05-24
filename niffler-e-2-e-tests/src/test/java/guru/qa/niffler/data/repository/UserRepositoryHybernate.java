package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.userAuth.Authority;
import guru.qa.niffler.data.entity.userAuth.UserAuthEntity;
import guru.qa.niffler.data.entity.userData.UserDataEntity;
import guru.qa.niffler.data.sjdbc.AuthorityEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryHybernate implements UserRepository{
    @Override
    public Object createUserInAuth(UserAuthEntity user) {
        return null;
    }

    @Override
    public Object createUserInUserData(UserDataEntity user) {
        return null;
    }

    @Override
    public Object updateUserInAuth(UserAuthEntity user,  List<String> authorityListForSet) {
        return null;
    }

    @Override
    public Object updateUserInUserdata(UserDataEntity user) {
        return null;
    }

    @Override
    public Optional<UserDataEntity> findUserInUserDataById(UUID uuid) {
        return Optional.empty();
    }
}