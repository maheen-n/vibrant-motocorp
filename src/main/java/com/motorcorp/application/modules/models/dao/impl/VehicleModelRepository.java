package com.motorcorp.application.modules.models.dao.impl;

import com.motorcorp.application.modules.models.dto.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleModelRepository extends JpaRepository<VehicleModel, Long>, JpaSpecificationExecutor<VehicleModel> {
}
