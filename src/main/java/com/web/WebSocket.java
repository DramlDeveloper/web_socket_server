package com.web;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class WebSocket {

    public static void main(String[] args) {
       try(ServerSocket serverSocket = new ServerSocket(8080)) {
           System.out.println("start");


           while (true){
               // ожидаем подключения
               Socket socket = serverSocket.accept();
               System.out.println("client connect");

               // для подключившегося клиента открываем
               // потоки чтения и записи
               try(BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                   PrintWriter output = new PrintWriter(socket.getOutputStream())) {

                   // ждем первой строки запроса
                   while (!input.ready());

                   // считаем и печатаем все что было отправлено клиентом
                   System.out.println();
                   while (input.ready()){
                       System.out.println(input.readLine());
                   }

                   // отправляем ответ
                   output.println("HTTP/1.1 200 OK");
                   output.println("Content-Type: text/html; charset=utf-8");
                   output.println();
                   output.println("<p>Привет всем!</p>");
                   output.flush();

                   // по окончанию выполнения блока try-with-resources потоки,
                   // а вместе с ними и соединение будет закрыты
                   System.out.println("Client disconnected");
                   socket.close();
               }
           }
       } catch (IOException e) {
           throw new RuntimeException(e);
       }


    }



}
