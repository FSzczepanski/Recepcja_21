
package recepcja.backend;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import recepcja.entities.Pokoj;
import recepcja.entities.Hotel;

public class BazaDanychPokoje {
    private Connection polaczenie;
      
    public BazaDanychPokoje(){
    
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
     
     public void dodajPokoj( int id_hotelu, int numer_pokoju, int pietro, int ilosc_osob, boolean prywatna_lazienka, int cena_za_dobe) {
    try {
         
            initConnection();
            CallableStatement procedura =
                        polaczenie.prepareCall("{call dbo.dodajPokoj(?,?,?,?,?,?)}");
            procedura.setInt(1,id_hotelu);
            procedura.setInt(2,numer_pokoju);
            procedura.setInt(3,pietro);
            procedura.setInt(4,ilosc_osob);
            procedura.setBoolean(5,prywatna_lazienka);
            procedura.setInt(6,cena_za_dobe);
            procedura.execute();
                    
            polaczenie.close();
    }
    catch(Exception e) {
        JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
        "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
    }
    }
    
     
     public ArrayList pobierzPokoje(int id_hotelu) {
         
         ArrayList<Pokoj> pokoje = new ArrayList();
         
         try {
            initConnection();
            PreparedStatement ps =
                polaczenie.prepareCall("{call dbo.pobierzPokoj(?)}");
            ps.setInt(1, id_hotelu);
            ps.execute();
            
            ResultSet rezultat = ps.executeQuery();
            while(rezultat.next()){
                
                // wyciaganie id_hotelu z procedury wyswietlanai hoteli
                BazaDanychHotele bdH = new BazaDanychHotele();
                Hotel hotel = bdH.pobierzHotel(rezultat.getInt("id_hotelu"));
                
                pokoje.add(new Pokoj(rezultat.getInt("id_pokoju"),
                        hotel,  //id hotelu wyciągmniete z procedury 
                        rezultat.getInt("numer_pokoju"),
                        rezultat.getInt("pietro"),
                        rezultat.getInt("ilosc_osob"),  
                        rezultat.getBoolean("prywatna_lazienka"),   
                        rezultat.getInt("cena_za_dobe")));                        
            }
          
            
            polaczenie.close();
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
            "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
        }
        return pokoje;
    }
     
      
      public void modyfikujPokoj(int id, int id_hotelu, int numer_pokoju, int pietro, int ilosc_osob,boolean prywatna_lazienka, int cena_za_dobe) {
         
     
        try {
            initConnection();
            CallableStatement procedura =
                    polaczenie.prepareCall("{call dbo.modyfikujPokoj(?,?,?,?,?,?,?)}");
            
            procedura.setInt(1,id);
            procedura.setInt(2,id_hotelu);
            procedura.setInt(3,numer_pokoju);
            procedura.setInt(4,pietro);
            procedura.setInt(5,ilosc_osob);
            procedura.setBoolean(6,prywatna_lazienka);
            procedura.setInt(7,cena_za_dobe);
            procedura.execute();
      
            
            polaczenie.close();
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Błąd "+e.getMessage(),
                "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
        }
        }
      
       public void usunPokoj(int id) {
        try {
            initConnection();
            CallableStatement procedura =
                    polaczenie.prepareCall("{call dbo.usunPokoj(?)}");
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