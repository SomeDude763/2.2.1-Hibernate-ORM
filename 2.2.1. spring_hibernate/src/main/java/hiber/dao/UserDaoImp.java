package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession()
                .createQuery("from User user left join fetch user.userCar");
        return query.getResultList();
    }

    @Override
    public User getUserCar(String model, int series) {
        String hql = "from User user left join fetch user.userCar " +
                "where user.userCar.model = :model AND user.userCar.series = :series";
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("model", model);
        query.setParameter("series", series);
        return query.getSingleResult();
    }
}


