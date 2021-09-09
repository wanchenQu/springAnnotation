package com.chenTest.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

// 名字默认是类名首字母小写
@Repository
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDao {
    private String label = "1";
}
