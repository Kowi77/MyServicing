package kov.develop.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import kov.develop.shared.Point;
import kov.develop.shared.PointType;
import kov.develop.shared.PointResult;

import java.util.List;

public interface GwtAppServiceAsync {

    void gwtAppCallServer(Point point, AsyncCallback<PointResult> async) throws IllegalArgumentException;

    void getAllPoints(AsyncCallback<List<PointResult>> async);

    void getAllPointsByType(PointType type, AsyncCallback<List<PointResult>> async);

    void getAllPointsByTypeAndCountry(PointType type, String country, AsyncCallback<List<PointResult>> async);

    void getPoint(int id, AsyncCallback<PointResult> async);
}
