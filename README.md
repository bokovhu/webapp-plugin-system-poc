# Plugin System POC

This repository contains a working proof-of-concept, that experiments with the topic of
creating a plugin (or extension) system for java web applications. 

This POC works in the following manner:

* The application uses Javalin to handle HTTP requests.
* The `plugin-api` project is meant to be added as a `compile` dependency to plugins. It 
    references `javalin` too, and contains the `Plugin` interface, which should be 
    implemented by a class in the plugin itself
* Built plugins should be `.jar` files, which have a manifest property called `X-Plugin-Class`,
    and the value of this manifest attribute should be the fully qualified name of the class
    that implements the `Plugin` interface.
* After starting the web server, the `PluginSystem` class looks for `.jar` files in the `plugins`
    directory (which, if does not yet exist is created), and if it finds one, which has the 
    `X-Plugin-Class` manifest attribute, it tries to instantiate the class it points to.
* The instantiated object is casted to `Plugin`, and the plugin can now register Javalin endpoints
    via implementing the `getEndpointGroup ()` function. 
* All endpoints are placed under `/plugins/{Plugin Stub}`

In a real-world application, the `plugin-api` module should contain the code plugins can call.
Plugins should also be able to use the database, register their own JPA entities, access application
services, etc.