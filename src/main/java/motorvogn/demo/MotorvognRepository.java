package motorvogn.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Comparator;

@Repository
public class MotorvognRepository {

    @Autowired
    private JdbcTemplate db;

    private Logger logger = LoggerFactory.getLogger(MotorvognRepository.class);

    public boolean lagreMotorvogn(Motorvogn motorvogn){
        String sql = "INSERT INTO Motorvogn (personnr, navn, adresse, kjennetegn, merke, type) VALUES(?,?,?,?,?,?)";
        try {
            db.update(sql, motorvogn.getPersonnr(), motorvogn.getNavn(), motorvogn.getAdresse(), motorvogn.getKjennetegn(), motorvogn.getMerke(), motorvogn.getType());
            return true;
        } catch (Exception e){
            logger.error("Feil i lagreMotorvogn: "+ e);
            return false;
        }
    }
    public List<Motorvogn> hentAlle() {
        String sql = "SELECT * FROM Motorvogn";
        try {
            List<Motorvogn> enMotorvogn = db.query(sql, new BeanPropertyRowMapper(Motorvogn.class));
            enMotorvogn.sort((Comparator.comparing(Motorvogn::getAdresse)));
            return enMotorvogn;
        } catch (Exception e){
            logger.error("Feil i hentAlle: " + e);
            return null;
        }
    }

    public Motorvogn hentEnMotorvogn(int id) {
        String sql = "SELECT * FROM Motorvogn WHERE id=?";
        try {
            List<Motorvogn> enMotorvogn = db.query(sql, new BeanPropertyRowMapper(Motorvogn.class), id);
            return enMotorvogn.get(0);
        } catch (Exception e){
            logger.error("Feil i hentEnMotorvogn: " + e);
            return null;
        }
    }

    public boolean endreMotorvogn(Motorvogn motorvogn) {
        String sql = "UPDATE Motorvogn SET personnr=?, navn=?, adresse=?, kjennetegn=?, merke=?, type=? WHERE id=?";
        try {
            db.update(sql, motorvogn.getPersonnr(), motorvogn.getNavn(), motorvogn.getAdresse(),
                    motorvogn.getKjennetegn(), motorvogn.getMerke(), motorvogn.getType(), motorvogn.getId());
            return true;
        } catch (Exception e){
            logger.error("Feil i endreMotorvogn: "+ e);
            return false;
        }
    }

    public boolean slettEnMotorvogn(int id){
        String sql = "DELETE FROM Motorvogn WHERE id=?;";
        try {
            db.update (sql, id);
            return true;
        } catch (Exception e){
            logger.error("Feil i slettEnMotorvogn: " + e);
            return false;
        }
    }

    public boolean slettAlle(){
        String sql = "DELETE FROM MOTORVOGN";
        try {
            db.update(sql);
            return true;
        } catch(Exception e){
            logger.error("Feil i slettAlle: "+ e);
            return false;
        }
    }

    public List<Bil> hentAlleBiler(){
        String sql = "SELECT * FROM Bil";
        try {
            List<Bil> alleBiler = db.query(sql, new BeanPropertyRowMapper(Bil.class));
            return alleBiler;
        } catch (Exception e){
            logger.error("Feil i hentAlleBiler: "+ e);
            return null;
        }
    }
    public boolean loggInn(String brukernavn, String passord){
        String sql = "SELECT count(*) FROM Bruker WHERE brukernavn=? AND passord=?";
        try {
            int funnetEnBruker = db.queryForObject(sql, Integer.class, brukernavn, passord);
            if(funnetEnBruker>0){
                return true;
            } else{
                return false;
            }

        } catch (Exception e){
            return false;
        }
    }
}