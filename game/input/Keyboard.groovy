package input

import engine.EngineWindow
import org.lwjgl.glfw.GLFW

public class Keyboard {

    //Массив булеанов
    private static boolean [] keys = new boolean[GLFW.GLFW_KEY_LAST];

    //Проверка нажатой клавиши
    public static boolean keyDown(int keyId){
        return GLFW.glfwGetKey(EngineWindow.getWindow().id, keyId) == 1;
    }

    public static boolean keyPressed(int keyId){
        return keyDown(keyId) && !keys[keyId];
    }

    public static boolean keyReleased(int keyId){
        return !keyDown(keyId) && keys[keyId];
    }

    //Метод удерживает ввод с клавиатуры
    public static void handleKeyboardInput(){

        for(int i = 0; i < GLFW.GLFW_KEY_LAST; i++){
            keys[i] = keyDown(i);
        }

    }

}
