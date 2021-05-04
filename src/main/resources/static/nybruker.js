$(()=>{
    $("#registrer").click(()=>{
        const bruker ={
            brukernavn : $("#brukernavn").val(),
            passord : $("#passord").val()
        }

        if(loggInnValideringOK()){
            $.post("/nyBruker", bruker, () =>{
                const url = "/loggInn?brukernavn="+bruker.brukernavn
                    +"&passord="+bruker.passord;

                $.get(url, OK=>{
                    if(OK){
                        window.location.href="/liste.html";
                    } else {
                        $("#feil").html("feil i brukernavn eller passord");
                    }
                })
            }).fail(jqXHR =>{
                const json = $.parseJSON(jqXHR.responseText);
                $("#feil").html(json.message);
            })
        }
    })


})