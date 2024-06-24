package com.voidmain.servlets;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItem;

import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestParser {

    public static Map<Object, List<String>> parseMultiPartRequest(HttpServletRequest request, Object obj) {
        Map<Object, List<String>> map = new HashMap<>();

        if (ServletFileUpload.isMultipartContent((javax.servlet.http.HttpServletRequest) request)) {
            try {
                List<String> files = new ArrayList<>();

                for (FileItem item : new ServletFileUpload(new DiskFileItemFactory()).parseRequest((javax.servlet.http.HttpServletRequest) request)) {
                    if (!item.isFormField()) {
                        String fileName = item.getName();
                        File uploadedFile = new File(request.getServletContext().getRealPath("/") + "uploads/" + fileName);
                        item.write(uploadedFile);
                        files.add(fileName);
                    } else {
                        String fieldName = item.getFieldName();
                        String fieldValue = item.getString();

                        Method[] methods = obj.getClass().getDeclaredMethods();

                        for (Method method : methods) {
                            if (method.getName().equalsIgnoreCase("set" + fieldName)) {
                                try {
                                    method.setAccessible(true);
                                    String type = method.getParameterTypes()[0].getName();

                                    if (type.equals("java.lang.String")) {
                                        method.invoke(obj, fieldValue);
                                    } else if (type.equals("int")) {
                                        method.invoke(obj, Integer.parseInt(fieldValue));
                                    } else if (type.equals("float")) {
                                        method.invoke(obj, Float.parseFloat(fieldValue));
                                    } else if (type.equals("long")) {
                                        method.invoke(obj, Long.parseLong(fieldValue));
                                    } else if (type.equals("double")) {
                                        method.invoke(obj, Double.parseDouble(fieldValue));
                                    } else if (type.equals("boolean")) {
                                        method.invoke(obj, Boolean.parseBoolean(fieldValue));
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }

                map.put(obj, files);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static Object parseRequest(HttpServletRequest request, Object obj) {
        Enumeration<String> enumeration = request.getParameterNames();

        while (enumeration.hasMoreElements()) {
            String parameterName = enumeration.nextElement();

            Method[] methods = obj.getClass().getDeclaredMethods();

            for (Method method : methods) {
                if (method.getName().equalsIgnoreCase("set" + parameterName)) {
                    try {
                        method.setAccessible(true);
                        String type = method.getParameterTypes()[0].getName();

                        if (type.equals("java.lang.String")) {
                            method.invoke(obj, request.getParameter(parameterName));
                        } else if (type.equals("int")) {
                            method.invoke(obj, Integer.parseInt(request.getParameter(parameterName)));
                        } else if (type.equals("float")) {
                            method.invoke(obj, Float.parseFloat(request.getParameter(parameterName)));
                        } else if (type.equals("long")) {
                            method.invoke(obj, Long.parseLong(request.getParameter(parameterName)));
                        } else if (type.equals("double")) {
                            method.invoke(obj, Double.parseDouble(request.getParameter(parameterName)));
                        } else if (type.equals("boolean")) {
                            method.invoke(obj, Boolean.parseBoolean(request.getParameter(parameterName)));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return obj;
    }
}


