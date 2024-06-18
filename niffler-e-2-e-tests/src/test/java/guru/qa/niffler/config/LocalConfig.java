package guru.qa.niffler.config;

public class LocalConfig implements Config {

    private static final String LOCAL_HOST_URL = "http://127.0.0.1";
    static final LocalConfig instance = new LocalConfig();

    private LocalConfig() {
    }

    @Override
    public String frontUrl() {
        return LOCAL_HOST_URL + ":3000/";
    }

    @Override
    public String spendUrl() {
        return LOCAL_HOST_URL + ":8093/";
    }

    @Override
    public String gatewayUrl() {
        return LOCAL_HOST_URL + ":8090/";
    }

    @Override
    public String currencyUrl() {
        return LOCAL_HOST_URL + ":8092";
    }

    @Override
    public String dbUrl() {
        return "127.0.0.1";
    }
}
