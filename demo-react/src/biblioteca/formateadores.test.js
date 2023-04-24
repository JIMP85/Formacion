import { titleCase } from "./formateadores";

describe.only('Prueba formateadores', ()  =>{
    test('Cadena en formato de título correcto', () =>{
        const result = titleCase("hola mundo");
        expect(result).toBe("Hola Mundo");
    })

    test('Cadena vacía', ()=>{
        const result = titleCase("");
        expect(result).toBe("");
    })

    test ("Una sola palabra", ()=>{
        const result = titleCase("hola");
        expect(result).toBe("Hola")
    })


})
