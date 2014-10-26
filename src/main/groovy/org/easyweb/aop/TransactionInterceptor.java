package org.easyweb.aop;

import org.hibernate.*;
import org.easyweb.utils.PersistentUtils;

/**
 * Created by angelgreen on 14-4-23.
 */
public class TransactionInterceptor implements Interceptor {
    @Override
    public void intercept(ActionInvocation ai) {
        SessionFactory sessionFactory = PersistentUtils.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            System.out.println("ok start transaction");
            //chain to the next
            ai.invoke();

            transaction.commit();
        } catch (Throwable t) {
            if (t instanceof HibernateException || t instanceof AssertionFailure) {
                if (null != transaction)
                    transaction.rollback();
            }
            System.out.println("transaction end");
            throw new RuntimeException(t);
        }
    }
}
