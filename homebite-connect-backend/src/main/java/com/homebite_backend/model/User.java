package com.homebite_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "user_id")
    private int id;

    private String name;
    private String email;
    private String password;
//    private String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Food> foods;

    public User(String name, String email, String password, int phone) {
        this.name=name;
        this.email=email;
        this.password=password;
//        this.phone= String.valueOf(phone);
    }

    public User() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

//    public String getPhone() { return phone; }
//    public void setPhone(String phone) { this.phone = phone; }

    public int getId() {
        return id;
    }
}
