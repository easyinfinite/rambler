
import org.beetl.sql.core.DefaultNameConversion;
import org.beetl.sql.core.annotatoin.Table;
import org.beetl.sql.core.kit.StringKit;

public class ReDbNameConversion extends DefaultNameConversion {
    @Override
    public String getTableName(Class<?> c) {
        //遵循BeetlSQL规范，@Table拥有最高优先级
        Table table = (Table)c.getAnnotation(Table.class);
        if(table!=null){
            return table.name();
        }
        //UserRole -> user_role
        String tableName = StringKit.enCodeUnderlined(c.getSimpleName());
        //user_role -> bt_user_role
        return "t_"+tableName;
    }

    @Override
    public  String getClassName(String tableName){
        //假定所有表都是以bt_开头
        //bt_user_role -> user_role
        tableName = tableName.substring(tableName.indexOf("_")+1);
        //user_role -> userRole
        String clsName = StringKit.deCodeUnderlined(tableName);
        //userRole -> UserRole
        return StringKit.toUpperCaseFirstOne(clsName);
    }

    @Override
    public String getPropertyName(Class<?> c, String attrName) {
        return StringKit.deCodeUnderlined(attrName);
    }

}