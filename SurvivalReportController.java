package com.greenlog.controller;

import com.greenlog.service.SurvivalReportService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/reports")
public class SurvivalReportController {

    private final SurvivalReportService survivalReportService;

    public SurvivalReportController(SurvivalReportService survivalReportService) {
        this.survivalReportService = survivalReportService;
    }

    @GetMapping("/drives/{driveId}/survival-rate")
    public BigDecimal getSurvivalRateByDrive(@PathVariable Long driveId) {
        return survivalReportService.getSurvivalReportByDrive(driveId);
    }

    @GetMapping("/species/{species}/survival-rate")
    public BigDecimal getSurvivalRateBySpecies(@PathVariable String species) {
        return survivalReportService.getSurvivalReportBySpecies(species);
    }

    @GetMapping("/drives/{driveId}")
    public BigDecimal getSurvivalReportByDriveId(@PathVariable Long driveId) {
        return survivalReportService.getSurvivalReportByDrive(driveId);
    }
}
