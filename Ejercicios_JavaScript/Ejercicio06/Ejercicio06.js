function calcularMayor(){
    let num1 = parseInt(document.getElementById("texto1").value);
    let num2 = parseInt(document.getElementById("texto2").value);

    if(num1 && num2){
        (num1 >= num2) ? alert(`El mayor es ${num1}`) : alert(`El mayor es ${num2}`);
    }else{
        alert('Un input está vacío');
    }
}