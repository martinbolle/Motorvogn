$(function (){
    hentAlleBiler();
});

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
    $("#merke").html(ut);
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

    $("#type").html(ut);
}

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
        $.post("/lagre", motorvogn, () => hent())

    .fail(function (jqXHR){
            const json = $.parseJSON(jqXHR.responseText);
            $("#feil").html(json.message);
        });

        window.location.href="/";
    } else {
        console.log("Feil i input");
    }
});

const inputval = motorvogn => {
    if (motorvogn.personnr ==="") return false
    else if (motorvogn.navn ==="") return false
    else if (motorvogn.adresse ==="") return false
    else if (motorvogn.kjennetegn ==="") return false
    else if (motorvogn.merke ==="") return false
    else return motorvogn.type !== "";
}

const hent = () => $.get("/hentAlle", biler => formater(biler));