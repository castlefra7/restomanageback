document.getElementById("selected-month").addEventListener("change", function (event) {
	event.preventDefault();
	request();
});

document.getElementById("selected-year").addEventListener("change", function (event) {
	event.preventDefault();
	request();
});


function request() {
	
	const year = parseInt(document.getElementById("selected-year").value);
	const month = parseInt(document.getElementById("selected-month").value) - 1;
	const date = new Date(year, month, 1);
	console.log(year);
	launch(date);
}

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
				},
				tooltip: {
					intersect: false
				}
			}
		},
	};
	
	window.chSellAmntPrg = confChart(id_canvas, window.chSellAmntPrg, config, labels, lbl_dt);
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
				},
				tooltip: {
					intersect: false
				}
			}
		}
	};

	window.chtCountByProd = confChart(id_canvas, window.chtCountByProd, config, labels, lbl_dt);
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
				},
				tooltip: {
					intersect: false
				}
			}
		}
	};

	window.chtAmntByProd = confChart(id_canvas, window.chtAmntByProd, config, labels, lbl_dt);
}