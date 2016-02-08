function limpiarFomrmulario(idFormulario){
    document.getElementById(idFormulario).reset();
}

function validarDiv(idChech){
    if(idChech){
        document.getElementById("divDaños").style.display = "block";
        document.getElementById("divListaDaños").style.display = "block";
    }
    else{
        document.getElementById("divDaños").style.display = "none";
        document.getElementById("divListaDaños").style.display = "none";
    }
}
