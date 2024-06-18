package guru.qa.niffler.test;

import guru.qa.niffler.api.GatewayApiClient;
import guru.qa.niffler.config.Config;

public abstract class BaseTest {

    protected static final Config CFG = Config.getInstance();
    protected GatewayApiClient gatewayApiClient = new GatewayApiClient();
}
