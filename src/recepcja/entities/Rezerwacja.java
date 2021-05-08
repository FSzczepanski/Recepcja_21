/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recepcja.entities;

import java.util.Date;

/**
 *
 * @author Filip Szczepanski
 */
public class Rezerwacja {
    private int id;
    private Hotel hotel;
    private Klient klient;
    private Pokoj pokoj;
    private Date data_przyjazdu;
    private Date data_wyjazdu;

    public Rezerwacja(int id, Hotel hotel, Klient klient, Pokoj pokoj, Date data_przyjazdu, Date data_wyjazdu) {
        this.id = id;
        this.hotel = hotel;
        this.klient = klient;
        this.pokoj = pokoj;
        this.data_przyjazdu = data_przyjazdu;
        this.data_wyjazdu = data_wyjazdu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Klient getKlient() {
        return klient;
    }

    public void setKlient(Klient klient) {
        this.klient = klient;
    }

    public Pokoj getPokoj() {
        return pokoj;
    }

    public void setPokoj(Pokoj pokoj) {
        this.pokoj = pokoj;
    }

    public Date getData_przyjazdu() {
        return data_przyjazdu;
    }

    public void setData_przyjazdu(Date data_przyjazdu) {
        this.data_przyjazdu = data_przyjazdu;
    }

    public Date getData_wyjazdu() {
        return data_wyjazdu;
    }

    public void setData_wyjazdu(Date data_wyjazdu) {
        this.data_wyjazdu = data_wyjazdu;
    }
    
    
            
    
}
