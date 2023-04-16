
function elegirBoton(){
    let opciones = document.getElementsByName("opcion");

    for( let i=0; i<opciones.length; i++){
        if(opciones[i].checked){
            alert(opciones[i].value);
        }
        
    }

}