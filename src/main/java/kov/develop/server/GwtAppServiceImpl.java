package kov.develop.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import kov.develop.client.GwtAppService;
import kov.develop.shared.Point;
import kov.develop.server.repository.PointRepository;
import kov.develop.shared.FieldValidator;
import kov.develop.shared.PointResult;
import kov.develop.shared.PointType;

import java.util.List;
import java.util.stream.Collectors;

public class GwtAppServiceImpl extends RemoteServiceServlet implements GwtAppService {

    private PointRepository repository;

    public GwtAppServiceImpl() {
        this.repository = new PointRepository();
    }

    public List<PointResult> getAllPoints() {
        return repository.getAllPoints().stream().map(p -> new PointResult(p)).collect(Collectors.toList());
    }

    public List<PointResult> getAllPointsByType(PointType type) {
        return repository.getAllPointsByType(type).stream().map(p -> new PointResult(p)).collect(Collectors.toList());
    }

    public List<PointResult> getAllPointsByTypeAndCountry(PointType type, String country) {
        return repository.getAllPointsByTypeAndCountry(type, country).stream().map(p -> new PointResult(p)).collect(Collectors.toList());
    }

    public PointResult getPoint(int id) {
        return new PointResult(repository.getPoint(id));
    }

    public PointResult gwtAppCallServer(Point point) throws IllegalArgumentException {
        if (!FieldValidator.isValidData(point.getName())) {
            throw new IllegalArgumentException("Имя должно быть больше трех символов");
        }

        PointResult pointResult = new PointResult(point);
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
