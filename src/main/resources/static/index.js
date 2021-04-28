const loggInn = () =>{
    const url ="/login?brukernavn=" + $("#brukernavn").val() + "&passord="+$("#passord").val();
    $.get(url, function (OK){
        if(OK){
            window.location.href="liste.html";
        } else {
            $("#feilLogin").html("Feil brukernavn eller passord");
        }
    })
        .fail(function (jqXHR){
            const json = $.parseJSON(jqXHR.responseText);
            $("#feilLogin").html(json.message);
        });
}