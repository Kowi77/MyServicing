package kov.develop.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import kov.develop.client.GwtAppService;
import kov.develop.model.Point;
import kov.develop.model.PointType;
import kov.develop.shared.FieldValidator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class GwtAppServiceImpl extends RemoteServiceServlet implements GwtAppService {

    private SessionFactory sessionFactory;
    private Session session;

    public GwtAppServiceImpl(SessionFactory sessionFactory, Session session) {
        this.sessionFactory = sessionFactory;
        this.session = session;
    }

    public GwtAppServiceImpl() {
    }

    public List<Point> getAllPoints() {
        return session.createSQLQuery("SELECT * FROM points").list();
    }

    public List<Point> getAllPointsByType(PointType type) {
        return session.createSQLQuery("SELECT * FROM points WHERE points.type = type").list();
    }

    public List<Point> getAllPointsByTypeAndCountry(PointType type, String country) {
        return session.createSQLQuery("SELECT * FROM points WHERE points.type = type AND points.country = country").list();
    }

    public Point getPoint(int id) {
        return (Point) session.createSQLQuery("SELECT DISTINCT * FROM points WHERE points.id = id").list().get(0);
    }

    public Point gwtAppCallServer(Point point) throws IllegalArgumentException {
        if (!FieldValidator.isValidData(point.getName())) {
            throw new IllegalArgumentException("Имя должно быть больше трех символов");
        }

        Point pointResult = new Point();
        pointResult.setCountry(point.getCountry() + "***");
        pointResult.setSity(point.getSity() + "***");
        pointResult.setAdress(point.getAdress() + "***");
        pointResult.setName(point.getName() + "***");
        pointResult.setPhone(point.getPhone() + "***");
        pointResult.setType(point.getType());

        return pointResult;
       /* String serverInfo = getServletContext().getServerInfo();
        String userAgent = getThreadLocalRequest().getHeader("User-Agent");

        data = escapeHtml(data);
        userAgent = escapeHtml(userAgent);

        return "Привет, " + data + "!<br> Инфо сервера: " + serverInfo + ".<br> Вы используете:" +
                "<br>" + userAgent;
    }

    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
                ">", "&gt;");
    }*/
    }
}
