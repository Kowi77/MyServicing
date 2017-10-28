package kov.develop.server.repository;

import javax.xml.bind.JAXBException;
import java.io.File;

/**
 * Created by Андрей on 27.10.2017.
 */
public interface XmlService {
    Object getObject(File file, Class c) throws JAXBException;
    void saveObject(File file, Object o) throws JAXBException;
}
