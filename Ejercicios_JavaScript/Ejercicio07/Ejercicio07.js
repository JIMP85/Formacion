function generarNumerosAleatorios(){
    let mostrarNumeros = document.getElementById("mostrar");

    let generarNumeros =[];

    for (let i= 0; i<10; i++){
        let numero = Math.floor(Math.random()*100 )+1;
        generarNumeros.push(numero);
    }

    mostrarNumeros.innerHTML = generarNumeros;

}