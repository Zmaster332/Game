package db

/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;*/
import groovy.sql.*

class GameSql {

    private def retValues = [];
    private Sql sql;

    GameSql(){
        String path=this.getClass().getResource("game.db").getPath();
        sql = Sql.newInstance("jdbc:sqlite:"+path, "org.sqlite.JDBC")
    }

    //Метод возвращает набор данных из таблицы, согласно запрашиваемымм параметрам
    public def getDataFromTable(String table, def param, ArrayList<String> ret){

        //Строка с кодом запроса
        String strSelect = ' select ';
        String strFrom = ' from ';
        String strWhere = ' where ';
        String strSql = strSelect;
        //Разбиваем, что возвращаем
        ArrayList<String> retVal = new ArrayList<String>();
        for(int i = 0; i<ret.size(); i++){
            if(i == 0){
                strSql = strSql  + ret[i] + ' ';
            }else{
                strSql = strSql + ' , ' + ret[i] + ' ';
            }
        }
        strSql = strSql + strFrom + table + strWhere;

        //Разбиваем параметры на массивы, чтобы создать запрос
        for(int i = 0; i<param.size(); i++){
            if(i == 0){
                strSql = strSql +  param.take(i+1).keySet().getAt(0) + ' = ' +  param.take(i+1).values().getAt(0);
            }else{
                strSql = strSql + ' and ' +  param.take(i+1).keySet().getAt(0) + ' = ' +  param.take(i+1).values().getAt(0);
            }
        }

        try{
            //Выполняем запрос и получаем набор данных
            def row = sql.rows(strSql);

            //Собираем, что идет на возврат
            if(row !=null){
                for (int i = 0; i<row[0].size(); i++){
                    retValues.add(row.take(i+1).getAt(0));
                }
            }else{
                print('Запрос ничего не нашел!');
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally {
            try {

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return retValues.getAt(0);
    }

    //Выполняем псевдохранимую функцию
    public def function(String name, def paramIn){

        def rowsSql = sql.firstRow('select  body, params from scripts where upper(name) like upper(:name)', ["name" : name]);

        String script = rowsSql.body;
        String param = rowsSql.params;

        //def params = ['name': 'hero'];
        //params.put(1,paramIn.)
        //params = ['name' : 'hero']
        /*for(int i = 0; i<(param.split(",").length); i++){
            params = params.put(param.split(",")[i], 1)
        }*/

        //params = param.split(",");

        //Object[] params = param.split(",");
        //params = param + paramIn;

        def rows = sql.rows(script, paramIn);



        println(1);








    }

    //Выполняем псевдохранимую процедуру
    void procedure(String sctipt){



    }

    //Получаем набор данных, согласно хранимому запросу
    def select(String script){

    }

}
