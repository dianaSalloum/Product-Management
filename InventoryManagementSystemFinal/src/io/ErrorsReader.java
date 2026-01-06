package io;

import java.util.Scanner;


public class ErrorsReader {

    final static private String path = "error.txt";

    public  static void errorReader(){
        try(Scanner sc = new Scanner(path)){
            while((sc.hasNext()) ){
                sc.useDelimiter("/////");
                System.out.println(sc.next());
            }

        }catch(Exception e){
            //ignore
        }
    }

}

