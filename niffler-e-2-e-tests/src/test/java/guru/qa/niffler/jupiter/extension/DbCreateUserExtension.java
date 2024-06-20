package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserEntity;
import guru.qa.niffler.data.repository.UserRepository;
import guru.qa.niffler.data.repository.logging.JsonAllureAppender;
import guru.qa.niffler.model.UserJson;

public class DbCreateUserExtension extends AbstractCreateUserExtension{
    UserRepository userRepository = UserRepository.getInstance();
    private final JsonAllureAppender jsonAppender = new JsonAllureAppender();

    @Override
    protected UserJson createUser(UserJson user) {
        UserAuthEntity userAuthEntity = UserAuthEntity.fromJson(user);
        userRepository.createUserInAuth(userAuthEntity);
        jsonAppender.logJson("userAuthEntity", userAuthEntity.toString());

        UserEntity userEntity = UserEntity.fromJson(user);
        userRepository.createUserInUserData(userEntity);
        jsonAppender.logJson("userEntity",userEntity.toString());

        return user;
    }
}