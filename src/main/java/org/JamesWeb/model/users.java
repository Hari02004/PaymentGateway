package org.JamesWeb.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString(exclude = {"userRoles","merchantDetails"})
public class users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Email",nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @OneToOne(mappedBy = "user")
    private MerchantDetails merchantDetails;
    @OneToMany(mappedBy = "users")
    private Set<User_roles> userRoles = new HashSet<>();

}
