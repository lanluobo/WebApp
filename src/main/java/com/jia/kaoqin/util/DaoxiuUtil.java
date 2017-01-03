package com.jia.kaoqin.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jia.common.util.HttpClientUtil;
import com.jia.kaoqin.po.DaoxiuEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


@SuppressWarnings("deprecation")
public class DaoxiuUtil {

    public static final String QINGJIAURL = "https://oa.channelsoft.com/seeyon/collaboration/collaboration.do?method=newColl&templateId=7698672957046214057";
    public static final String LOGINURL = "https://oa.channelsoft.com/seeyon/main.do?method=login";
    public static final String LOGINERROR = "登录失败...";
    public static final String LOGINSUCCESS = "登录成功...";
    public static final String FAILMSG = "提交倒休失败";
    public static final String SUCCESSMSG = "倒休提交成功...";
    public static final String REPALSUCCESS = "撤销成功";
    public static final String REPALFAIL = "撤销失败:已经结束,不允许撤销";
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static Logger logger = Logger.getLogger(DaoxiuUtil.class);

    public static String getParams(DaoxiuEntity entity)
            throws UnsupportedEncodingException {

        return "_json_params=%7B%22_currentDiv%22%3A%7B%22_currentDiv%22%3A%220%22%7D%2C%22mainbodyDataDiv_0%22%3A%7B%22id%22%3A%22-1%22%2C%22createId%22%3A%220%22%2C%22createDate%22%3A%222014-09-23+17%3A19%3A06.0%22%2C%22modifyId%22%3A%22-8759983201933441557%22%2C%22modifyDate%22%3A%222016-04-08+15%3A39%3A35.0%22%2C%22moduleType%22%3A%221%22%2C%22moduleId%22%3A%22"
                + entity.moduleId
                + "%22%2C%22contentType%22%3A%2220%22%2C%22moduleTemplateId%22%3A%22-8966704838840793234%22%2C%22contentTemplateId%22%3A%227823586773149304210%22%2C%22sort%22%3A%221%22%2C%22title%22%3A%22%E8%AF%B7%E5%81%87%E7%94%B3%E8%AF%B7%E5%8D%95%EF%BC%88%E9%9D%92%E7%89%9B%E5%8C%97%E4%BA%AC%EF%BC%89%7B%E5%A7%93%E5%90%8D%7D%7B%E5%A4%A9%E6%95%B0%E5%90%88%E8%AE%A1%7D%E5%A4%A9%7B%E9%9A%B6%E5%B1%9E%E9%83%A8%E9%97%A8%7D%7B%E6%B5%81%E6%B0%B4%E5%8F%B7%7D%22%2C%22content%22%3A%22%22%2C%22rightId%22%3A%22-5108736762399011756%22%2C%22status%22%3A%22STATUS_RESPONSE_VIEW%22%2C%22viewState%22%3A%221%22%2C%22contentDataId%22%3A%22"
                + entity.dataId
                + "%22%7D%2C%22formmain_0021%22%3A%7B%22field0008%22%3A%22%22%2C%22field0008_0_editAtt%22%3A%22true%22%7D%2C%22formson_0022%22%3A%5B%7B%22id%22%3A%22"
                + entity.id
                + "%22%2C%22field0010%22%3A%22-7690567483763964362%22%2C%22field0010_txt%22%3A%22%E5%80%92%E4%BC%91%22%2C%22field0011%22%3A%22"
                + URLEncoder.encode(entity.startTime)
                + "%22%2C%22field0012%22%3A%22"
                + URLEncoder.encode(entity.endTime)
                + "%22%2C%22field0013%22%3A%220.1%22%2C%22field0014%22%3A%22"
                + URLEncoder.encode(entity.reason, "utf-8")
                + new Scanner(DaoxiuUtil.class.getClassLoader().getResourceAsStream("params.txt")).nextLine()
                + entity.moduleId
                + "%22%2C%22parentSummaryId%22%3A%22%22%2C%22tId%22%3A%227698672957046214057%22%2C%22curTemId%22%3A%227698672957046214057%22%2C%22resentTime%22%3A%22%22%2C%22archiveId%22%3A%224123442059241015585%22%2C%22prevArchiveId%22%3A%224123442059241015585%22%2C%22currentNodesInfo%22%3A%22%22%2C%22tembodyType%22%3A%2220%22%2C%22formtitle%22%3A%22%E8%AF%B7%E5%81%87%E7%94%B3%E8%AF%B7%E5%8D%95%EF%BC%88%E9%9D%92%E7%89%9B%E5%8C%97%E4%BA%AC%EF%BC%89%22%2C%22saveAsTempleteSubject%22%3A%22%22%2C%22phaseId%22%3A%22%22%2C%22caseId%22%3A%22%22%2C%22currentaffairId%22%3A%22%22%2C%22createDate%22%3A%222016-05-03+08%3A52%3A53%22%2C%22useForSaveTemplate%22%3A%22no%22%2C%22oldProcessId%22%3A%22%22%2C%22temCanSupervise%22%3A%22true%22%2C%22standardDuration%22%3A%224320%22%2C%22forwardMember%22%3A%22%22%2C%22saveAsFlag%22%3A%22%22%2C%22transtoColl%22%3A%220%22%2C%22bzmenuId%22%3A%22%22%2C%22newflowType%22%3A%22%22%2C%22contentViewState%22%3A%221%22%2C%22isOpenWindow%22%3A%22true%22%2C%22canTrackWorkFlow%22%3A%22%22%2C%22subject%22%3A%22%E8%AF%B7%E5%81%87%E7%94%B3%E8%AF%B7%E5%8D%95%EF%BC%88%E9%9D%92%E7%89%9B%E5%8C%97%E4%BA%AC%EF%BC%89%7B%E5%A7%93%E5%90%8D%7D%7B%E5%A4%A9%E6%95%B0%E5%90%88%E8%AE%A1%7D%E5%A4%A9%7B%E9%9A%B6%E5%B1%9E%E9%83%A8%E9%97%A8%7D%7B%E6%B5%81%E6%B0%B4%E5%8F%B7%7D%22%2C%22importantLevel%22%3A%221%22%2C%22projectIdSelect%22%3A%22%22%2C%22projectId%22%3A%22%22%2C%22isTemplateHasPigeonholePath%22%3A%22true%22%2C%22colPigeonhole%22%3A%223%22%2C%22process_info%22%3A%22%E4%BA%A7%E5%93%81%E5%88%9B%E6%96%B0%E9%83%A8%E5%8A%A9%E7%90%86%28%E7%9F%A5%E4%BC%9A%29%E3%80%81%E4%BA%A7%E5%93%81%E8%90%A5%E9%94%80%E9%83%A8%E6%80%BB%E7%BB%8F%E7%90%86%28%E5%AE%A1%E6%89%B9%29%E3%80%81%E7%A9%BA%E8%8A%82%E7%82%B9%E3%80%81%E4%BA%BA%E5%8A%9B%E8%B5%84%E6%BA%90%E9%83%A8%E6%80%BB%E7%9B%91%28%E5%AE%A1%E6%89%B9%29%E3%80%81%E4%BA%BA%E5%8A%9B%E8%B5%84%E6%BA%90%E5%8A%A9%E7%90%86%28%E7%9F%A5%E4%BC%9A%29%E3%80%81CEO%28%E5%AE%A1%E6%89%B9%29%E3%80%81%E7%94%B0%E7%A3%8A%28%E5%AE%A1%E6%89%B9%29%E3%80%81%E4%BA%A7%E5%93%81%E8%90%A5%E9%94%80%E9%83%A8%E6%80%BB%E7%BB%8F%E7%90%86%28%E5%AE%A1%E6%89%B9%29%E3%80%81%E7%A9%BA%E8%8A%82%E7%82%B9%E3%80%81%E4%BA%BA%E5%8A%9B%E8%B5%84%E6%BA%90%E9%83%A8%E6%80%BB%E7%9B%91%28%E5%AE%A1%E6%89%B9%29%22%2C%22canTrack%22%3A%221%22%2C%22radioall%22%3A%220%22%2C%22radiopart%22%3Anull%2C%22zdgzry%22%3A%22%22%2C%22deadLineselect%22%3A%220%22%2C%22deadLine%22%3A%220%22%2C%22deadLineDateTime%22%3A%22%22%2C%22deadLineDateTimeHidden%22%3A%22%22%2C%22canForward%22%3A%221%22%2C%22canModify%22%3A%221%22%2C%22canEdit%22%3Anull%2C%22canEditAttachment%22%3Anull%2C%22canArchive%22%3A%221%22%2C%22unCancelledVisor%22%3A%22-8759983201933441557%22%2C%22supervisorIds%22%3A%22-8759983201933441557%22%2C%22detailId%22%3A%22%22%2C%22supervisorNames%22%3A%22%E6%9D%8E%E8%B4%BA%22%2C%22advanceRemind%22%3A%220%22%2C%22awakeDate%22%3A%222016-09-06+09%3A00%22%2C%22canAutostopflow%22%3Anull%2C%22title%22%3A%22%22%7D%2C%22comment_deal%22%3A%7B%22id%22%3A%22%22%2C%22pid%22%3A%220%22%2C%22clevel%22%3A%221%22%2C%22path%22%3A%2200%22%2C%22moduleType%22%3A%221%22%2C%22moduleId%22%3A%22"
                + entity.moduleId
                + "%22%2C%22extAtt1%22%3A%22%22%2C%22relateInfo%22%3A%22%22%2C%22ctype%22%3A%22-1%22%2C%22content_coll%22%3A%22%22%7D%2C%22attachmentInputs%22%3A%5B%5D%7D";
    }

    /* 获取倒休页面动态相关参数 */
    public static void setEntity(HttpClient client, DaoxiuEntity entity) throws Exception {
        String result = null;
        try {
            result = HttpClientUtil.getDataByGet(client, QINGJIAURL, null);

            Pattern p = Pattern.compile("\"id\" value='.*'");
            Matcher m = p.matcher(result);
            if (m.find()) {
                entity.moduleId = result.substring(m.start() + 12, m.end() - 1);
            }
            p = Pattern.compile("DataId\" value='.*'");
            m = p.matcher(result);

            if (m.find()) {
                entity.dataId = result.substring(m.start() + 15, m.end() - 1);
            }
            p = Pattern.compile("recordid=\".*\" path");
            m = p.matcher(result);
            if (m.find()) {
                entity.id = result.substring(m.start() + 10, m.end() - 6);
            }
        } catch (Exception e) {
            logger.error(e);
            throw new Exception("倒休失败，获取oa官网参数异常");
        }
    }

    public static boolean login(HttpClient client, String username, String password) throws Exception {
        try {
            HttpPost post = HttpClientUtil.getHttpPost(DaoxiuUtil.LOGINURL);
            post.setEntity(new StringEntity("login_username=" + username
                    + "&login_password=" + password
                    + "&login.smsVerifyCode=&bodyWidth=1353&bodyHeight=593"));
            HttpResponse resp = client.execute(post);
            String url = resp.getFirstHeader("Location").getValue();
            EntityUtils.consume(resp.getEntity());
            if (url.equals("https://oa.channelsoft.com/seeyon/indexOpenWindow.jsp")) {
                logger.debug(username + DaoxiuUtil.LOGINSUCCESS);
                return true;
            }

        } catch (Exception e) {
            throw new Exception("oa登录异常", e);
        }
        return false;
    }

    public static void calculate(HttpClient client, DaoxiuEntity entity) throws Exception {
        HttpPost post = new HttpPost(
                "https://oa.channelsoft.com/seeyon/form/formData.do?method=calculate&"
                        + "formMasterId="
                        + entity.dataId
                        + "&formId=7823586773149304210&tableName=&fieldName=field0013&recordId="
                        + entity.id
                        + "&rightId=-5108736762399011756&moduleId=-1&tag=1462072714076");
        post.addHeader("Content-Type", "application/x-www-form-urlencoded");
        try {
            post.setEntity(new StringEntity(
                    "_json_params={\"formmain_0021\":{\"field0008\":\"\",\"field0008_0_editAtt\":\"true\"},"
                            + "\"formson_0022\":[{\"id\":\"-2975331646700437085\",\"field0010\":\"-7690567483763964362\","
                            + "\"field0010_txt\":\"倒休\",\"field0011\":\"2012-04-04 09:00\",\"field0012\":\"2016-04-04 10:00\",\"field0013\":\"0.1\",\"field0014\":\"\"}],\"attachmentInputs\":[]}"));
            HttpResponse resp = null;
            resp = client.execute(post);
            String result = EntityUtils.toString(resp.getEntity(), "utf-8");
            if (result.contains("success\":\"true")) {
                logger.debug(entity.username + "倒休时间校验正确..");
            }
        } catch (Exception e) {
            logger.error(e);
            throw new Exception("oa官网校验倒休时间异常");
        }
    }

    public static void doSomeThing(HttpClient client, DaoxiuEntity entity) throws Exception {

        String result = null;
        HttpPost post = HttpClientUtil.getHttpPost("https://oa.channelsoft.com/seeyon/ajax.do?method=ajaxAction&managerName=WFAjax&rnd=57193");
        try {
            post.setEntity(new StringEntity(
                    "managerMethod=transBeforeInvokeWorkFlow&arguments=[{\"appName\":\"collaboration\","
                            + "\"processXml\":123,\"processId\":\"-5052523580086073740\",\"caseId\":\"-1\","
                            + "\"currentActivityId\":\"-1\",\"currentWorkitemId\":\"-1\",\"currentUserId\":"
                            + "\"2413986904539155110\",\"currentAccountId\":\"670869647114347\",\"formData\":\""
                            + entity.dataId
                            + "\",\"mastrid\":\""
                            + entity.dataId
                            + "\",\"debugMode\":false,\"processTemplateId\":\"-5052523580086073740\"},{\"allNotSelectNodes\":[],\"allSelectNodes\":[],\"allSelectInformNodes\":[],\"pop\":false,\"token\":\"\",\"last\":\"false\",\"alreadyChecked\":\"false\"}]"));
            HttpResponse resp = null;

            resp = client.execute(post);

            result = EntityUtils.toString(resp.getEntity(), "utf-8");
            if (result.contains("canSubmit\":\"true")) {
                logger.debug(entity.username + "可以倒休...");
            }


            post = new HttpPost(
                    "https://oa.channelsoft.com/seeyon/ajax.do?method=ajaxAction&managerName=collaborationFormBindEventListener&rnd=63784");
            post.addHeader("Content-Type", "application/x-www-form-urlencoded");
            post.setEntity(new StringEntity(
                    "managerMethod=achieveTaskType&arguments=[\"" + entity.dataId
                            + "\",\"start\",\"-5108736762399011756\"]"));
            resp = client.execute(post);
            result = EntityUtils.toString(resp.getEntity(), "utf-8");
        } catch (IOException e) {
            logger.error(e);
            throw new Exception("倒休失败，oa官网请求异常");
        }
    }

    /**
     * 倒休提交最后一步
     */
    public static boolean submit(HttpClient client, DaoxiuEntity entity) throws Exception {
        try {


            HttpPost post = HttpClientUtil.getHttpPost("https://oa.channelsoft.com/seeyon/collaboration/collaboration.do?method=send&from=");
            String params = DaoxiuUtil.getParams(entity);
            post.setEntity(new StringEntity(params));
            HttpResponse resp = client.execute(post);
            String result = EntityUtils.toString(resp.getEntity(), "utf-8");
            if (result.contains("collaboration.do?method=listSent")) {

                return true;
            }
        } catch (Exception e) {
            logger.error(e);
            throw new Exception("倒休提交异常，oa官网查看详情");
        }
        return false;
    }

    public static void setSummaryIdAndAffairId(HttpClient client, DaoxiuEntity entity) throws Exception {

        try {


            HttpPost post = HttpClientUtil.getHttpPost("https://oa.channelsoft.com/seeyon/ajax.do?method=ajaxAction&managerName=colManager&rnd=62552");
            post.setEntity(new StringEntity("managerMethod=getSentList&arguments=" +
                    "[{\"page\":1,\"size\":1},{}]"));
            HttpResponse resp = client.execute(post);
            String result = EntityUtils.toString(resp.getEntity());
            Pattern p = Pattern.compile("affairId\":\".{3}\\d+\"");
            Matcher m = p.matcher(result);
            if (m.find()) {
                entity.affairId = result.substring(m.start() + 11, m.end() - 1);

            }
            p = Pattern.compile("summaryId\":\".{3}\\d+\"");
            m = p.matcher(result);
            if (m.find()) {
                entity.summaryId = result.substring(m.start() + 12, m.end() - 1);
            }
            p = Pattern.compile("createDate\":\"\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}\"");
            m = p.matcher(result);
            if (m.find()) {
                entity.createTime = result.substring(m.start() + 13, m.end() - 1);
            }
            p = Pattern.compile("subject\":\".*qjcc\\d+\"");
            m = p.matcher(result);
            if (m.find()) {
                entity.subject = result.substring(m.start() + 21, m.end() - 1);
            }
            logger.debug("成功获取:affairId=" + entity.affairId
                    + ",summaryId=" + entity.summaryId + ",createTime=" + entity.createTime);
            logger.debug("成功获取:subject=" + entity.subject);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception("倒休提交成功，参数入库失败，撤销请去官网");
        }
    }


    public static void getSummaryIdAndAffairId(DaoxiuEntity entity) {

        DBUtil.selectRecord(entity);
    }

    /**
     * 撤销倒休单
     */
    public static boolean repalSubmit(HttpClient client, DaoxiuEntity entity) throws Exception {
        // TODO Auto-generated method stub
        HttpPost post = HttpClientUtil.getHttpPost("https://oa.channelsoft.com/seeyon/ajax.do?method=ajaxAction&managerName=colManager&rnd=16458");
        post.setEntity(new StringEntity("managerMethod=transRepal&arguments=[{\"repealComment\":\"" + URLEncoder.encode("及时赶到！", "utf-8") + "\",\"summaryId\":\"" + entity.summaryId + "\",\"affairId\":\"" + entity.affairId + "\",\"trackWorkflowType\":\"0\"}]"));
        HttpResponse resp = client.execute(post);
        String result = EntityUtils.toString(resp.getEntity());
        if (result.equals("\"\"")) {
            logger.debug(entity.username + DaoxiuUtil.REPALSUCCESS);
            return true;
        } else if (result.contains("已经结束,不允许撤销")) {
            logger.debug(entity.username + DaoxiuUtil.REPALFAIL);
        } else {
            logger.debug("其他撤销错误...");
            throw new Exception("其他撤销错误...");
        }
        return false;

    }

    public static String getSystemTime() {
        return format.format(new Date()) + " ";
    }


    public static void setStartEndTime(DaoxiuEntity entity) {
        Date d = new Date();
        d.setHours(9);
        d.setMinutes(0);
        d.setSeconds(0);

        Date t = new Date();
        if (t.after(d)) {
            d.setDate(d.getDate() + 1);
            entity.startTime = format.format(d);
            d.setHours(d.getHours() + 1);
            entity.endTime = format.format(d);

        } else {
            entity.startTime = format.format(d);
            d.setHours(d.getHours() + 1);
            entity.endTime = format.format(d);
        }

        logger.debug(entity.username + "设置倒休时间: " + entity.startTime +
                "___" + entity.endTime);
    }

}
