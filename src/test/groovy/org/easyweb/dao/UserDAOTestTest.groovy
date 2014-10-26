package org.easyweb.dao


import org.junit.Test

/**
 * Created by angelgreen on 10/26/14.
 */
class UserDAOTestTest extends BaseDAOTest{

    @Test
    public void save() {
        def user = new User(name: "angelgreen",password: "333")

        def dao = DAOFactory.getUserDAO()

        dao.saveUser(user, args)

        commit()
    }

    @Test
    public void findUser() {
        def dao = DAOFactory.getUserDAO()

        def user = new User(name: "angelgreen",password: "333")
        def persistUser = dao.getOneUserByNameAndPassword(user, args)

        printf "%s\n", persistUser.name
        printf "%s\n", persistUser.password
        printf "%s\n", persistUser.id
    }

    @Test
    public void findSingle() {
        def dao = DAOFactory.getUserDAO()

        def persistUser = dao.getOneUserByName("angelgreen", args)

        printf "%s\n", persistUser.name
        printf "%s\n", persistUser.password
        printf "%s\n", persistUser.id


    }

    @Test
    public void update() {
        def dao = DAOFactory.getUserDAO()

        def persistUser = dao.getOneUserByName("angelgreen", args)

        persistUser.name = "bobo"

        dao.updateUser(persistUser,args)

        commit()
    }
}
