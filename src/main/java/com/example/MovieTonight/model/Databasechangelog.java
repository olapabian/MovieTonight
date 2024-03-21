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
@Table(name = "databasechangelog")
public class Databasechangelog {
    @Id
    @Column(name = "rowid", nullable = false)
    private Long id;

    @Column(name = "id", nullable = false)
    private String id1;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "dateexecuted", nullable = false)
    private Instant dateexecuted;

    @Column(name = "orderexecuted", nullable = false)
    private Long orderexecuted;

    @Column(name = "exectype", nullable = false, length = 10)
    private String exectype;

    @Column(name = "md5sum", length = 35)
    private String md5sum;

    @Column(name = "description")
    private String description;

    @Column(name = "comments")
    private String comments;

    @Column(name = "tag")
    private String tag;

    @Column(name = "liquibase", length = 20)
    private String liquibase;

    @Column(name = "contexts")
    private String contexts;

    @Column(name = "labels")
    private String labels;

    @Column(name = "deployment_id", length = 10)
    private String deploymentId;

}