package fr.simplon.minorease.services;

import fr.simplon.minorease.entities.Chambre;
import fr.simplon.minorease.entities.Hotel;

import java.util.ArrayList;
import java.util.List;

public class RechercheServices {

    public List<Hotel> getHotelByFourchette(List<Hotel> allhotels, double prixMini, double prixMax) {
        List<Hotel> hotelsToReturn = new ArrayList<>();
        for (Hotel hotel : allhotels) {
            if (getChamberByFourchette(hotel.getChambres(),prixMini,prixMax)==true){
                hotelsToReturn.add(hotel);
            }
        }
        return hotelsToReturn;
    }

    public boolean getChamberByFourchette(List<Chambre> allChambreByHotel, double prixMini, double prixMax) {
        for(Chambre chambre :allChambreByHotel){
            if (chambre.getPrix()<=prixMax && chambre.getPrix()>= prixMini){
                return true;
            }
        }
        return false;
    }



}
