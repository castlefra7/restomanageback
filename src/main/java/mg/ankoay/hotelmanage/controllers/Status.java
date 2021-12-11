package mg.ankoay.hotelmanage.controllers;

public class Status {
	private int code;
	private String message;

	public Status() {
        setCode(200);
        setMessage("Succés");
    }

	public int getCode() {
		return code;
	}

	public final void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public final void setMessage(String message) {
		this.message = message;
	}
}
