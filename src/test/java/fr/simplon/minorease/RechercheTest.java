package fr.simplon.minorease;

import fr.simplon.minorease.entities.Chambre;
import fr.simplon.minorease.entities.Hotel;
import fr.simplon.minorease.entities.Reservation;
import fr.simplon.minorease.services.RechercheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RechercheTest {

    @Autowired
    RechercheService rechercheService;

    RechercheTest rechercheTest;
    List<Hotel> allHotels;
    List<Chambre> allChambres;
    List<Reservation> allReservations;
    LocalDateTime dateDebut, dateFin;

    @BeforeEach
    void setUp() {
        rechercheTest = new RechercheTest();

        // Générer des réservations
        allReservations = Arrays.asList(
                new Reservation(LocalDateTime.of(2023, 7, 5, 12, 0), LocalDateTime.of(2023, 7, 9, 12, 0)),
                new Reservation(LocalDateTime.of(2023, 7, 14, 12, 0), LocalDateTime.of(2023, 7, 20, 12, 0))
        );

        // Générer des chambres avec des réservations
        Chambre chambre1 = new Chambre();
        chambre1.setReservation(new ArrayList<>(allReservations));

        Chambre chambre2 = new Chambre();
        chambre2.setReservation(new ArrayList<>());

        allChambres = Arrays.asList(chambre1, chambre2);

        // Générer des hôtels avec des chambres
        Hotel hotel = new Hotel();
        hotel.setChambres(new ArrayList<>(allChambres));

        allHotels = Arrays.asList(hotel);

        // Définir les dates de début et de fin pour les tests
        dateDebut = LocalDateTime.of(2023, 7, 10, 12, 0);
        dateFin = LocalDateTime.of(2023, 7, 15, 12, 0);
    }

    @Test
    void testGetHotelsDisponibles() {
        List<Hotel> result = rechercheService.getHotelsDisponibles(allHotels, dateDebut, dateFin);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetChambresDisponibles() {
        List<Chambre> result = rechercheService.getChambresDisponibles(allChambres, dateDebut, dateFin);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testEstDisponible() {
        boolean result = rechercheService.estDisponible(allReservations, dateDebut, dateFin);
        assertFalse(result);
    }
}


