package com.example.MovieTonight.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "databasechangeloglock")
public class Databasechangeloglock {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "locked", nullable = false)
    private Boolean locked = false;

    @Column(name = "lockgranted")
    private Instant lockgranted;

    @Column(name = "lockedby")
    private String lockedby;

}