package com.second.backend.model;
import jakarta.persistence.*;

@Entity
@Table(name = "carts")
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Users getUser() {
        return users;
    }

    public void setUser(Users users) {
        this.users = users;
    }
}
