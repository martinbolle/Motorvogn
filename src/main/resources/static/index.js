$(function (){
    hent();
});


$("#slettAlle").click(() =>{
        $.get("/slettAlle",function () {
            hent();
        });
});


const hent = () => $.get("/hentAlle", biler => formater(biler));



const formater = biler =>{
    let ut = "<table class='table table-striped'><tr><th>Personnr</th><th>Navn</th><th>Adresse</th><th>Kjennetegn</th><th>Bilmerke</th><th>Biltype</th><th><th></th></th></tr>";

    for (let bil of biler){
        ut+= "<tr><td>" + bil.personnr + "</td><td>" + bil.navn + "</td><td>" + bil.adresse + "</td><td>"+ bil.kjennetegn+"</td>"
            +"<td>"+ bil.merke+"</td><td>"+bil.type+"</td>"
            +"<td><button class='btn btn-primary' onclick='idTilEndring("+bil.id+")'>Endre</button></td>"
            +"<td><button class='btn btn-danger' onclick='slettEnMotorvogn("+bil.id+")'>Slett</button></td></tr>";
    }
    ut+="</table>";

    $("#personRegister").html(ut);
}

function idTilEndring(id){
    window.location.href = "/endre.html?" + id;
}

function slettEnMotorvogn(id){
    let url = "/slettEn?id="+id;
    $.get(url, function (){
        hent();
    })

}