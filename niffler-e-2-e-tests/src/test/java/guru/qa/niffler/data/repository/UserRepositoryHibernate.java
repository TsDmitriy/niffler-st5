package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.DataBase;
import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserEntity;

import guru.qa.niffler.data.jpa.EmProvider;
import jakarta.persistence.EntityManager;
import org.hibernate.query.Query;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

public class UserRepositoryHibernate implements UserRepository{
    private final EntityManager authEm = EmProvider.dataSource(DataBase.AUTH);
    private final EntityManager udEm = EmProvider.dataSource(DataBase.USERDATA);
    private final static PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();


    @Override
    public Optional<UserAuthEntity> findUserInAuth(String username) {
        return Optional.ofNullable(authEm.createQuery("FROM UserAuthEntity WHERE username = :username", UserAuthEntity.class)
                .setParameter("username", username)
                .getSingleResult());
    }

    @Override
    public UserAuthEntity createUserInAuth(UserAuthEntity user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        authEm.persist(user);
        return user;
    }

    @Override
    public Optional<Object> findUserInUserData(String username) {
        return Optional.ofNullable(udEm.createQuery("FROM UserEntity WHERE username = :username", UserEntity.class)
                .setParameter("username", username).getSingleResult());
    }

    @Override
    public UserEntity createUserInUserData(UserEntity user) {
        udEm.persist(user);
        return user;
    }

    @Override
    public UserAuthEntity updateUserInAuth(UserAuthEntity user) {
        return authEm.merge(user);
    }

    @Override
    public UserEntity updateUserInUserdata(UserEntity user) {
        return udEm.merge(user);
    }

    @Override
    public Optional<UserEntity> findUserInUserDataById(UUID uuid) {
        return Optional.ofNullable(authEm.find(UserEntity.class, uuid));
    }
}