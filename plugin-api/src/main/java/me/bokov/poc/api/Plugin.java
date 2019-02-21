package me.bokov.poc.api;

import io.javalin.apibuilder.EndpointGroup;

public interface Plugin {

    String getName ();
    String getStub ();
    String getAuthor ();
    String getVersion ();
    EndpointGroup getEndpoints ();

}
