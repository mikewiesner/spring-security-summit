package pu.pto.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName="of")
@Builder
@Entity
public class Employee {

	private Employee() {

	}

	@Id
	private String id;
	private String email;
	private String firstname;
	private String lastname;
	private String password;
	private String role;

}
