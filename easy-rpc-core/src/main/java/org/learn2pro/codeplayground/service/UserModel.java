package org.learn2pro.codeplayground.service;

import java.io.Serializable;

/**
 * @NAME :org.learn2pro.codeplayground.service.UserModel
 * @AUTHOR :tderong
 * @DATE :2020/6/27
 */
public class UserModel implements Serializable {

  private int id;
  private String name;
  private Address address;
  private String school;

  public UserModel(int id, String name, Address address, String school) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.school = school;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public static class Address implements Serializable {

    private String province;
    private String City;
    private String street;

    public Address(String province, String city, String street) {
      this.province = province;
      City = city;
      this.street = street;
    }

    public void setProvince(String province) {
      this.province = province;
    }

    public void setCity(String city) {
      City = city;
    }

    public void setStreet(String street) {
      this.street = street;
    }

    public String getProvince() {
      return province;
    }

    public String getCity() {
      return City;
    }

    public String getStreet() {
      return street;
    }

    @Override
    public String toString() {
      return "Address{" +
          "province='" + province + '\'' +
          ", City='" + City + '\'' +
          ", street='" + street + '\'' +
          '}';
    }
  }

  @Override
  public String toString() {
    return "UserModel{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", address=" + address +
        ", school='" + school + '\'' +
        '}';
  }

  public static UserModel fake() {
    return new UserModel(1, "tt", new Address("sc", "cd", "sl"), "scu");
  }

}
