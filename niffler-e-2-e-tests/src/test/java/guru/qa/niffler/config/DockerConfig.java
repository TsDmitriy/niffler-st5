package guru.qa.niffler.config;

public class DockerConfig implements Config {

    static final DockerConfig instance = new DockerConfig();

    private DockerConfig() {
    }

    @Override
    public String frontUrl() {
        return "http:/frontend.niffler.dc:8093/";
    }

    @Override
    public String spendUrl() {
        return "http:/spend.niffler.dc:8093/";
    }

    @Override
    public String gatewayUrl() {
        return "http:/gateway.niffler.dc:8090/";
    }

    @Override
    public String currencyUrl() {
        return "http:/urrency.niffler.dc:8090/";
    }

    @Override
    public String dbUrl() {
        return "niffler-all-db";
    }
}
