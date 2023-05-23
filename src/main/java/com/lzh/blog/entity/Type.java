package com.lzh.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import java.util.ArrayList;
import java.util.List;

/**
 * 类别实体
 */

@Entity
@Table(name = "table_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type {
    @Id
    @GeneratedValue
    private Long id;
    private String name; //类别名


    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();


    @Override
    public String toString() {
        return "this is type";
    }
}
