package fr.simplon.minorease;

import fr.simplon.minorease.entities.Hotel;
import fr.simplon.minorease.entities.Parent;
import fr.simplon.minorease.entities.Rating;
import fr.simplon.minorease.repositories.RatingRepository;
import fr.simplon.minorease.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;
    private RatingService ratingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ratingService = new RatingService(ratingRepository);
    }

    @Test
    void laisserCommentaire_avecCommentaireNonVide_sauvegardeRatingAvecCommentaire() {
        // Arrange
        Parent parent = new Parent();
        Hotel hotel = new Hotel();
        String commentaire = "Ceci est un commentaire.";

        // Act
        Rating rating = ratingService.laisserCommentaire(parent, hotel, commentaire);

        // Assert
        assertEquals(parent, rating.getParent());
        assertEquals(hotel, rating.getHotel());
        assertEquals(commentaire, rating.getDescription());
        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    void laisserCommentaire_avecCommentaireVide_lanceException() {
        // Arrange
        Parent parent = new Parent();
        Hotel hotel = new Hotel();
        String commentaire = "";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ratingService.laisserCommentaire(parent, hotel, commentaire);
        });

        assertEquals("Le commentaire ne peut pas être vide", exception.getMessage());
        verifyNoInteractions(ratingRepository);
    }

    @Test
    void laisserNote_avecNoteValide_sauvegardeRatingAvecNote() {
        // Arrange
        Parent parent = new Parent();
        Hotel hotel = new Hotel();
        Double note = 4.5;

        // Act
        Rating rating = ratingService.laisserNote(parent, hotel, note);

        // Assert
        assertEquals(parent, rating.getParent());
        assertEquals(hotel, rating.getHotel());
        assertEquals(note, rating.getRating());
        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    void laisserNote_avecNoteInvalide_lanceException() {
        // Arrange
        Parent parent = new Parent();
        Hotel hotel = new Hotel();
        Double note = 6.0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ratingService.laisserNote(parent, hotel, note);
        });

        assertEquals("La note doit être comprise entre 0 et 5", exception.getMessage());
        verifyNoInteractions(ratingRepository);
    }

    @Test
    void calculerMoyenneNotes_avecRatingsNonVides_retourneMoyenneNotes() {
        // Arrange
        Hotel hotel = new Hotel();
        List<Rating> ratings = new ArrayList<>();
        ratings.add(createRating(4.0));
        ratings.add(createRating(3.0));
        ratings.add(createRating(5.0));
        when(ratingRepository.findByHotel(hotel)).thenReturn(ratings);

        // Act
        double moyenneNotes = ratingService.calculerMoyenneNotes(hotel);

        // Assert
        assertEquals(4.0, moyenneNotes, 0.001);
        verify(ratingRepository, times(1)).findByHotel(hotel);
    }

    @Test
    void calculerMoyenneNotes_avecRatingsVides_retourneZero() {
        // Arrange
        Hotel hotel = new Hotel();
        List<Rating> ratings = new ArrayList<>();
        when(ratingRepository.findByHotel(hotel)).thenReturn(ratings);

        // Act
        double moyenneNotes = ratingService.calculerMoyenneNotes(hotel);

        // Assert
        assertEquals(0.0, moyenneNotes, 0.001);
        verify(ratingRepository, times(1)).findByHotel(hotel);
    }

    @Test
    void obtenirMessageNote_avecMoyenneZero_retourneMessagePasDeNotesDisponibles() {
        // Arrange
        double moyenneNotes = 0.0;

        // Act
        String message = ratingService.obtenirMessageNote(moyenneNotes);

        // Assert
        assertEquals("Pas de notes disponibles.", message);
    }

    @Test
    void obtenirMessageNote_avecMoyenneNonZero_retourneMessageMoyenneNotes() {
        // Arrange
        double moyenneNotes = 3.5;

        // Act
        String message = ratingService.obtenirMessageNote(moyenneNotes);

        // Assert
        assertEquals("Moyenne des notes : 3.5", message);
    }

    private Rating createRating(Double note) {
        Rating rating = new Rating();
        rating.setRating(note);
        return rating;
    }
}

