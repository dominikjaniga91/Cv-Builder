package com.cvgenerator.cvgenerator.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String school;
    private String city;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String description;

    @ManyToOne
    @JoinColumn(name = "userCvId")
    private UserCv userCv;
}
