package com.crud.base.demo.model;

import com.crud.base.demo.exceptions.NotAllowedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Entity
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", updatable=false, unique=true, nullable=false)
    private Integer id;

    @NotBlank(message = "O nome do curso é necessário.")
    private String name;

    @NotNull(message = "O ano do curso é necessário.")
    private Integer year;

    private UUID userId;

    public Course(){

    }

    public Course(UUID userID, String name, Integer year){
        this.userId = userID;
        this.name = name;
        this.year = year;
    }

    public static Course getInstanceByMap(Map<String, Object> courseMap){
        return new ObjectMapper().convertValue(new ObjectMapper(), Course.class);
    }

    public static Course getInstance(Map<String, Object> courseMap) throws NotAllowedException {
        Course course = new Course();

        if (courseMap.get("name")!=null){
            course.setName(courseMap.get("name").toString());
        }

        if(courseMap.get("year") != null && !String.valueOf(courseMap.get("year")).isEmpty()){

            try {
                course.setYear(Integer.parseInt(courseMap.get("year").toString()));
            }catch (NumberFormatException e){
                throw new NotAllowedException("Forneça um ano válido. Por exemplo: 2020");
            }

        }
        return course;
    }
}
