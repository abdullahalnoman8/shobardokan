package com.tp365.shobardokan.model;

import lombok.Data;

import java.util.Date;

@Data
public class Image {
    private Integer id;
    private byte[] data;
    private String fileName;
    private int fileSize;
    private String imageHash;
    private String fileType;
    private Date createdDate;
}