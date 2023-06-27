package fr.simplon.minorease.entities;

import jakarta.persistence.*;



    @Entity
    @Table(name = "image")
    public class Image {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String url;

        @ManyToOne
        @JoinColumn(name = "hotel_id")
        private Hotel hotel;
    }
