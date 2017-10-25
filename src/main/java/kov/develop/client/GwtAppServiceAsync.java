package kov.develop.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import kov.develop.model.Point;
import kov.develop.model.PointType;

import java.util.List;

public interface GwtAppServiceAsync {

    void gwtAppCallServer(Point point, AsyncCallback<Point> callback) throws IllegalArgumentException;

    void getAllPoints(AsyncCallback<List<Point>> async);

    void getAllPointsByType(PointType type, AsyncCallback<List<Point>> async);

    void getAllPointsByTypeAndCountry(PointType type, String country, AsyncCallback<List<Point>> async);

    void getPoint(int id, AsyncCallback<Point> async);
}
