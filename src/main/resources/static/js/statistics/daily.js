document.getElementById("selected-date").addEventListener("change", function (event) {
	event.preventDefault();
	const dateStr = document.getElementById("selected-date").value;
	const date = new Date(dateStr);
	launch(date);
});

document.getElementById("btn-next").addEventListener("click", function (event) {
	event.preventDefault();
	const dateStr = document.getElementById("selected-date").value;
	const date = new Date(dateStr);
	const nextDt = addDays(date, 1);
	document.getElementById("selected-date").value = format_date(nextDt);
	launch(nextDt);
});

document.getElementById("btn-prev").addEventListener("click", function (event) {
	event.preventDefault();
	const dateStr = document.getElementById("selected-date").value;
	const date = new Date(dateStr);
	const prevDt = addDays(date, -1);
	document.getElementById("selected-date").value = format_date(prevDt);
	launch(prevDt);
});

const today = new Date();
launch(today);

function launch(dt) {
	fetch(`${URL_STAT_BY_PROD}?date=${dt.getFullYear()}-${dt.getMonth() + 1}-${dt.getDate()}`)
		.then(response => response.json())
		.then(jresponse => {
			if (jresponse) {
				if (jresponse.status.code == 200) {
					const data = jresponse.data;
					if (data && data.length > 0) {
						report_amount_by_hour("chart-report-amount-by-hours", data[0].sellAmountByHour);
						set_value_card_currency("stat-sum-selling-amount", data[0].sumSellingAmount);
						set_value_card_currency("stat-count-selling", data[0].sellingCount);
						set_value_card_currency("stat-avg-selling-amount", data[0].avgSellingAmount);
					}
				} else {
					alert(jresponse.status.message);
				}
			} else {
				alert("Une erreur inconnue s'est produite");
			}
		}).catch((error) => {
			console.log(error);
			alert(error);
		});
}

function report_amount_by_hour(id_canvas, map) {
	const labels = [];
	const data_amount = [];
	for (let i = 0; i <= 23; i++) {
		if (i < 10) {
			labels.push("0" + i.toString());
		} else {
			labels.push(i.toString());
		}
		//data_amount.push(Math.random() * 35000);
		let amount = -1;
		for (let iM = 0; iM < map.length; iM++) {
			if (i == map[iM][0]) amount = map[iM][1];
		}

		if (amount != -1) {
			data_amount.push(amount);
		} else {
			data_amount.push(0.0);
		}

	}
	
	const data = {
		labels: labels,
		datasets: [{
			label: 'Montant',
			data: data_amount,
			borderColor: "#000000",
			backgroundColor: "#795DDA"
		}]
	}

	const lbl_dt = {
		data: data_amount
	}

	const config = {
		type: 'bar',
		data: data,
		options: {
			responsive: true,
			plugins: {
				legend: {
					position: 'top',
				},
				title: {
					display: true,
					text: 'Ventes par heure'
				},
				tooltip: {
					intersect: false
				}
			}
		},
	};

	window.chAmntByHour = confChart(id_canvas, window.chAmntByHour, config, labels, lbl_dt);
}