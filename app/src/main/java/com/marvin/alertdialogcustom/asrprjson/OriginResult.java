package com.marvin.alertdialogcustom.asrprjson;

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
    private String corpus_no;
    private String err_no;
    private String sn;
    private Result result;

    public Result getResult() {
        return result;
    }

    public String getCorpus_no() {
        return corpus_no;
    }

    public String getErr_no() {
        return err_no;
    }

    public String getSn() {
        return sn;
    }
}
