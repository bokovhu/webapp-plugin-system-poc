package me.bokov.poc;

import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import me.bokov.poc.api.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

public class PluginSystem {

    private static PluginSystem INSTANCE = new PluginSystem ();

    private static final Logger LOGGER = LoggerFactory.getLogger (PluginSystem.class);

    public static PluginSystem getInstance () {
        return INSTANCE;
    }

    public void loadPlugins (Javalin app) throws Exception {

        File pluginsDir = new File ("plugins");

        if (!pluginsDir.exists ()) {
            pluginsDir.mkdirs ();
        }

        for (File file : pluginsDir.listFiles ()) {

            if (file.isFile () && file.getName ().endsWith (".jar")) {

                JarFile jarFile = new JarFile (file);

                Attributes attributes = jarFile.getManifest ().getMainAttributes ();

                String pluginClassName = attributes.getValue ("X-Plugin-Class");

                if (pluginClassName != null && !pluginClassName.isEmpty ()) {

                    URLClassLoader classLoader = new URLClassLoader (
                            new URL[] {file.toURI ().toURL ()},
                            getClass ().getClassLoader ()
                    );

                    Class<Plugin> pluginClass = (Class<Plugin>) classLoader.loadClass (pluginClassName);

                    Plugin plugin = pluginClass.newInstance ();

                    LOGGER.info (
                            "New plugin! Name = {}, Author = {}, Version = {}, Stub = {}",
                            plugin.getName (),
                            plugin.getAuthor (),
                            plugin.getVersion (),
                            plugin.getStub ()
                    );

                    app.routes (
                            () ->
                                ApiBuilder.path (
                                        "/plugins/" + plugin.getStub (),
                                        plugin.getEndpoints ()
                                )
                    );

                }

            }

        }

    }

}
