package org.kodluyoruz;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    public static int firtPlayerPoint = 0;
    public static int secondPlayerPoint = 0;

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("SOS Oynamak için Hazır mısını? ");
        System.out.println("Lütfen Panel Boytunu Seçin.. 3/7 ?");
        int boyut = scan.nextInt();
        char panel[][] = new char[boyut][boyut];

        startPanel(panel, boyut);
        tableScor(firtPlayerPoint, secondPlayerPoint);

        System.out.println("Oyun Başlıyor...?");
        Random random = new Random();
        int oyuncu = random.nextInt(2) + 0;
        System.out.println(oyuncu + 1 + ". Oyuncu ile Başlıyor");
        boolean status = true;
        if (oyuncu + 1 == 1) {
            do {
                firstPlayer(panel, boyut);
                status = panelIsFull(panel, boyut);
                if (!status) break;
                secondPlayer(panel, boyut);
                status = panelIsFull(panel, boyut);
            } while (status);
            System.out.println("Oyun Bitti");
        }
        else {
            do {
                secondPlayer(panel, boyut);
                status = panelIsFull(panel, boyut);
                if (!status) break;
                firstPlayer(panel, boyut);
                status = panelIsFull(panel, boyut);
            } while (status);
            System.out.println("Oyun Bitti");
        }

        if(secondPlayerPoint>firtPlayerPoint){
            System.out.println("Tebrikler Kazandınız !");
        }
        else if(firtPlayerPoint>secondPlayerPoint){
            System.out.print("Kaybettiniz...");
        }
        else{
            System.out.print("Skorlar Eşit... ");
        }


    }


    static void startPanel(char panel[][], int boyut) {
        for (int i = 0; i < boyut; i++) {
            for (int j = 0; j < boyut; j++) {
                panel[i][j] = 'x';
            }
        }
        printPanel(panel, boyut);
    }

    static void printPanel(char panel[][], int boyut) {
        for (int i = 0; i < boyut; i++) {
            for (int j = 0; j < boyut; j++) {
                System.out.print("      " + panel[i][j]);
            }
            System.out.print("\n");
        }
    }


    static void tableScor(int firstPlayer, int secondPlayer) {

        System.out.println(" ___________________________________");
        System.out.println("|          Puan Tablosu             |");
        System.out.println("|   1.Oyuncu : " + firstPlayer + "                    |");
        System.out.println("|   2.Oyuncu : " + secondPlayer + "                    |");
        System.out.println("|___________________________________|");

    }

    static void firstPlayer(char panel[][], int boyut) {
        boolean status;
        Random random = new Random();
        do {
            int satir;
            int sutun;
            do {
                satir = random.nextInt(3);
                sutun = random.nextInt(3);
            } while (panel[satir][sutun] != 'x');

            int randomHarf = random.nextInt(2);
            if (randomHarf == 0) {
                panel[satir][sutun] = 'S';
            } else {
                panel[satir][sutun] = 'O';
            }
            System.out.println("1. Oyuncu");
            printPanel(panel, boyut);

            int addPoint = puan(panel, boyut, satir, sutun, panel[satir][sutun]);
          //  System.out.println("eklenen" +addPoint);
          //  System.out.println("eski puan: " +Main.firtPlayerPoint);
            Main.firtPlayerPoint += addPoint;
            status = addPoint >0  && panelIsFull(panel, boyut)!= true? true : false;

        } while (status);
        tableScor(Main.firtPlayerPoint, Main.secondPlayerPoint);
    }

    static void secondPlayer(char panel[][], int boyut) {
        boolean status;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Girmek istediğiniz kutunun satır ve sütun numarasını giriniz:");
            int satir = scan.nextInt();
            int sutun = scan.nextInt();

            if (panel[satir][sutun] != 'x') {
                System.out.println("Lütfen Başka bir kutu seçin");
                do {
                    System.out.println("Girmek istediğiniz kutunun satır ve sütun numarasını giriniz:");
                    satir = scan.nextInt();
                    sutun = scan.nextInt();
                } while (panel[satir][sutun] != 'x');
            }

            System.out.print("Harfi giriniz: S için 83 /O için 79 : ");
            int harf = scan.nextInt();
            panel[satir][sutun] = (char) harf;

            System.out.println("2. Oyuncu");
            printPanel(panel, boyut);

            int addPoint = puan(panel, boyut, satir, sutun, panel[satir][sutun]);
            //System.out.println("eklenen" +addPoint);
           // System.out.println("eski puan: " +Main.secondPlayerPoint);
            Main.secondPlayerPoint += addPoint;

            status = addPoint > 0 && panelIsFull(panel, boyut)== true ? true : false;

        } while (status );

        tableScor(Main.firtPlayerPoint, Main.secondPlayerPoint);
    }

    static boolean panelIsFull(char panel[][], int boyut) {
        for (int i = 0; i < boyut; i++) {
            for (int j = 0; j < boyut; j++) {
                if (panel[i][j] == 'x') {
                    return true;
                }
            }
        }
        return false;
    }


    static int puan(char panel[][], int boyut, int satir, int sutun, char harf) {
        int puan = 0;

        if (harf == 'S') {

            if(satir == 0 && sutun == 2){
                //02 01 00
                if(panel[satir][sutun-1] =='O' && panel[satir][sutun-2]=='S'){
                    puan++;
                }
                //02 12 22
                if(panel[satir+1][sutun]== 'O' && panel[satir+2][sutun] == 'S'){
                    puan++;
                }

                //02 11 20
                if(panel[satir+1][sutun-1]== 'O' && panel[satir+2][sutun-2] == 'S'){
                    puan++;
                }
            }

            //20 21 22
            if (satir == 2 && sutun=='0') {
                if (panel[satir][sutun + 1] == 'O' && panel[satir][sutun + 2] == 'S') {
                    puan++;
                }
               //20 10 00
               if(panel[satir-1][sutun] == 'O' && panel[satir-2][sutun]== 'S'){
                   puan++;
               }
            }

            if(satir == 1 && sutun== 0){
                    //10 11 12
                    if(panel[satir][sutun+1]== 'O' && panel[satir][sutun+2] == 'S'){
                        puan++;
                    }
            }

            if (sutun== 2 && satir ==2) {
                if (panel[satir - 1][sutun - 1] == 'O' && panel[satir - 2][sutun - 2] == 'S') {
                    puan++;
                }
                if (panel[satir - 1][sutun] == 'O' && panel[satir - 2][sutun] == 'S') {
                    puan++;
                }
                if (panel[satir][sutun - 1] == 'O' && panel[satir][sutun - 2] == 'S') {
                    puan++;
                }
            }

            return puan;
        }
        else {
            //10 00 20
            if ( satir==1 && sutun==0 ) {
                if (panel[satir-1][sutun] == 'S' && panel[satir+1][sutun] == 'S') {
                    puan++;
                }
            }
            // 01 00 02
            if(satir== 0 && sutun==1){
                if (panel[satir][sutun - 1] == 'S' && panel[satir][sutun + 1] == 'S') {
                    puan++;
                }
            }

            //12 02 22
            if( satir==1 && sutun== 2) {
                if (panel[satir - 1][sutun] == 'S' && panel[satir + 1][sutun] == 'S') {
                    puan++;
                }
            }

            // 21 20 22
            if (satir== 2 && sutun==1){
                if (panel[satir][sutun - 1] == 'S' && panel[satir][sutun+1] == 'S') {
                    puan++;
                }
            }

            if(satir==1 && sutun== 1 ) {
                //11 01 21
                if (panel[satir - 1][sutun] == 'S' && panel[satir + 1][sutun] == 'S') {
                    puan++;
                }
                //11 10  12
                if (panel[sutun][satir - 1] == 'S' && panel[sutun][satir + 1] == 'S') {
                    puan++;
                }

                //11 02 20
                if(panel[satir-1][sutun+1] == 'S' && panel[satir+1][sutun-1]== 'S'){
                    puan++;
                }
                //11 00 22
                if (panel[satir-1][sutun-1]== 'S' && panel[satir+1][sutun+1] == 'S') {
                    puan++;
                }
            }
            return puan;
        }
    }

}
