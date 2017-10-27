package kov.develop.server.repository;

import kov.develop.server.HibernateUtil;
import kov.develop.shared.PointType;
import kov.develop.shared.Point;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Андрей on 26.10.2017.
 */
public class PointRepository implements Serializable{

    private SessionFactory sessionFactory;
    private Session session;

    public PointRepository() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.session = this.sessionFactory.openSession();
    }
    /* //Singleton repository
    private PointRepository() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.session = this.sessionFactory.openSession();
    }

    private static class RepositoryGetter {
        private final static  PointRepository repository = new PointRepository();
    }

    public static PointRepository getRepository(){ return RepositoryGetter.repository;}
*/

    public List<Point> getAllPoints() {
        return session.createSQLQuery("SELECT * FROM points").list();
    }
/*
    public List<Point> getAllPoints() {
        List<Point> list = new ArrayList<>();
        list.add(new Point(1, "Russia", "Nsk", "Pirogova", "SuperPoint", "8-999-231-33", PointType.SENDING));
        list.add(new Point(2, "USA", "New-York", "Brodway", "SmallPoint", "1-234-567-89", PointType.RECIEVING));
        return list;
    }
*/

    public List<Point> getAllPointsByType(PointType type) {
        return session.createSQLQuery("SELECT * FROM point.points WHERE point.points.type = type").list();
    }

    public List<Point> getAllPointsByTypeAndCountry(PointType type, String country) {
        return session.createSQLQuery("SELECT * FROM point.points AS p WHERE p.type = type AND p.country = country").list();
    }

    public Point getPoint(int id) {
        return (Point) session.createSQLQuery("SELECT DISTINCT * FROM point.points WHERE point.points.id = id").list().get(0);
    }
}
