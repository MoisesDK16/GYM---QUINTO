package com.gym.generic;

import javax.imageio.stream.FileImageInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.stream.FileImageInputStream;
import java.io.File;
import java.io.IOException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FirstClass {

    public static void main(String[] args) {

        System.out.println("Hola Mundo");

        try {
            FileInputStream fis = new FileInputStream("C:/Users/User/Pictures/5TO/PI/Plantillas/Factura.jpg");
            boolean final_archivo = false;
            List<Integer> bytes = new ArrayList<>();

            while (!final_archivo) {
                int byte_img = fis.read();
                if (byte_img != -1) {
                    bytes.add(byte_img);
                }else{
                    final_archivo = true;
                    System.out.println("Valor en bytes: "+bytes);
                }
            }

        } catch (IOException e) {

        }
    }
}


