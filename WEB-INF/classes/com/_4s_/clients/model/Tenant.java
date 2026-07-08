package com._4s_.clients.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * A tenant owns/maintains a sub-section of the web application.
 * Can be thought of as a website within the application.
 * Users can register for multiple tenant without them
 * knowing that each separate tenant is part of one and
 * the same application.
 * So the uniqueness of user accounts is determined by the
 * combination of the user's unique ID (= email) combined
 * with the tenant ID.
 */
@Entity
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(nullable = false)
    private String name;

    public Tenant() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
