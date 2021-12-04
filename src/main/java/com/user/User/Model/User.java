package com.user.User.Model;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name ="name")
    private String name;

    @Column(name ="email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "kyc_flag")
    private Boolean kyc_flag;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getKyc_flag() {
        return kyc_flag;
    }

    public void setKyc_flag(Boolean kyc_flag) {
        this.kyc_flag = kyc_flag;
    }
}
