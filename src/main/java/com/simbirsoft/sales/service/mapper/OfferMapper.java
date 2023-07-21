package com.simbirsoft.sales.service.mapper;

import com.simbirsoft.sales.domain.Employee;
import com.simbirsoft.sales.domain.Offer;
import com.simbirsoft.sales.service.dto.EmployeeDTO;
import com.simbirsoft.sales.service.dto.OfferDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Offer} and its DTO {@link OfferDTO}.
 */
@Mapper(componentModel = "spring")
public interface OfferMapper extends EntityMapper<OfferDTO, Offer> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    OfferDTO toDto(Offer s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
