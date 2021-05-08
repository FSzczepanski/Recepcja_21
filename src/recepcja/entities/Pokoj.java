/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recepcja.entities;

/**
 *
 * @author Filip Szczepanski
 */
public class Pokoj {
    private int id;
    private Hotel hotel;
    private int numerPokoju;
    private int pietro;
    private int iloscOsob;
    private boolean prywatnaLazienka;
    private int cenaZaDobe;

    public Pokoj(int id,Hotel hotel, int numerPokoju, int pietro, int iloscOsob, boolean prywatnaLazienka, int cenaZaDobe) {
        this.id = id;
        this.hotel = hotel;
        this.numerPokoju = numerPokoju;
        this.pietro = pietro;
        this.iloscOsob = iloscOsob;
        this.prywatnaLazienka = prywatnaLazienka;
        this.cenaZaDobe = cenaZaDobe;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getNumerPokoju() {
        return numerPokoju;
    }

    public void setNumerPokoju(int numerPokoju) {
        this.numerPokoju = numerPokoju;
    }

    public int getPietro() {
        return pietro;
    }

    public void setPietro(int pietro) {
        this.pietro = pietro;
    }

    public int getIloscOsob() {
        return iloscOsob;
    }

    public void setIloscOsob(int iloscOsob) {
        this.iloscOsob = iloscOsob;
    }

    public boolean isPrywatnaLazienka() {
        return prywatnaLazienka;
    }

    public void setPrywatnaLazienka(boolean prywatnaLazienka) {
        this.prywatnaLazienka = prywatnaLazienka;
    }

    public int getCenaZaDobe() {
        return cenaZaDobe;
    }

    public void setCenaZaDobe(int cenaZaDobe) {
        this.cenaZaDobe = cenaZaDobe;
    }
    
    
    
}
