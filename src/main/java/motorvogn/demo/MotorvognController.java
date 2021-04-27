package motorvogn.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MotorvognController {

    @Autowired
    private MotorvognRepository rep;


    @GetMapping("/hentBiler")
    public List<Bil> hentBiler(){
        return rep.hentAlleBiler();
    }

    @PostMapping("/lagre")
    public void lagreKunde(Motorvogn motorvogn){
        rep.lagreMotorvogn(motorvogn);
    }

    @GetMapping("/hentAlle")
    public List<Motorvogn> hentAlle(){
        return rep.hentAlle();
    }

    @GetMapping("/hentEnMotorvogn")
    public Motorvogn hentEnMotorvogn(int id){
        return rep.hentEnMotorvogn(id);
    }


    @PostMapping("/endre")
    public void endre(Motorvogn motorvogn){
        rep.endreMotorvogn(motorvogn);
    }

    @GetMapping("/slettAlle")
    public void slettAlle(){
        rep.slettAlle();
    }

    @GetMapping("/slettEn")
    public void slettEnMotorvogn(int id){
        rep.slettEnMotorvogn(id);
    }
}
