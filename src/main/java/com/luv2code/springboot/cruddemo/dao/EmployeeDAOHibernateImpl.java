package com.luv2code.springboot.cruddemo.dao;

import com.luv2code.springboot.cruddemo.entity.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {

    // define field for entity manager, automatically created by spring boot
    private final EntityManager entityManager;

    // set up constructor injection
    @Autowired
    public EmployeeDAOHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Employee> findAll() {

        // get the current session
        Session session = entityManager.unwrap(Session.class);

        // create a query
        Query<Employee> query = session.createQuery("from Employee", Employee.class);

        // execute it, get list
        return query.getResultList();
    }

    @Override
    public Employee findById(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Employee.class, id);
    }

    @Override
    public void save(Employee employee) {
        Session session = entityManager.unwrap(Session.class);

        session.flush();
        session.clear();
        session.saveOrUpdate(employee); // id = 0 then save, if id is not
        // zero it will update
        // or id already exists then also update
    }

    @Override
    @SuppressWarnings("rawUseOfParameterizedClass")
    public void deleteById(int id) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("delete from Employee where id=:empId");
        query.setParameter("empId", id);
        query.executeUpdate();
    }
}
