package kov.develop.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import kov.develop.model.Point;
import kov.develop.model.PointType;

import java.util.List;

@RemoteServiceRelativePath("gwtAppService")
public interface GwtAppService extends RemoteService {
    Point gwtAppCallServer(Point data) throws IllegalArgumentException;

    List<Point> getAllPoints();

    List<Point> getAllPointsByType(PointType type);

    List<Point> getAllPointsByTypeAndCountry(PointType type, String country);

    Point getPoint(int id);

}
