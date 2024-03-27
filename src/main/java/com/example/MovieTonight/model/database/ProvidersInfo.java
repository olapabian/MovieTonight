package com.example.MovieTonight.model.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "providers_info")
public class ProvidersInfo {
    @Id
    @Column(name = "provider_id", nullable = false)
    private Long id;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    public ProvidersInfo(long id, String name) {
        this.id = id;
        this.name = name;
    }
}