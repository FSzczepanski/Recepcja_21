/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recepcja.frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.naming.spi.InitialContextFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import recepcja.backend.BazaDanychHotele;
import recepcja.backend.BazaDanychKlienci;
import recepcja.backend.BazaDanychPokoje;
import recepcja.backend.BazaDanychRezerwacje;
import recepcja.entities.Adres;
import recepcja.entities.Hotel;
import recepcja.entities.Klient;
import recepcja.entities.Pokoj;
import recepcja.entities.Rezerwacja;

/**
 *
 * @author Filip Szczepanski
 */
public class StartFrame extends javax.swing.JFrame {
    private BazaDanychRezerwacje bd;
    private ArrayList<Rezerwacja> rezerwacje;
    

    HoteleFrame hf;
    PokojeFrame pf;
    KlienciFrame kf;
    AdresyFrame af;
    
    public StartFrame() {
        initComponents();
        bd = new BazaDanychRezerwacje();
        initFrames();
        
        initComboBoxesHotels();
        initComboBoxClients();
        initComboBoxRooms();
        comboBoxListeners();
        initTable();
    }
    
    
    private void initFrames(){
        hf = new HoteleFrame();
        hf.setTitle("Hotele");
        hf.setSize(800,580);
        
        pf = new PokojeFrame();
        pf.setTitle("Pokoje");
        pf.setSize(800,580);
        
        kf = new KlienciFrame();
        kf.setTitle("Klienci");
        kf.setSize(800,580);     
        
        af = new AdresyFrame();
        af.setTitle("Adresy");
        af.setSize(800,580);   
    }
    
    private void comboBoxListeners(){
    
         rComboKlienci.addActionListener (new ActionListener () {
    public void actionPerformed(ActionEvent e) {
            rComboKlienci.removeAllItems();
            initComboBoxClients();
        }
      });
         
         rComboPokoje.addActionListener (new ActionListener () {
    public void actionPerformed(ActionEvent e) {
            rComboPokoje.removeAllItems();
            initComboBoxRooms();
        }
      });
    }
      private void initComboBoxesHotels(){
        //hotele
        ArrayList<Hotel> hotele= new ArrayList<>();
         BazaDanychHotele bdH = new BazaDanychHotele();
        hotele = bdH.pobierzHotele();
       
        for (int i = 0; i < hotele.size(); i++) {
            String x = hotele.get(i).getId()+"."+hotele.get(i).getNazwaHotelu();
            comboHotels.addItem(x);
            rComboHotele.addItem(x);
        }
        
     
        
        comboHotels.addActionListener (new ActionListener () {
    public void actionPerformed(ActionEvent e) {
        clearTable();
        initTable();
        }
      });
        
       rComboHotele.addActionListener (new ActionListener () {
    public void actionPerformed(ActionEvent e) {
        rComboPokoje.removeAllItems();
         ArrayList<Pokoj> pokoje = new ArrayList();
         BazaDanychPokoje bdP = new BazaDanychPokoje();
         
         String idString = rComboHotele.getSelectedItem().toString();
        String[] tab = idString.split("");
        int id = Integer.parseInt(tab[0]);
        
        pokoje = bdP.pobierzPokoje(id);
      
        
        for (int i = 0; i < pokoje.size(); i++) {
            String x = pokoje.get(i).getId()+"."+pokoje.get(i).getNumerPokoju();
            rComboPokoje.addItem(x);
        }
        }
      });

    }
      
    private void initComboBoxClients(){
         //klienci
        ArrayList<Klient> klienci = new ArrayList();
         BazaDanychKlienci bdK = new BazaDanychKlienci();
        klienci = bdK.pobierzKlientow();
        
         for (int i = 0; i < klienci.size(); i++) {
            String x = klienci.get(i).getId()+"."+klienci.get(i).getImie()+" "+klienci.get(i).getNazwisko();
            rComboKlienci.addItem(x);
        }
    }
    
    private void initComboBoxRooms(){
            
         //pokoje
        ArrayList<Pokoj> pokoje = new ArrayList();
         BazaDanychPokoje bdP = new BazaDanychPokoje();
         
         String idString = rComboHotele.getSelectedItem().toString();
        String[] tab = idString.split("");
        int id = Integer.parseInt(tab[0]);
        
        pokoje = bdP.pobierzPokoje(id);
      
        
        for (int i = 0; i < pokoje.size(); i++) {
            String x = pokoje.get(i).getId()+"."+pokoje.get(i).getNumerPokoju();
            rComboPokoje.addItem(x);
        }
    }
    
  
    
    
    private void initTable(){
        
        String idString = comboHotels.getSelectedItem().toString();
        String[] tab = idString.split("");
        int id = Integer.parseInt(tab[0]);
        
        rezerwacje = bd.pobierzRezerwacje(id);
        DefaultTableModel model = (DefaultTableModel) tableRezerwacje.getModel();
        
        for (Rezerwacja rezerwacja: rezerwacje) {
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
            String data_przyjazdu= formatter.format(rezerwacja.getData_przyjazdu());  
            String data_wyjazdu= formatter.format(rezerwacja.getData_wyjazdu());  
            
            String daysCount="";
          
            long diff = rezerwacja.getData_wyjazdu().getTime() - rezerwacja.getData_przyjazdu().getTime();
            long time=TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            daysCount = String.valueOf(time);
            int cena = rezerwacja.getPokoj().getCenaZaDobe()*Integer.parseInt(daysCount);
            
            Object[] row = {
                rezerwacja.getId(),
                "nr "+rezerwacja.getPokoj().getNumerPokoju(),
                rezerwacja.getPokoj().getIloscOsob(),
                rezerwacja.getKlient().getImie()+" "+rezerwacja.getKlient().getNazwisko(),
                data_przyjazdu,
                data_wyjazdu,
                cena+" zł"
            };
            
             model.addRow(row);
            
        }
       
    }
    
    
    private void clearTable(){
        DefaultTableModel dm = (DefaultTableModel) tableRezerwacje.getModel();
        int rowCount = dm.getRowCount();
        
        for (int i = rowCount - 1; i >= 0; i--) {
            dm.removeRow(i);
}
    }
   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane2 = new javax.swing.JDesktopPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableRezerwacje = new javax.swing.JTable();
        comboHotels = new javax.swing.JComboBox<>();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        bDodajHotel = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        bEdytuj = new javax.swing.JButton();
        bUsun = new javax.swing.JButton();
        eID = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        eDataPrzDD = new javax.swing.JTextField();
        rComboHotele = new javax.swing.JComboBox<>();
        rComboKlienci = new javax.swing.JComboBox<>();
        rComboPokoje = new javax.swing.JComboBox<>();
        eDataPrzMM = new javax.swing.JTextField();
        eDataWyjRR = new javax.swing.JTextField();
        eDataWyjDD = new javax.swing.JTextField();
        eDataWyjMM = new javax.swing.JTextField();
        eDataPrzRR = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuHotele = new javax.swing.JMenuItem();
        menuPokoje = new javax.swing.JMenuItem();
        menuKlienci = new javax.swing.JMenuItem();
        menuAdresy = new javax.swing.JMenuItem();
        menuZakoncz = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tableRezerwacje.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "pokoj", "ilosc osob", "klient", "data przyjazdu", "data wyjazdu", "cena"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableRezerwacje);

        jDesktopPane2.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(comboHotels, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane2Layout = new javax.swing.GroupLayout(jDesktopPane2);
        jDesktopPane2.setLayout(jDesktopPane2Layout);
        jDesktopPane2Layout.setHorizontalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
                    .addGroup(jDesktopPane2Layout.createSequentialGroup()
                        .addComponent(comboHotels, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jDesktopPane2Layout.setVerticalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboHotels, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        bDodajHotel.setText("Dodaj");
        bDodajHotel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDodajHotelActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel6.setText("Dodaj rezerwacje");
        jLabel6.setBorder(new javax.swing.border.MatteBorder(null));

        jLabel2.setText("Klient");

        jLabel5.setText("data wyjazdu");

        jLabel1.setText("Hotel");

        jLabel3.setText("Pokoj");

        jLabel4.setText("data przyjazdu");

        bEdytuj.setText("Edytuj");
        bEdytuj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEdytujActionPerformed(evt);
            }
        });

        bUsun.setText("Usuń");
        bUsun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUsunActionPerformed(evt);
            }
        });

        jLabel7.setText("podaj id");

        eDataPrzDD.setText("dd");

        eDataPrzMM.setText("mm");

        eDataWyjRR.setText("rrrr");

        eDataWyjDD.setText("dd");

        eDataWyjMM.setText("mm");

        eDataPrzRR.setText("rrrr");

        jDesktopPane1.setLayer(bDodajHotel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(bEdytuj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(bUsun, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(eID, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(eDataPrzDD, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(rComboHotele, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(rComboKlienci, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(rComboPokoje, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(eDataPrzMM, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(eDataWyjRR, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(eDataWyjDD, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(eDataWyjMM, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(eDataPrzRR, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rComboPokoje, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rComboHotele, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rComboKlienci, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(57, 57, 57)
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(eDataWyjDD, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(eDataWyjMM, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(eDataWyjRR, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(14, 14, 14)
                                        .addComponent(eDataPrzDD, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(eDataPrzMM, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(eDataPrzRR, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(6, 6, 6)
                                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                        .addGap(56, 56, 56)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35)
                                        .addComponent(eID, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                                        .addComponent(bEdytuj, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(bUsun, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(22, 22, 22))))
                            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addComponent(bDodajHotel, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(eDataPrzDD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(rComboHotele, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eDataPrzMM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eDataPrzRR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(bEdytuj)
                    .addComponent(bUsun)
                    .addComponent(rComboKlienci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eDataWyjDD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eDataWyjMM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eDataWyjRR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(rComboPokoje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bDodajHotel))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        jMenu1.setText("Menu");

        menuHotele.setText("Hotele");
        menuHotele.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHoteleActionPerformed(evt);
            }
        });
        jMenu1.add(menuHotele);

        menuPokoje.setText("Pokoje");
        menuPokoje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPokojeActionPerformed(evt);
            }
        });
        jMenu1.add(menuPokoje);

        menuKlienci.setText("Klienci");
        menuKlienci.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuKlienciActionPerformed(evt);
            }
        });
        jMenu1.add(menuKlienci);

        menuAdresy.setText("Adresy");
        menuAdresy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAdresyActionPerformed(evt);
            }
        });
        jMenu1.add(menuAdresy);

        menuZakoncz.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuZakoncz.setText("Zakończ");
        menuZakoncz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuZakonczActionPerformed(evt);
            }
        });
        jMenu1.add(menuZakoncz);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDesktopPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuKlienciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuKlienciActionPerformed
        kf.setVisible(true);
    }//GEN-LAST:event_menuKlienciActionPerformed

    private void menuHoteleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHoteleActionPerformed
        hf.setVisible(true);
    }//GEN-LAST:event_menuHoteleActionPerformed

    private void menuZakonczActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuZakonczActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menuZakonczActionPerformed

    private void menuPokojeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPokojeActionPerformed
        pf.setVisible(true);
    }//GEN-LAST:event_menuPokojeActionPerformed

    private void bDodajHotelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDodajHotelActionPerformed
        clearTable();
        String idStringH = rComboHotele.getSelectedItem().toString();
        String[] tabH = idStringH.split("");
        int idH = Integer.parseInt(tabH[0]);
        
        String idStringK = rComboKlienci.getSelectedItem().toString();
        String[] tabK = idStringK.split("");
        int idK = Integer.parseInt(tabK[0]);
        
        String idStringP = rComboPokoje.getSelectedItem().toString();
        String[] tabP = idStringP.split("");
        int idP = Integer.parseInt(tabP[0]);
        
        String dataPrzyjazdu = eDataPrzDD.getText()+"/"+eDataPrzMM.getText()+"/"+eDataPrzRR.getText();
        String dataWyjazdu = eDataWyjDD.getText()+"/"+eDataWyjMM.getText()+"/"+eDataWyjRR.getText();
        
        
        try{
            
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(dataPrzyjazdu);  
        Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(dataWyjazdu);  
        bd.dodajRezerwacje(idH, idK, idP, date1, date2);
        JOptionPane.showMessageDialog(null,"dodano rezerwację",
        "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Błąd "+ex.getMessage(),
        "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
        }
        
       
        initTable();
    }//GEN-LAST:event_bDodajHotelActionPerformed

    private void bEdytujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEdytujActionPerformed
        clearTable();
       String idStringH = rComboHotele.getSelectedItem().toString();
        String[] tabH = idStringH.split("");
        int idH = Integer.parseInt(tabH[0]);
        
        String idStringK = rComboKlienci.getSelectedItem().toString();
        String[] tabK = idStringK.split("");
        int idK = Integer.parseInt(tabK[0]);
        
        String idStringP = rComboPokoje.getSelectedItem().toString();
        String[] tabP = idStringP.split("");
        int idP = Integer.parseInt(tabP[0]);
        
        String dataPrzyjazdu = eDataPrzDD.getText()+"/"+eDataPrzMM.getText()+"/"+eDataPrzRR.getText();
        String dataWyjazdu = eDataWyjDD.getText()+"/"+eDataWyjMM.getText()+"/"+eDataWyjRR.getText();
        
        
        try{
            
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(dataPrzyjazdu);  
        Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(dataWyjazdu);  
        bd.modyfikujRezerwacje(Integer.parseInt(eID.getText()),idH, idK, idP, date1, date2);
        
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Błąd "+ex.getMessage(),
        "Błąd aplikacji",JOptionPane.ERROR_MESSAGE);
        }
        initTable();
    }//GEN-LAST:event_bEdytujActionPerformed

    private void bUsunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUsunActionPerformed
        clearTable();
        bd.usunRezerwacje(Integer.parseInt(eID.getText()));
        initTable();
    }//GEN-LAST:event_bUsunActionPerformed

    private void menuAdresyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAdresyActionPerformed
       af.setVisible(true);
    }//GEN-LAST:event_menuAdresyActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bDodajHotel;
    private javax.swing.JButton bEdytuj;
    private javax.swing.JButton bUsun;
    private javax.swing.JComboBox<String> comboHotels;
    private javax.swing.JTextField eDataPrzDD;
    private javax.swing.JTextField eDataPrzMM;
    private javax.swing.JTextField eDataPrzRR;
    private javax.swing.JTextField eDataWyjDD;
    private javax.swing.JTextField eDataWyjMM;
    private javax.swing.JTextField eDataWyjRR;
    private javax.swing.JTextField eID;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem menuAdresy;
    private javax.swing.JMenuItem menuHotele;
    private javax.swing.JMenuItem menuKlienci;
    private javax.swing.JMenuItem menuPokoje;
    private javax.swing.JMenuItem menuZakoncz;
    private javax.swing.JComboBox<String> rComboHotele;
    private javax.swing.JComboBox<String> rComboKlienci;
    private javax.swing.JComboBox<String> rComboPokoje;
    private javax.swing.JTable tableRezerwacje;
    // End of variables declaration//GEN-END:variables
}
