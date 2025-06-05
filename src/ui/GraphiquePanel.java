package src;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Comparator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/** Afficher le cours d'un produit financier et ses volumes associés. */
public class GraphiquePanel extends JPanel {
    private static final int TAILLE_FENETRE_INITIAL = 31;   // nombre de jours initial
    private static final int PAS_FENETRE = 2;               // nombre de jours rajoutés

    private String titre; 
    private JFreeChart chart;    
    private XYPlot plot;         // tracé du graphique

    private Date dateDebut;      // début de la fenêtre
    private Date dateFin;        // fin de la fenêtre
    private int periode;         // nombre de jours actuellement

    private List<String[]> donnees;
    private XYSeries seriePrix;                 // les prix de clôture et leur date
    private XYSeries serieVolume;               // les volumes et leur date 
    private XYSeriesCollection datasetVolume;   // dataset du graphique de volume

    private final JButton boutonAvancer;        // bouton pour avancer dans le temps

    private double prixGraphe;
    private PropertyChangeSupport pcs;

    /**
     * Constructeur.
     * @param titre titre du graphique
     */
    public GraphiquePanel(String titre) {
        this.titre = titre;
        this.periode = TAILLE_FENETRE_INITIAL;

        seriePrix = new XYSeries("Prix de Cloture");  
        serieVolume = new XYSeries("Volume");      

        dateDebut = new Date();             
        dateFin = ajouterJours(dateDebut, periode);
        prixGraphe = 0.0;
        pcs = new PropertyChangeSupport(this);
        construireGraphique();

        boutonAvancer = new JButton("Avancer");
        boutonAvancer.setEnabled(false);                  // désactivé avant chargement
        boutonAvancer.addActionListener(e -> {
            if (donnees != null && !donnees.isEmpty()) {
                avancerDansLeTemps();                     // étend la fenêtre
            }
        });
        add(boutonAvancer, BorderLayout.NORTH);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public double getPrixGraphe() {
        return prixGraphe;
    }


    /**
     * Charge et trie les données, initialise la fenêtre sur l'année la plus récente
     * @param data liste de tableaux [date, ..., prix, volume]
     */
    public void setData(List<String[]> data) {
        this.donnees = data;
        this.periode = TAILLE_FENETRE_INITIAL;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        double oldPrixGraphe = prixGraphe;
        if (donnees == null || donnees.isEmpty()) {
            dateDebut = new Date();                       // pas de données
        } else {
            // tri chronologique
            donnees.sort(Comparator.comparing(l -> {
                try { return sdf.parse(l[0]); }
                catch (ParseException ex) { return new Date(0); }
            }));
            try {
                Date derniere = sdf.parse(donnees.get(donnees.size() - 1)[0]);
                Calendar cal = Calendar.getInstance();
                cal.setTime(derniere);
                cal.set(Calendar.MONTH, Calendar.JANUARY);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                dateDebut = cal.getTime();                 // 1er janvier année dernière date
            } catch (ParseException ex) {
                dateDebut = new Date();                  
            }
        }
        dateFin = ajouterJours(dateDebut, periode);      // calcul date de fin
        boutonAvancer.setEnabled(true);                  // activation du bouton
        mettreAJourGraphique();
        pcs.firePropertyChange("prixGraphe", oldPrixGraphe, prixGraphe);
    }

    /**
     * Étend la fenêtre de PAS_FENETRE jours et rafraîchit
     */
    public void avancerDansLeTemps() {
        periode += PAS_FENETRE;                          // incrément de jours
        dateFin = ajouterJours(dateDebut, periode);      // recalcul date fin
        double oldPrixGraphe = prixGraphe;
        mettreAJourGraphique();                          // mise à jour graphique
        pcs.firePropertyChange("prixGraphe", oldPrixGraphe, prixGraphe);
        
    }

    /**
     * Affiche ou masque les bandes de Bollinger
     * @param window taille de la fenêtre pour le calcul
     */
    public void bandeBollinger(int window) {
        if (plot.getDataset(1) != null) {
            plot.setDataset(1, null); plot.setRenderer(1, null);
            repaint(); return;
        }
        double[][] tab = extraireSeries(seriePrix);
        XYSeries sup = new XYSeries("Boll sup"), inf = new XYSeries("Boll inf");
        for (int i = window - 1; i < tab.length; i++) {
            double sum = 0;
            for (int j = i - window + 1; j <= i; j++) sum += tab[j][1];
            double moy = sum / window;
            double ec = Math.sqrt(
                java.util.Arrays.stream(tab, i-window+1, i+1)
                    .mapToDouble(p -> (p[1] - moy)*(p[1] - moy)).sum()/window
            );
            long dt = (long) tab[i][0];
            sup.add(dt, moy + 2*ec); inf.add(dt, moy - 2*ec);
        }
        XYSeriesCollection boll = new XYSeriesCollection();
        boll.addSeries(sup); boll.addSeries(inf);
        plot.setDataset(1, boll);
        plot.setRenderer(1, new XYLineAndShapeRenderer(true, false));
        repaint();
    }

    /**
     * Affiche ou masque la moyenne mobile sur 'window' périodes
     * @param window taille de la fenêtre mobile
     */
    public void moyenneMobile(int window) {
        if (plot.getDataset(2) != null) {
            plot.setDataset(2, null); plot.setRenderer(2, null);
            repaint(); return;
        }
        double[][] tab = extraireSeries(seriePrix);
        XYSeries mm = new XYSeries("MM("+window+" )");
        for (int i = window - 1; i < tab.length; i++) {
            double sum = 0;
            for (int j = i - window + 1; j <= i; j++) sum += tab[j][1];
            mm.add((long)tab[i][0], sum/window);
        }
        plot.setDataset(2, new XYSeriesCollection(mm));
        plot.setRenderer(2, new XYLineAndShapeRenderer(true, false));
        repaint();
    }

    /**
     * Met à jour le dataset principal (prix) et volume
     */
    public void mettreAJourGraphique() {
        List<String[]> filtres = filtrerParFenetre();
        // prix
        seriePrix.clear(); serieVolume.clear();
        peuplerSeries(filtres);
        XYSeriesCollection data0 = (XYSeriesCollection) plot.getDataset(0);
        data0.removeAllSeries(); data0.addSeries(seriePrix);
        // volume
        datasetVolume.removeAllSeries(); datasetVolume.addSeries(serieVolume);
        if (!filtres.isEmpty()) {
                prixGraphe = Double.parseDouble(filtres.get(filtres.size()-1)[4]);
        } else {
                prixGraphe = 0.0;
        }
        repaint();
    }

    /**
     * Filtre les données entre dateDebut et dateFin
     * @return liste filtrée
     */
    private List<String[]> filtrerParFenetre() {
        List<String[]> out = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (String[] l : donnees) {
            try {
                Date d = sdf.parse(l[0]);
                if (!d.before(dateDebut) && !d.after(dateFin)) out.add(l);
            } catch (Exception e) {}
        }
        plot.getDomainAxis().setRange(dateDebut.getTime(), dateFin.getTime());
        return out;
    }

    /**
     * Remplit les séries prix et volume
     * @param data points à ajouter
     */
    private void peuplerSeries(List<String[]> data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (String[] l : data) {
            try {
                Date dt = sdf.parse(l[0]);
                long x = dt.getTime();
                seriePrix.add(x, Double.parseDouble(l[4]));
                serieVolume.add(x, Double.parseDouble(l[5]));
            } catch (Exception e) {}
        }
    }

    /** Convertit une série XY en tableau */
    private double[][] extraireSeries(XYSeries s) {
        double[][] tab = new double[s.getItemCount()][2];
        for (int i = 0; i < s.getItemCount(); i++) {
            tab[i][0] = s.getX(i).doubleValue();
            tab[i][1] = s.getY(i).doubleValue();
        }
        return tab;
    }

    /** Ajoute des jours à une date */
    private Date ajouterJours(Date d, int j) {
        Calendar c = Calendar.getInstance();           // crée un nouvel objet Calendar avec la date et l'heure actuelles
        c.setTime(d);                                  // remplace la date actuelle du Calendar par d
        c.add(Calendar.DAY_OF_YEAR, j);                // Calendar.DAY_OF_YEAR : précise que l'on ajoute des jours
        return c.getTime();
    }

    /** Construit les chart panels pour prix et volume */
    private void construireGraphique() {
        // prix
        XYSeriesCollection prixDS = new XYSeriesCollection(seriePrix);
        chart = ChartFactory.createXYLineChart(titre, 
                                               "Date", 
                                               "Prix", 
                                               prixDS,
                                               PlotOrientation.VERTICAL, 
                                               true, 
                                               true, 
                                               false
                                               );
        plot = chart.getXYPlot(); 
        chart.removeLegend();
        DateAxis axe = new DateAxis("Date"); 
        axe.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));
        plot.setDomainAxis(axe); 
        plot.setRenderer(0, new XYLineAndShapeRenderer(true, false));

        // volume
        datasetVolume = new XYSeriesCollection(serieVolume);
        JFreeChart volChart = ChartFactory.createXYBarChart("Volume", 
                                                            "Date", 
                                                            true, 
                                                            "Valeur", 
                                                            datasetVolume,
                                                            PlotOrientation.VERTICAL, 
                                                            false, 
                                                            true, 
                                                            false
                                                            );
        volChart.removeLegend();
        
        ChartPanel cpPrix = new ChartPanel(chart); 
        cpPrix.setPreferredSize(new Dimension(800, 400));
        ChartPanel cpVol = new ChartPanel(volChart); 
        cpVol.setPreferredSize(new Dimension(800, 200));
        
        setLayout(new BorderLayout());
        add(cpPrix, BorderLayout.CENTER);
        add(cpVol, BorderLayout.SOUTH);
    }
}

