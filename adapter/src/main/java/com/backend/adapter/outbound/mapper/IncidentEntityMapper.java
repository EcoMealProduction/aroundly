package com.backend.adapter.outbound.mapper;

import com.backend.adapter.outbound.entity.IncidentEntity;
import com.backend.domain.happening.Incident;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {HappeningEntityMapper.class
        })
public interface IncidentEntityMapper {

    @Mapping(source = "engagementStats.confirms", target = "confirms")
    @Mapping(source = "engagementStats.denies", target = "denies")
    IncidentEntity toIncidentEntity(Incident incident);

//    @Mapping(source = "engagementStats.confirms", target = "confirms")
//    @Mapping(source = "engagementStats.denies", target = "denies")
    Incident toIncident(IncidentEntity incidentEntity);

}