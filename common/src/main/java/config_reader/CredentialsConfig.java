package config_reader;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:credentials.properties"})
public interface CredentialsConfig extends Config {

    @Key("admin_username")
    String adminUsername();

    @Key("admin_password")
    String adminPassword();
}
