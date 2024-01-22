package com.example.library.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Delay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delay_id")
    private Long id;

    private LocalDate loanableTransitionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lending_id")
    private Lending lending;
}
