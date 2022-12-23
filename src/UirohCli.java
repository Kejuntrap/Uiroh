import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class UirohCli {
    public static void main(String[] args){
        final int port = 12222; // ポート番号
        final String endMessage = "-kill";    // エンドメッセージ
        String host = "localhost";    // ホスト情報

        System.out.print("hostname?:>");
        Scanner sc = new Scanner(System.in);
        host = sc.next();       // ホスト情報を入力

        try (Socket socket = new Socket(host, port);
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(System.in))) { // エンドメッセージを入れるまで続投
            while (true) {
                String input = inputBuffer.readLine();
                if (input.equals(endMessage)) {
                    printWriter.write("エンドメッセージが送信されました");
                    break;
                }else{
                    printWriter.println(input); // サーバー側に渡す
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
