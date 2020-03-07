package org.springframework.samples.theionianbookshelf.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
@Embeddable
public class Status {
	
	@NotBlank
	@Pattern(regexp = "^" + Status.PENDING + "|" + Status.ACCEPTED + "|" + Status.DENIED + "$")
	private String status;

	public static final String PENDING = "PENDING";
	public static final String ACCEPTED = "ACCEPTED";
	public static final String DENIED = "DENIED";

	public static Collection<Status> listStatus() {
		Collection<Status> res;
		Status status;

		res = new ArrayList<Status>();

		status = new Status();
		status.setStatus(Status.ACCEPTED);
		res.add(status);

		status = new Status();
		status.setStatus(Status.PENDING);
		res.add(status);
		
		status = new Status();
		status.setStatus(Status.DENIED);
		res.add(status);
		
		return res;
	}

}

