package mg.ankoay.hotelmanage.bl.services.stats;

public class StatSelling {
	Iterable<StatSellingAmount> sellAmount;
	Iterable<StatSellingCount> sellCount;

	public Iterable<StatSellingAmount> getSellAmount() {
		return sellAmount;
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
