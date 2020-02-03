package engine

import input.Mouse
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWVidMode
import org.lwjgl.opengl.GL
import org.lwjgl.system.MemoryStack
import org.lwjgl.opengl.GL11

import java.nio.IntBuffer

class EngineWindow {

    private int width;  //Ширина окна
    private int height; //Высота окна

    //Буферы разрешения окна
    public IntBuffer bufferdWidth;
    public IntBuffer bufferdHeight;

    private GLFWVidMode videoMode;   //Видеорежим
    private String title;   //Заголовок окна
    public long id; //ИД окна

    public static EngineWindow instance;    //Статическая инстанция

    EngineWindow(int width, int height, String title) {
        instance = this
        this.width = width;
        this.height = height;
        this.title = title;

    }

    //Создает окно
    public void create(){
        //Проверка установки библиотеки
        if(!GLFW.glfwInit()){
            System.err.println('GLFW не инициализированна!');
            System.exit(-1);

        }
        //Создаем окно
        this.id = GLFW.glfwCreateWindow(this.width, this.height, this.title, 0,0);

        //Проверка существования окна
        if(this.id == 0){
            System.err.println('Окно не создано!');
            System.exit(-1);

        }

        //Стак для следующего фрейма
        try(MemoryStack mem = MemoryStack.stackPush()){
            //Упаковка в буфера размеров окна
            this.bufferdWidth = BufferUtils.createIntBuffer(1);
            this.bufferdHeight = BufferUtils.createIntBuffer(1);
            GLFW.glfwGetWindowSize(this.id, this.bufferdWidth,this.bufferdHeight);


        }catch(Exception e){

        }

        //Остальные параметры окна
        GLFW.glfwSetWindowTitle(this.id, this.title);
        GLFW.glfwSetWindowSize(this.id, this.width, this.height);
        GLFW.glfwSetWindowAspectRatio(this.id, this.width, this.height);

        //Позиция окна
        videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        GLFW.glfwSetWindowPos(this.id,
                ((this.videoMode.width() -  this.bufferdWidth.get(0))/2) as int,
                ((this.videoMode.height() - this.bufferdHeight.get(0))/2) as int);
        GLFW.glfwSetWindowSizeLimits(this.id, this.width, this.height, 1600, 900);

        //Привязка контекста к текущему окну
        GLFW.glfwMakeContextCurrent(this.id);
        GL.createCapabilities();

        //Зона отображения
        GL11.glViewport(0, 0, this.bufferdWidth.get(), this.bufferdHeight.get())

        Mouse.setMouseCallbacks(this.id);


    }

    public static EngineWindow getWindow(){
        return instance;
    }

    //Обновление окна
    public void update(){
        GLFW.glfwPollEvents();
        GLFW.glfwSwapBuffers(this.id);

    }
    //Уничтожение окна
    public void destroy(){
        GLFW.glfwDestroyWindow(this.id);

    }

    //Поверка на закрытие окна через крестик
    public boolean isCloseRequest(){
        return GLFW.glfwWindowShouldClose(this.id);
    }

    //Геттеры и Сеттеры
    int getWidth() {
        return width
    }

    void setWidth(int width) {
        this.width = width
    }

    int getHeight() {
        return height
    }

    void setHeight(int height) {
        this.height = height
    }

    String getTitle() {
        return title
    }

    void setTitle(String title) {
        this.title = title
    }
}

