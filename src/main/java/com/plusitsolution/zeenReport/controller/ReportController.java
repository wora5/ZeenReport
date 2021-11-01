package com.plusitsolution.zeenReport.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.plusitsolution.zeenReport.service.ReportService;

@RestController
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
	@RequestMapping(value = "/getConvert", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<byte[]> getConvert(@RequestParam("uploadCSV") MultipartFile uploadfile)  throws IOException{
		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.csv\"").body(reportService.getConvert(uploadfile));
	}
	
	@RequestMapping(value = "/getConvert2", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<byte[]> getConvert2(@RequestParam("uploadCSV") MultipartFile uploadfile)  throws IOException{
		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.csv\"").body(reportService.getConvert(uploadfile));
	}

}
