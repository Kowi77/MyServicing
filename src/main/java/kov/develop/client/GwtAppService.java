package kov.develop.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import kov.develop.shared.Point;
import kov.develop.shared.PointType;
import kov.develop.shared.PointResult;

import java.util.List;

@RemoteServiceRelativePath("gwtAppService")
public interface GwtAppService extends RemoteService {

    PointResult gwtAppCallServer(Point data) throws IllegalArgumentException;

    List<PointResult> getAllPoints();

    List<PointResult> getAllPointsByType(PointType type);

    List<PointResult> getAllPointsByTypeAndCountry(PointType type, String country);

    PointResult getPoint(int id);

}
