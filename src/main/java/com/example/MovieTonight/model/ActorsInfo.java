package com.example.MovieTonight.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "actors_info")
public class ActorsInfo {
    @Id
    @Column(name = "actor_id", nullable = false)
    private Long id;

    @Column(name = "full_name", length = Integer.MAX_VALUE)
    private String fullName;

}