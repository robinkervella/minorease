package fr.simplon.minorease.services;

import fr.simplon.minorease.entities.Chambre;
import fr.simplon.minorease.entities.Hotel;
import fr.simplon.minorease.entities.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe fournit des services de recherche pour les hôtels, les chambres et les réservations.
 */
@Service
public class RechercheService {

    /**
     * Recherche les hôtels disponibles dans la fourchette de prix spécifiée.
     *
     * @param allhotels La liste complète des hôtels.
     * @param prixMini  Le prix minimum.
     * @param prixMax   Le prix maximum.
     * @return Une liste des hôtels disponibles dans la fourchette de prix spécifiée.
     */
    public List<Hotel> trouverLesHotelsDansLaFourchetteDePrix(List<Hotel> allhotels, double prixMini, double prixMax) {
        List<Hotel> hotelsToReturn = new ArrayList<>();
        for (Hotel hotel : allhotels) {
            if (trouverLesChambresDansLaFourchetteDePrix(hotel.getChambres(), prixMini, prixMax)) {
                hotelsToReturn.add(hotel);
            }
        }
        return hotelsToReturn;
    }

    /**
     * Vérifie si des chambres sont disponibles dans la fourchette de prix spécifiée.
     *
     * @param allChambreByHotel La liste complète des chambres pour un hôtel.
     * @param prixMini          Le prix minimum.
     * @param prixMax           Le prix maximum.
     * @return True si au moins une chambre est disponible dans la fourchette de prix spécifiée, sinon False.
     */
    public boolean trouverLesChambresDansLaFourchetteDePrix(List<Chambre> allChambreByHotel, double prixMini, double prixMax) {
        for (Chambre chambre : allChambreByHotel) {
            if (chambre.getPrix() <= prixMax && chambre.getPrix() >= prixMini) {
                return true;
            }
        }
        return false;
    }

    /**
     * Récupère les hôtels disponibles pour les dates spécifiées.
     *
     * @param allHotels  La liste complète des hôtels.
     * @param dateDebut  La date de début.
     * @param dateFin    La date de fin.
     * @return Une liste des hôtels disponibles pour les dates spécifiées.
     */
    public List<Hotel> getHotelsDisponibles(List<Hotel> allHotels, LocalDateTime dateDebut, LocalDateTime dateFin) {
        List<Hotel> hotelsDisponibles = new ArrayList<>();
        for (Hotel hotel : allHotels) {
            List<Chambre> chambresDisponibles = getChambresDisponibles(hotel.getChambres(), dateDebut, dateFin);
            if (!chambresDisponibles.isEmpty()) {
                hotelsDisponibles.add(hotel);
            }
        }
        return hotelsDisponibles;
    }

    /**
     * Récupère les chambres disponibles pour les dates spécifiées.
     *
     * @param allChambres La liste complète des chambres.
     * @param dateDebut   La date de début.
     * @param dateFin     La date de fin.
     * @return Une liste des chambres disponibles pour les dates spécifiées.
     */
    public List<Chambre> getChambresDisponibles(List<Chambre> allChambres, LocalDateTime dateDebut, LocalDateTime dateFin) {
        List<Chambre> chambresDisponibles = new ArrayList<>();

        for (Chambre chambre : allChambres) {
            if (estDisponible(chambre.getReservation(), dateDebut, dateFin)) {
                chambresDisponibles.add(chambre);
            }
        }

        return chambresDisponibles;
    }

    /**
     * Vérifie si une chambre est disponible pour les dates spécifiées.
     *
     * @param allReservations La liste complète des réservations pour une chambre.
     * @param dateDebut       La date de début.
     * @param dateFin         La date de fin.
     * @return True si la chambre est disponible, sinon False.
     */
    public boolean estDisponible(List<Reservation> allReservations, LocalDateTime dateDebut, LocalDateTime dateFin) {
        for (Reservation reservation : allReservations) {
            if (dateDebut.isBefore(reservation.getDate_fin()) && dateFin.isAfter(reservation.getDate_debut())) {
                return false;
            }
        }
        return true;
    }
}
