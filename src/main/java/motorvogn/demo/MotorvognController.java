package motorvogn.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
public class MotorvognController {

    @Autowired
    private MotorvognRepository rep;

    @Autowired
    private HttpSession session;

    private Logger logger = LoggerFactory.getLogger(MotorvognController.class);

    private boolean validerMotorvognOK (Motorvogn motorvogn){
        String regexPersonnr = "[0-9]{11}";
        String regexNavn = "[a-zæøåA-ZÆØÅ .\\-]{2,30}";
        String regexAdresse = "[0-9a-zA-ZæøåÆØÅ .,\\-]{2,30}";
        String regexKjennetegn = "[A-Z][A-Z][0-9]{5}";
        String regexMerke = "[a-zA-Z. \\-]{2,20}";
        String regexType = "[0-9a-zA-ZæøåÆØÅ .\\-]{2,10}";

        boolean personnrOK = motorvogn.getPersonnr().matches(regexPersonnr);
        boolean navnOK = motorvogn.getNavn().matches(regexNavn);
        boolean adresseOK = motorvogn.getAdresse().matches(regexAdresse);
        boolean kjennetegnOK = motorvogn.getKjennetegn().matches(regexKjennetegn);
        boolean merkeOK = motorvogn.getMerke().matches(regexMerke);
        boolean typeOK = motorvogn.getType().matches(regexType);
        if (personnrOK && navnOK && adresseOK && kjennetegnOK && merkeOK && typeOK){
            return true;
        }
        logger.error("Valideringsfeil");
        return false;
    }


    @GetMapping("/hentBiler")
    public List<Bil> hentBiler(HttpServletResponse response) throws IOException{
        List<Bil> alleBiler = rep.hentAlleBiler();
        if(alleBiler == null){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
        }
        return alleBiler;

    }

    @PostMapping("/lagre")
    public void lagreKunde(Motorvogn motorvogn, HttpServletResponse response) throws IOException {
        if (validerMotorvognOK(motorvogn)) {
            if (!rep.lagreMotorvogn(motorvogn)) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
            }
        } else {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Valideringsfeil - feil i input");
        }
    }
    @GetMapping("/hentAlle")
    public List<Motorvogn> hentAlle(HttpServletResponse response) throws IOException {
        if ((boolean) session.getAttribute("Innlogget")) {
            List<Motorvogn> alleMotorvogner = rep.hentAlle();
            if (alleMotorvogner == null) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
            }
            return alleMotorvogner;
        } else{
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Du må være logget inn for å se register");
            return null;
        }
    }

    @GetMapping("/hentEnMotorvogn")
    public Motorvogn hentEnMotorvogn(int id, HttpServletResponse response) throws IOException {
        Motorvogn motorvogn = rep.hentEnMotorvogn(id);
        if (motorvogn == null) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
        }
        return motorvogn;
    }

    @PostMapping("/endre")
    public void endre(Motorvogn motorvogn, HttpServletResponse response) throws IOException {
        if (validerMotorvognOK(motorvogn)) {
            if (!rep.endreMotorvogn(motorvogn)) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
            }
        } else {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Vailideringsfeil - feil i input");
        }
    }

    @GetMapping("/slettAlle")
    public void slettAlle(HttpServletResponse response) throws IOException{
        if(!rep.slettAlle()){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
        }
    }

    @GetMapping("/slettEn")
    public void slettEnMotorvogn(int id, HttpServletResponse response) throws IOException{
        if(!rep.slettEnMotorvogn(id)){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
        }
    }


    @GetMapping("/loggUt")
    public void loggUt(){
        session.setAttribute("Innlogget", false);
    }



    @GetMapping("/loggInn")
    public boolean loggInn(String brukernavn, String passord){
        if(rep.loggInn(brukernavn, passord)){
            session.setAttribute("Innlogget", true);
            return true;
        } else {
            return false;
        }
    }

    @PostMapping("/nyBruker")
    public void registrerBruker(Bruker bruker, HttpServletResponse response) throws IOException{
        if(!rep.registrerBruker(bruker)){
            response.sendError(500, "Lagre bruker feilet");
        }
    }

    @GetMapping("/krypterAllePassord")
    public boolean krypterAllePassord(){
        return rep.krypterAllePassord();
    }
}
