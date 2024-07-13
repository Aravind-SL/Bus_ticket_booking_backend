package com.acker.busticketbackend.routes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUpdateRouteRequest {

    private Integer fromStation;
    private Integer toStation;
    private double distance;


}
