package com.backend.triba.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Location")
public class Location {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", columnDefinition = "NVARCHAR(255)")
    private String name;
    
    @Column(name = "thumbnail", columnDefinition = "ntext")
    private String thumbnail;
    
    @ManyToMany(mappedBy = "locations")
    @JsonIgnore
    private List<Job> jobs;

	@Override
	public String toString() {
		return "Location [id=" + id + ", name=" + name + "]";
	}
}