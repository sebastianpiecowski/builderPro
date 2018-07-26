package com.pl.exaco.builder_pro.service;

import com.pl.exaco.builder_pro.entity.FileEntity;
import com.pl.exaco.builder_pro.dto.FileDTO;
import com.pl.exaco.builder_pro.entity.FileEntity;
import com.pl.exaco.builder_pro.repository.FileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    private ModelMapper modelMapper;
    public List<FileDTO> getFiles() {
        modelMapper=new ModelMapper();
        List<FileDTO> list=new ArrayList<>();
        List<FileEntity> files=fileRepository.findAll();
        files.forEach(e-> {
            list.add(modelMapper.map(e, FileDTO.class));
        });
        return list;
    }

    public FileDTO getFile(int id) {
        modelMapper=new ModelMapper();
        return modelMapper.map(fileRepository.findById(id),FileDTO.class);
    }
	@Autowired
	private FileRepository fileRepository;

	public void updateFileStatus(int fileId, int statusId){
		FileEntity fileEntity = fileRepository.findById(fileId);
		fileEntity.setStatusId(statusId);
		fileRepository.save(fileEntity);
	}

}
