package fr.simplon.minorease.services;

import fr.simplon.minorease.entities.Hotel;
import fr.simplon.minorease.entities.Parent;
import fr.simplon.minorease.entities.Rating;
import fr.simplon.minorease.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {


    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Rating laisserCommentaire(Parent parent, Hotel hotel, String commentaire) {
        if (commentaire.isEmpty()) {
            throw new IllegalArgumentException("Le commentaire ne peut pas être vide");
        }
        Rating rating = new Rating();
        rating.setParent(parent);
        rating.setHotel(hotel);
        rating.setDescription(commentaire);

        return ratingRepository.save(rating);
    }

    public Rating laisserNote(Parent parent, Hotel hotel, Double note) {
        if (note < 0 || note > 5) {
            throw new IllegalArgumentException("La note doit être comprise entre 0 et 5");
        }
        Rating rating = new Rating();
        rating.setParent(parent);
        rating.setHotel(hotel);
        rating.setRating(note);

        return ratingRepository.save(rating);
    }

    public double calculerMoyenneNotes(Hotel hotel) {
        List<Rating> ratings = ratingRepository.findByHotel(hotel);

        if (ratings.isEmpty()) {
            return 0.0;
        }

        double sommeNotes = 0.0;
        for (Rating rating : ratings) {
            Double note = rating.getRating();
            if (note != null) {
                sommeNotes += note;
            }
        }

        return sommeNotes / ratings.size();
    }
    public String obtenirMessageNote(double moyenneNotes) {
        if (moyenneNotes == 0) {
            return "Pas de notes disponibles.";
        } else {
            return "Moyenne des notes : " + moyenneNotes;
        }
    }

}


