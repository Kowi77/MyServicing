package kov.develop.shared;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Entity and Xml class
 */
@Entity
@Table(name="points")
@XmlRootElement(name="point")
@XmlType(propOrder = {"id", "country", "sity", "adress", "name", "phone", "type"})
public class Point implements Serializable {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Basic
    @Column(name="country", nullable = false, length = 30)
    private String country;
    @Basic
    @Column(name="sity", nullable = false, length = 30)
    private String sity;
    @Basic
    @Column(name="adress", nullable = false, length = 30)
    private String adress;
    @Basic
    @Column(name="name", nullable = false, length = 50)
    private String name;
    @Basic
    @Column(name="phone", nullable = false, length = 18)
    private String phone;
    @Basic
    @Column(name="type", nullable = false, length = 20)
    private PointType type;

    public Point() {
    }

    public Point(String country, String sity, String adress, String name, String phone, PointType type) {
        this.country = country;
        this.sity = sity;
        this.adress = adress;
        this.name = name;
        this.phone = phone;
        this.type = type;
    }

    public Point(int id, String country, String sity, String adress, String name, String phone, PointType type) {
        this.id = id;
        this.country = country;
        this.sity = sity;
        this.adress = adress;
        this.name = name;
        this.phone = phone;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSity() {
        return sity;
    }

    public void setSity(String sity) {
        this.sity = sity;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PointType getType() {
        return type;
    }

    public void setType(PointType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Point{" +
                "country='" + country + '\'' +
                ", sity='" + sity + '\'' +
                ", adress='" + adress + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", type=" + type +
                '}';
    }
}
