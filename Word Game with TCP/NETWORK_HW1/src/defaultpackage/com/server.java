package defaultpackage.com;

        import java.io.*;
        import java.net.*;
        import java.util.ArrayList;

public class server {
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String RED_BRIGHT = "\033[0;91m";
    public static final String RESET = "\033[0m";
    public static void main(String[] args) throws IOException {

        String clientGelen;
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        ArrayList<String> wordsMirror = new ArrayList<String>();
        try {
            serverSocket = new ServerSocket(7755);
        } catch (Exception e) {
            System.out.println("Port Hatası!");
        }
        try {
            System.out.println("SERVER BAĞLANTI İÇİN HAZIR CLIENT BEKLENİYOR...");
            System.out.println(BLUE+"HER KELİMEYE KARŞI CEVAP VERMEK İÇİN 15 SANİYENİZ OLACAK. BU SÜRE İÇİNDE CEVAP VERMEZSENİZ DOĞRU CEVAP VERSENİZ BİLE OYUNU KAYBEDECEKSİNİZ!"+RESET);
            System.out.println(BLUE+"LÜTFEN BÜYÜK HARF KULLANMAYINIZ"+RESET);
            clientSocket = serverSocket.accept();

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader data = new BufferedReader(new InputStreamReader(System.in));

            while ((clientGelen = in.readLine()) != null) {
                String lastTwoChar;
                String firstTwoChar;
                wordsMirror.add(clientGelen);
                if (clientGelen.equals("Client yanlış kelime girdi.OYUN BİTTİ. KAZANDINIZ :)")  || clientGelen.equals("Client zamanında kelime yazamadı.YOU WIN!")){
                    System.out.println(clientGelen);
                    break;
                }
                /**************************************/
                System.out.println(BLUE+"Client'dan gelen kelime = " + clientGelen+RESET);
                System.out.println("Client'a gönderilecek kelimeyi giriniz :");
                long startTime = System.currentTimeMillis();
                String serverGiden = data.readLine();
                long endTime = System.currentTimeMillis();
                long estimatedTime = endTime - startTime;
                double seconds = (double)estimatedTime/1000;
                if (seconds>15.0){
                    System.out.println( seconds+"sn de kelimeyi girdiniz.YOU LOSE GAME OVER");
                    out.println("Server zamanında kelime yazamadı.YOU WIN!");
                    System.exit(0);
                }
                while (serverGiden.isEmpty() || serverGiden.contains(" ")){
                    System.out.println("Boş dizin gönderemezsiniz.");
                    serverGiden = data.readLine();
                }

                while(wordsMirror.contains(serverGiden)){
                    System.out.println("BU KELİME DAHA ÖNCE KULLANILDI!!!");
                    System.out.println("LÜTFEN BAŞKA BİR KELİME GİRİNİZ:");
                    serverGiden = data.readLine();
                }
                lastTwoChar = clientGelen.substring(clientGelen.length() - 2);
                firstTwoChar = serverGiden.substring(0, 2);
                if (lastTwoChar.equals(firstTwoChar)) {
                    System.out.println(PURPLE+seconds+"sn. Doğru bir kelime girdiniz.Client bekleniyor..."+RESET);
                    out.println(serverGiden);
                    wordsMirror.add(serverGiden);
                } else {
                    System.out.println(RED_BRIGHT+"Yanlış kelime gönderdiniz.OYUN BİTTİ. KAYBETTİNİZ :("+RESET);
                    out.println(RED_BRIGHT+"Server yanlış kelime girdi.OYUN BİTTİ. KAZANDINIZ :)"+RESET);
                    break;
                }
            }

            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
        }catch (NullPointerException e){
            System.out.println("NULL POINTER EXCEPTION!");
        }
    }

}