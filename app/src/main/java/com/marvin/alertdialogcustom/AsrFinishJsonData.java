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
public class AsrFinishJsonData {
    private String error;
    private String sub_error;
    private String desc;
    private OriginResult origin_result;

    public String getError() {
        return error;
    }

    public String getSub_error() {
        return sub_error;
    }

    public String getDesc() {
        return desc;
    }

    public OriginResult getOrigin_result() {
        return origin_result;
    }
}
