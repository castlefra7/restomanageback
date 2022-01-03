package mg.ankoay.hotelmanage.controllers;

import java.util.ArrayList;

public class ResponseBody<T> {
	private Status status;
	private Iterable<T> data;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ResponseBody() {
		setStatus(new Status());
		setData(new ArrayList<>());
	}

	public Status getStatus() {
		return status;
	}

	public final void setStatus(Status statut) {
		this.status = statut;
	}

	public Iterable<T> getData() {
		return data;
	}

	public final void setData(Iterable<T> data) {
		this.data = data;
	}
}
