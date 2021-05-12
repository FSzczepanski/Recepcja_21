
package recepcja.backend;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import recepcja.entities.Adres;
import recepcja.entities.Hotel;
import recepcja.entities.Klient;
import recepcja.entities.Pokoj;
import recepcja.entities.Rezerwacja;


public class BazaDanychRezerwacje {
    private Connection polaczenie;

    
    private void initConnection(){
        try{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             polaczenie= DriverManager.getConnection(
            "jdbc:sqlserver://localhost\\sqlexpress;da6tabaseName=pab_zaliczenie",
                    "recepcjonista","s2232haslo");
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
            "Błąd polaczenia",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
     public void dodajRezerwacje(int id_hotelu, int id_klienta, int id_pokoju, Date data_przyjazdu, Date data_wyjazdu) {
        try {
         
            initConnection();
            CallableStatement procedura =
                        polaczenie.prepareCall("{call dbo.dodajRezerwacje(?,?,?,?,?)}");
            procedura.setInt(1, id_hotelu);
            procedura.setInt(2,id_klienta);
            procedura.setInt(3,id_pokoju);
           procedura.setDate(4,new java.sql.Date(data_przyjazdu.getTime()));
            procedura.setDate(5,new java.sql.Date(data_wyjazdu.getTime()));
            procedura.execute();
            
                    
            polaczenie.close();
        }
        catch(Exception e) {
        JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
        "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
        }
    }
     
     
     public ArrayList pobierzRezerwacje(int id_hotelu) {
         
         ArrayList<Rezerwacja> rezerwacje = new ArrayList();
         
        try {
            initConnection();
            PreparedStatement ps =
                    polaczenie.prepareCall("{call dbo.pobierzRezerwacje(?)}");
            ps.setInt(1, id_hotelu);
            ps.execute();
            
            ResultSet rezultat = ps.executeQuery();
            while(rezultat.next()){
                
                BazaDanychHotele bdH = new BazaDanychHotele();
                Hotel hotel = bdH.pobierzHotel(rezultat.getInt("id_hotelu"));
                
                //temp
                Klient klient = new Klient(51, new Adres(64, "miasto", "kod", "ul", "nr"), "imie", "nazwisko", "nrtel");
                Pokoj pokoj = new Pokoj(52,hotel,3,4,4,true,420);
                //temp
                
                Date data_przyjazdu = new java.util.Date(rezultat.getDate("data_przyjazdu").getTime());
                Date data_wyjazdu = new java.util.Date(rezultat.getDate("data_wyjazdu").getTime());
                
                rezerwacje.add(new Rezerwacja(rezultat.getInt("id_rezerwacji"),hotel,klient,pokoj,data_przyjazdu,data_wyjazdu));
            }
          
            
            polaczenie.close();
        }
        catch(Exception e) {
        JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
        "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
        }
    return rezerwacje;
    }
     
     
     public void modyfikujRezerwacje(int id, int id_hotelu, int id_klienta, int id_pokoju, Date data_przyjazdu, Date data_wyjazdu) {
         
     
        try {
            initConnection();
            CallableStatement procedura =
                    polaczenie.prepareCall("{call dbo.modyfikujRezerwacje(?,?,?,?,?,?)}");
            
            procedura.setInt(1, id);
            procedura.setInt(2, id_hotelu);
            procedura.setInt(3,id_klienta);
            procedura.setInt(4,id_pokoju);
           procedura.setDate(5,new java.sql.Date(data_przyjazdu.getTime()));
            procedura.setDate(6,new java.sql.Date(data_wyjazdu.getTime()));
            procedura.execute();
      
            
            polaczenie.close();
        }
        catch(Exception e) {
        JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
        "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
        }
    }
     
     
     public void usunRezerwacje(int id) {
        try {
            initConnection();
            CallableStatement procedura =
                    polaczenie.prepareCall("{call dbo.usunRezerwacje(?)}");
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
