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


//Dos formas de crear Arrays
let tab = new Array(); 
tab = [];

tab = [10,20,30]; //En la mayoria de los lenguajes, los Arrays son fijos en la creación en cuanto a longitud
tab[10]= "otro"; //En javaScript se puede modificar la longitud de los Arrays y no tienen que ser todos del mismo tipo
tab[5]= (a,b) => a+b;
tab.push('añade');
tab.splice(3,1); //Elimina un elemento del índice 3

console.log('Indices');
for( v in tab){
    console.log(v)
};

console.log('Valores');
for (v of tab){
    console.log(v)
};
//console.log(tab[5](2,3)) //Le pasa los parámetros 2 y 3 a la función que hay en el Array en la posición 5

let o = new Object();
o={};

o.nombre = 'Pepito';
o['apellidos'] = 'Grillo',
o.nom = ()=> this.nombre+ ' '+this.apellidos; //Al coger el this de una funcion lambda, el this es global y no lo usa a nivel interno
                                              // por lo tanto, da undefined
p = {nombre : 'carmelo', apellidos: 'coton', nom: function () { return this.nombre + ' ' + this.apellidos}}; //Aquí el this está encapsulado en la función
                                                    //y por tanto, lo usa correctamente.
o=p;

console.log(`${o.nombre} ${o.apellidos} ${o.nom()}`);
const ponNombre = function(tratamiento){ return tratamiento+ ' ' + this.nombre + ' ' + this.apellidos};

console.log(`ponNombre: ${ponNombre('Sr.', 1, 2)}`);
console.log(`ponNombre: ${ponNombre.apply(o,['Sr.'])}`); //El apply y el call es lo mismo, solo cambia la forma de pasar los parámetros
console.log(`ponNombre: ${ponNombre.call(o, 'Sr.', 1, 2)}`);
o.ponNombre= ponNombre;
console.log(`ponNombre: ${o.ponNombre('Sr.', 1, 2)}`);

//Mucho cuidado con el this porque puede referenciar tanto como parte de la funcion local como de forma global, depende de como y cuando se ejecute
function MiClase(elId, elNombre) {
    let vm = this; //De esta manera aseguramos que no cambia el this (al ser asignado a una variable local) y usa el objeto pasado en la function
    vm.id = elId;
    vm.nombre = elNombre;
    vm.muestraId = function() {
    alert("El ID del objeto es " + vm.id);
    }
    this.ponNombre = function(nom) {
        vm.nombre=nom.toUpperCase();
    }
}
    var elObjeto = new MiClase("99", "Objeto de prueba");
    var otroObjeto = MiClase("99", "Objeto de prueba"); //No es función constructora (no tiene el new), por lo tanto devuelve undefinned
                                                        // si no tiene return

 MiClase.prototype.cotilla = () => console.log('estoy en el prototype')
 MiClase.prototype.resumen = function() {console.log(`id: ${this.id} nombre: ${this.nombre}`)} //En este caso, en el prototype, se debe usar
                                            //el this global porque cada objeto creado tendra su propio this
 var o1 =  new Miclase ("77", "Objeto de prueba");
 o1.cotilla();


let x=10, y=20;
let punto = {x:X , y:y, suma : function() {return this.x + this.y}} //Forma antigua
punto = {x, y, suma () {return this.x + this,y}}

//El class en JavaScript es una sintaxis alternativa para crear una función constructura, no una clase como en Java
//Por convenio, las propiedades "privadas", se usan con un "_" antes del nombre para indicar que no se usen
 

class Persona{
    constructor(id, nombre, apellidos) { //al pasarle parámetros a una class siempre se le pone el nombre constructor
        this.id = id;
        this.nombre = nombre;
        this.apellidos= apellidos;
    }
    get nombreCompleto (){retun `${this.apellidos}, ${this.nombre}`}

    pinta(){
        console.log(this.nombreCompleto);
    }
}

let p1 = new Persona(1,"Pepito", "Grillo");
let p2 = new Persona(2,"Carmelo", "Coton");
let p3 = Persona(1,"Pepito", "Grillo"); //Al hacerlo desde una class, si no se pone el new, da error 

p1.pinta();
console.log(p2.nombreCompleto)
p2.nombreCompleto = 'kk' //Debería dar error al no tener setter, solo getter
