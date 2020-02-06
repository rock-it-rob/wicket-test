function createDoughnutChart() {
    var chart = new Chart($('#chart'), {
        type: 'doughnut',
        data: {
            labels: $('#labels').val().split(' '),
            datasets: [{
                data: $('#data').val().split(' '),
                backgroundColor: $('#backgroundColor').val().split(' ')
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true
        }
    });
}