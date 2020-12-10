package pers.regulus.cloud.common.util;

import lombok.extern.slf4j.Slf4j;
import pers.regulus.cloud.common.constant.CommonConstants;

import java.io.*;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Http Util
 *
 * @author Regulus
 */
@Slf4j
public class HttpUtils {

    /**
     * 向指定 URL 发送 GET 请求
     *
     * @param url    请求 URL
     * @param params 请求参数，格式：key1=value1&key2=value2
     * @return 响应结果
     */
    public static String sendGet(String url, String params) {
        return sendGet(url, params, CommonConstants.UTF8);
    }

    /**
     * 向指定 URL 发送 GET 请求
     *
     * @param url         请求 URL
     * @param params      请求参数，格式：key1=value1&key2=value2
     * @param contentType 编码类型
     * @return
     */
    public static String sendGet(String url, String params, String contentType) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;

        try {
            String urlStr = url + "?" + params;
            log.info("HttpUtils.sendGet - {}", urlStr);

            URL realUrl = new URL(urlStr);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.connect();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), contentType));

            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("HttpUtils.sendGet.Recv - {}", result);
        } catch (ConnectException e) {
            log.error("HttpUtils.sendGet ConnectException, url = " + url + ",params = " + params, e);
        } catch (SocketTimeoutException e) {
            log.error("HttpUtils.sendGet SocketTimeoutException, url = " + url + ",params = " + params, e);
        } catch (IOException e) {
            log.error("HttpUtils.sendGet IOException, url = " + url + ",params = " + params, e);
        } catch (Exception e) {
            log.error("HttpUtils.sendGet Exception, url = " + url + ",params = " + params, e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                log.error("HttpUtils.sendGet: in.close Exception, url = " + url + ",params = " + params, e);
            }
        }

        return result.toString();
    }

    /**
     * 向指定 URL 发送 POST 请求
     *
     * @param url    请求 URL
     * @param params 请求参数
     * @return
     */
    public static String sendPost(String url, String params) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            String urlStr = url;
            log.info("HttpUtils.sendPost - {}" + urlStr);

            URL realUrl = new URL(urlStr);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new PrintWriter(conn.getOutputStream());
            out.print(params);
            out.flush();

            InputStream in1;
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), CommonConstants.UTF8));

            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("HttpUtils.sendPost.Recv - {}", result);
        } catch (ConnectException e) {
            log.error("HttpUtils.sendPost ConnectException, url = " + url + ",params = " + params, e);
        } catch (SocketTimeoutException e) {
            log.error("HttpUtils.sendPost SocketTimeoutException, url = " + url + ",params = " + params, e);
        } catch (IOException e) {
            log.error("HttpUtils.sendPost IOException, url = " + url + ",params = " + params, e);
        } catch (Exception e) {
            log.error("HttpUtils.sendPost Exception, url = " + url + ",params = " + params, e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }

                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                log.error("HttpUtils.sendPost: in.close Exception, url = " + url + ",params = " + params, e);
            }
        }

        return result.toString();
    }

}
