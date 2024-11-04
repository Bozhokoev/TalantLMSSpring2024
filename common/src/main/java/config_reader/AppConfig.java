package config_reader;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:app.properties"})
public interface AppConfig extends Config {
    @Key("default.implicitly.wait")
    int implicitlyWait();

    @Key("default.implicitly.sleep")
    int implicitlySleep();

    @Key("docker.remote")
    boolean remote();

    @Key("remote.url.docker")
    String dockerUrl();
}