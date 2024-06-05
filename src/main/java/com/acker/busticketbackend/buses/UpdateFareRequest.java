package com.acker.busticketbackend.buses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The Request Body for updating pricePerDistance of a bus
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFareRequest {
    private float pricePerUnitDistance;
}
