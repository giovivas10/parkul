function limpiarFomrmulario(idFormulario) {
    document.getElementById(idFormulario).reset();
}

function mostrarMenu(menus){
    var op = menus.split(",");
    for(var i = 0;op.length;i++){
        document.getElementById(op[i]).style.display = "block";
    }
}
