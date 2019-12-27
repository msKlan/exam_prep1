var local = "http://localhost:8080/CA-2/api/address/all";
var droplet = "https://localhost:8080/CA-2/api/address/all";

fetch(local)
        .then(res => res.json())
        .then(data =>{
            console.log("data", data);
        var rows = data.map(function (address){
            return "<tr>" +
                    "<td>" + address.street + "</td>" + 
                    "<td>" + address.additionalInfo + "</td>" +
                    "</tr>";
        }).join("");
       document.getElementById("address").innerHTML = rows;
});
