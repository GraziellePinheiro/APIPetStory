package config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Key;

@Config.Sources({"file:src/test/resources/properties/test.properties"})
public interface Configuracoes extends Config{
    @Key("baseURI")
    String baseURI();

    @Key("basePath")
    String basePath();
    
} 
