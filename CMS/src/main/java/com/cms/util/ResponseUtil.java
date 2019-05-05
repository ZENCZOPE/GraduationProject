package com.cms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * Ajax请求响应处理
 *
 * @author linql 创建 2012-2-21
 * @author linql 修改 2013-01-09  ResBo中增加了对异常类型errorType字段，根据errorType进行不同的提示信息<br/>
 * BusinessException提示为提示信息，SystemException提示为错误信息
 * @version 1.0 Copyright(c) 北京神州数码思特奇信息技术股份有限公司
 */
public final class ResponseUtil {

    /**
     * 日志输出
     */
    private static final Log LOG = LogFactory.getLog(ResponseUtil.class);

    /**
     * Ajax响应输出开始信息
     */
    private static final String AJAX_PACKET_STR = "var response = new AJAXPacket();";

    /**
     * Ajax响应输出响应信息
     */
    private static final String AJAX_PACKET_END = "core.ajax.receivePacket(response);";

    /**
     * 编码格式
     */
    private static final String PAGE_ENCODING = "UTF-8";

    /**
     * 构造函数私有化
     */
    private ResponseUtil() {

    }

    /**
     * 把信息通过输出流输出到界面
     *
     * @param response HttpServletResponse对象-Action中通过HttpServletResponse response =
     *                 ServletActionContext.getResponse()获取
     * @param printObj String-要输出到界面的字符串
     */
    public static void printAjax(HttpServletResponse response, Object printObj) {
        response.setContentType("text/html; charset=" + PAGE_ENCODING);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out;
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            out = response.getWriter();
            out.println(objectMapper.writeValueAsString(printObj));
            out.close();
        } catch (IOException e) {
            LOG.error("Error:Cannot create PrintWriter Object !");
        }
    }

    /**
     * 把信息通过输出流输出到界面
     *
     * @param response HttpServletResponse对象-Action中通过HttpServletResponse response =
     *                 ServletActionContext.getResponse()获取
     * @param str      String-要输出到界面的字符串
     */
    public static void print(HttpServletResponse response, String str) {
        response.setContentType("text/html; charset=" + PAGE_ENCODING);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.println(str);
            out.close();
        } catch (IOException e) {
            LOG.error("Error:Cannot create PrintWriter Object !");
        }
    }

    /**
     * 把信息通过输出流输出到界面
     *
     * @param response HttpServletResponse对象-Action中通过HttpServletResponse response =
     *                 ServletActionContext.getResponse()获取
     * @param resBo    要输出到界面的对象
     * @throws java.io.IOException
     */
   /* public static void printAjaxPack(HttpServletResponse response, ResBo resBo) {
        response.setContentType("text/html; charset=" + PAGE_ENCODING);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.println(AJAX_PACKET_STR);
            // 得到resBo的json字符串
            String jsonStr = String.valueOf(JSONObject.fromObject(resBo));
            if (LOG.isDebugEnabled()) {
                LOG.debug("jsonStr = " + jsonStr);
            }
            out.println("response.data=eval(" + jsonStr + ")");
            out.println(AJAX_PACKET_END);
            out.close();
        } catch (IOException e) {
            LOG.warn("输出信息错误", e);
        }
    }

    *//**
     * 把错误信息通过输出流弹出错误提示信息框
     *
     * @param response HttpServletResponse对象
     * @param resBo    要输出到界面的对象，resBo.getErrMsg()获取错误信息
     * @throws java.io.IOException
     *//*
    public static void printAjaxError(HttpServletResponse response, ResBo resBo) {
        response.setContentType("text/html; charset=" + PAGE_ENCODING);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out;
        try {
            out = response.getWriter();
            // out.println(AJAX_PACKET_STR);
            if(ResBo.ERROR_TYPE_SYSTEM == resBo.getErrorType()) {
                out.print("<script>alertMsg.error('" + StringUtil.htmlEscape(resBo.getErrMsg()) + "');</script>");
            } else {
                out.print("<script>alertMsg.info('" + StringUtil.htmlEscape(resBo.getErrMsg()) + "');</script>");
            }
            // out.println(AJAX_PACKET_END);
            out.close();
        } catch (IOException e) {
            LOG.warn("输出信息错误", e);
        }
    }*/

    /**
     *  初始化下载流
     * @param request 请求信息
     * @param response 响应信息
     * @param contextType 文件下载类型
     * @param outputFileName 另存为文件名
     * @param contextLength 文件大小
     */
    public static void initFileDownload(HttpServletRequest request, HttpServletResponse response, String contextType,
                                        String outputFileName, String contextLength) {
        // 重置响应头
        response.reset();
        if (null == contextType) {
            contextType = "application/download";
        }
        response.setContentType(contextType);
        // 获取浏览器类型，格式化显示的文件名称
        String agent = request.getHeader("USER-AGENT");
        try {
            outputFileName = processFileName(outputFileName, agent);
        } catch (IOException e) {
            LOG.warn(outputFileName + "通过processFileName方法转码失败！", e);
        }
        // 设置输出信息
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setHeader("content-disposition", "attachment; filename=" + outputFileName);
        // 设置下载长度信息
        if (null != contextLength) {
            response.setHeader("Content_Length", contextLength);
        }
    }

    /**
     * <p>
     * 文件下载
     * </p>
     *
     * @param response       响应信息
     * @param contextType    附件类型，null时默认为application/download
     * @param outputFileName 导出文件的名称
     * @param downloadFile   需要下载的文件
     * @param agent          浏览器信息
     * @throws Exception 
     */
    public static void downloadFile(HttpServletResponse response, String contextType,
                                    String outputFileName, File downloadFile, String agent) throws Exception {
        // 文件输入流
        FileInputStream fileInputStream = null;
        // 文件输出流那样
        OutputStream outputStream = null;
        // 附件类型
        if (null == contextType) {
            contextType = "application/download";
        }
        try {
            outputStream = response.getOutputStream();
            response.reset();
            response.setContentType(contextType);
            String tempFileName = outputFileName;
           // tempFileName = StringUtil.replace(tempFileName, "+", "%20");
            // 调用私有方法特殊处理火狐浏览器下载文件名问题
            tempFileName = processFileName(tempFileName, agent);
            response.setHeader("content-disposition", "attachment; filename=" + tempFileName);
            byte[] byteArr = new byte[1024];
            long fileLength = downloadFile.length();
            String length1 = String.valueOf(fileLength);
            response.setHeader("Content_Length", length1);
            fileInputStream = new FileInputStream(downloadFile);
            int n = 0;
            while ((n = fileInputStream.read(byteArr)) != -1) {
                outputStream.write(byteArr, 0, n);
            }
            response.flushBuffer();
        } catch (IOException e) {
           e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    LOG.warn("文件关闭错误", e);
                }
            }
        }
    }

    /**
     * <p/>
     * 1、解决火狐下下载Excel模板名称编码问题
     * <p/>
     * <p/>
     * 2、解决IE下如果超过文件名超过 17 个中文字符出现的问题。
     * <p/>
     *
     * @param fileName 文件名称
     * @param agent    浏览器信息
     * @return 文件名
     * @throws java.io.IOException io异常
     * @author sunhao
     */
    private static String processFileName(String fileName, String agent) throws IOException {
        String codedfilename = null;
        // IE下如果超过文件名超过 17 个中文字符可能出现的问题，未经测试暂时注释该解决办法
        // if (null != agent && -1 != agent.indexOf("MSIE")) {
        // String prefix = fileName.lastIndexOf(".") != -1 ? fileName.substring(0,
        // fileName.lastIndexOf(".")) : fileName;
        // String extension = fileName.lastIndexOf(".") != -1 ? fileName.substring(fileName
        // .lastIndexOf(".")) : "";
        // String name = java.net.URLEncoder.encode(fileName, "UTF8");
        // if (name.lastIndexOf("%0A") != -1) {
        // name = name.substring(0, name.length() - 3);
        // }
        // int limit = 150 - 4;
        // if (name.length() > limit) {
        // name = java.net.URLEncoder.encode(
        // prefix.substring(0, Math.min(prefix.length(), limit / 9)), "UTF-8");
        // if (name.lastIndexOf("%0A") != -1) {
        // name = name.substring(0, name.length() - 3);
        // }
        // }
        //
        // codedfilename = name;
        // } else
        if (null != agent && -1 != agent.indexOf("Firefox")) {
            codedfilename = MimeUtility.encodeText(fileName, "UTF8", "B");
        } else {
            codedfilename = java.net.URLEncoder.encode(fileName, "UTF-8");
        }
        return codedfilename;
    }
}
