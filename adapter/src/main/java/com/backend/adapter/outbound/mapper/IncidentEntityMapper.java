package com.backend.adapter.outbound.mapper;

import com.backend.adapter.outbound.entity.IncidentEntity;
import com.backend.domain.happening.Incident;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        HappeningEntityMapper.class
})
public interface IncidentEntityMapper {

    IncidentEntity toIncidentEntity(Incident incident);

    Incident toIncident(IncidentEntity incidentEntity);
}
