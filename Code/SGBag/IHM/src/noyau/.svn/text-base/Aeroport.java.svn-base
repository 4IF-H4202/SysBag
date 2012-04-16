package noyau;

import java.util.ArrayList;

import java.util.Collection;
import java.util.Iterator;

import java.util.LinkedList;

import java.util.ListIterator;
import java.util.Random;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Aeroport extends ElementNoyau {


    /**
     * @attribute : distance de sécurité entre deux chariots qui se suivent, en mètres
     */
    protected Double distanceSecu;
    /**
     * @attribute : paramètre de la loi de poisson qui gère l'arrivée aléatoire de bagages
     */
    private Double lambdaPoisson;
    /**
     * @attribute : nombre de bagages à générer selon la loi de poisson
     */
    private Double nbBagagesAGenerer;
    
    /**
     * @attribute : largeur de l'aéroport en mètres
     */
    private Double largeurM;

    /**
     * @attribute : longueur de l'aéroport en mètres
     */
    private Double longueurM;

    /**
     * @associates <{noyau.Chemin}> : liste des chemins
     */
    LinkedList<Chemin> chemins;

    /**
     * @associates <{noyau.TapisRoulant}> : liste des tapisRoulants
     */
    LinkedList<TapisRoulant> tapisRoulants;
    /**
     * @associates <{noyau.Rail}> : liste des rails
     */    
    LinkedList <Rail> rails;
    /**
     * @associates <{noyau.Toboggan}> : liste des toboggans
     */    
    LinkedList <Toboggan> toboggans;
    /**
     * @associates <{noyau.VoieGarage}> : liste des voieGarage
     */    
    LinkedList <VoieGarage> voiesGarage;
    
    /**
     * @associates <{noyau.Intersection}> : liste des intersections
     */
    LinkedList<Intersection> intersections;


    /**
     * @associates <{noyau.Chariot}> : liste des chariots
     */
    LinkedList<Chariot> chariots;

    /**
     * @associates <{noyau.Vol}> : liste des vols
     */
    LinkedList<Vol> vols;

    /**
     * Constructeur de la classe
     */
    public Aeroport() {
        super();
        distanceSecu = Double.valueOf(10);
        lambdaPoisson = Double.valueOf(1);
        nbBagagesAGenerer = Double.valueOf(0);
        largeurM = Double.valueOf(0);
        longueurM = Double.valueOf(0);
        chemins = new LinkedList<Chemin>();
        tapisRoulants = new LinkedList<TapisRoulant>();
        rails = new LinkedList<Rail>();
        toboggans = new LinkedList<Toboggan>();
        voiesGarage = new LinkedList<VoieGarage>();
        intersections = new LinkedList<Intersection>();
        chariots = new LinkedList<Chariot>();
        vols = new LinkedList<Vol>();
    }


   /**
     * Méthode qui crée le noeud XML à partir du document en paramètre
     * @param document
     * @return
     */
    public Element CreerNoeudXML(Document document) {
       
        Element racine = document.createElement(this.getClass().getName());
        
        Attr idAttribut = document.createAttribute("id");
        racine.setAttributeNode(idAttribut);
        idAttribut.setValue(Integer.toString(id));
        
        Attr longueurAttribut = document.createAttribute("longueur");
        racine.setAttributeNode(longueurAttribut);
        longueurAttribut.setValue(Double.toString(longueurM));
        
        Attr largeurAttribut = document.createAttribute("largeur");
        racine.setAttributeNode(largeurAttribut);
        largeurAttribut.setValue(Double.toString(largeurM));

        Attr distanceSecuAttribut = document.createAttribute("distanceSecu");
        racine.setAttributeNode(distanceSecuAttribut);
        distanceSecuAttribut.setValue(Double.toString(distanceSecu));
        
        Attr lambdaPoissonAttribut = document.createAttribute("lambdaPoisson");
        racine.setAttributeNode(lambdaPoissonAttribut);
        lambdaPoissonAttribut.setValue(Double.toString(lambdaPoisson));
        
        for(Intersection inter : intersections){
            if( inter != null){
                Element interElement = inter.CreerNoeudXML(document);
                if (interElement != null) {
                    racine.appendChild(interElement);
                } else {
                    return null;
                }
            }
        }
        
        for(Rail r : rails){
            if( r != null){
                Element interElement = r.CreerNoeudXML(document);
                if (interElement != null) {
                    racine.appendChild(interElement);
                } else {
                    return null;
                }
            }
        }
        
        for(Vol v : vols){
            if( v != null){
                Element interElement = v.CreerNoeudXML(document);
                if (interElement != null) {
                    racine.appendChild(interElement);
                } else {
                    return null;
                }
            }
        }
        
        for(Chariot ch : chariots){
            if( ch != null){
                Element interElement = ch.CreerNoeudXML(document);
                if (interElement != null) {
                    racine.appendChild(interElement);
                } else {
                    return null;
                }
            }
        }
        
        for(TapisRoulant tapis : tapisRoulants){
            if( tapis != null){
                    Element interElement = tapis.CreerNoeudXML(document);
                    if (interElement != null) {
                        racine.appendChild(interElement);
                    } else {
                        return null;
                }
            }    
        }    
        
        for(Toboggan to : toboggans){
            if( to != null){
                Element interElement = to.CreerNoeudXML(document);
                if (interElement != null) {
                    racine.appendChild(interElement);
                } else {
                    return null;
                }
            }    
        }    
        
        for(VoieGarage vg : voiesGarage){
            if( vg != null){
                Element interElement = vg.CreerNoeudXML(document);
                if (interElement != null) {
                    racine.appendChild(interElement);
                } else {
                    return null;
                }
            } 
        } 
        
        return racine;
       
    }
    
    /**
     * Méthode permettant le chargement de la configuration du système à partir d'un fichier xml
     * @param noeudDOMRacine
     * @return OK ou ERROR
     */
    public int ConstruireAPartirDeDOMXML(Element noeudDOMRacine){
        
        NodeList liste;
        String tag = "noyau.Aeroport";
        liste = noeudDOMRacine.getElementsByTagName(tag);
        
        Element aeroport = (Element) liste.item(0);
        if (aeroport == null || !"noyau.Aeroport".equals(aeroport.getNodeName())) {
            return Aeroport.PARSE_ERROR;
        }
        
        longueurM = Double.parseDouble(aeroport.getAttribute("longueur"));
        largeurM = Double.parseDouble(aeroport.getAttribute("largeur"));
        id = Integer.parseInt(aeroport.getAttribute("id"));
        distanceSecu = Double.parseDouble(aeroport.getAttribute("distanceSecu"));;
        lambdaPoisson = Double.parseDouble(aeroport.getAttribute("lambdaPoisson"));;


        // Intersections :
        tag = "noyau.Intersection";
        liste = aeroport.getElementsByTagName(tag);
        
        for (int i = 0; i < liste.getLength(); i++) {
            Element intersection = (Element) liste.item(i);
            Intersection nouvelleIntersection = new Intersection();
            if (nouvelleIntersection.ConstruireAPartirDeDOMXML(intersection)!= ElementNoyau.PARSE_OK){
                return ElementNoyau.PARSE_ERROR;
            }
            //Ajout de l'element créé à la structure
            intersections.add(nouvelleIntersection);
        }
        
        
        // Rails :
        tag = "noyau.Rail";
        liste = aeroport.getElementsByTagName(tag);
        
        for (int i = 0; i < liste.getLength(); i++) {
            Element rail = (Element) liste.item(i);
            Rail nouveauRail = new Rail();
            
            if (nouveauRail.ConstruireAPartirDeDOMXML(rail)!= ElementNoyau.PARSE_OK){
                return ElementNoyau.PARSE_ERROR;
            }
            
            //lecture des attributs
            Integer idDepart = Integer.valueOf(rail.getAttribute("idDepart"));
            Integer idArrivee = Integer.valueOf(rail.getAttribute("idArrivee"));
            
            // On lie le rail aux intersections correspondantes
            Intersection intersectionDeb = Intersection.getIntersectionById(intersections, idDepart);
            Intersection intersectionFin = Intersection.getIntersectionById(intersections, idArrivee);
            nouveauRail.setNoeudDeb(intersectionDeb);
            nouveauRail.setNoeudFin(intersectionFin);

            Double longueur =
                Math.sqrt(Math.pow(Math.abs(intersectionDeb.getCoordonneesAbsolues().getX() - intersectionFin.getCoordonneesAbsolues().getX()),
                                   2) +
                          Math.pow(Math.abs(intersectionDeb.getCoordonneesAbsolues().getY() - intersectionFin.getCoordonneesAbsolues().getY()),
                                   2));

            nouveauRail.setLongueur(longueur);

            //Ajout de l'element créé à la structure
            rails.add(nouveauRail);
        }
        
        // Tapis Roulant
        tag = "noyau.TapisRoulant";
        liste = aeroport.getElementsByTagName(tag);
        
        for (int i = 0; i < liste.getLength(); i++) {
            Element tapisRoulant = (Element) liste.item(i);
            TapisRoulant nouveauTapisRoulant = new TapisRoulant();
            
            if (nouveauTapisRoulant.ConstruireAPartirDeDOMXML(tapisRoulant)!= ElementNoyau.PARSE_OK){
                return ElementNoyau.PARSE_ERROR;
            }
            //lecture des attributs
            Integer idDepart = Integer.valueOf(tapisRoulant.getAttribute("idDepart"));
            Integer idArrivee = Integer.valueOf(tapisRoulant.getAttribute("idArrivee"));
        
            // On lie le rail aux intersections correspondantes
            Intersection intersectionDeb = Intersection.getIntersectionById(intersections, idDepart);
            Intersection intersectionFin = Intersection.getIntersectionById(intersections, idArrivee);
            nouveauTapisRoulant.setNoeudDeb(intersectionDeb);
            nouveauTapisRoulant.setNoeudFin(intersectionFin);
            
            
            Double longueur =
                Math.sqrt(Math.pow(Math.abs(intersectionDeb.getCoordonneesAbsolues().getX() - intersectionFin.getCoordonneesAbsolues().getX()),
                                   2) +
                          Math.pow(Math.abs(intersectionDeb.getCoordonneesAbsolues().getY() - intersectionFin.getCoordonneesAbsolues().getY()),
                                   2));

            nouveauTapisRoulant.setLongueur(longueur);
            
            intersectionFin.setPointRetrait(nouveauTapisRoulant);
            
            //Ajout de l'element créé à la structure
            tapisRoulants.add(nouveauTapisRoulant);
        }
        
        
        // Voie de Garage
        tag = "noyau.VoieGarage";
        liste = aeroport.getElementsByTagName(tag);
        
        for (int i = 0; i < liste.getLength(); i++) {
            Element voieGarage = (Element) liste.item(i);
            VoieGarage nouvelleVoieGarage = new VoieGarage();
            
            if (nouvelleVoieGarage.ConstruireAPartirDeDOMXML(voieGarage)!= ElementNoyau.PARSE_OK){
                return ElementNoyau.PARSE_ERROR;
            }
            //lecture des attributs
            Integer idDepart = Integer.valueOf(voieGarage.getAttribute("idDepart"));
            Integer idArrivee = Integer.valueOf(voieGarage.getAttribute("idArrivee"));
        
            // On lie la voie de garage aux intersections correspondantes
            Intersection intersectionDeb = Intersection.getIntersectionById(intersections, idDepart);
            Intersection intersectionFin = Intersection.getIntersectionById(intersections, idArrivee);
            nouvelleVoieGarage.setNoeudDeb(intersectionDeb);
            nouvelleVoieGarage.setNoeudFin(intersectionFin);
            
            Double longueur =
                Math.sqrt(Math.pow(Math.abs(intersectionDeb.getCoordonneesAbsolues().getX() - intersectionFin.getCoordonneesAbsolues().getX()),
                                   2) +
                          Math.pow(Math.abs(intersectionDeb.getCoordonneesAbsolues().getY() - intersectionFin.getCoordonneesAbsolues().getY()),
                                   2));

            nouvelleVoieGarage.setLongueur(longueur);
            //Ajout de l'element créé à la structure
            voiesGarage.add(nouvelleVoieGarage);
        }
        
        // Vols
        tag = "noyau.Vol";
        liste = aeroport.getElementsByTagName(tag);
        
        for (int i = 0; i < liste.getLength(); i++) {
            Element vol = (Element) liste.item(i);
            Vol nouveauVol = new Vol();
            if (nouveauVol.ConstruireAPartirDeDOMXML(vol)!= ElementNoyau.PARSE_OK){
                return ElementNoyau.PARSE_ERROR;
            }
            //Ajout de l'element créé à la structure
            vols.add(nouveauVol);       
        }

        
        // Toboggan
        tag = "noyau.Toboggan";
        liste = aeroport.getElementsByTagName(tag);
        
        for (int i = 0; i < liste.getLength(); i++) {
            Element toboggan = (Element) liste.item(i);
            Toboggan nouveauToboggan = new Toboggan();
            
            if (nouveauToboggan.ConstruireAPartirDeDOMXML(toboggan)!= ElementNoyau.PARSE_OK){
                return ElementNoyau.PARSE_ERROR;
            }
            
            //lecture des attributs
            Integer idDepart = Integer.valueOf(toboggan.getAttribute("idDepart"));
            Integer idArrivee = Integer.valueOf(toboggan.getAttribute("idArrivee"));
            Integer idVol = Integer.valueOf(toboggan.getAttribute("idVol"));
            
            // On lie le rail aux intersections correspondantes
            Intersection intersectionDeb = Intersection.getIntersectionById(intersections, idDepart);
            Intersection intersectionFin = Intersection.getIntersectionById(intersections, idArrivee);
            Vol volCourant = Vol.getVolById(vols, idVol);
            
            nouveauToboggan.setNoeudDeb(intersectionDeb);
            nouveauToboggan.setNoeudFin(intersectionFin);
            nouveauToboggan.setVol(volCourant);
            
            Double longueur =
                Math.sqrt(Math.pow(Math.abs(intersectionDeb.getCoordonneesAbsolues().getX() - intersectionFin.getCoordonneesAbsolues().getX()),
                                   2) +
                          Math.pow(Math.abs(intersectionDeb.getCoordonneesAbsolues().getY() - intersectionFin.getCoordonneesAbsolues().getY()),
                                   2));

            nouveauToboggan.setLongueur(longueur);
            
            intersectionDeb.setPointDepot(nouveauToboggan);
            //Ajout de l'element créé à la structure
            toboggans.add(nouveauToboggan);
        }
        
        // Chariots :
        tag = "noyau.Chariot";
        liste = aeroport.getElementsByTagName(tag);
        
        for (int i = 0; i < liste.getLength(); i++) {
            Element chariot = (Element) liste.item(i);
            
            Integer idChemin = Integer.valueOf(chariot.getAttribute("idChemin"));
            Chariot nouveauChariot = new Chariot();

            if (nouveauChariot.ConstruireAPartirDeDOMXML(chariot)!= ElementNoyau.PARSE_OK){
                return ElementNoyau.PARSE_ERROR;
            }
        
            CheminChariot chemin = Rail.getRailById(rails,idChemin);
            if(chemin == null) {
                chemin = VoieGarage.getVoieGarageById(voiesGarage, idChemin);
            }
            nouveauChariot.setCheminActuel(chemin);
            
            //Ajout de l'element créé à la structure
            chariots.add(nouveauChariot);
        }
        
        return ElementNoyau.PARSE_OK;
    }
    
    
    
    /**
     * Méthode permettant le chargement des éléments de la simulation à partir d'un fichier xml
     * @param noeudDOMRacine
     * @return OK ou ERROR
     */
    public int ConstruireSimulAPartirDeDOMXML(Element noeudDOMRacine){

        NodeList sousListe;
        String tag = "noyau.Aeroport";
        NodeList majorListe = noeudDOMRacine.getElementsByTagName(tag);
        
        Element aeroport = (Element) majorListe.item(0);
        if (aeroport == null || !"noyau.Aeroport".equals(aeroport.getNodeName())) {
            return Aeroport.PARSE_ERROR;
        }
                
        // Chariots :
        tag = "noyau.Chariot";
        NodeList liste = aeroport.getElementsByTagName(tag);
        
        for (int i = 0; i < liste.getLength(); i++) {
            Element chariot = (Element) liste.item(i);
            
            Integer idChariot = Integer.valueOf(chariot.getAttribute("id"));

            Bagage nouveauBagage = new Bagage();
            tag="noyau.Bagage";
            sousListe = chariot.getElementsByTagName(tag);
            
            for( int nb = 0; nb < sousListe.getLength() ; nb++){
                Element bagage = (Element) sousListe.item(nb);
                
                Integer idVol = Integer.valueOf(bagage.getAttribute("idVol"));
            
                if (nouveauBagage.ConstruireAPartirDeDOMXML(bagage)!= ElementNoyau.PARSE_OK){
                    return ElementNoyau.PARSE_ERROR;
                }
                
                Vol volExistant = Vol.getVolById(vols,idVol);
                nouveauBagage.setVol(volExistant);
        
                Chariot chariotExistant = Chariot.getChariotById(chariots, idChariot);
                
                if(chariotExistant != null){
                    if(nouveauBagage.getPosition()>0 ){
                        if( chariotExistant.getCheminActuel().getLongueur() > chariotExistant.getPositionRail()
                            || chariotExistant.getCheminActuel().getNoeudFin().getPointDepot() == null
                            || chariotExistant.getAction() != Chariot.Action.deposerBagage ){
                                nouveauBagage.setPosition(0.0);
                        }
                    }
                    chariotExistant.setBagagePorte(nouveauBagage);
                }
            }
        }
        
        // Bagages :
        tag = "noyau.TapisRoulant";
        liste = aeroport.getElementsByTagName(tag);
        
        for (int i = 0; i < liste.getLength(); i++) {
            Element tapisRoulant = (Element) liste.item(i);
            
            //On recupere l'id
            Integer id = Integer.valueOf(tapisRoulant.getAttribute("id"));
            
            TapisRoulant monTapisRoulant = TapisRoulant.getTapisRoulantById(tapisRoulants,id);
            
            Bagage nouveauBagage = new Bagage();
            tag="noyau.Bagage";
            sousListe = tapisRoulant.getElementsByTagName(tag);
            
            for( int nb = 0; nb < sousListe.getLength(); nb++){
                Element bagage = (Element) sousListe.item(nb);
                
                Integer idVol = Integer.valueOf(bagage.getAttribute("idVol"));
                String statut = bagage.getAttribute("status");

                if (nouveauBagage.ConstruireAPartirDeDOMXML(bagage)!= ElementNoyau.PARSE_OK){
                    return ElementNoyau.PARSE_ERROR;
                }
                
                Vol volExistant = Vol.getVolById(vols,idVol);
                nouveauBagage.setVol(volExistant);
                
                if("porte".compareTo(statut) == 0 ){
                    nouveauBagage.setCheminPorteur(monTapisRoulant);
                    monTapisRoulant.bagagesPortes.add(nouveauBagage);
                }else if("attente".compareTo(statut) == 0 ){
                    monTapisRoulant.bagagesAttente.add(nouveauBagage);
                }else{
                    // nothing
                }
            }    
        }
        
        // Bagages :
        tag = "noyau.Toboggan";
        liste = aeroport.getElementsByTagName(tag);
        
        for (int i = 0; i < liste.getLength(); i++) {
            Element toboggan = (Element) liste.item(i);
            
            //On recupere l'id
            Integer id = Integer.valueOf(toboggan.getAttribute("id"));
            
            Toboggan monToboggan = Toboggan.getTobogganById(toboggans,id);
            
            Bagage nouveauBagage = new Bagage();
            tag="noyau.Bagage";
            sousListe = toboggan.getElementsByTagName(tag);
            
            for( int nb = 0; nb < sousListe.getLength(); nb++){
                Element bagage = (Element) sousListe.item(nb);
                
                Integer idVol = Integer.valueOf(bagage.getAttribute("idVol"));
                String statut = bagage.getAttribute("status");

                if (nouveauBagage.ConstruireAPartirDeDOMXML(bagage)!= ElementNoyau.PARSE_OK){
                    return ElementNoyau.PARSE_ERROR;
                }
                
                Vol volExistant = Vol.getVolById(vols,idVol);
                nouveauBagage.setVol(volExistant);
                
                if("porte".compareTo(statut) == 0 ){
                    nouveauBagage.setCheminPorteur(monToboggan);
                    monToboggan.bagagesPortes.add(nouveauBagage);
                }else if("attente".compareTo(statut) == 0 ){
                    monToboggan.bagagesAttente.add(nouveauBagage);
                }else{
                    // nothing
                }
            }    
        }
        
        return Aeroport.PARSE_OK;
    }

    /**
     * Méthode qui fait avancer tous les éléments de l'aéroport en fonction du jetonTemps
     * @param jetonTemps
     * @param simuAuto
     */
    public void Actualiser(Double jetonTemps, Boolean simuAuto) {
        if(simuAuto) {
            genererBagagesAuto(jetonTemps);
        }
        
        for(VoieGarage voieGarare : voiesGarage) {
            voieGarare.AvancerChariots(jetonTemps, distanceSecu, simuAuto);
        }
        
        for(Rail rail : rails) {
            rail.AvancerChariots(jetonTemps, distanceSecu, simuAuto);
            rail.AvancerEtincelles(jetonTemps);
        }
        
        for(Intersection intersection : intersections) {
            intersection.AvancerChariot(jetonTemps, distanceSecu, simuAuto);
        }
        
        for (Toboggan toboggan : toboggans) {
            toboggan.AvancerBagages(jetonTemps);
        }
        
        for (TapisRoulant tapisRoulant : tapisRoulants) {
            tapisRoulant.AvancerBagages(jetonTemps);
        }
         
        for (Toboggan toboggan : toboggans) {
            toboggan.setDeplace(false);
        }
        
        for (TapisRoulant tapisRoulant : tapisRoulants) {
            tapisRoulant.setDeplace(false);
        }
         
        for(Chariot chariot : chariots) {                          
            chariot.setDeplace(false);
        }
    }
    

    /**
     * Génère les bagages selon la loi d'arrivée et du jeton temps.
     * @param jetonTps
     */
    public void genererBagagesAuto(double jetonTps){

        nbBagagesAGenerer += poisson(lambdaPoisson);
        int  cptBagages = (int)nbBagagesAGenerer.doubleValue();
        
        if(cptBagages>0){
            // Il y a au moins 1 bagage à générer
            nbBagagesAGenerer = nbBagagesAGenerer - cptBagages;
            
            while(cptBagages > 0){
                
                // Choix d'un tapis roulant pour y ajouter notre bagage
                TapisRoulant tr = choisirAleaTR();
                
                if(tr != null){
                    // Création du bagage à ajouter
                    Bagage bag = creerBagageAlea();
                    
                    if(bag != null){
                        bag.setCheminPorteur(tr);
                        
                        // Ajout du bagage au tapis roulant
                        tr.EntrerBagage(bag);
                        
                        // Calcul du trajet jusqu'à la voie de garage la plus proche ayant des chariots
                        LinkedList<CheminChariot> trajet  = tr.calculerTrajet();
                         
                        if(!trajet.isEmpty()) {
                            // Récupération de la voie de garage
                            VoieGarage voieGarage = (VoieGarage) trajet.pop();
                            
                            // Déblocage du chariot dans la voie de garage
                            voieGarage.debloquerChariot(trajet);
                        }
                        
                    } // end of if
                    
                } // end of if
                
                cptBagages --;
                
            } // end of while

        } // end of if
        
    } // end of genererBagage
    
   /**
     * Retourne un tapis roulant de la collection de manière aléatoire
     * @return
     */
    private TapisRoulant choisirAleaTR(){
       if(tapisRoulants.size()>0){
            int max = tapisRoulants.size();
    
           Random randomGenerator = new Random();
           int random = randomGenerator.nextInt(max);
            
            if(tapisRoulants.get(random) != null)
                return tapisRoulants.get(random);
       } // end of if
       return null;
    } // end of choisirAleaTR
   
   /**
     * Crée un nouveau bagage avec un vol choisi aléatoirement dans la liste et un poids aléatoire
     * @return
     */
   private Bagage creerBagageAlea(){
       if(vols.size()>0){
           // Choix alétoire d'un vol dans la liste
           int maxVol = vols.size();
    
           Random randomGenerator = new Random();
           int itVol = randomGenerator.nextInt(maxVol);
           
           // Choix aléatoire du poids
           int minPoids = 0;
           int maxPoids = 30;
           
           double poids = (int)((Math.random() * (maxPoids-minPoids)) + minPoids);

           if(vols.get(itVol) != null)
                return new Bagage(poids, vols.get(itVol));
        
       } // end of if
       
       return null;
       
   } // end of creerBagageAlea


    /**
     * Méthode qui retourne le nombre de bagages à générer d'après la loi de poisson et du paramètre lambda
     * @param lambda
     * @return
     */
    public static double poisson(double lambda) {
        if (lambda != 0) {
            Random rand = new Random();
            return - (1 / lambda) * Math.log( 1 - rand.nextDouble() );
        } else {
            return 0.0;
        }
   }
   
    public Double getLargeurM() {
        return largeurM;
    }

    public Double getLongueurM() {
        return longueurM;
    }

    public LinkedList<Chemin> getChemins() {
        return chemins;
    }

    public LinkedList<Intersection> getIntersections() {
        return intersections;
    }

    public LinkedList<Chariot> getChariots() {
        return chariots;
    }

    public LinkedList<Vol> getVols() {
        return vols;
    }
    
    
    public void avancer(){
        
    }

    /**
     * Méthode qui supprime le chemin en paramètre de la liste des chemins
     * @param chemin
     */
    public void supprimerChemin(Chemin chemin){
        chemins.remove(chemin);
    }

    /**
     * Méthode qui supprime le chariot en paramètre de la liste des chariots
     * @param chariot
     */
    public void supprimerChariot(Chariot chariot){
        chariots.remove(chariot);
    }

    /**
     * Méthode qui supprime l'intersection en paramètre de la liste des intersections
     * @param intersection
     */
    public void supprimerIntersection(Intersection intersection){
        intersections.remove(intersection);
    }

    /**
     * Méthode qui supprime le vol en paramètre de la liste des vols
     * @param vol
     */
    public void supprimerVol(Vol vol){
        vols.remove(vol);
    }

    /**
     * Méthode qui crée un bagage associé au vol et qui l'ajoute au tapisRoulant
     * @param tapisRoulant
     * @param vol
     * 
     */
    public void ajouterBagage(TapisRoulant tapisRoulant, Vol vol){ // pour ajout manuel
        // génération aléatoire d'un poids dans la limite
        Double poids = (Double)(Math.random() * 30);
        // création du nouveau bagage
        Bagage bagage= new Bagage((double)poids,vol);
        // ajout au tapis roulant choisi
        tapisRoulant.EntrerBagage(bagage);
        
}

    /**
     * Méthode qui ajoute au trajet du chariot le chemin en paramètre
     * @param chariot
     * @param chemin
     * 
     */
    public void ajouterCheminATrajetChariot(Chariot chariot, CheminChariot chemin){
        if(chariot!=null){
            chariot.AjouterChemin(chemin);
        }
    }

    /**
     * Méthode qui récupère le bagage du tapisRoulant à proximité du chariot en paramètre, et qui lui ajoute
     * @param chariot
     * 
     */
    public void prendreBagageDuTapisRoulant(Chariot chariot){
        chariot.setAction(Chariot.Action.retirerBagage);
    }

    /**
     * Méthode qui pose le bagage du chariot en paramètre vers le toboggan à proximité
     * @param chariot
     * 
     */
    public void poserBagageSurToboggan(Chariot chariot){
        chariot.setAction(Chariot.Action.deposerBagage);
    }

    /**
     * En mode auto, sort un chariot de la voieGarage en paramètre et lui ajoute un parcours
     * @param voieGarage
     * 
     */
    public void debloquerChariotVoieGarage(VoieGarage voieGarage){
        CheminChariot chemin = voieGarage.getNoeudFin().getVoiesSuiv().get(0);
        LinkedList <CheminChariot> parcours= new LinkedList <CheminChariot>() ;
        parcours.add(chemin);
        voieGarage.debloquerChariot(parcours);
    }

    /**
     * En mode manuel, sort un chariot de la voieGarage en paramètre
     * @param voieGarage
     * 
     */
    public void debloquerChariotVoieGarageManuel(VoieGarage voieGarage){
        voieGarage.debloquerChariotManuel();
    }

    public Double getDistanceSecu() {
        return distanceSecu;
    }

    public double getLambdaPoisson() {
        return lambdaPoisson;
}

    public double getNbBagages() {
        return nbBagagesAGenerer;
    }

    public LinkedList<TapisRoulant> getTapisRoulants() {
        return tapisRoulants;
    }

    public LinkedList<Rail> getRails() {
        return rails;
    }

    public LinkedList<Toboggan> getToboggans() {
        return toboggans;
    }

    public LinkedList<VoieGarage> getVoiesGarage() {
        return voiesGarage;
    }

    /**
     * Méthode qui met à vrai l'attribut estArrete de tous les chariots de la liste chariot
     * 
     */
    private void debloquerChariotsAttente() {
        /*ListIterator<Chariot> itChariot = chariots.listIterator();
        while(itChariot.hasNext()) {
            Chariot chariot = itChariot.next();
            chariot.setEstArrete(false);
        }*/
        
/*         for(Chariot c : this.chariots) {
            c.setEstArrete(false);
        } */
    }

    /**
     * Méthode qui retourne le nombre total de chariots contenus dans les voiesGarages
     * @return
     */
    public Integer getNbChariotsVG() {
        Integer totalChar = 0;
        for (VoieGarage vg : this.voiesGarage) {
            totalChar += vg.getNombreChariots();
        }
        return totalChar;
    }

    /**
     * Méthode qui retourne le nombre total de chariots en attente dans les tapisRoulant
     * @return
     */
    public Integer getNbBagagesEnControle() {
        Integer total = 0;
        for (TapisRoulant t : this.tapisRoulants) {
            total += t.getBagagesAttente().size();
        }
        return total;
    }

    /**
     * Méthode qui retourne le nombre total de chariots en attente dans les toboggans
     * @return
     */
    public Integer getNbBagagesCharges() {
        Integer total = 0;
        for (Toboggan t : this.toboggans) {
            total += t.getBagagesAttente().size();
        }
        return total;
    }

    /**
     * Méthode qui retourne le nombre total de chariots en déplacement dans l'ensemble de l'aéroport
     * @return
     */
    public Integer gettNbBagagesEnDeplacement() {
        Integer total = 0;
        for (Toboggan t : this.toboggans) {
            total += t.getBagagesPortes().size();
        }
        for (TapisRoulant t : this.tapisRoulants) {
            total += t.getBagagesPortes().size();
        }
        for (Chariot c : this.chariots) {
            if (c.getBagagePorte() != null) {
                total ++;
            }
        }
        return total;
    }

    public void setLambdaPoisson(Double lambdaPoisson) {
        this.lambdaPoisson = lambdaPoisson;
    }
}
