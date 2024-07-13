package com.acker.busticketbackend.routes;

import com.acker.busticketbackend.station.Station;
import jakarta.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "fromStation", nullable = false)
    private Station fromStation;

    @ManyToOne
    @JoinColumn(name = "toStation", nullable = false)
    private Station toStation;

    @Column(nullable = false)
    private double distance;

}
