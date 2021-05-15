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
import recepcja.entities.Klient;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class BazaDanychKlienci {
    private Connection polaczenie;
    

    public BazaDanychKlienci() {
        
    }
    
    private void initConnection(){
        try{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection polaczenie=DriverManager.getConnection(
                    "jdbc:sqlserver://localhost\\sqlexpress;databaseName=pab_ZALICZENIE",
                    "recepcjonista", "admin123");
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
            "Błąd polaczenia",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
     public void dodajKlienta(int id_adresu, String imie, String nazwisko, String numerTelefonu) {
        try {
         
            initConnection();
            CallableStatement procedura =
                        polaczenie.prepareCall("{call dbo.dodajKlienta(?,?,?,?)}");
            procedura.setInt(1,111);
            procedura.setString(2,imie);
            procedura.setString(3,nazwisko);
            procedura.setString(4,numerTelefonu);
            procedura.execute();
                    
            polaczenie.close();
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
                "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
        }
    }

     public ArrayList pobierzKlientow() {
         
         ArrayList<Klient> klienci = new ArrayList();
         
        try {
            initConnection();
            PreparedStatement ps =
                    polaczenie.prepareCall("{call dbo.pobierzKlienta()}");
            
            ResultSet rezultat = ps.executeQuery();
            while(rezultat.next()){
                
                BazaDanychAdresy bdA = new BazaDanychAdresy();
                Adres adres = bdA.pobierzAdres(rezultat.getInt("id_adresu"));
                
                klienci.add(new Klient(rezultat.getInt("id_klienta"),
                        adres,
                        rezultat.getString("imie"),
                        rezultat.getString("nazwisko"),
                        rezultat.getString("numerTelefonu")));
            }
          
            
            polaczenie.close();
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
                "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
        }
        return klienci;
    }
     
     
     public void modyfikujKlienta(int id, int id_adresu, String imie, String nazwisko, String numerTelefonu) {
         
     
        try {
            initConnection();
            CallableStatement procedura =
                    polaczenie.prepareCall("{call dbo.modyfikujKlienta(?,?,?,?,?)}");
            
            procedura.setInt(1,id);
            procedura.setInt(2,id_adresu);
            procedura.setString(3,imie);
            procedura.setString(4,nazwisko);
            procedura.setString(5,numerTelefonu);
            procedura.execute();
      
            
            polaczenie.close();
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
                "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
        }
    }
     
     
     public void usunKlienta(int id) {
        try {
            initConnection();
            CallableStatement procedura =
                    polaczenie.prepareCall("{call dbo.usunKlienta(?)}");
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
