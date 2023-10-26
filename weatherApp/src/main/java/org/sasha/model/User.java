package org.sasha.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String region;

    private String email;

    private String pass;

    @CreationTimestamp
    @Temporal(TIMESTAMP)
    private Date createdAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    List<CurrentWeatherCheck> checks = new ArrayList<>();

    public User(String name, String region, String email, String pass) {
        this.name = name;
        this.region = region;
        this.email = email;
        this.pass = pass;
    }

    public CurrentWeatherCheck getLastCheck() {
        if (checks.isEmpty()) {
            return null;
        }
        return checks.get(checks.size() - 1);
    }
}
