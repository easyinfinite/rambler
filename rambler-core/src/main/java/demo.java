///**
// * @ClassName:demo
// * @Description //TODO
// * @Author chenyunxuan
// * @Date $ $
// **/
//
//import org.beetl.sql.core.*;
//import org.beetl.sql.core.db.DBStyle;
//import org.beetl.sql.core.db.MySqlStyle;
//import org.beetl.sql.ext.DebugInterceptor;
//
///**
// *@ClassName:demo
// *@Description //TODO
// @Author chenyunxuan
// @Date $ $
// **/
//public class demo {
//    public static void main(String[] args) {
//        ConnectionSource source = ConnectionSourceHelper.getSimple(driver, url, userName, password);
//        DBStyle mysql = new MySqlStyle();
//// sql语句放在classpagth的/sql 目录下
//        SQLLoader loader = new ClasspathLoader("/sql");
//// 数据库命名跟java命名一样，所以采用DefaultNameConversion，还有一个是UnderlinedNameConversion，下划线风格的，
//        UnderlinedNameConversion nc = new  UnderlinedNameConversion();
//// 最后，创建一个SQLManager,DebugInterceptor 不是必须的，但可以通过它查看sql执行情况
//        SQLManager sqlManager = new SQLManager(mysql,loader,source,nc,new Interceptor[]{new DebugInterceptor()});
//
//    }
//}
////