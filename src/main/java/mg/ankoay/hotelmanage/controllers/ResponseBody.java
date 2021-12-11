package mg.ankoay.hotelmanage.controllers;

import java.util.ArrayList;

public class ResponseBody<T> {
	private Status status;
	private Iterable<T> data;

	public ResponseBody() {
		setStatut(new Status());
		setData(new ArrayList<>());
	}

	public Status getStatut() {
		return status;
	}

	public final void setStatut(Status statut) {
		this.status = statut;
	}

	public Iterable<T> getData() {
		return data;
	}

	public final void setData(Iterable<T> data) {
		this.data = data;
	}
}
