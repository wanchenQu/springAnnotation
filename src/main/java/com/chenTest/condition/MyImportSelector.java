package com.chenTest.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {

    // 返回值就是导入到容器中的组件的全类名
    // AnnotationMetadata当前标注@Import注解的类的所有注解信息
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        return new String[]{"com.chenTest.bean.Blue", "com.chenTest.bean.Yellow"};
    }

}
