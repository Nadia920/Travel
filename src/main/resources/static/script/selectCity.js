protocol = $(location).attr('protocol') + '//';
hostUrl = $(location).attr('host');
localServerUrl = protocol+hostUrl+'/';

$(document).ready(function() {

    const selectCity = document.getElementById('selectCity');
    const selectButton = document.getElementById('select_');
    selectButton.addEventListener("change", function () {
        const select = document.getElementById("select_");
        let id = select.value;
        const obj = {"id" :select.value};
        console.log(JSON.stringify(obj));
        fetch( localServerUrl+"busStation/country/"+id,{
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
        })

            .then(resp => resp.json()
                .then(json => {
                    console.log(json);
                    while (selectCity.length > 0) {
                        selectCity.remove(0);
                    }

                    json.map(item => {
                        const option = document.createElement("option");
                        option.setAttribute("value", item.id.toString());
                        option.innerHTML = item.name.toString();
                        selectCity.appendChild(option);
                    });
                }));

    });



});