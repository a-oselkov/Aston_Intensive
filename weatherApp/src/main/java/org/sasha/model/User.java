package org.sasha.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@Getter
@Setter
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="user_location",
            joinColumns=  @JoinColumn(name="user_id", referencedColumnName="id"),
            inverseJoinColumns= @JoinColumn(name="location_id", referencedColumnName="id") )
    private Set<Location> locations = new HashSet<>();

    public CurrentWeatherCheck getLastCheck() {
        if (checks.isEmpty()) {
            return null;
        }
        return checks.get(checks.size() - 1);
    }
}
