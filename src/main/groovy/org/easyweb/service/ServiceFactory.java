package org.easyweb.service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public final class ServiceFactory {

    private static final Logger LOG = Logger.getLogger(ServiceFactory.class.getName());

    private static final Map<Class<? extends Service>, Object> serviceMap = new HashMap<Class<? extends Service>, Object>();

    private ServiceFactory() {
        super();
    }


    /**
     * single object
     */
    private static synchronized <T extends Service> T getService(Class<T> clazz) {
        T t = null;

        if (serviceMap.get(clazz) == null) {
            try {
                t = clazz.cast(clazz.newInstance());
                serviceMap.put(clazz, t);
            } catch (Exception e) {
                //just ignore it ...
                LOG.warning(String.format("get the service %s error", clazz.getSimpleName()));
            }
        } else {
            t = clazz.cast(serviceMap.get(clazz));
        }
        return t;
    }
}
