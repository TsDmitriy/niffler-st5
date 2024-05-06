package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

import static guru.qa.niffler.model.UserJson.userForTest;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UsersQueueExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    private static final Map<User.Selector, Queue<UserJson>> USERS = new ConcurrentHashMap<>();

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UsersQueueExtension.class);

    static {

        USERS.put(User.Selector.FRIEND, new ConcurrentLinkedQueue<>(
                List.of(userForTest("dotsarev", "dotsarev")))
        );
        USERS.put(User.Selector.INVITE_RECEIVED, new ConcurrentLinkedQueue<>(
                List.of(userForTest("DOG", "DOG")))
        );
        USERS.put(User.Selector.INVITE_SENT, new ConcurrentLinkedQueue<>(
                List.of(userForTest("CAT", "CAT")))
        );
    }

    @Override
    public void beforeEach(ExtensionContext context) {

        Method testMethod = context.getRequiredTestMethod();

        List<Method> beforeEachMethods = Arrays.stream(
                context.getRequiredTestClass().getDeclaredMethods()
        ).filter(i -> i.isAnnotationPresent(BeforeEach.class)).toList();

        List<Method> methods = new ArrayList<>();
        methods.add(testMethod);
        methods.addAll(beforeEachMethods);

        List<Parameter> parameters = methods.stream()
                .flatMap(m -> Arrays.stream(m.getParameters()))
                .filter(p -> p.isAnnotationPresent(User.class))
                .toList();

        Map<User.Selector, List<UserJson>> users = new HashMap<>();
        for (Parameter parameter : parameters) {
            User.Selector selector = parameter.getAnnotation(User.class).selector();

            UserJson userForTest = null;

            Queue<UserJson> queue = USERS.get(selector);

            while (userForTest == null) {
                userForTest = queue.poll();
            }

            /*
            Данный if необходим для случая, если в тесте передается две одинаковых аннотации(например FRIEND),
            чтобы у нас было два разных юзера с одним типом (например FRIEND).
             */
            if (users.containsKey(selector)) {
                List<UserJson> userList = new ArrayList<>(users.get(selector));
                userList.add(userForTest);
                users.put(selector, userList);
            } else {
                users.put(selector, List.of(userForTest));
            }

            context.getStore(NAMESPACE).put(context.getUniqueId(), users);

            Allure.getLifecycle().updateTestCase(testCase -> testCase.setStart(new Date().getTime()));
        }
    }


    @Override
    public void afterEach(ExtensionContext extensionContext) {
        Map<User.Selector, List<UserJson>> usersFromTest =
                extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), Map.class);
        for (Map.Entry<User.Selector, List<UserJson>> users : usersFromTest.entrySet()) {
            USERS.get(users.getKey()).addAll(users.getValue());
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserJson.class) &&
                parameterContext.getParameter().isAnnotationPresent(User.class);
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        User.Selector selector = parameterContext.getParameter().getAnnotation(User.class).selector();

        Map<User.Selector, List<UserJson>> users = extensionContext
                .getStore(NAMESPACE)
                .get(extensionContext.getUniqueId(), Map.class);

        List<UserJson> userList = users.get(selector);
        UserJson user = null;

        if (userList != null && !userList.isEmpty()) {
            /*
            Данный if необходим для случая, если в тесте передается две одинаковых аннотации(например FRIEND),
            чтобы передавать в тест юзера который еще туда не был передан ранее.
             */
            if (userList.size() > 1) {
                user = userList.getFirst();
                userList.remove(user);
            } else {
                user = userList.getFirst();
            }
        }

        return user;
    }
}

