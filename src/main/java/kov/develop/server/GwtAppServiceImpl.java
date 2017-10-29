package kov.develop.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import kov.develop.client.GwtAppService;
import kov.develop.shared.Point;
import kov.develop.server.repository.PointRepository;
import kov.develop.shared.FieldValidator;
import kov.develop.shared.PointResult;
import kov.develop.shared.PointType;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class GwtAppServiceImpl extends RemoteServiceServlet implements GwtAppService {

    private PointRepository repository;
    private static long lastModified;
    /*private static Thread loader;*/

    {

    }

    public GwtAppServiceImpl() {
        this.repository = new PointRepository();
        PointRepository.readAllFromXml("E:\\Java\\Servicing\\src\\main\\resources\\point.xml");
        lastModified = new File("E:\\Java\\Servicing\\src\\main\\resources\\point.xml").lastModified();
        loader.setDaemon(true);
        loader.start();
    }


    public List<PointResult> getAllPoints() {
        return repository.getAllPoints().stream().map(p -> new PointResult(p)).collect(Collectors.toList());
    }

    public List<PointResult> getAllPointsByType(PointType type) {
        return repository.getAllPointsByType(type).stream().map(p -> new PointResult(p)).collect(Collectors.toList());
    }

    public List<PointResult> getAllPointsByTypeAndCountry(String type, String country) {
        return repository.getAllPointsByTypeAndCountry(type.equals("")? null : PointType.valueOf(type), country).stream().map(p -> new PointResult(p)).collect(Collectors.toList());
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
    }

    Thread loader = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (new File("E:\\Java\\Servicing\\src\\main\\resources\\point.xml").lastModified() > lastModified) {
                    PointRepository.readAllFromXml("E:\\Java\\Servicing\\src\\main\\resources\\point.xml");
                    lastModified = new File("E:\\Java\\Servicing\\src\\main\\resources\\point.xml").lastModified();
                }
                System.out.println(new File("E:\\Java\\Servicing\\src\\main\\resources\\point.xml").lastModified());
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });
}
