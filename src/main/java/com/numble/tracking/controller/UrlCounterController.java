package com.numble.tracking.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/url")
public class UrlCounterController {

    @ApiOperation(value = "URI 증가 API 메서드", notes = "호출 시 URL Count 증가")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK !!"),
        @ApiResponse(code = 500, message = "Internal Server Error !!"),
        @ApiResponse(code = 404, message = "Not Found !!")
    })
    @PostMapping("/counter")
    public ResponseEntity<?> increaseCount(@ApiParam(value = "URL", required = true) @RequestParam String url){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam String url){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/stats/weekly")
    public ResponseEntity<?> getWeeklyStats(@RequestParam String url){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
