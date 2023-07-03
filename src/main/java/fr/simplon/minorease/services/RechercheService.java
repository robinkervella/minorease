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

    public List<Hotel> rechercheHotel(List<Hotel> allHotels,String ville,LocalDateTime dateDebut,LocalDateTime dateFin,int nombreDePersonne){

        List<Hotel> hotelParVille = trouverHotelParVille(allHotels, ville);
        List<Hotel> hotelParPlace = trouverHotelParPlaceDansLaChambre(hotelParVille, nombreDePersonne);
        List<Hotel> hotelParDate = trouverHotelsDisponibles(hotelParPlace, dateDebut, dateFin);
        return hotelParDate;
        }

    /**
     * Recherche les hôtels disponibles dans la fourchette de prix spécifiée.
     *
     * @param allhotels La liste complète des hôtels.
     * @param prixMini  Le prix minimum.
     * @param prixMax   Le prix maximum.
     * @return Une liste des hôtels disponibles dans la fourchette de prix spécifiée.
     */
    public List<Hotel> trouverLesHotelsDansLaFourchetteDePrix(List<Hotel> allhotels, double prixMini, double prixMax) {
        if (prixMini < 0 || prixMax < 0 || prixMini > prixMax) {
            throw new IllegalArgumentException("Les prix doivent être des nombres décimaux positifs et prixMini doit être inférieur ou égal à prixMax.");
        }

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
        if (prixMini < 0 || prixMax < 0 || prixMini > prixMax) {
            throw new IllegalArgumentException("Les prix doivent être des nombres décimaux positifs et prixMini doit être inférieur ou égal à prixMax.");
        }

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
    public List<Hotel> trouverHotelsDisponibles(List<Hotel> allHotels, LocalDateTime dateDebut, LocalDateTime dateFin) {
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

    /**

     Recherche les hôtels situés dans une ville donnée.
     @param allHotel La liste complète des hôtels à vérifier.
     @param ville La ville dans laquelle rechercher les hôtels.
     @return Une liste d'hôtels situés dans la ville spécifiée. Si aucun hôtel n'est trouvé, la liste sera vide.
     */
    public List<Hotel> trouverHotelParVille(List<Hotel> allHotel, String ville) {
        List<Hotel> hotelsParVille = new ArrayList<>();
        for (Hotel hotel : allHotel) {
            if (hotel.getVille().equals(ville)) {
                hotelsParVille.add(hotel);
            }
        }
        return hotelsParVille;
    }

    /**

     Recherche les hôtels qui ont suffisamment de place dans leurs chambres pour accueillir un certain nombre de clients.

     @param allHotels La liste complète des hôtels à vérifier.

     @param nombreDeClient Le nombre de clients à héberger dans une chambre (doit être supérieur ou égal à zéro).

     @return Une liste d'hôtels qui ont suffisamment de place dans leurs chambres.

     @throws IllegalArgumentException Si le nombre de clients est inférieur à zéro.
     */
    public List<Hotel> trouverHotelParPlaceDansLaChambre(List<Hotel> allHotels, int nombreDeClient) {
        if (nombreDeClient < 0) {
            throw new IllegalArgumentException("Le nombre de clients ne peut pas être inférieur à zéro.");
        }

        List<Hotel> hotelsARetourner = new ArrayList<>();
        for (Hotel hotel : allHotels) {
            if (chambreAvecAssezDePlace(hotel.getChambres(), nombreDeClient)) {
                hotelsARetourner.add(hotel);
            }
        }
        return hotelsARetourner;
    }

    /**

     Vérifie si toutes les chambres de la liste donnée ont suffisamment de place pour accueillir un certain nombre de clients.

     @param allChambres La liste complète des chambres à vérifier.

     @param nombreDeClient Le nombre de clients à héberger dans une chambre (doit être supérieur ou égal à zéro).

     @return true si au moins une chambre à suffisamment de place, sinon false.

     @throws IllegalArgumentException Si le nombre de clients est inférieur à zéro.
     */
    public boolean chambreAvecAssezDePlace(List<Chambre> allChambres, int nombreDeClient) {
        if (nombreDeClient < 0) {
            throw new IllegalArgumentException("Le nombre de clients ne peut pas être inférieur à zéro.");
        }

        for (Chambre chambre : allChambres) {
            if (chambre.getNombre_lit() >= nombreDeClient) {
                return true;
            }
        }
        return false;
    }

 public List<Integer> renvoyerTypeChambre(List<Chambre> allChambres,LocalDateTime dateDebut,LocalDateTime dateFin){
        List<Integer> listeARetourner = new ArrayList<>();
        for (Chambre chambre : allChambres){
            if(estDisponible(chambre.getReservation(),dateDebut,dateFin))
            if (!listeARetourner.contains(chambre.getNombre_lit())){
                listeARetourner.add(chambre.getNombre_lit());
            }
        }
        return listeARetourner;
 }
    public Chambre getChambreByTypeChambre(List<Chambre> allChambres,int typeChambre){
        for (Chambre chambre : allChambres){
            if (chambre.getNombre_lit() == typeChambre){
                return chambre;
            }
        }
        return null;
 }

}
