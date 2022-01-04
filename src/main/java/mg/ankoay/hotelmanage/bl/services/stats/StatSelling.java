package mg.ankoay.hotelmanage.bl.services.stats;

public class StatSelling {
	Iterable<StatSellingAmount> sellAmount;
	Iterable<StatSellingCount> sellCount;
	Iterable<StatSellingAmount> sellAmountByHour;
	Double sumSellingAmount;
	Long sellingCount;
	Iterable<StatSellingAmount> sellingAmountProgress;

	public Iterable<StatSellingAmount> getSellingAmountProgress() {
		return sellingAmountProgress;
	}

	public void setSellingAmountProgress(Iterable<StatSellingAmount> sellingAmountProgress) {
		this.sellingAmountProgress = sellingAmountProgress;
	}

	Double avgSellingAmount;

	public Double getAvgSellingAmount() {
		return avgSellingAmount;
	}

	public void setAvgSellingAmount(Double avgSellingAmount) {
		this.avgSellingAmount = avgSellingAmount;
	}

	public Long getSellingCount() {
		return sellingCount;
	}

	public void setSellingCount(Long sellingCount) {
		this.sellingCount = sellingCount;
	}

	public Double getSumSellingAmount() {
		return sumSellingAmount;
	}

	public void setSumSellingAmount(Double sumSellingAmount) {
		this.sumSellingAmount = sumSellingAmount;
	}

	public Iterable<StatSellingAmount> getSellAmount() {
		return sellAmount;
	}

	public Iterable<StatSellingAmount> getSellAmountByHour() {
		return sellAmountByHour;
	}

	public void setSellAmountByHour(Iterable<StatSellingAmount> sellAmountByHour) {
		this.sellAmountByHour = sellAmountByHour;
	}

	public void setSellAmount(Iterable<StatSellingAmount> sellAmount) {
		this.sellAmount = sellAmount;
	}

	public Iterable<StatSellingCount> getSellCount() {
		return sellCount;
	}

	public void setSellCount(Iterable<StatSellingCount> sellCount) {
		this.sellCount = sellCount;
	}

}
