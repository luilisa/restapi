package com.example.rest.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_account")
public class UserEntity {

    private long id;
    private String login;
    private String password;
    private List<Portfolio> portfolios;

    public UserEntity(String login, String password, List<Portfolio> portfolios) {
        this.login = login;
        this.password = password;
        this.portfolios = portfolios;
    }

    public UserEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "login", nullable = false)
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", login=" + login + ", password=" + password + " ]";
    }
    @JsonIgnore
    @OneToMany(mappedBy = "userEntity")
    public List<Portfolio> getPortfolios() {
        return portfolios;
    }
    public void setPortfolios(List<Portfolio> portfolios) {
        this.portfolios = portfolios;
    }
}
