package pu.pto.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Getter
@AllArgsConstructor(staticName="of")
@Builder
@Entity
public class UserRole {
	
	private UserRole() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	private String id;
	
	@Singular
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<UserRight> rights;
	
	public String[] toRightsArray() {		
		return rights.stream()
				.map(e -> "RIGHT_"+e.getId())
				.toArray(size -> new String[size]);
	}

}
