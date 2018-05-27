package com.lee.tac.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * program: tac-root
 * description: 丢弃 stdout 打印 stderr 如果不将buffer中的信息输出 会导致buffer满了  线程阻塞
 * author: zhao lee
 * create: 2018-04-27 15:52
 **/
public class ProcessUtil {
    public final static void executeProcessByWait(String command, String processDir){
        try {
            final Process p = Runtime.getRuntime().exec(command,null,new File(processDir));
            new Thread(new Runnable() {

                @Override
                public void run() {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(p.getInputStream()));
                    try {
                        while (br.readLine() != null) {}
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            BufferedReader br = null;
            br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            p.waitFor();
            br.close();
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public final static void executeProcess(String command, String processDir){
        try {
            final Process p = Runtime.getRuntime().exec(command,null,new File(processDir));
            new Thread(new Runnable() {

                @Override
                public void run() {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(p.getInputStream()));
                    try {
                        while (br.readLine() != null) {}
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            BufferedReader br = null;
            br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        // String result = getUrlAsString("http://www.gewara.com/");
        // System.out.println(result);
        executeProcess("mvn clean compile","/root/TAC_Server/executor/repo");
    }

}
