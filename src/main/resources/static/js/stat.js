const data2 = {
	prd1: 15000,
	prd2: 50000
};

const data1 = {
	pr1: 12,
	prd2: 5
};


document.getElementById("btn-validate1").addEventListener("click", function (event) {
	event.preventDefault();
	const dateStr = document.getElementById("selected-date").value;
	const date = new Date(dateStr);
	launch(date);
});

document.getElementById("btn-validate2").addEventListener("click", function (event) {
	event.preventDefault();
	//TODO: Add year field too
	const year = 2022;
	const month = parseInt(document.getElementById("selected-month").value) - 1;
	const date = new Date(year, month, 1);
	launch(date);
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
						report_product_amount_purchases("chart-report-amount-by-product", data[0].sellAmount);
						// report_product_count_purchases("chart-report-count-by-product", data[0].sellCount);
						report_amount_by_hour("chart-report-amount-by-hours", data[0].sellAmountByHour);
						set_value_card_currency("stat-sum-selling-amount", data[0].sumSellingAmount);
						set_value_card_currency("stat-count-selling", data[0].sellingCount);
						set_value_card_currency("stat-avg-selling-amount", data[0].avgSellingAmount);
						report_selling_amount_progress("chart-selling-amount-progress", data[0].sellingAmountProgress, dt);
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

function report_selling_amount_progress(id_canvas, map, dt) {
	const labels = generateMonthLabels(dt);

	const data_amount = [];
	for (let iL = 0; iL < labels.length; iL++) {
		let amount = -1;
		for (let iM = 0; iM < map.length; iM++) {
			if (labels[iL].localeCompare(map[iM][0]) == 0) {
				amount = map[iM][1];
				break;
			}
		}
		if (amount != -1) {
			data_amount.push(amount);
		} else {
			data_amount.push(0);
		}
	}

	const data = {
		labels: labels,
		datasets: [{
			label: 'Montant',
			data: data_amount,
			borderColor: "#795DDA",
			backgroundColor: "#795DDA"
		}]
	}

	const lbl_dt = {
		data: data_amount
	}

	const config = {
		type: 'line',
		data: data,
		options: {
			responsive: true,
			plugins: {
				legend: {
					position: 'top',
				},
				title: {
					display: true,
					text: 'Progression des ventes'
				}
			}
		},
	};
	
	window.chSellAmntPrg = confChart(id_canvas, window.chSellAmntPrg, config, labels, lbl_dt);
	// if (document.getElementById(id_canvas)) {
	// 	if (window.chSellAmntPrg instanceof Chart) {
	// 		removeData(window.chSellAmntPrg);
	// 		for (let iD = 0; iD < labels.length; iD++) {
	// 			addData(window.chSellAmntPrg, labels[iD], lbl_dt.data[iD]);
	// 		}
	// 	} else {
	// 		let ctx = document.getElementById(id_canvas).getContext("2d");
	// 		window.chSellAmntPrg = new Chart(ctx, config);
	// 	}
	// }
}

function confChart(id_canvas, id_chart, config, labels, lbl_dt) {

	if (document.getElementById(id_canvas)) {
		if (id_chart instanceof Chart) {
			removeData(id_chart);
			for (let iD = 0; iD < labels.length; iD++) {
				addData(id_chart, labels[iD], lbl_dt.data[iD]);
			}
			return id_chart;
		} else {
			let ctx = document.getElementById(id_canvas).getContext("2d");
			return new Chart(ctx, config);
		}
	}
	return null;
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
				}
			}
		},
	};


	// confChart(id_canvas, window.chAmntByHour, config, labels, lbl_dt);
	if (document.getElementById(id_canvas)) {
		if (window.chAmntByHour instanceof Chart) {
			removeData(window.chAmntByHour);
			for (let iD = 0; iD < labels.length; iD++) {
				addData(window.chAmntByHour, labels[iD], lbl_dt.data[iD]);
			}
		} else {
			let ctx = document.getElementById(id_canvas).getContext("2d");
			window.chAmntByHour = new Chart(ctx, config);
		}
	}
}

function report_product_count_purchases(id_canvas, map) {
	const labels_temp = [];
	const data_temp = [];
	for (let iM = 0; iM < map.length; iM++) {
		labels_temp.push(map[iM].name);
		data_temp.push(map[iM].numbers);
	}
	const lbl_dt = {
		labels: labels_temp,
		data: data_temp
	}

	const datasets = [{
		label: 'Nombre ventes',
		data: lbl_dt.data,
		backgroundColor: generate_colors(lbl_dt.labels.length)
	}];
	const data = {
		labels: labels_temp,
		datasets: datasets
	};
	const labels = lbl_dt.labels;
	const config = {
		type: 'doughnut',
		data: data,
		options: {
			responsive: true,
			plugins: {
				legend: {
					position: 'bottom',
				},
				title: {
					display: true,
					text: 'Nombre ventes par produit'
				}
			}
		}
	};
	// confChart(id_canvas, window.chtCountByProd, config, labels, lbl_dt);
	if (document.getElementById(id_canvas)) {
		if (window.chtCountByProd instanceof Chart) {
			removeData(window.chtCountByProd);
			for (let iD = 0; iD < labels.length; iD++) {
				addData(window.chtCountByProd, labels[iD], lbl_dt.data[iD]);
			}
		} else {
			let ctx = document.getElementById(id_canvas).getContext("2d");
			window.chtCountByProd = new Chart(ctx, config);
		}
	}
}

function report_product_amount_purchases(id_canvas, map) {

	const labels_temp = [];
	const data_temp = [];
	for (let iM = 0; iM < map.length; iM++) {
		labels_temp.push(map[iM][0]);
		data_temp.push(map[iM][1]);
	}

	const lbl_dt = {
		labels: labels_temp,
		data: data_temp
	};

	const labels = lbl_dt.labels
	const datasets = [{
		label: 'Montant',
		data: lbl_dt.data,
		backgroundColor: generate_colors(lbl_dt.labels.length)
	}];
	const data = {
		labels: labels,
		datasets: datasets
	};
	const config = {
		type: 'bar',
		data: data,
		options: {
			indexAxis: 'y',
			responsive: true,
			elements: {
				bar: {
					borderWidth: 2,
				}
			},
			plugins: {
				legend: {
					position: 'top',
				},
				title: {
					display: true,
					text: 'Top 10 meilleures ventes du mois'
				}
			}
		}
	};
	// confChart(id_canvas, window.chtAmntByProd, config, labels, lbl_dt);

	if (document.getElementById(id_canvas)) {
		if (window.chtAmntByProd instanceof Chart) {
			removeData(window.chtAmntByProd);
			for (let iD = 0; iD < labels.length; iD++) {
				addData(window.chtAmntByProd, labels[iD], lbl_dt.data[iD]);
			}
		} else {
			let ctx = document.getElementById(id_canvas).getContext("2d");
			window.chtAmntByProd = new Chart(ctx, config);
		}
	}
}


function daysInMonth(month, year) {
	return new Date(year, month, 0).getDate();
}

function generateMonthLabels(dt) {
	const nbDays = daysInMonth(dt.getMonth() + 1, dt.getFullYear());
	const result = [];
	for (let iD = 1; iD <= nbDays; iD++) {
		let mnth = "";
		if ((dt.getMonth() + 1) < 10) {
			mnth = "0" + (dt.getMonth() + 1);
		} else {
			mnth = dt.getMonth() + 1;
		}

		let dy = "";
		if (iD < 10) {
			dy = "0" + iD;
		} else {
			dy = iD;
		}
		result.push(`${dt.getFullYear()}-${mnth}-${dy}`);
	}
	return result;
}