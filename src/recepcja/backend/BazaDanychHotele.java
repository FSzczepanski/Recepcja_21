
package recepcja.backend;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import recepcja.entities.Hotel;



public class BazaDanychHotele {
    private Connection polaczenie;

    public BazaDanychHotele() {
        
    }
    
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
    
    
    
     public void dodajHotel(String nazwaHotelu, String miasto, int liczbaGwiazdek, boolean calodobowaRecepcja, boolean basen) {
    try {
         
            initConnection();
            CallableStatement procedura =
                        polaczenie.prepareCall("{call dbo.dodajHotel(?,?,?,?,?)}");
            procedura.setString(1,nazwaHotelu);
            procedura.setString(2,miasto);
            procedura.setInt(3,liczbaGwiazdek);
            procedura.setBoolean(4,calodobowaRecepcja);
            procedura.setBoolean(5,basen);
            procedura.execute();
                    
            polaczenie.close();
    }
    catch(Exception e) {
        JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
        "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
    }
    }
     
     
     public ArrayList pobierzHotele() {
         
         ArrayList<Hotel> hotele = new ArrayList();
         
    try {
            initConnection();
            PreparedStatement ps =
                    polaczenie.prepareCall("{call dbo.pobierzHotel()}");
            
            ResultSet rezultat = ps.executeQuery();
            while(rezultat.next()){
                hotele.add(new Hotel(rezultat.getInt("id_hotelu"),
                        rezultat.getString("nazwa_hotelu"),
                        rezultat.getString("miasto"),
                        rezultat.getInt("liczba_gwiazdek"),
                        rezultat.getBoolean("calodobowa_recepcja"),
                        rezultat.getBoolean("basen")));
            }
          
            
            polaczenie.close();
    }
    catch(Exception e) {
        JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
        "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
    }
    return hotele;
    }
     
     
     public void modyfikujHotel(int id, String nazwaHotelu, String miasto, int liczbaGwiazdek, boolean calodobowaRecepcja, boolean basen) {
         
     
    try {
            initConnection();
            CallableStatement procedura =
                    polaczenie.prepareCall("{call dbo.modyfikujHotel(?,?,?,?,?,?)}");
            
            procedura.setInt(1,id);
            procedura.setString(2,nazwaHotelu);
            procedura.setString(3,miasto);
            procedura.setInt(4,liczbaGwiazdek);
            procedura.setBoolean(5,calodobowaRecepcja);
            procedura.setBoolean(6,basen);
            procedura.execute();
      
            
            polaczenie.close();
    }
    catch(Exception e) {
        JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
        "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
    }
    }
     
     
     public void usunHotel(int id) {
    try {
            initConnection();
            CallableStatement procedura =
                    polaczenie.prepareCall("{call dbo.usunHotel(?)}");
            procedura.setInt(1,id);
            
            procedura.execute();
                    
            polaczenie.close();
    }
    catch(Exception e) {
        JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
        "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
    }
    }
     
     
     public Hotel pobierzHotel(int id) {
         
         Hotel hotel = null;
         
    try {
            initConnection();
            PreparedStatement ps =
                    polaczenie.prepareCall("{call dbo.pobierzHotelJeden(?)}");
             ps.setInt(1,id);
            
            ps.execute();
            
            ResultSet rezultat = ps.executeQuery();
            while(rezultat.next()){
            
                hotel = new Hotel(rezultat.getInt("id_hotelu"),
                        rezultat.getString("nazwa_hotelu"),
                        rezultat.getString("miasto"),
                        rezultat.getInt("liczba_gwiazdek"),
                        rezultat.getBoolean("calodobowa_recepcja"),
                        rezultat.getBoolean("basen"));
            
          
            }
            polaczenie.close();
    }
    catch(Exception e) {
        JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
        "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
    }
    return hotel;
    }
     
     

}
