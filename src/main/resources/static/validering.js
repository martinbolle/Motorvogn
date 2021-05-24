function validerPersonnr(){
    const personnr = $("#personnr").val();
    const regexpnr = /^[0-9]{11}$/;
    const ok = regexpnr.test(personnr);
    if(!ok){
        $("#feilPersonnr").html("Personnummeret må bestå av 11 siffer");
        return false;
    } else {
        $("#feilPersonnr").html("");
        return true;
    }
}

const validerNavn = () => {
    const navn = $("#navn").val();
    const regexN = /^[a-zA-ZæøåÆØÅ .\-]{2,20}$/;
    const ok = regexN.test(navn);
    if(!ok){
        $("#feilNavn").html("Navnet må bestå av 2-20 bokstaver");
        return false;
    } else{
        $("#feilNavn").html("");
        return true;
    }
}

const validerAdresse = () => {
    const adresse = $("#adresse").val();
    const regexA = /^[0-9a-zA-ZæøåÆØÅ. \-,]{2,30}$/;
    const ok = regexA.test(adresse);
    if(!ok){
        $("#feilAdresse").html("Adressen må bestå av 2-30 bokstaver");
        return false;
    } else {
        $("#feilAdresse").html("");
        return true;
    }
}
const validerKjennetegn = () => {
    const kjennetegn = $("#kjennetegn").val();
    const regexK = /^[A-Z][A-Z][0-9]{5}$/;
    const ok = regexK.test(kjennetegn);
    if(!ok){
        $("#feilKjennetegn").html("Kjennetegnet må ha to store bokstaver og 5 tall");
        return false;
    } else {
        $("#feilKjennetegn").html("");
        return true;
    }
}

const validerMerke = () =>{
    const merke = $("#valgtMerke").val();
    if(merke==="Velg merke"){
        $("#feilMerke").html("Velg et merke!");
        return false;
    } else {
        $("#feilMerke").html("");
        return true;
    }
}

const ingenValideringsFeil = () => {
    return (validerPersonnr() && validerNavn() && validerAdresse() && validerKjennetegn() && validerMerke());
}

const validerBrukernavn = () => {
    const brukernavn = $("#brukernavn").val();
    const regexp = /^[a-zA-ZæøåÆØÅ. \-]{2,20}$/;
    const ok = regexp.test(brukernavn);
    if (!ok) {
        $("#feilBrukernavn").html("Navnet må bestå av 2 til 20 bokstaver");
        return false;
    } else {
        $("#feilBrukernavn").html("");
        return true;
    }
}

const validerPassord = () => {
    const passord = $("#passord").val();
    const regexp = /^(?=.*[A-ZÆØÅa-zøæå])(?=.*\d)[A-ZØÆÅa-zøæå\d]{4,}$/;
    const ok = regexp.test(passord);
    if (!ok) {
        $("#feilPassord").html("Passordet må være minimum 8 tegn, minst en bokstav og et tall");
        return false;
    } else {
        $("#feilPassord").html("");
        return true;
    }
}

const loggInnValideringOK = () => {
    return (validerBrukernavn && validerPassord());
}


