package fr.simplon.minorease.entities;

import jakarta.persistence.*;



@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;

    private Double rating;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
