package fr.simplon.minorease.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime date_debut;
    private LocalDateTime date_fin;

    public Reservation(LocalDateTime of, LocalDateTime of1) {
        this.date_debut = of;
        this.date_fin = of1;
    }

    public Reservation() {

    }

    public LocalDateTime getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDateTime date_debut) {
        this.date_debut = date_debut;
    }

    public LocalDateTime getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDateTime date_fin) {
        this.date_fin = date_fin;
    }

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @ManyToOne
    @JoinColumn(name = "chambre_id")
    private Chambre chambre;
    @OneToOne(mappedBy = "reservation")
    private Rating rating;
    @ManyToOne
    @JoinColumn(name = "mineur_id")
    private Mineur mineur;

    public Mineur getMineur() {
        return mineur;
    }

    public void setMineur(Mineur mineur) {
        this.mineur = mineur;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }


}
