function limpiarFomrmulario(idFormulario) {
    document.getElementById(idFormulario).reset();
}

function mostrarMenu(menus){
    var op = menus.split(",");
    for(var i = 0;i<op.length;i++){
        document.getElementById(op[i]).style.display = "block";
    }
}

function habilitarCampos(id){
    document.getElementById(id).disabled = false;
    document.getElementById(id).className = "ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all tamanioInputText form-control";
}
