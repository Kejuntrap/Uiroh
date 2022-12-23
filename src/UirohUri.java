import org.snowink.bouyomichan.BouyomiChan4J;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class UirohUri {     // 読み上げちゃんのサーバープログラム

    public static void main(String[] args) {
        BouyomiChan4J bouyomi = new BouyomiChan4J();    // ぼうよみ
        final long waitTime = 100;    // 待機時間 ms
        final String endMessage = "-kill";    // エンドメッセージ
        final int port = 12222;     // ポート

        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                try {
                    Socket skt = server.accept();
                    System.out.println("Connected!");
                    bouyomi.talk("接続しました");
                    BufferedReader bufReader = null;        // バッファまち
                    try {
                        bufReader = new BufferedReader(new InputStreamReader(skt.getInputStream()));        // ストリームを受け取る
                        String content = "";    // 受け取った文字列を保管
                        bouyomi.talk("ストリームを受信開始しました");
                        System.out.println("Streaming available!");
                        while (true) {
                            content = bufReader.readLine();        // ストリームを受け取るところ
                            if (content.equals(endMessage)) {
                                break; // エンドメッセージならおわり
                            } else {
                                System.out.println("message: "+content);
                                bouyomi.talk(content);    // 棒読みで発言する
                                Thread.sleep(waitTime);        // ちょっと待つ
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    } finally {    // リソース解放
                        bufReader.close();
                        skt.close();
                        server.close();
                        System.gc();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// https://howalunar.hatenablog.com/entry/2012/01/14/215229 参考文献