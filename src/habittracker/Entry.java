package habittracker;

import java.io.Serializable;

public class Entry implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean success;
	private ReasonForFailure reasonForFailure;

	public Entry(boolean success, ReasonForFailure reasonForFailure) {
		this.success = success;
		this.reasonForFailure = reasonForFailure;
	}

	// Getters / Setters

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ReasonForFailure getReasonForFailure() {
		return reasonForFailure;
	}

	public void setReasonForFailure(ReasonForFailure reasonForFailure) {
		this.reasonForFailure = reasonForFailure;
	}

}
