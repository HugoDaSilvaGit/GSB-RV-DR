package fr.gsb.rv.dr.technique;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;


public class ConnexionBD {

    private static String dbURL = "jdbc:mariadb://localhost:3306/gsbrv";
    private static String user = "gsbrvadmin" ;
    private static String password = "gsbrv2021" ;

    private static Connection connexion = null ;

    private ConnexionBD() throws ConnexionException {
        try {
            Class.forName( "org.mariadb.jdbc.Driver" ) ;
            connexion = (Connection) DriverManager.getConnection(dbURL, user, password) ;
        }
        catch( Exception e ){
            throw new ConnexionException() ;
        }
    }

    public static Connection getConnexion() throws ConnexionException {
        if( connexion == null ){
            new ConnexionBD() ;
        }
        return connexion ;
    }
}