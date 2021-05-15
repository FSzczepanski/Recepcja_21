/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recepcja.backend;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import recepcja.entities.Adres;
import recepcja.entities.Hotel;

/**
 *
 * @author Domi
 */
public class BazaDanychAdresy {
     private Connection polaczenie;
     
     public BazaDanychAdresy() {
        
    }
     
     private void initConnection(){
        try{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             polaczenie= DriverManager.getConnection(
            "jdbc:sqlserver://localhost\\sqlexpress;da6tabaseName=pab_zaliczenie",
                    "recepcjonista","admin123");
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
            "Błąd polaczenia",JOptionPane.ERROR_MESSAGE);
        }
    }
     
     
    public void dodajAdres(String miasto, String kodPocztowy, String ulica, String numer){
        try {
            
            initConnection();
            CallableStatement procedura =
                        polaczenie.prepareCall("{call dbo.dodajAdres(?,?,?,?)}");
            procedura.setString(1,miasto);
            procedura.setString(2,kodPocztowy);
            procedura.setString(3,ulica);
            procedura.setString(4,numer);
            procedura.execute();
                    
            polaczenie.close();
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
                "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public ArrayList pobierzAdres() {
         
         ArrayList<Adres> adresy = new ArrayList();
         
        try {
            initConnection();
            PreparedStatement ps =
                    polaczenie.prepareCall("{call dbo.pobierzAdres()}");
            
            ResultSet rezultat = ps.executeQuery();
            while(rezultat.next()){
                adresy.add(new Adres(rezultat.getInt("id_adresu"),
                        rezultat.getString("miasto"),
                        rezultat.getString("kodPcztowy"),
                        rezultat.getString("ulica"),
                        rezultat.getString("numer")));
            }
          
            
            polaczenie.close();
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
                "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
        }
        return adresy;
    }
    
    public Adres pobierzAdres(int id){
        Adres adres = null;
         
        try {
            initConnection();
            PreparedStatement ps =
                    polaczenie.prepareCall("{call dbo.pobierzAdresJeden(?)}");
             ps.setInt(1,id);
            
            ps.execute();
            
            ResultSet rezultat = ps.executeQuery();
            while(rezultat.next()){
            
                adres = new Adres(rezultat.getInt("id_adresu"),
                        rezultat.getString("miasto"),
                        rezultat.getString("kodPocztowy"),
                        rezultat.getString("ulica"),
                        rezultat.getString("numer"));
            }
                polaczenie.close();
        }
        catch(Exception e) {
        JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
        "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
        }
        return adres;
    }
    
    public void modyfikujAdres(int id, String miasto, String kodPocztowy, String ulica, String numer) {
         
     
        try {
            initConnection();
            CallableStatement procedura =
                    polaczenie.prepareCall("{call dbo.modyfikujAdres(?,?,?,?,?)}");
            
            procedura.setInt(1,id);
            procedura.setString(2,miasto);
            procedura.setString(3,kodPocztowy);
            procedura.setString(4,ulica);
            procedura.setString(5,numer);
            procedura.execute();
      
            
            polaczenie.close();
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
                "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
        }
    }
     
     
     public void usunAdres(int id) {
        try {
            initConnection();
            CallableStatement procedura =
                    polaczenie.prepareCall("{call dbo.usunAdres(?)}");
            procedura.setInt(1,id);
            
            procedura.execute();
                    
            polaczenie.close();
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
                "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
        }
    }
}

    
