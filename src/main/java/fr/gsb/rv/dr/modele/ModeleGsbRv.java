package fr.gsb.rv.dr.modele;


import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModeleGsbRv {

    public static Visiteur seConnecter( String matricule , String mdp ) throws ConnexionException {

        Connection connexion = ConnexionBD.getConnexion() ;


        String requete = "SELECT t.vis_matricule , vis_prenom, vis_nom , vis_mdp " +
                "FROM (SELECT tr.vis_matricule, MAX(jjmmaa) AS MaxDate ,vis_prenom , vis_nom, vis_mdp " +
                "FROM Travailler as tr inner join Visiteur as v on tr.vis_matricule = v.vis_matricule " +
                "GROUP BY vis_matricule) derniere_date_for_each_visiteur " +
                "JOIN Travailler t " +
                "ON derniere_date_for_each_visiteur.vis_matricule = t.vis_matricule " +
                "AND derniere_date_for_each_visiteur.MaxDate = t.jjmmaa " +
                "AND t.tra_role = 'Délégué'" +
                "AND t.vis_matricule = ? " +
                "AND vis_mdp = ?";
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
            requetePreparee.setString( 1 , matricule );
            requetePreparee.setString( 2 , mdp );
            ResultSet resultat = requetePreparee.executeQuery() ;
            if( resultat.next() ){
                Visiteur visiteur = new Visiteur() ;
                visiteur.setMatricule( matricule );
                visiteur.setNom( resultat.getString( "vis_nom" ) ) ;
                visiteur.setPrenom(resultat.getString("vis_prenom"));
                visiteur.setMdp((resultat.getString("vis_mdp")));
                requetePreparee.close() ;
                return visiteur ;
            }
            else {
                return null ;
            }
        }
        catch( Exception e ){
            return null ;
        }
    }

    public static List<Praticien> getPraticiensHesitants() throws ConnexionException {
        Connection connexion = ConnexionBD.getConnexion() ;

        String requete = "SELECT p.pra_num, p.pra_nom, p.pra_prenom, p.pra_ville, p.pra_coefnotoriete, rv.rap_coefconfiance, rv.rap_date_visite " +
                "FROM (SELECT pra_num, MAX(rap_date_visite) AS MaxDate " +
                "FROM RapportVisite " +
                "GROUP BY pra_num) derniere_date_for_each_rapportvisite " +
                "JOIN Praticien as p " +
                "JOIN RapportVisite as rv " +
                "ON derniere_date_for_each_rapportvisite.pra_num = p.pra_num " +
                "AND derniere_date_for_each_rapportvisite.MaxDate = rv.rap_date_visite " +
                "AND rv.rap_coefconfiance < 5";

        try {
            PreparedStatement requetePrepare = (PreparedStatement) connexion.prepareStatement(requete);
            ResultSet resultat = requetePrepare.executeQuery();
            List<Praticien> PraticiensHesitant = new ArrayList<>();

            while(resultat.next()){
                PraticiensHesitant.add(new Praticien(
                        resultat.getInt("pra_num"),
                        resultat.getString("pra_nom"),
                        resultat.getString("pra_ville"),
                        resultat.getDouble("pra_coefnotoriete"),
                        resultat.getDate("rap_date_visite").toLocalDate(),
                        resultat.getInt("rap_coefconfiance"))
                );
            }
            return PraticiensHesitant;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
