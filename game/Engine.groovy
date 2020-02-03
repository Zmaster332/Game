import engine.EngineWindow
import input.Keyboard
import engine.EngineWindow
import input.Mouse
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30
import org.lwjgl.system.MemoryUtil

import java.nio.FloatBuffer

class Engine {

    //Размеры создаваемого окна
    public static  final int WIDTH = 1200;
    public static final int HEIGHT = 675;
    public static final String TITLE = 'Engine 0.0.1 pre-alpha';
    public EngineWindow engineWindow;



   public void run(){
       this.init();
   }
    //Логика движка
    public void init() {
        this.engineWindow = new EngineWindow(WIDTH, HEIGHT, TITLE);
        this.engineWindow.create();
        this.update();
    }

    public FloatBuffer storeDateInFloatBuffer(float [] date){
        FloatBuffer buffer = MemoryUtil.memAllocFloat(date.length); //Позволяет управлять памятью с объектами
        buffer.put(date);
        buffer.flip();

        return buffer;
    }

    //Обновление
    public void update(){

        //Координаты
        float [] v_position = [
            0.0f, 0.5f, 0f,
            -0.5f, -0.5f, 0f,
            0.5f, -0.5f, 0f,
        ];

        //Создадим Vao - набор координат объектов
        int vaoId = GL30.glGenVertexArrays();

        GL30.glBindVertexArray(vaoId);    //Связать объекты

        //Создадим Vbo - набор координат объектов
        int vboId = GL30.glGenBuffers();
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboId); //Указываем, что буфер вершин
        FloatBuffer buffer = this.storeDateInFloatBuffer(v_position);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW)   //Как данные будут использоваться
        GL30.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0 );    //1 - ид атрибута, 2 - количество координат у системы (2,3), 3-тип данных

        MemoryUtil.memFree(buffer); //Очистка буфера
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboId);    //Развязать буфер, чтобы оставить
        GL30.glBindVertexArray(vaoId)    //Развязать объекты, чтобы оставить



        while(!this.engineWindow.isCloseRequest()){

            Keyboard.handleKeyboardInput();
            Mouse.handleMouseInput();

            //Очистка экрана перед рендером изображения зеленым
            GL11.glClearColor(0,1,0, 1);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            //Отрисовка
            GL30.glBindVertexArray(vaoId);
            GL30.glEnableVertexAttribArray(0)

            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, v_position.length/3 as int)  //Рисовка треугольниками, начало рисовки, количество позиций


            GL30.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(vaoId);


            this.engineWindow.update();
            //рендеринг

        }
        this.engineWindow.destroy();

    }

    public EngineWindow getEngineWindow(){
        return this.engineWindow;
    }

}
