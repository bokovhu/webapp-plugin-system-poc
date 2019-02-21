package me.bokov.poc;

import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;

public class Main {

    public static void main (String [] args) {

        Javalin app = Javalin.create ().start (8080);

        app.get ("/", ctx -> ctx.status (200).result ("Hello, world!"));

        try {
            PluginSystem.getInstance ().loadPlugins (app);
        } catch (Exception exc) {
            exc.printStackTrace ();
        }

    }

}
