function limpiarFomrmulario(idFormulario) {
    document.getElementById(idFormulario).reset();
}

function pruebaX() {
    var w = window,
            d = document,
            e = d.documentElement,
            g = d.getElementsByTagName('body')[0],
            x = w.innerWidth || e.clientWidth || g.clientWidth,
            y = w.innerHeight || e.clientHeight || g.clientHeight;
    alert(x + ' × ' + y);
}

function alertSize() {
    var myWidth = 0, myHeight = 0;
    if (typeof (window.innerWidth) == 'number') {
        //No-IE 
        myWidth = window.innerWidth;
        myHeight = window.innerHeight;
    } else if (document.documentElement && (document.documentElement.clientWidth || document.documentElement.clientHeight)) {
        //IE 6+ 
        myWidth = document.documentElement.clientWidth;
        myHeight = document.documentElement.clientHeight;
    } else if (document.body && (document.body.clientWidth || document.body.clientHeight)) {
        //IE 4 compatible 
        myWidth = document.body.clientWidth;
        myHeight = document.body.clientHeight;
    }
    //document.getElementById("resultado").innerHTML = "El ancho de la pagina es de " + myWidth + "px y el alto es de " + myHeight + "px";
    //alert(myWidth + "px");
   // document.getElementById("si:x").value = myWidth + "px";
    //document.getElementById("si:y").value = myHeight + "px";
}
