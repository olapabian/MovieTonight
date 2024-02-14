package com.example.MovieTonight.model.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "directors_info")
@AllArgsConstructor
@NoArgsConstructor
public class DirectorsInfo {
    @Id
    @Column(name = "director_id", nullable = false)
    private Long id;

    @Column(name = "full_name", length = Integer.MAX_VALUE)
    private String fullName;

}