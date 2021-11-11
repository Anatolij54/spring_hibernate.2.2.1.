package hiber.service;

import hiber.config.AppConfig;
import hiber.dao.UserDao;
import hiber.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    @Override
    public User getUser(String model, int series) {

        String HQL = "FROM User us WHERE us.userCar.model=:carModel and us.userCar.series=:carSeries";
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(HQL, User.class).setParameter("carModel", model).setParameter("carSeries", series);
        return query.getSingleResult();
    }
}
