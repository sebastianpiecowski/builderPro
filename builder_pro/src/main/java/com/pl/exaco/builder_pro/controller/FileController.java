package com.pl.exaco.builder_pro.controller;

import com.pl.exaco.builder_pro.dto.FileDTO;
import com.pl.exaco.builder_pro.entity.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pl.exaco.builder_pro.service.FileService;

import java.util.List;

@RestController
@RequestMapping(value = "/builderpro/v1")
public class FileController {

	@Autowired
	private FileService fileService;

	@GetMapping(value="/file")
	public ResponseEntity<List<FileDTO>> getFiles(){
		return new ResponseEntity<>(fileService.getFiles(), HttpStatus.OK);
	}
	@GetMapping(value="/file/{fileId}")
	public ResponseEntity<FileDTO> getFile(@PathVariable("fileId") Integer id) {
		return new ResponseEntity<>(fileService.getFile(id), HttpStatus.OK);
	}
	
}
