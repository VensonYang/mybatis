package controller.base;

public class ReturnResult {
	private int status;
	private Object total;
	private String message;
	private Object data;

	public int getStatus() {
		return this.status;
	}

	public ReturnResult setStatus(StatusType status) {
		this.status = status.getStatus();
		this.message = status.getMessage();
		return this;
	}

	public String getMessage() {
		return this.message;
	}

	public ReturnResult setMessage(StatusType message) {
		this.message = message.getMessage();
		return this;
	}

	public Object getTotal() {
		return this.total;
	}

	public ReturnResult setTotal(Object total) {
		this.total = total;
		return this;
	}

	public Object getData() {
		return this.data;
	}

	public ReturnResult setData(Object data) {
		this.data = data;
		return this;
	}

}