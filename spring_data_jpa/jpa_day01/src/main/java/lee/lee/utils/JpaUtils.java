package lee.lee.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Lee
 * @Description: ${todo}
 * @date 2019/1/12 0012 下午 7:14
 */
public class JpaUtils {
    private static EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory= Persistence.createEntityManagerFactory("jpa");
    }

    public static EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
}
