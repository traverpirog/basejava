package com.javaops.webapp;

import com.javaops.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume();
        System.out.println(resume.getClass());
        Field field = resume.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(resume));
        field.set(resume, "asdkljasdfklasjdfklj");
        System.out.println(field.get(resume));
        System.out.println(Resume.class.getMethod("toString").invoke(new Resume()));
    }
}
