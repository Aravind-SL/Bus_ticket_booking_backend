package com.acker.busticketbackend.routes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "routes")
@AllArgsConstructor
@NoArgsConstructor
public class Routes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long routeId;

    @Column(name = "fromStationId", nullable = false)
    private Long fromStation;

    @Column(name = "toStationId", nullable = false)
    private Long toStation;

    @Column(nullable = false)
    private double distance;

}
