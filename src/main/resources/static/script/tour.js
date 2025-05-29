protocol = $(location).attr('protocol') + '//';
hostUrl = $(location).attr('host');
localServerUrl = protocol+hostUrl+'/';

google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(drawBasic);

function drawBasic() {

    var init = [
        ['Маршрут', 'Кол-во рейсов']
    ];

    $.ajax({
        url: localServerUrl + "Travel/info/tour/data",
        contentType: "application/json",
        method: "GET",
        success: function (result) {
            console.log(result);
            init = init.concat(func(result));
            console.log(init);
            draw(init);
        },
        dataType: "json"
    });

}

func = (result) => {
    return result.map(elem => {
        return [elem.tripName, elem.countTrip]
    })
};

draw = (init)=>{
    var data = google.visualization.arrayToDataTable(init);

    var options = {
        title: 'Статистика популярности существующих направлений поездок',
        chartArea: {width: '70%'},
        hAxis: {
            title: 'Количество рейсов',
            minValue: 0
        },
        vAxis: {
            title: 'Р е й с'
        }
    };

    var tour = new google.visualization.BarTour(document.getElementById('tour_div'));

    tour.draw(data, options);
};