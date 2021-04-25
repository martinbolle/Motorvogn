$(function (){
    hentAlleBiler();
})


$(() =>{
    $("#regMotorvogn").click(() => {
        const personnr = $("#personnr");
        const navn = $("#navn");
        const adresse = $("#adresse");
        const kjennetegn = $("#kjennetegn");
        const merke = $("#valgtMerke");
        const type = $("#valgtType");


        const motorvogn = {
            personnr: personnr.val(),
            navn: navn.val(),
            adresse: adresse.val(),
            kjennetegn: kjennetegn.val(),
            merke: merke.val(),
            type: type.val()
        };

        if(inputval(motorvogn)){
            $.post("/lagre", motorvogn, () => hent());

            personnr.val("");
            navn.val("");
            adresse.val("");
            kjennetegn.val("");
            merke.val("");
            type.val("");
        } else {
            console.log("Feil i input");
        }
    });
    $("#slettAlle").click(() =>{
        $.get("/slettAlle",function () {
            hent();
        });
    });
});

const hent = () => $.get("/hentAlle", biler => formater(biler));

const inputval = motorvogn => {
    if (motorvogn.personnr ==="") return false
    else if (motorvogn.navn ==="") return false
    else if (motorvogn.adresse ==="") return false
    else if (motorvogn.kjennetegn ==="") return false
    else if (motorvogn.merke ==="") return false
    else return motorvogn.type !== "";
}

const formater = biler =>{
    let ut = "<table class='table table-striped'><tr><th>Personnr</th><th>Navn</th><th>Adresse</th><th>Kjennetegn</th><th>Bilmerke</th><th>Biltype</th><th></th></tr>";

    for (let bil of biler){
        ut+= "<tr><td>" + bil.personnr + "</td><td>" + bil.navn + "</td><td>" + bil.adresse + "</td><td>"+ bil.kjennetegn+"</td>"
             +"<td>"+ bil.merke+"</td><td>"+bil.type+"</td><td><button class='btn btn-danger' onclick='slettEnMotorvogn("+bil.id+")'>Slett</button></td></tr>";
    }
    ut+="</table>";

    $("#personRegister").html(ut);
}
function hentAlleBiler(){
    $.get("/hentBiler", function (biler){
        formaterBiler(biler);
    });
}
function formaterBiler(biler){
    let ut = "<select id='valgtMerke' onchange='finnTyper()'>"
    let forrigeMerke = "";
    ut += "<option>Velg merke</option>";

    for (const bil of biler){
        if(bil.merke != forrigeMerke){
            ut += "<option>" + bil.merke + "</option>";
        }
        forrigeMerke = bil.merke;
    }
    ut += "</select>";
    $("#bilmerke").html(ut);
}

function finnTyper(){
    const valgtMerke =$("#valgtMerke").val();
    $.get("/hentBiler", function (biler){
       formaterTyper(biler, valgtMerke);
    });
}
function formaterTyper(biler, valgtMerke){
    let ut ="<select id='valgtType'>";
    for (const bil of biler){
        if(bil.merke === valgtMerke){
            ut+= "<option>" + bil.type + "</option>";
        }
    }
    ut+="</select>";

    $("#biltype").html(ut);
}

function slettEnMotorvogn(id){
    let url = "/slettEn?id="+id;
    $.get(url, function (){
        hent();
    })

}