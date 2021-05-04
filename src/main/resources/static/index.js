$(()=>{

$("#loggInn").click(() => {
    if (loggInnValideringOK()) {
        const url = "/loggInn?brukernavn=" + $("#brukernavn").val() + "&passord=" + $("#passord").val();
        $.get(url, OK => {
            if (OK) {
                window.location.href = "/liste.html";
            } else {
                $("#feil").html("Feil i brukernavn eller passord");
            }
        })
            .fail(jqXHR => {
                const json = $.parseJSON(jqXHR.responseText);
                $("#feil").html(json.message);
            });
    }
});

    $("#registrer").click(()=>{
        window.location.href="/nybruker.html";
    });

$("#krypterAllePassord").click(()=>{
    $.get("/krypterAllePassord", OK =>{
        if(OK){
            $("feil").html("Kryptering fullørt");
        } else{
            $("#feil").html("Kryptering feilet");
        }
    })
})


})