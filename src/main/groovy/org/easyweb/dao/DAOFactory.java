package org.easyweb.dao;

import org.easyweb.daoImpl.AbstractDAOImpl;

import java.util.*;
import java.util.logging.Logger;

public final class DAOFactory {

	private static final Logger LOG = Logger.getLogger(DAOFactory.class.getName());

	private static final Map<Class, Object> daoMap = new HashMap<Class, Object>();

	private DAOFactory() {
		super();
	}

	/**
	* single object
	**/
	private static synchronized <T extends AbstractDAOImpl> T getDAO(Class<T> clazz) {
		T t = null;

		if(daoMap.get(clazz) == null) {
			try{
				t = clazz.cast(clazz.newInstance());
				daoMap.put(clazz, t);
			}catch(Exception e) {
			//ignore it ...
				LOG.warning(String.format("get the org.easyweb.dao %s error", clazz.getSimpleName()));

			}
		}else {
			t = clazz.cast(daoMap.get(clazz));
		}
			return t;
	}

    public static UserDAO getUserDAO() {
        return getDAO(UserDAOImpl.class);
    }
}
