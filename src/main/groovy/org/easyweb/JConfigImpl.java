package org.easyweb;

import org.easyweb.controller.*;

public class JConfigImpl extends JConfig {
    @Override
    public void configure(final Route route) {

        route.put("test", TestController.class);
    }
}
