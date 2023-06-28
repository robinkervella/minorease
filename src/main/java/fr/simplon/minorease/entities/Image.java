package fr.simplon.minorease.entities;

import jakarta.persistence.*;



    @Entity
    @Table(name = "image")
    public class Image {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        @ManyToOne
        @JoinColumn(name = "hotel_id")
        private Hotel hotel;

        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Hotel getHotel() {
            return hotel;
        }

        public void setHotel(Hotel hotel) {
            this.hotel = hotel;
        }
    }


