package com.lee.tac.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * program: tac-root
 * description: ${description}
 * author: zhao lee
 * create: 2018-04-27 15:52
 **/
public class ProcessUtil {
    public static void executeProcess(String command, String processDir){
        try {
            Process p = Runtime.getRuntime().exec(command,null,new File(processDir));
            p.waitFor();
            InputStreamReader is = new InputStreamReader(p.getInputStream());
            BufferedReader br = new BufferedReader(is);
            String line = null;
            while((line = br.readLine())!=null){
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
