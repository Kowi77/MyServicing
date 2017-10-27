package kov.develop.server.repository;


import kov.develop.shared.Point;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class XmlUtils  {

    JAXBContext context = JAXBContext.newInstance(Point.class);


    public XmlUtils() throws JAXBException {
    }
}
