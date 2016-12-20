package watchdog;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	final static int[] PORT = {9989, 7777};	// 待受ポート番号
	
	static String cameraA = "172.16.107.3";
	static String cameraB = "172.16.107.2";
	static int point=0;

	public static void main(String[] args) {
		
		byte[] buffer         = new byte[512]; // ファイル受信時のバッファ
		while(true){
			// Server Start
			System.out.println("Server Start...");
			
				// ソケットオブジェクトの作成
				SocketObject so = new SocketObject(PORT[0], cameraA, cameraB);
				// ソケットスタート
				so.start();
				
				// クライアントのアドレスを調べカメラ情報を付加する
				int camInf;
				String camName;
				String clientAdd = so.socket.getInetAddress().getHostAddress();
				if(clientAdd.equals(cameraA)){
					camInf = 0; // ０→カメラA
					camName = "A";
				}else if(clientAdd.equals(cameraB)){
					camInf = 1; // １→カメラB
					camName = "B";
				}else{		//　例外
					camInf = 3;
					camName = "E";
				}
				
				// 画像を受信した日時を画像名に設定
				SetDate date = new SetDate();
				String picName = camName +"_"+ date.str + ".bmp";
				String outputFilepath = "/home/user/CamPic/"+ picName;       // 受信したファイルの保存先
				
				// 画像の受信
				so.SocketBmp(outputFilepath);
				// ソケット終了
				so.close();
				System.out.println("画像を受信");
				
				// ソケットオブジェクトの作成
				SocketObject so1 = new SocketObject(PORT[1], cameraA, cameraB);
				// ソケットスタート
				so1.start();
				//　画像撮影ポイントの取得
				point = so1.SocketPoint();
				//　ソケット終了
				so1.close();
				System.out.println("撮影ポイントを取得 : point = "+ point);
				
				// データベースオブジェクトの作成
				DBAccess dbAccess = new DBAccess(camInf, date.Today, date.Time, picName, point);
				//　データベースに挿入
				dbAccess.DBnextID();
				dbAccess.DBinsert();
				
		}
	}
}
