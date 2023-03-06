package com.adobe.aem.thermoking.site.core.listeners;

import com.adobe.aem.thermoking.site.core.utils.ResolverUtil;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.observation.ResourceChange;
import org.apache.sling.api.resource.observation.ResourceChangeListener;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import java.util.List;
import java.util.Set;

@Component(
        immediate = true,
        service = ResourceChangeListener.class,
        property = {
                ResourceChangeListener.PATHS+"=/content/thermoking/us/en/article-page",
                ResourceChangeListener.CHANGES+"=ADDED",
                ResourceChangeListener.CHANGES+"=REMOVED",
                ResourceChangeListener.CHANGES+"=CHANGED"
        }
)
public class ResourceBasedEventHandler implements ResourceChangeListener{

    private static final Logger LOG = LoggerFactory.getLogger(ResourceBasedEventHandler.class);
    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    public void onChange(List<ResourceChange> list) {
           for(ResourceChange rc : list){
               try {
                   LOG.info("\n Event : {} , Resource : {} ", rc.getType(), rc.getPath());
                   ResourceResolver resourceResolver= ResolverUtil.newResolver(resourceResolverFactory);
                   Resource resource=resourceResolver.getResource(rc.getPath());
                   Node node=resource.adaptTo(Node.class);
                   node.setProperty("Resourcechangedeventhandlertask","Event "+rc.getType()+" by "+resourceResolver.getUserID());
                   resourceResolver.commit();
               }catch (Exception e){
                   LOG.info("\n Exception : {} ", e.getMessage());
               }
           }

    }
}
