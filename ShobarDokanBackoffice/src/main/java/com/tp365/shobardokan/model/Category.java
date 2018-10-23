package com.tp365.shobardokan.model;

import lombok.Data;

import java.util.Date;

@Data
public class Category {
   private Integer id;
   private String name;
   private Image image;
   private Date createdDate;

}