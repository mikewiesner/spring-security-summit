package pu.pto.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor(staticName="of")
@Builder(toBuilder=true)
@ToString
@Entity
public class PTO {
	
	private PTO() {
	}

	
	@Id
	private String id;
	@OneToOne
	private Employee requester;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate fromDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate toDate;
	private String comment;

}
