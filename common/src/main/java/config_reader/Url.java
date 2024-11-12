package config_reader;
import org.aeonbits.owner.Config;
public interface Url extends Config{
    @Key("base.url")
    String base_url();
}
