package com.example.MovieTonight.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "directors_info")
public class DirectorsInfo {
    @Id
    @Column(name = "director_id", nullable = false)
    private Long id;

    @Column(name = "full_name", length = Integer.MAX_VALUE)
    private String fullName;

}