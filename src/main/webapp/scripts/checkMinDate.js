/**
* Indica el atributo mín del parámetro fecha del formulario
* html para comprobar que la fecha introducida es superior a la actual
*
* @author Ricardo Espantaleón Pérez
*/

var actualDate = new Date();
var dd = actualDate.getDate();

// Enero empieza en 0
var mm = actualDate.getMonth() + 1;
var yyyy = actualDate.getFullYear();

var hours = actualDate.getHours();
var minutes = actualDate.getMinutes();

if (dd < 10)
	dd = '0' + dd;

if (mm < 10)
	mm = '0' + mm;

actualDate = yyyy + '-' + mm + '-' + dd;
var currentTime = hours + ':' + minutes; 

document.getElementById("fecha").setAttribute("min", actualDate);
//document.getElementById("hora").setAttribute("min", currentTime);
