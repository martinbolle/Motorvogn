package motorvogn.demo;


import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;


import java.util.Comparator;
import java.util.List;

@Repository
public class MotorvognRepository {

    @Autowired
    private JdbcTemplate db;


    private Logger logger = LoggerFactory.getLogger(MotorvognRepository.class);

    public boolean lagreMotorvogn(Motorvogn motorvogn) {
        String sql = "INSERT INTO Motorvogn (personnr, navn, adresse, kjennetegn, merke, type) VALUES(?,?,?,?,?,?)";
        try {
            db.update(sql, motorvogn.getPersonnr(), motorvogn.getNavn(), motorvogn.getAdresse(), motorvogn.getKjennetegn(), motorvogn.getMerke(), motorvogn.getType());
            return true;
        } catch (Exception e) {
            logger.error("Feil i lagreMotorvogn: " + e);
            return false;
        }
    }

    public List<Motorvogn> hentAlle() {
        String sql = "SELECT * FROM Motorvogn";
        try {
            List<Motorvogn> enMotorvogn = db.query(sql, new BeanPropertyRowMapper(Motorvogn.class));
            enMotorvogn.sort((Comparator.comparing(Motorvogn::getAdresse)));
            return enMotorvogn;
        } catch (Exception e) {
            logger.error("Feil i hentAlle: " + e);
            return null;
        }
    }

    public Motorvogn hentEnMotorvogn(int id) {
        String sql = "SELECT * FROM Motorvogn WHERE id=?";
        try {
            List<Motorvogn> enMotorvogn = db.query(sql, new BeanPropertyRowMapper(Motorvogn.class), id);
            return enMotorvogn.get(0);
        } catch (Exception e) {
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
        } catch (Exception e) {
            logger.error("Feil i endreMotorvogn: " + e);
            return false;
        }
    }

    public boolean slettEnMotorvogn(int id) {
        String sql = "DELETE FROM Motorvogn WHERE id=?;";
        try {
            db.update(sql, id);
            return true;
        } catch (Exception e) {
            logger.error("Feil i slettEnMotorvogn: " + e);
            return false;
        }
    }

    public boolean slettAlle() {
        String sql = "DELETE FROM MOTORVOGN";
        try {
            db.update(sql);
            return true;
        } catch (Exception e) {
            logger.error("Feil i slettAlle: " + e);
            return false;
        }
    }

    public List<Bil> hentAlleBiler() {
        String sql = "SELECT * FROM Bil";
        try {
            List<Bil> alleBiler = db.query(sql, new BeanPropertyRowMapper(Bil.class));
            return alleBiler;
        } catch (Exception e) {
            logger.error("Feil i hentAlleBiler: " + e);
            return null;
        }
    }


    public boolean loggInn(String brukernavn, String passord) {
        String sql = "SELECT * FROM Bruker WHERE brukernavn = ?";
        try {
            List<Bruker> list = db.query(sql, new BeanPropertyRowMapper(Bruker.class), brukernavn);

            if (list != null) {
                if (sjekkPassord(passord, list.get(0).getPassord())){
                    return true;
                }
            }
            return false;
        } catch (Exception e){
            logger.error("Klarte ikke logge inn " + e);
            return false;
        }
    }

    public boolean sjekkPassord(String passord, String hashPassword) {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder(15);
        return bCrypt.matches(passord, hashPassword);
    }

    public boolean registrerBruker(Bruker bruker){
        String sql = "INSERT INTO Bruker (brukernavn, passord) VALUES (?,?)";

        try{
            String kryptertPassord = krypterPassord(bruker.getPassord());
            db.update(sql, bruker.getBrukernavn(), kryptertPassord);
            return true;
        } catch (Exception e){
            logger.error("Kunne ikke lagre bruker" + e);
            return false;
        }
    }

    public String krypterPassord(String passord) {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder(15);
        return bCrypt.encode(passord);
    }

    public boolean krypterAllePassord(){
        String sql = "SELECT * FROM Bruker";
        String kryptertPassord;

        try{
            List<Bruker> list = db.query(sql, new BeanPropertyRowMapper(Bruker.class));

            for(Bruker bruker : list){
                kryptertPassord = krypterPassord(bruker.getPassord());

                sql = "UPDATE Bruker SET passord=? WHERE id=?";
                db.update(sql, kryptertPassord, bruker.getId());
            }
            return true;
        } catch (Exception e){
            logger.error("Klarte ikke å oppdatere alle brukere" + e);
            return false;
        }
    }
}

