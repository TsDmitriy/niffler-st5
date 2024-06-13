package guru.qa.niffler.config;

import guru.qa.niffler.data.repository.UserRepositoryHibernate;

public interface Config {

    static Config getInstance() {
        String env = System.getProperty("test.env");

        if ("local".equals(env)){
            return LocalConfig.instance;
        }
        else if ("docker".equals(env)){
            return DockerConfig.instance;
        }
        return  LocalConfig.instance;
    }

    public String frontUrl();

    public String spendUrl();

    public String gatewayUrl();

    public String currencyUrl();

    public String dbUrl();

    default int dbPort(){
        return 5432;
    };
}
