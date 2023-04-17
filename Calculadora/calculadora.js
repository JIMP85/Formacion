window.addEventListener('load', ()=>{
    const pantalla = document.querySelector('.calculadora-display');
    const botonesNum= document.getElementsByClassName('boton-teclado');

    const arrayNumeros = Array.from(botonesNum);

    arrayNumeros.forEach( (button) =>{
        button.addEventListener('click', () =>{
            calculadora(button, pantalla)
        })
    })
})


function calculadora (button, pantalla) {
    switch(button.innerHTML){
        case 'C':
            borrar(pantalla);
            break;

        case '=':
            calcular(pantalla);
            break;

        default:
            actualizar(pantalla, button)
            break;
    }
}

function calcular(pantalla){
    pantalla.innerHTML = eval(pantalla.innerHTML)
}

function actualizar(pantalla, button){
    if(pantalla.innerHTML == 0) {
        pantalla.innerHTML =''
    }
    pantalla.innerHTML += button.innerHTML;
}

function borrar(pantalla){
    pantalla.innerHTML = 0;
}