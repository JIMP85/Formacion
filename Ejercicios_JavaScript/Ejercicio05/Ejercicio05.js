function agregarTexto(){
    let texto = document.getElementById("texto");
    console.log(texto.value);
    let pegar = document.getElementById("textoPegado");

    pegar.innerHTML += texto.value;
}