package input

import engine.EngineWindow
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWCursorEnterCallbackI
import org.lwjgl.glfw.GLFWCursorPosCallbackI
import org.lwjgl.system.NativeType

class Mouse {
    //Массив булеанов
    private static boolean [] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    public static float mouseX;
    public static float mouseY;
    public static boolean isEntered;

    //Проверка нажатой клавиши
    public static boolean buttonDown(int keyId){
        return GLFW.glfwGetMouseButton(EngineWindow.getWindow().id, keyId) == 1;
    }

    public static boolean buttonPressed(int keyId){
        return buttonDown(keyId) && !buttons[keyId];
    }

    public static boolean buttonReleased(int keyId){
        return !buttonDown(keyId) &&buttons[keyId];
    }

    //ОТслеживание перемещения мыши
    public static void setMouseCallbacks(long id){
        setCursorPositionCallback(id);
        setCursorEnterCallback(id);
    }

     static void setCursorPositionCallback(long id){
        GLFW.glfwSetCursorPosCallback(id, new GLFWCursorPosCallbackI() {
            @Override
            void invoke(long windowId, double x, double y) {
                mouseX = (float)x;
                mouseY = (float)y;

            }
        });
     }

    static void setCursorEnterCallback(long id){
        GLFW.glfwSetCursorEnterCallback(id, new GLFWCursorEnterCallbackI() {
            @Override
            void invoke( long windowId, boolean entered) {
                isEntered = entered;

            }
        });
    }

    //Метод удерживает ввод с клавиатуры
    public static void handleMouseInput(){

        for(int i = 0; i < GLFW.GLFW_MOUSE_BUTTON_LAST; i++){
            buttons[i] = buttonDown(i);
        }

    }
}
