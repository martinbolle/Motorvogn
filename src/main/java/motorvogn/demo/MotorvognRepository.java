package motorvogn.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MotorvognRepository {

    @Autowired
    private JdbcTemplate db;

    private Logger logger = LoggerFactory.getLogger(MotorvognRepository.class);


    public void lagreMotorvogn(Motorvogn motorvogn){
        String sql = "INSERT INTO Motorvogn (personnr, navn, adresse, kjennetegn, merke, type) VALUES(?,?,?,?,?,?)";
        db.update(sql, motorvogn.getPersonnr(), motorvogn.getNavn(), motorvogn.getAdresse(), motorvogn.getKjennetegn(), motorvogn.getMerke(), motorvogn.getType());

    }
    public List<Motorvogn> hentAlle(){
        String sql = "SELECT * FROM Motorvogn";
        return db.query(sql, new BeanPropertyRowMapper(Motorvogn.class));

    }

    public Motorvogn hentEnMotorvogn(int id){
        String sql = "SELECT * FROM Motorvogn WHERE id=?";
        List<Motorvogn> enMotorvogn = db.query(sql, new BeanPropertyRowMapper(Motorvogn.class), id);
        return enMotorvogn.get(0);
    }

    public void endreMotorvogn(Motorvogn motorvogn){
        String sql = "UPDATE Motorvogn SET personnr=?, navn=?, adresse=?, kjennetegn=?, merke=?, type=? WHERE id=?";
        db.update(sql, motorvogn.getPersonnr(), motorvogn.getNavn(), motorvogn.getAdresse(),
                motorvogn.getKjennetegn(), motorvogn.getMerke(), motorvogn.getType(), motorvogn.getId());
    }

    public void slettEnMotorvogn(int id){
        String sql = "DELETE FROM Motorvogn WHERE id=?;";
        db.update(sql, id);

    }

    public void slettAlle(){
        String sql = "DELETE FROM MOTORVOGN";
        db.update(sql);

    }

    public List<Bil> hentAlleBiler(){
        String sql = "SELECT * FROM Bil";
        return db.query(sql, new BeanPropertyRowMapper(Bil.class));

    }
}