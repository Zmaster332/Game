
import db.GameSql
import engine.Animation;
import engine.Texture
import input.Keyboard
import engine.EngineWindow
import input.Mouse
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryUtil

import java.nio.FloatBuffer

class  Engine {

    //Размеры создаваемого окна
    public static  final int WIDTH = 1200;
    public static final int HEIGHT = 960;
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

        //GameSql sql = new GameSql();
        int x = 50;
        int y = 50;

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        def tex =  new Animation().hero();


        //String imgHero = "./images/hero.png";




        //Получаем изображение из базы
        //def param = ['id': 1];
        //ArrayList<String> returnParam  = new ArrayList<String>();
        //returnParam.add('body');

        //byte[] imgHero = sql.getDataFromTable('images', param, returnParam).(returnParam[0]);

        //def params =  [ 'name': 'hero' ];
       // def imgHeroParam = sql.function('getImageParam',params );



        //ArrayList<Texture> texHero = new ArrayList<Texture>();
        //texHero.add(new Texture(imgHero, 96, 96, 0, 192) );
        //texHero.add(new Texture(imgHero, 96, 96, 0, 96) );
        //texHero.add(new Texture(imgHero, 96, 96, 0, 0) );
        //texHero.add(new Texture(imgHero, 96, 96, 0, 288) );

/*
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
*/


        while(!this.engineWindow.isCloseRequest()){

            Keyboard.handleKeyboardInput();
            Mouse.handleMouseInput();




            //Очистка экрана перед рендером изображения зеленым
            GL11.glClearColor(0,0,0, 1);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);


            if(Keyboard.keyDown(GLFW.GLFW_KEY_RIGHT)){
                x < WIDTH ? x++ : 1;
                println('x =' + x);
                tex[0].bind();
                printTexture(96, 96, 1, x, y);
            }else if(Keyboard.keyDown(GLFW.GLFW_KEY_LEFT)){
                x > 0 ? x-- : 1;
                println('x =' + x);
                tex[1].bind();
                printTexture(96, 96, 1, x, y);
            }else if(Keyboard.keyDown(GLFW.GLFW_KEY_DOWN)){
                y < HEIGHT ? y++ : 1;
                println('y =' + y);
                tex[2].bind();
                printTexture(96, 96, 1, x, y);
            }else if(Keyboard.keyDown(GLFW.GLFW_KEY_UP)){
                y > 0 ? y-- : 1;
                println('y =' + y);
                tex[3].bind();
                printTexture(96, 96, 1, x, y);
            }else{
                tex[0].bind();
                printTexture(96, 96, 1, x, y);
            }














            /*//Отрисовка
            GL30.glBindVertexArray(vaoId);
            GL30.glEnableVertexAttribArray(0)

            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, v_position.length/3 as int)  //Рисовка треугольниками, начало рисовки, количество позиций


            GL30.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(vaoId);
*/

            this.engineWindow.update();
            //рендеринг

        }
        this.engineWindow.destroy();

    }

    public EngineWindow getEngineWindow(){
        return this.engineWindow;
    }

    static printTexture(int width, int height, double m, int x, int y){

        //this.WIDTH
        // Масштаб картинки
        double nHeight = height*m;
        //Толщина, при новом масштабе
        double nWidth = width*m;

        //Расчет расположения точек на координатах
        double x00 = scale((x-nWidth/2),0);
        double y00 = scale((y-nHeight/2),1);
        double x10 = scale((x+nWidth/2),0);
        double y10 = scale((y-nHeight/2),1);
        double x11 = scale((x+nWidth/2),0);
        double y11 = scale((y+nHeight/2),1);
        double x01 = scale((x-nWidth/2),0);
        double y01 = scale((y+nHeight/2),1);

        GL11.glBegin(GL11.GL_QUADS)
        GL11.glTexCoord2f(0,0);
        GL11.glVertex2d(x00,y00);
        GL11.glTexCoord2f(1,0);
        GL11.glVertex2d(x10, y10);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex2d(x11,y11);
        GL11.glTexCoord2f(0,1);
        GL11.glVertex2d(x01,y01);
        GL11.glEnd();
    }

    static scale(double x, int WH){

        double glX;
        if (WH == 0){
            x >= WIDTH/2 ? (glX = (x-WIDTH/2)/(WIDTH/2)) : (glX = -(WIDTH/2-x)/(WIDTH/2));
        }else{
            x >= HEIGHT/2 ? (glX = -(x-HEIGHT/2)/(HEIGHT/2)) : (glX = (HEIGHT/2-x)/(HEIGHT/2)) ;
        }
         return glX;
    }

}
