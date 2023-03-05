package com.adobe.aem.thermoking.site.core.utils;

import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

public class ResolverUtil {

    public final static String THERMOKING_SYSTEM_USER = "thermokingsystemuser";

    /**
     * @param resourceResolverFactory factory
     * @return new resource resolver for Sony service user
     * @throws LoginException if problems
     */
    public static ResourceResolver newResolver(ResourceResolverFactory resourceResolverFactory) throws LoginException {
        final Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(ResourceResolverFactory.SUBSERVICE, THERMOKING_SYSTEM_USER);

        // fetches the admin service resolver using service user.
        ResourceResolver resolver = resourceResolverFactory.getServiceResourceResolver(paramMap);

        return resolver;
    }
}
