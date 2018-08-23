package com.pl.exaco.builder_pro.controller;

import com.pl.exaco.builder_pro.entity.BuildDictEntity;
import com.pl.exaco.builder_pro.utils.AuthenticationHelper;
import com.pl.exaco.builder_pro.utils.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pl.exaco.builder_pro.service.BuildService;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping(value = Configuration.VERSION)
public class BuildController {

	@Autowired
	private BuildService buildService;

	@GetMapping(value = "/build/dict")
	private ResponseEntity<List<BuildDictEntity>> getBuildDict(@RequestHeader(AuthenticationHelper.HEADER_FIELD) String token){
		try {
			AuthenticationHelper.Authorize(token);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(buildService.getBuildDict(), HttpStatus.OK);
	}

	@DeleteMapping(value="/build/{id}")
	private ResponseEntity<Void> deleteBuild(@PathVariable("id") Integer id, @RequestHeader(AuthenticationHelper.HEADER_FIELD) String token){
		try {
			AuthenticationHelper.Authorize(token);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		try {
			buildService.deleteBuild(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

}
