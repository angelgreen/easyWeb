package org.easyweb.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * persistent Utils
 */
public class PersistentUtils {

    private static SessionFactory persistentUtils = createPersistent();


    private static SessionFactory createPersistent() {
        try{

            return new Configuration().configure().buildSessionFactory();
        }catch (Throwable e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return persistentUtils;
    }
}
