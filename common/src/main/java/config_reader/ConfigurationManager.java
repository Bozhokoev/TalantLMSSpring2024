package config_reader;

import org.aeonbits.owner.ConfigCache;

public class ConfigurationManager {

    private ConfigurationManager() {
    }


    public static AppConfig getConfig() {
        return ConfigCache.getOrCreate(AppConfig.class);
    }

    public static Url getUrlConfiguration(){
        return ConfigCache.getOrCreate(Url.class);
    }

    public static CredentialsConfig getCredentials() {
        return ConfigCache.getOrCreate(CredentialsConfig.class);
    }
}
