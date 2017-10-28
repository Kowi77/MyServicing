package kov.develop.server.repository;

import kov.develop.shared.Point;
import kov.develop.shared.PointType;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;

/**
 * Created by Андрей on 27.10.2017.
 */

public class JaxbServiceTest {
    private XmlService service = new XmlServiceImpl();
    private File file = new File("point.xml");

 /*   @Before
    public void setUp() throws Exception {
        service = new XmlServiceImpl();
        file = new File("point.xml");
    }*/

    @Test
    public void testGetObject() throws Exception {
        Point point = (Point) service.getObject(file, Point.class);
        System.out.println(point);
    }

    @Test
    public void testSaveObject() throws Exception {
        Point point = new Point(1, "Russia", "Nsk", "Pirogova", "SuperPoint", "8-999-231-33", PointType.ОТПРАВКА);
        /*Point point1 = new Point(2, "USA", "New-York", "Brodway", "SmallPoint", "1-234-567-89", PointType.RECIEVING);*/
        service.saveObject(file, point);
        /*service.saveObject(file, point1);*/
    }
    @Test
    public void testReadXml() throws Exception {
        List<Point> points = XmlServiceImpl.getDbFromXml("classpath:point.xml");
        points.forEach(p -> System.out.println(p));

    }
}
