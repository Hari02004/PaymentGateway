package org.JamesWeb.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@Table(name = "user_roles")
@ToString(exclude = "users")
@EntityListeners(AuditingEntityListener.class)
public class User_roles {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id",nullable = false)
    private Roles role;

    @ManyToOne
    @JoinColumn(name = "User_id",nullable = false)
    private users users;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Role assignedBy;
}
