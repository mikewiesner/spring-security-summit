package pu.pto.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(staticName="of")
@Builder
@Getter
@Entity
public class UserRight {
	
	private UserRight() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	private String id;


}
