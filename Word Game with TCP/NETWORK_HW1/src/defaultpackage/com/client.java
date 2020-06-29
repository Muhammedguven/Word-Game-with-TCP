package defaultpackage.com;


import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class client {
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String RESET = "\033[0m";
    public static void main(String[] args) throws IOException {
        Connect();

    }

    public static void Connect() throws UnknownHostException, IOException {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String serverdanGelen;
        String clientGiden;
        ArrayList<String> words = new ArrayList<String>();
        try {
            socket = new Socket("localhost", 7755);
        } catch (Exception e) {
            System.out.println("Port Hatası!");
        }
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println(BLUE+"HER KELİMEYE KARŞI CEVAP VERMEK İÇİN 15 SANİYENİZ OLACAK. BU SÜRE İÇİNDE CEVAP VERMEZSENİZ DOĞRU CEVAP VERSENİZ BİLE OYUNU KAYBEDECEKSİNİZ!"+RESET);
        System.out.println(BLUE+"LÜTFEN BÜYÜK HARF KULLANMAYINIZ"+RESET);
        System.out.println("--------OYUN BAŞLADI--------");
        System.out.println("Server'a gönderilecek ilk kelimeyi giriniz:");

        BufferedReader data = new BufferedReader(new InputStreamReader(System.in));
        for (int i=0; i<1; ){
        clientGiden = data.readLine();
            while (clientGiden.isEmpty() || clientGiden.contains(" ")){
                System.out.println("Boş dizin gönderemezsiniz.");
                clientGiden = data.readLine();
            }
        out.println(clientGiden);
        words.add(clientGiden);
            i++;
        }
        System.out.println("Lütfen serverın kelime göndermesini bekleyiniz...");

        while((serverdanGelen = in.readLine()) != null) {
            String lastTwoChar;
            String firstTwoChar;
            words.add(serverdanGelen);
            if (serverdanGelen.equals("Server yanlış kelime girdi.OYUN BİTTİ. KAZANDINIZ :)") || serverdanGelen.equals("Server zamanında kelime yazamadı.YOU WIN!")){
                System.out.println(serverdanGelen);
                System.exit(0);
            }
            System.out.println(BLUE+"Server'dan gelen kelime = " + serverdanGelen+RESET);
            System.out.println("Server'a gönderilecek kelimeyi giriniz: ");
            long startTime = System.currentTimeMillis();
            String clientGiden2 = data.readLine();
            long endTime = System.currentTimeMillis();
            long estimatedTime = endTime - startTime;
            double seconds = (double)estimatedTime/1000;
            if (seconds>15.0){
                System.out.println( seconds+"sn de kelimeyi girdiniz.YOU LOSE GAME OVER");
                out.println("Client zamanında kelime yazamadı.YOU WIN!");
                System.exit(0);
            }
            while (clientGiden2.isEmpty() || clientGiden2.contains(" ")){
                System.out.println("Boş dizin gönderemezsiniz.");
                clientGiden2 = data.readLine();
            }

            while(words.contains(clientGiden2)) {
                System.out.println("BU KELİME DAHA ÖNCE KULLANILDI!");
                System.out.println("LÜTFEN BAŞKA BİR KELİME GİRİNİZ:");
                clientGiden2 = data.readLine();
            }
            lastTwoChar = serverdanGelen.substring(serverdanGelen.length() - 2);
            firstTwoChar = clientGiden2.substring(0, 2);

            if (firstTwoChar.equals(lastTwoChar)) {
                System.out.println(PURPLE+seconds+"sn. Doğru bir kelime girdiniz.Server bekleniyor..."+RESET);
                out.println(clientGiden2);
                words.add(clientGiden2);

            } else {
                System.out.println("Yanlış kelime gönderdiniz.OYUN BİTTİ. KAYBETTİNİZ :(");
                out.println("Client yanlış kelime girdi.OYUN BİTTİ. KAZANDINIZ :)");
                System.exit(0);
            }
        }


        out.close();
        in.close();
        data.close();
        socket.close();
    }
}