package me.bokov.poc.testplugin;

import io.javalin.apibuilder.EndpointGroup;
import me.bokov.poc.api.Plugin;
import static io.javalin.apibuilder.ApiBuilder.*;

public class TestPlugin implements Plugin {

    @Override
    public String getName () {
        return "Test Plugin";
    }

    @Override
    public String getStub () {
        return "test";
    }

    @Override
    public String getAuthor () {
        return "Botond János Kovács";
    }

    @Override
    public String getVersion () {
        return "0.0.1";
    }

    @Override
    public EndpointGroup getEndpoints () {
        return () -> {
            get ("hello", ctx -> ctx.status (200).result ("Hello from plugin!"));
        };
    }

}
