package com.motorcorp.application.dashboard.models;

import lombok.Getter;
import lombok.Setter;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
public class MaintenanceInfo {
    private int maintenances;

    private int free_maintenances;

    private int paid_maintenances;

}
