package fr.simplon.minorease.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Chambre")
public class Chambre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int nombre_lit;
    private double prix;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToMany(mappedBy = "chambre")
    private List<Reservation> reservation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNombre_lit() {
        return nombre_lit;
    }

    public void setNombre_lit(int nombre_lit) {
        this.nombre_lit = nombre_lit;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(List<Reservation> reservation) {
        this.reservation = reservation;
    }

    // Constructeurs, getters et setters
}