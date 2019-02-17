package ru.javaops.masterjava.model;

import java.util.Objects;

public class User {
    private UserFlag userFlag;
    private String city;
    private String email;

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public UserFlag getUserFlag() {
        return userFlag;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserFlag(UserFlag userFlag) {
        this.userFlag = userFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userFlag == user.userFlag &&
                Objects.equals(city, user.city) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userFlag, city, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "userFlag=" + userFlag +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
