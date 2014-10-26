package org.easyweb.daoImpl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.easyweb.utils.PersistentUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AbstractDAOImpl {

    protected final SessionFactory factory = PersistentUtils.getSessionFactory();

    //saveUser the object
    protected <T> void save(T t, String[] msgs) {

        //warning get current thread session
        Session session = factory.getCurrentSession();
        try {
            session.save(t);
        } catch (Throwable e) {
            msgs[0] = "error";
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //delete the object
    protected <T> void delete(T t, String[] msgs) {
        Session session = factory.getCurrentSession();
        try {
            session.delete(t);
        } catch (Throwable e) {
            e.printStackTrace();
            msgs[0] = "error";
            throw new RuntimeException(e);
        }
    }

    //update the object
    protected <T> void update(T t, String[] msgs) {
        Session session = factory.getCurrentSession();
        try {
            session.update(t);
        } catch (Throwable e) {
            e.printStackTrace();
            msgs[0] = "error";
        }
    }

    //load the object
    @SuppressWarnings("unchecked")
    protected <T extends Serializable> T load(T t, Class<? extends T> clazz, String[] msgs) {
        Session session = factory.getCurrentSession();
        try {
            return (T) session.load(clazz, t);
        } catch (Throwable e) {
            e.printStackTrace();
            msgs[0] = "error";
        }

        return null;
    }

    //query
    @SuppressWarnings("unchecked")
    protected <T> List<T> queryMany(String sql, Map<String, Object> paras, String[] msgs) {

        Session session = factory.getCurrentSession();
        Query query = session.createQuery(sql);

        for (String key : paras.keySet()) {
            query.setParameter(key, paras.get(key));
        }

        try {
            return (List<T>) query.list();
        } catch (Throwable e) {
            e.printStackTrace();
            msgs[0] = "error";
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    protected <T> T querySingle(String sql, Map<String, Object> paras, String[] msgs) {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery(sql);
        //single
        query.setMaxResults(1);
        for (String key : paras.keySet()) {
            query.setParameter(key, paras.get(key));
        }


        try {
            return (T)query.uniqueResult();
        } catch (Throwable e) {
            e.printStackTrace();
            msgs[0] = "error";
        }

        return null;
    }
}
