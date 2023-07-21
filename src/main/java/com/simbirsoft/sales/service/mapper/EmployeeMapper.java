package com.simbirsoft.sales.service.mapper;

import com.simbirsoft.sales.domain.Employee;
import com.simbirsoft.sales.service.dto.EmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {}
