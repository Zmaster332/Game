package engine;

import db.GameSql;

public class Animation {

    private GameSql sql = new GameSql();
    ArrayList<Texture> tex = new ArrayList<Texture>();

    public def hero(){

        //Получаем изображение из базы
        def paramDate = ['id': 1];
        def paramsImg =  [ 'name': 'hero' ];
        ArrayList<String> returnParam  = new ArrayList<String>();
        returnParam.add('body');

        def imgHeroParam = sql.function('getImageParam',paramsImg )
        byte[] imgHero = sql.getDataFromTable('images', paramDate, returnParam).(returnParam[0]);

        tex.add(new Texture(imgHero, 96, 96, 0, 192) );
        tex.add(new Texture(imgHero, 96, 96, 0, 96) );
        tex.add(new Texture(imgHero, 96, 96, 0, 0) );
        tex.add(new Texture(imgHero, 96, 96, 0, 288) );

        return tex;

    }
}
