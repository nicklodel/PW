/**
 * @author Ricardo Espantaleón Pérez
 */

var dates = [];

var element = document.getElementById("fechas");


document.getElementById("submitFecha").addEventListener("click", function() {

	if (document.getElementById("hora").value != "" && document.getElementById("fecha").value != "") {
		var hour = document.getElementById("hora").value;
		dates.push(document.getElementById("fecha").value + "|" + hour);

		var text = "Usted ha seleccionado las siguientes fechas: " + dates

		element.innerHTML = text;
	}
});

document.getElementById("buttonSubmit").addEventListener("click", function() {

	document.getElementById("uriFechas").value = dates;
});