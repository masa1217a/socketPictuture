package watchdog;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  final static int[] PORT = {7777, 8888}; // 待受ポート番号


  public static void main(String[] args) {
	// ソケットオブジェクトの作成
	  SocketObject soc1 = new SocketObject(PORT[0]);
	  for(int i=0;i<2; i++){
	    try{
			byte[] buffer         = new byte[512]; // ファイル受信時のバッファ
			
			    // Server Start
			    System.out.println("Server Start...");
				
			     // ソケットスタート
			    soc1.start();
			    
			    new Srv(soc1).start();
			    if(soc1.serverSocket != null){
	    			soc1.serverSocket.close();
	    		}
			   
		    
	    }catch (Exception e) {
	        System.out.println("IOException!");
	        e.printStackTrace();
	      }
	    finally{
	    	try{
	    		if(soc1.serverSocket != null){
	    			soc1.serverSocket.close();
	    		}
	    		
	    	}catch(Exception e){
	    		System.out.println(e.getMessage());
	    	}
	    }
	  }
}
}

class Srv extends Thread{
    private SocketObject soc;

    int i=0;
    
    static String cameraA = "172.16.107.2";
    static String cameraB = "172.16.107.3";
    static String picName = "Error";
    static String camName="E";
    
    static int point=0;
    
    static int camInf;
    
    static String clientAdd;
    
    
    SetDate date = new SetDate();

    public Srv(SocketObject sct){
        soc=sct;
        clientAdd = soc.socket.getInetAddress().getHostAddress();
        System.out.println("Thread is Generated.  Connect to " +
            clientAdd);
        System.out.println("PORT : " + soc.PORT+ " : " + i);
        i++;
    }

    public void run(){
    	soc.SocketPoint();
    	// クライアントのアドレスを調べカメラ情報を付加する
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
		picName = camName +"_"+ date.str + ".bmp";
		String outputFilepath = "/home/user/CamPic/"+ picName;       // 受信したファイルの保存先
		
		// 画像の受信
		soc.SocketBmp(outputFilepath);
		
		
		System.out.println("画像を受信");
		
		if(camName.equals("B") || camName.equals("E")){
			
			//　画像撮影ポイントの取得
			soc.SocketPoint();
    	}
		if(camName.equals("A")){
			// データベースオブジェクトの作成]
		      DBAccess dbAccess = new DBAccess(camInf, date.Today, date.Time, picName, point);
		       //　データベースに挿入
		      dbAccess.DBnextID();
		      dbAccess.DBinsert();
		}
		// ソケット終了
		soc.close();
/*
		else if(camName.equals("B") || camName.equals("E")){
				
				//　画像撮影ポイントの取得
				soc.SocketPoint();
				//　ソケット終了
				//soc.closeChar();
				//System.out.println("撮影ポイントを取得 : point = "+ point);
				// データベースオブジェクトの作成]
			      DBAccess dbAccess = new DBAccess(camInf, date.Today, date.Time, picName, point);
			       //　データベースに挿入
			      dbAccess.DBnextID();
			      System.out.println(camInf);
			      System.out.println(date.Today);
			      System.out.println(date.Time);
			      System.out.println(picName);
			      dbAccess.DBinsert();
	}*/
				
   }
        
}

