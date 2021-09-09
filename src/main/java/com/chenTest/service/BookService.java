package com.chenTest.service;

import com.chenTest.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

@Service
public class BookService {

    //@Autowired(required = false)
    //@Qualifier("bookDao")

    //@Resource
    @Inject


    // 默认按照属性名称装配
    // 把IOC容器中的对象装配到对象中
    private BookDao bookDao;

    public void print() {
        System.out.println(bookDao);
    }

    @Override
    public String toString() {
        return "BookService{" +
                "bookDao=" + bookDao +
                '}';
    }
}
