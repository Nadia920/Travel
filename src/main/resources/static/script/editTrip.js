protocol = $(location).attr('protocol') + '//';
hostUrl = $(location).attr('host');
localServerUrl = protocol+hostUrl+'/';

$(document).ready(function () {

    //City from
    const selectCity_fr = document.getElementById('select_city_fr');
    const selectButton_fr = document.getElementById('select_country_fr');
    selectButton_fr.addEventListener("change", function () {
        $('#error-dates').text("");
        const select = document.getElementById("select_country_fr");
        let id = select.value;
        console.log(id);
        fetch(localServerUrl + "Travel/trups/countries/" + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
        })

            .then(resp => resp.json()
                .then(json => {
                    console.log(json);
                    while (selectCity_fr.length > 0) {
                        selectCity_fr.remove(0);
                    }

                    json.map(item => {
                        const option = document.createElement("option");
                        option.setAttribute("value", item.id.toString());
                        option.innerHTML = item.name.toString();
                        selectCity_fr.appendChild(option);
                    });
                }));

    });

    //City to
    const selectCity_to = document.getElementById('select_city_to');
    const selectButton_to = document.getElementById('select_country_to');
    selectButton_to.addEventListener("change", function () {
        $('#error-dates').text("");
        const select = document.getElementById("select_country_to");
        let id = select.value;
        console.log(id);
        fetch(localServerUrl + "Travel/trips/countries/" + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
        })

            .then(resp => resp.json()
                .then(json => {
                    console.log(json);
                    while (selectCity_to.length > 0) {
                        selectCity_to.remove(0);
                    }

                    json.map(item => {
                        const option = document.createElement("option");
                        option.setAttribute("value", item.id.toString());
                        option.innerHTML = item.name.toString();
                        selectCity_to.appendChild(option);
                    });
                }));

    });

//BusStation from
    const selectBusStation_fr = document.getElementById('select_busStation_fr');
    const selectButtonCity_fr = document.getElementById('select_city_fr');
    selectButtonCity_fr.addEventListener("change", function () {
        $('#error-dates').text("");
        const select = document.getElementById("select_city_fr");
        let id = select.value;
        console.log(id);
        fetch(localServerUrl + "Travel/trips/cities/" + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
        })

            .then(resp => resp.json()
                .then(json => {
                    console.log(json);
                    while (selectBusStation_fr.length > 0) {
                        selectBusStation_fr.remove(0);
                    }

                    json.map(item => {
                        const option = document.createElement("option");
                        option.setAttribute("value", item.id.toString());
                        option.innerHTML = item.name.toString() + '(' + item.code.toString() + ')';
                        selectBusStation_fr.appendChild(option);
                    });
                }));

    });

    //BusStation to
    const selectBusStation_to = document.getElementById('select_busStation_to');
    const selectButtonCity_to = document.getElementById('select_city_to');
    selectButtonCity_to.addEventListener("change", function () {
        $('#error-dates').text("");
        const select = document.getElementById("select_city_to");
        let id = select.value;
        console.log(id);
        fetch(localServerUrl + "Travel/trips/cities/" + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
        })

            .then(resp => resp.json()
                .then(json => {
                    console.log(json);
                    while (selectBusStation_to.length > 0) {
                        selectBusStation_to.remove(0);
                    }

                    json.map(item => {
                        const option = document.createElement("option");
                        option.setAttribute("value", item.id.toString());
                        option.innerHTML = item.name.toString() + '(' + item.code.toString() + ')';
                        selectBusStation_to.appendChild(option);
                    });
                }));

    });

    //Bus
    const selectBus = document.getElementById('select_bus');
    const selectButtonCompany = document.getElementById('select_company');
    selectButtonCompany.addEventListener("change", function () {
        $('#error-dates').text("");
        const select = document.getElementById("select_company");
        let id = select.value;
        console.log(id);
        fetch(localServerUrl + "Travel/trips/cities/" + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
        })

            .then(resp => resp.json()
                .then(json => {
                    console.log(json);
                    while (selectBus.length > 0) {
                        selectBus.remove(0);
                    }

                    json.map(item => {
                        const option = document.createElement("option");
                        option.setAttribute("value", item.id.toString());
                        option.innerHTML = item.sideNumber.toString();
                        selectBus.appendChild(option);
                    });
                }));

    });

    //timepicker
    $('#picker1').datetimepicker({
        timepicker: true,
        datepicker: true,
        format: 'Y-m-d H:i',
        step: 5,
        yearStart: 2019,
        yearEnd: 2021,
        onShow: function (ct) {
            this.setOptions({
                maxDateTime: $('#picker2').val() ? $('#picker2').val() : false
            })
        }
    });

    $('#picker2').datetimepicker({
        timepicker: true,
        datepicker: true,
        format: 'Y-m-d H:i',
        step: 5,
        yearStart: 2019,
        yearEnd: 2021,
        onShow: function (ct) {
            this.setOptions({
                minDateTime: $('#picker1').val() ? $('#picker1').val() : false
            })
        }
    });


    // input-spinner
    $("input[type='number']").inputSpinner();
    var $allSeats = $("#allSeats");
    var $freeSeats = $("#freeSeats");

    $allSeats.on("change", function (event) {
        $freeSeats.attr("max", $allSeats.val());
        $freeSeats.val($allSeats.val());
    });


    //save button
    $('#save').click(function () {
        $('#error-dates').text("");
        var cityFromId = $('#select_city_fr').val();
        var cityToId = $("#select_city_to").val();
        var busStationFromId = $("#select_busStation_fr").val();
        var busStationToId = $("#select_busStation_to").val();
        var dateDeparture = $("#picker1").val() + "";
        var dateArrival = $("#picker2").val() + "";
        var companyId = $("#select_company").val();
        var busId = $("#select_bus").val();
        var allSeats = $('#allSeats').val();
        var freeSeats = $('#freeSeats').val();
        var ticketPrice = $('#price').val();
        console.log(
            "city_fr = " + cityFromId + '\n' +
            "city_to = " + cityToId + '\n' +
            "busStation_fr = " + busStationFromId + '\n' +
            "busStation_to = " + busStationToId + '\n' +
            "dateDeparture = " + dateDeparture + '\n' +
            "dateArrival = " + dateArrival + '\n' +
            "busId = " + busId + '\n' +
            "companyId = " + companyId + '\n' +
            "allSeats = " + allSeats + '\n' +
            "freeSeats = " + freeSeats + '\n' +
            "ticketPrice = " + ticketPrice + '\n'
        );

        if (cityFromId == "" || cityFromId == null || cityToId == "" || cityToId == null || busStationFromId == "" ||
            busStationFromId == null || busStationToId == "" || busStationToId == null ||
            dateDeparture == "" || dateArrival == "" || busId == "" || companyId == "" || busId == null ||
            companyId == null || allSeats == "" || freeSeats == "" || ticketPrice == "") {

            $('#error-dates').text("Please, fill in all fields!");
            return;
        }
        if (cityToId === cityFromId) {
            $('#error-dates').text("Please, Enter different cities!");
            return;
        }

        var dateD = Date.parse(dateDeparture) + "";
        var dateA = Date.parse(dateArrival) + "";

        var currentDate = new Date();
        var currD = Date.parse(currentDate);

        if (dateD < currD) {
            $('#error-dates').text("Incorrect dates");
            return;
        }

        var diffHours = dateA - dateD;
        if (diffHours <= 0) {
            $('#error-dates').text("Incorrect dates");
            return;
        }

        var tripDTO = {
            "cityFromId": cityFromId,
            "cityToId": cityToId,
            "busStationFromId": busStationFromId,
            "busStationToId": busStationToId,
            "dateDeparture": dateD,
            "dateArrival": dateA,
            "busId": busId,
            "companyId": companyId,
            "allSeats": allSeats,
            "freeSeats": freeSeats,
            "ticketPrice": ticketPrice,
            "id": null

        };
        console.log(JSON.stringify(tripDTO));
        $.ajax({
            url: localServerUrl + "Travel/trips",
            contentType: "application/json",
            method: "POST",
            data: JSON.stringify(tripDTO),
            success: function (trip) {
                alert("Trip was created successfully");
                url = localServerUrl + "Travel/trips";
                window.location.replace(url);
            },
            error: function (error) {
                if (error.status == 409) {
                    var error_msg = error.responseJSON.message;
                    $('#error-dates').text(error_msg);
                }

            },
            dataType: "json"
        });

    });

    //click edit country_from button
    const country_fr = document.getElementById('select_country_fr');
    $("#edt_country_fr").click(function () {

        const a = document.getElementById('select_city_fr');
        const b = document.getElementById('select_busStation_fr');
        //Edit
        while (a.length > 0) {
            a.remove(0);
        }

        while (b.length > 0) {
            b.remove(0);
        }

        fetch(localServerUrl + "Travel/trips/countries", {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
        })

            .then(resp => resp.json()
                .then(json => {
                    console.log(json);
                    while (country_fr.length > 0) {
                        country_fr.remove(0);
                    }

                    json.map(item => {
                        const option = document.createElement("option");
                        option.setAttribute("value", item.id.toString());
                        option.innerHTML = item.name.toString();
                        country_fr.appendChild(option);
                    });
                }));
    });

    //click edit country_to button
    const country_to = document.getElementById('select_country_to');
    $("#edt_country_to").click(function () {

        const a = document.getElementById('select_city_to');
        const b = document.getElementById('select_busStation_to');
        //Edit
        while (a.length > 0) {
            a.remove(0);
        }

        while (b.length > 0) {
            b.remove(0);
        }


        fetch(localServerUrl + "Travel/trips/countries", {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
        })

            .then(resp => resp.json()
                .then(json => {
                    console.log(json);
                    while (country_to.length > 0) {
                        country_to.remove(0);
                    }

                    json.map(item => {
                        const option = document.createElement("option");
                        option.setAttribute("value", item.id.toString());
                        option.innerHTML = item.name.toString();
                        country_to.appendChild(option);
                    });
                }));
    });

    //click edit city_from button
    const city_fr = document.getElementById('select_city_fr');
    $("#edt_city_fr").click(function () {

        const b = document.getElementById('select_busStation_fr');
        //Edit
        while (b.length > 0) {
            b.remove(0);
        }

        let id = $('#select_country_fr').val();
        fetch(localServerUrl + "Travel/trips/countries/" + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
        })

            .then(resp => resp.json()
                .then(json => {
                    console.log(json);
                    while (city_fr.length > 0) {
                        city_fr.remove(0);
                    }

                    json.map(item => {
                        const option = document.createElement("option");
                        option.setAttribute("value", item.id.toString());
                        option.innerHTML = item.name.toString();
                        city_fr.appendChild(option);
                    });
                }));
    });

    //click edit city_to button
    const city_to = document.getElementById('select_city_to');
    $("#edt_city_to").click(function () {

        const b = document.getElementById('select_busStation_to');
        //Edit
        while (b.length > 0) {
            b.remove(0);
        }

        let id = $('#select_country_to').val();
        fetch(localServerUrl + "Travel/trips/countries/" + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
        })

            .then(resp => resp.json()
                .then(json => {
                    console.log(json);
                    while (city_to.length > 0) {
                        city_to.remove(0);
                    }

                    json.map(item => {
                        const option = document.createElement("option");
                        option.setAttribute("value", item.id.toString());
                        option.innerHTML = item.name.toString();
                        city_to.appendChild(option);
                    });
                }));
    });

    //click edit busStation_to button
    const busStation_fr = document.getElementById('select_busStation_fr');
    $("#edt_busStation_from").click(function () {

        let id = $('#select_city_fr').val();
        fetch(localServerUrl + "Travel/trips/cities/" + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
        })

            .then(resp => resp.json()
                .then(json => {
                    console.log(json);
                    while (busStation_fr.length > 0) {
                        busStation_fr.remove(0);
                    }

                    json.map(item => {
                        const option = document.createElement("option");
                        option.setAttribute("value", item.id.toString());
                        option.innerHTML = item.name.toString() + '(' + item.code.toString() + ')';
                        busStation_fr.appendChild(option);
                    });
                }));
    });


    const busStation_to = document.getElementById('select_busStation_to');
    $("#edt_busStation_to").click(function () {

        let id = $('#select_city_to').val();
        fetch(localServerUrl + "Travel/trips/cities/" + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
        })

            .then(resp => resp.json()
                .then(json => {
                    console.log(json);
                    while (busStation_to.length > 0) {
                        busStation_to.remove(0);
                    }

                    json.map(item => {
                        const option = document.createElement("option");
                        option.setAttribute("value", item.id.toString());
                        option.innerHTML = item.name.toString() + '(' + item.code.toString() + ')';
                        busStation_to.appendChild(option);
                    });
                }));
    });


    //Bus


    $("#edt_company").click(function () {
        const select = document.getElementById("select_company");

        fetch(localServerUrl + "Travel/trips/companies", {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
        })

            .then(resp => resp.json()
                .then(json => {
                    console.log(json);
                    while (select.length > 0) {
                        select.remove(0);
                    }

                    json.map(item => {
                        const option = document.createElement("option");
                        option.setAttribute("value", item.id.toString());
                        option.innerHTML = item.name.toString();
                        select.appendChild(option);
                    });
                }));

    });

    //Bus
    const bus = document.getElementById('select_bus');
    $('#edt_bus').click(function () {


        let id = $('#select_company').val();
        console.log(id);
        fetch(localServerUrl + "Travel/trips/companies/" + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
        })

            .then(resp => resp.json()
                .then(json => {
                    console.log(json);
                    while (bus.length > 0) {
                        bus.remove(0);
                    }

                    json.map(item => {
                        const option = document.createElement("option");
                        option.setAttribute("value", item.id.toString());
                        option.innerHTML = item.sideNumber.toString();
                        bus.appendChild(option);
                    });
                }));

    });

    // input-spinner

    var $allSeatsEdit = $("#allSeatsEdit");
    var $freeSeatsEdit = $("#freeSeatsEdit");
    var $soldTickets = $('#soldTickets');

    $allSeatsEdit.attr("min", $soldTickets.val());

    $allSeatsEdit.on("change", function (event) {
        $freeSeatsEdit.attr("max", $allSeatsEdit.val() - $soldTickets.val());
        $freeSeatsEdit.val($allSeatsEdit.val());
    });


    //edit button
    $('#edit').click(function () {
        $('#error-dates').text("");
        var id = $("#id").val();
        var cityFromId = $('#select_city_fr').val();
        var cityToId = $("#select_city_to").val();
        var busStationFromId = $("#select_busStation_fr").val();
        var busStationToId = $("#select_busStation_to").val();
        var dateDeparture = $("#picker1").val() + "";
        var dateArrival = $("#picker2").val() + "";
        var companyId = $("#select_company").val();
        var busId = $("#select_bus").val();
        var allSeats = $('#allSeatsEdit').val();
        var freeSeats = $('#freeSeatsEdit').val();
        var ticketPrice = $('#price').val();
        console.log(
            "id=" + id + '\n',
            "city_fr = " + cityFromId + '\n' +
            "city_to = " + cityToId + '\n' +
            "busStation_fr = " + busStationFromId + '\n' +
            "busStation_to = " + busStationToId + '\n' +
            "dateDeparture = " + dateDeparture + '\n' +
            "dateArrival = " + dateArrival + '\n' +
            "busId = " + busId + '\n' +
            "companyId = " + companyId + '\n' +
            "allSeats = " + allSeats + '\n' +
            "freeSeats = " + freeSeats + '\n' +
            "ticketPrice = " + ticketPrice + '\n'
        );

        if (cityFromId == "" || cityFromId == null || cityToId == "" || cityToId == null || busStationFromId == "" ||
            busStationFromId == null || busStationToId == "" || busStationToId == null ||
            dateDeparture == "" || dateArrival == "" || busId == "" || companyId == "" || busId == null ||
            companyId == null || allSeats == "" || freeSeats == "" || ticketPrice == "") {

            $('#error-dates').text("Please, fill in all fields!");
            return;
        }
        if (cityToId === cityFromId) {
            $('#error-dates').text("Please, Enter different cities!");
            return;
        }

        var dateD = Date.parse(dateDeparture) + "";
        var dateA = Date.parse(dateArrival) + "";

        var currentDate = new Date();
        var currD = Date.parse(currentDate);

        if (dateD < currD) {
            $('#error-dates').text("Incorrect dates");
            return;
        }

        var diffHours = dateA - dateD;
        if (diffHours <= 0) {
            $('#error-dates').text("Incorrect dates");
            return;
        }

        var tripDTO = {

            "cityFromId": cityFromId,
            "cityToId": cityToId,
            "busStationFromId": busStationFromId,
            "busStationToId": busStationToId,
            "dateDeparture": dateD,
            "dateArrival": dateA,
            "busId": busId,
            "companyId": companyId,
            "allSeats": allSeats,
            "freeSeats": freeSeats,
            "ticketPrice": ticketPrice,
            "id": id
        };

        console.log(JSON.stringify(tripDTO));
        $.ajax({
            url: localServerUrl + "Travel/trips/" + id,
            contentType: "application/json",
            method: "POST",
            data: JSON.stringify(tripDTO),
            success: function (Trip) {
                alert("Trip was edited successfully")
                url = localServerUrl + "Travel/trips";
                window.location.replace(url);
            },
            error: function (error) {
                if (error.status == 409) {
                    var error_msg = error.responseJSON.message;
                    $('#error-dates').text(error_msg);
                }

            },
            dataType: "json"
        });
    });
});