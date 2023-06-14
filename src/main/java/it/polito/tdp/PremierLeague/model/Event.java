package it.polito.tdp.PremierLeague.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Event implements Comparable<Event> {
	
	//tipo singolo: partita
	
	private Match partita;
	private LocalDateTime data;
	
	public Event(Match partita, LocalDateTime data) {
		this.partita = partita;
		this.data = data;
	}

	public Match getPartita() {
		return partita;
	}

	public void setPartita(Match partita) {
		this.partita = partita;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		return Objects.hash(partita);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return Objects.equals(partita, other.partita);
	}

	@Override
	public int compareTo(Event o) {
		return this.data.compareTo(o.data);
	}
	
	
	

}
