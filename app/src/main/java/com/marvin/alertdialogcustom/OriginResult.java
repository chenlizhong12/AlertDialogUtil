package com.marvin.alertdialogcustom;

/**
 * ┏┓　   ┏┓
 * ┏┛┻━━━━━┛┻━┓
 * ┃　　　　   ┃
 * ┃　━　━　   ┃
 * ████━████   ┃
 * ┃　　　　   ┃
 * ┃　 ┻　    ┃
 * ┗━┓      ┏━┛
 * 　┃      ┃
 * 　┃ 0BUG ┗━━━┓
 * 　┃0Error     ┣┓
 * 　┃0Warning   ┏┛
 * 　┗┓┓┏━┳┓┏┛ ━
 * 　　┃┫┫ ┃┫┫
 * 　　┗┻┛ ┗┻┛
 * Created by clz on 2019/8/6
 */
public class OriginResult {
    private String error;
    private String sub_error;
    private String sn;
    private String desc;

    public String getSn() {
        return sn;
    }

    public String getDesc() {
        return desc;
    }

    public String getError() {
        return error;
    }

    public String getSub_error() {
        return sub_error;
    }
}
