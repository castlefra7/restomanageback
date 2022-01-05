const URL_STAT_BY_PROD = "http://localhost:8080/stat/selling";


function set_value_card_currency(id_canvas, value) {
	if (document.getElementById(id_canvas)) {
		var element = document.getElementById(id_canvas);
		let formated_value = new Intl.NumberFormat('fr-FR').format(value);
		element.innerText = `${formated_value}`;
	}
}

/* CHART FUNCTIONS */

function format_date(date) {
	let day = date.getDate();
	let month = date.getMonth() + 1;
	if (day < 10) {
		day = "0" + day;
	}
	if (month < 10) {
		month = "0" + month;

	}
	return `${date.getFullYear()}-${month}-${day}`;
}

function addDays(date, days) {
	var result = new Date(date);
	result.setDate(result.getDate() + days);
	return result;
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


function removeData(chart) {
	chart.data.labels = [];
	chart.data.datasets.forEach((dataset) => {
		dataset.data = [];
	});
	chart.update();
}

function addData(chart, label, data) {
	chart.data.labels.push(label);
	chart.data.datasets.forEach((dataset) => {
		dataset.data.push(data);
	});
	chart.update();
}

function get_label_data(map) {
	const labels = [];
	const data = [];
	const temp = [];
	for (const [key, value] of Object.entries(map)) {
		temp.push({
			key: key,
			value: value
		});
	}

	temp.sort(function (a, b) {
		return b.value - a.value;
	});

	for (let iT = 0; iT < temp.length; iT++) {
		labels.push(temp[iT].key);
		data.push(temp[iT].value);
	}

	return {
		labels: labels,
		data: data
	};
}

/* GENERATE COLORS */

function generate_colors(count) {
	const COLORS = [
		'#C5283D',
		'#730071',
		'#390040',
		'#074F57',
		'#481D24',
		'#175676',
		'#1E152A',

		'#FFC857',
		'#C297B8',
		'#255F85'
	];
	const result = [];
	for (let iC = 0; iC < count; iC++) {
		result.push(COLORS[iC % COLORS.length]);
	}
	return result;
}

function color(index) {
	return COLORS[index % COLORS.length];
}