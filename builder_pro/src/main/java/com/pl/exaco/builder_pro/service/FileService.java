package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.entity.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pl.exaco.builder_pro.repository.FileRepository;

@Service
public class FileService {

	@Autowired
	private FileRepository fileRepository;

	public void updateFileStatus(int fileId, int statusId){
		FileEntity fileEntity = fileRepository.findById(fileId);
		fileEntity.setStatusId(statusId);
		fileRepository.save(fileEntity);
	}
	
}
