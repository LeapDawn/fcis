package g11.commons.util;

import g11.commons.config.DataSourceAttr;
import g11.commons.config.SpringUtil;

public class BackupCommand {

    private static String commandPrefix;

    static {
        DataSourceAttr dataSourceAttr = SpringUtil.getBean(DataSourceAttr.class);
        String username = dataSourceAttr.getUsername();
        String password = dataSourceAttr.getPassword();
        String url = dataSourceAttr.getUrl();
        String tmp = url.substring(0, url.indexOf("?"));
        String dbName = tmp.substring(tmp.lastIndexOf("/") + 1);
        String address;
        if (tmp.contains("///")) {
            address = "127.0.0.1";
        } else {
            address = tmp.substring(tmp.indexOf("//") + 2, tmp.lastIndexOf(":"));
        }
        commandPrefix = "mysqldump -u" + username + " -p" + password + " -h" + address
                + " " + dbName + " > ";
    }

    /**
     * 获取备份数据库命令
     * @param filePath 生成备份文件的绝对地址
     * @return
     */
    public static String getBackupCommand(String filePath) {
//        String cd = "cmd.exe /k " + filePath.split(":")[0] + ":";
//        return cd + " && " + commandPrefix + filePath.substring(1);
        return "cmd.exe /c " + commandPrefix + filePath;
    }
}
