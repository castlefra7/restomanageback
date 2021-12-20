const data2 = {
	prd1: 15000,
	prd2: 50000
};

const data1 = {
	pr1: 12,
	prd2: 5
};

fetch(URL_STAT_BY_PROD)
	.then(response => response.json())
	.then(jresponse => {
		if (jresponse) {
			console.log(jresponse);
			if (jresponse.status.code == 200) {
				const data = jresponse.data;
				console.log(data);
				if (data && data.length > 0) {
					report_product_amount_purchases("chart-report-amount-by-product", data[0].sellAmount);
					report_product_count_purchases("chart-report-count-by-product", data[0].sellCount);
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
		labels_temp.push(map[iM].name);
		data_temp.push(map[iM].amount);
	}

	const lbl_dt = {
		labels: labels_temp,
		data: data_temp
	};

	const labels = lbl_dt.labels
	const datasets = [{
		label: 'Montant ventes',
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
					position: 'right',
				},
				title: {
					display: true,
					text: 'Montant ventes par produit'
				}
			}
		}
	};
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