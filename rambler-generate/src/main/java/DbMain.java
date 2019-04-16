import org.beetl.sql.core.*;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.core.db.TableDesc;
import org.beetl.sql.core.kit.StringKit;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.gen.GenConfig;
import org.beetl.sql.ext.gen.MDCodeGen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class DbMain {

    private static final String mysqlDriver="com.mysql.jdbc.Driver";
    private static final String url="jdbc:mysql://localhost:3306/moke?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=false";
    private static final String userName="root";
    private static final String password="root";

    /**
     * 生成代码
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ConnectionSource source = ConnectionSourceHelper
                .getSimple(mysqlDriver, url, userName, password);
        DBStyle mysql = new MySqlStyle();
        //SQL语句放于classpath的sql目录下
        SQLLoader loader = new ClasspathLoader("/develop/rambler/rambler-business/src/main/resources/sql");
        //数据库命名和java命名一样，所以采用DefaultNameConversion,还有一个UnderlinedNameConversion下划线风格的
        ReDbNameConversion nc = new ReDbNameConversion();
        //最后，创建一个SQLManager,DebugInterceptor,不是必须的，但可以通过它查看SQL的执行情况
        SQLManager sqlManager = new SQLManager(mysql, loader, source, nc, new Interceptor[]{new DebugInterceptor()});
////        或者直接生成java文件
        GenConfig config = new GenConfig();
        config.setDisplay(false);
        config.setPreferBigDecimal(true);
        Set<String> tables = sqlManager.getMetaDataManager().allTable();
        for (String table : tables) {
            System.out.printf("%-20s %s\n",table , "生成完毕");
            //默认生成实体类的实现
            String ProjectPath = System.getProperty("user.dir") + File.separator + "rambler-business";
            String packagePath = ProjectPath + File.separator + "src" +File.separator+ "main" + File.separator + "java" + File.separator+"/web/entity";
//            sqlManager.genPojoCode(table, packagePath,"", config);
            //自定义实现
//            genMd(sqlManager, config, table);
            //自定义实现
            genMapper(sqlManager, config, table);
        }
    }

    /**
     * 生成md文件
     */
    public static void genMd(SQLManager sqlManager, GenConfig config, String table) throws IOException {
        String fileName = StringKit.toLowerCaseFirstOne(sqlManager.getNc().getClassName(table));
        if (config.getIgnorePrefix() != null && !config.getIgnorePrefix().trim().equals("")) {
            fileName = fileName.replaceFirst(StringKit.toLowerCaseFirstOne(config.getIgnorePrefix()), "");
            fileName = StringKit.toLowerCaseFirstOne(fileName);
        }
        String target = System.getProperty("user.dir") + "/rambler-business/src/main/resources/sql/"+ fileName + ".md";
        TableDesc desc = sqlManager.getMetaDataManager().getTable(table);
        FileWriter writer = new FileWriter(new File(target));
        MDCodeGen mdCodeGen = new MDCodeGen();
        mdCodeGen.setMapperTemplate(config.getTemplate("/temp/md.btl"));
        mdCodeGen.genCode(sqlManager.getBeetl(), desc, sqlManager.getNc(), null, writer);
        writer.close();
    }

    /**
     * 生成mapper
     */
    public static void genMapper(SQLManager sqlManager, GenConfig config, String table) {
//        MapperCodeGen mapperCodeGen = new MapperCodeGen(System.getProperty("user.dir") + "/rambler-business/src/main/java/me/cyx/web/mapper");
        MapperCodeGen mapperCodeGen = new MapperCodeGen(null);
        mapperCodeGen.setMapperTemplate(config.getTemplate("/temp/mapper.btl"));
        mapperCodeGen.genCode(System.getProperty("user.dir") + "/rambler-business/src/main/java/me/cyx/web/map", sqlManager.getNc().getClassName(table), sqlManager.getMetaDataManager().getTable(table), null, false);
    }

}