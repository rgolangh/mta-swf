package com.redhat.mta;

import java.util.Map;

import org.kie.kogito.internal.process.runtime.KogitoWorkItem;
import org.kogito.workitem.rest.decorators.RequestDecorator;

import io.vertx.mutiny.ext.web.client.HttpRequest;


public class SSLRequestDecorator implements RequestDecorator {

    @Override
    public void decorate(KogitoWorkItem item, Map<String, Object> parameters, HttpRequest<?> request) {
          
        request.ssl(true);
   
    }
    
}
