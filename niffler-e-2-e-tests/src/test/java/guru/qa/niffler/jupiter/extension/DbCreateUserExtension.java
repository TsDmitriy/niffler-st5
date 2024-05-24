package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.userAuth.UserAuthEntity;
import guru.qa.niffler.data.entity.userData.UserDataEntity;
import guru.qa.niffler.data.repository.UserRepository;
import guru.qa.niffler.model.UserJson;

public class DbCreateUserExtension extends AbstractCreateUserExtension{
    UserRepository userRepository = UserRepository.getInstance();

    @Override
    protected UserJson createUser(UserJson user) {
        userRepository.createUserInAuth(UserAuthEntity.fromJson(user));
        userRepository.createUserInUserData(UserDataEntity.fromJson(user));
        return user;
    }
}