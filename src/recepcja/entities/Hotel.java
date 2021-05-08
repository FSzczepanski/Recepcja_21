
package recepcja.entities;


public class Hotel {
    private int id;
    private String nazwaHotelu;
    private String miasto;
    private int liczbaGwiazdek;
    private boolean calodobowaRecepcja;
    private boolean basen;

    public Hotel(int id, String nazwaHotelu, String miasto, int liczbaGwiazdek, boolean calodobowaRecepcja, boolean basen) {
        this.id = id;
        this.nazwaHotelu = nazwaHotelu;
        this.miasto = miasto;
        this.liczbaGwiazdek = liczbaGwiazdek;
        this.calodobowaRecepcja = calodobowaRecepcja;
        this.basen = basen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwaHotelu() {
        return nazwaHotelu;
    }

    public void setNazwaHotelu(String nazwaHotelu) {
        this.nazwaHotelu = nazwaHotelu;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public int getLiczbaGwiazdek() {
        return liczbaGwiazdek;
    }

    public void setLiczbaGwiazdek(int liczbaGwiazdek) {
        this.liczbaGwiazdek = liczbaGwiazdek;
    }

    public boolean isCalodobowaRecepcja() {
        return calodobowaRecepcja;
    }

    public void setCalodobowaRecepcja(boolean calodobowaRecepcja) {
        this.calodobowaRecepcja = calodobowaRecepcja;
    }

    public boolean isBasen() {
        return basen;
    }

    public void setBasen(boolean basen) {
        this.basen = basen;
    }
    
    
}
