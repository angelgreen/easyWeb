package org.easyweb.dao

import org.hibernate.Session
import org.hibernate.Transaction
import org.junit.After
import org.junit.Before
import org.easyweb.utils.PersistentUtils


/**
 * Created by angelgreen on 10/26/14.
 */
class BaseDAOTest {

    def sessionFactory
    Transaction transaction

    String[] args = new String[1]
    @Before
    public void setUp() {
        //set up transaction
        sessionFactory = PersistentUtils.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        transaction = session.beginTransaction();
    }

    @After
    public void tearDown() {
        if (null != transaction && !transaction.wasCommitted()){
            printf "%s\n", "rollback"
            transaction.rollback()
        }

    }

    def commit() {
       if(null != transaction){
           transaction.commit()
           printf "%s\n","commit"
       }
    }
}
