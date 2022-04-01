package com.motorcorp;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;
@Configuration
public class AngularResourceHandler extends PathResourceResolver {
    @Override
    protected Resource getResource(String resourcePath, Resource location) throws IOException {
        Resource requestedResource = location.createRelative(resourcePath);

        Resource defaultResource = location.createRelative("/index.html");
        return requestedResource.exists() && requestedResource.isReadable() ? requestedResource : defaultResource;
    }
}
