package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class RequestLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate requestDate;

    @NotNull
    private Timestamp timestamp;

    @OneToMany(mappedBy = "requestLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CurrencyRate> currencies;
}
