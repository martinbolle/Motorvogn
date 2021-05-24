$("#registrer").click(()=>{
        const bruker ={
            brukernavn : $("#brukernavn").val(),
            passord : $("#passord").val()
        }
        console.log("Hei")

        if(loggInnValideringOK()){
            $.post("/nyBruker", bruker, () =>{
                const url = "/loggInn?brukernavn="+bruker.brukernavn
                    +"&passord="+bruker.passord;

                $.get(url, OK=>{
                    if(OK){
                        window.location.href="/liste.html";
                    } else {
                        $("#feilRegistrering").html("feil i brukernavn eller passord");
                    }
                })
            }).fail(jqXHR =>{
                const json = $.parseJSON(jqXHR.responseText);
                $("#feilRegistrering").html(json.message);
            })
        }
    })