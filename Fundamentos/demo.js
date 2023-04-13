'use strict' //con esto hace que las variables no declaradas den error

console.log("Soy el fichero");
a='5'
var b=2
c=2
console.log(`------> ${a+b+c}`);
// console.log(`------> ${a*b+c}`);

function kk(){
    var a= 7;
    console.log(`------> ${a+b+c}`);
    if(false){
        var x = `bloque`  //Las variables locales declaradas con var son a nivel de función, no de bloque
    }
    d=a+x; //Las variables no declaradas son declaradas a nivel global
}

kk();

x=33;

kk();
console.log(`------> ${d}`); 

if(a=1){
    console.log(`Cierto`)
}

function kk(){
    var a= 7;
    console.log(`------> ${a+b+c}`);
    if(false){
        let x = `bloque`  //Las variables locales declaradas con let son a nivel bloque, como en otros lenguajes
    }
    d=a+x; //Las variables no declaradas son declaradas a nivel global
}

t=[10,20,30];
for (v in t){ //Devuelve los índices
    console.log(v);
}
for (v in t){ //Devuelve los valores
    console.log(t[v]);
}

for (v of t){ //Devuelve los valores de un array, no de un object (map)
    console.log(v);
}

let cond =0;
if(cond ? `Cierto`:`Falso`);

function avg() {
    var rslt= 0;
    for(var i=0; i < arguments.length; i++) {
    rslt += arguments[i];
    }
    return arguments.length ? (rslt / arguments.length) : 0;
    }

console.log(avg(10,20,30));
console.log(avg(...t));

punto={x:10 , y:20};

function coordenadas(x,y){
    return x+y;
}

console.log(coordenadas(punto.x, punto.y));
console.log(coordenadas(...punto));

//funcion
function suma(a,b){return a+b; }
//Arrow function
suma = (a,b) => a+b; //Con varios parámetros hay que meterlos entre paréntesis
t.filter(item => item % 2) //si solo va un parámetro, no hace falta ponerlo entre los paréntesis
t.filter(item => item >10)
t.filter(function(item) {return item>10}) //Función idéntica a la anterior



filtrado = function(item) {return item>10}
t.filter(filtrado);



coordenadas = (x,y) => x+y;
