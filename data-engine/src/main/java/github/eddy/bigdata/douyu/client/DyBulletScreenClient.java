package github.eddy.bigdata.douyu.client;

import github.eddy.bigdata.douyu.crawler.AbstractMessageHandler;
import github.eddy.bigdata.douyu.protocol.DyMessage;
import github.eddy.bigdata.douyu.protocol.MsgView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import static java.time.Instant.now;

/**
 * @version V1.0
 * @Summary: 弹幕客户端类
 * @author: FerroD
 * @date: 2016-3-12
 */

/**
 * @author edliao
 * @apiNote 多线程改造 ,删除了单例代码
 * @apiNote 增加回调函数注入
 */
@Slf4j
public class DyBulletScreenClient {
    /**
     * 第三方弹幕协议服务器地址
     */
    private static final String HOST_NAME = "openbarrage.douyutv.com";

    /**
     * 第三方弹幕协议服务器端口
     */
    private static final int PORT = 8601;

    /**
     * 设置字节获取buffer的最大值
     */
    private static final int MAX_BUFFER_LENGTH = 4096;

    /**
     * socket相关配置
     */
    private Socket sock;
    private BufferedOutputStream bos;
    private BufferedInputStream bis;

    @Getter
    private int roomId;
    @Getter
    private int groupId;

    private AbstractMessageHandler messageHandler;

    /**
     * 获取弹幕线程及心跳线程运行和停止标记
     */
    private boolean readyFlag = false;

    /**
     * 快速构造运行
     */
    public DyBulletScreenClient(AbstractMessageHandler messageHandler, int roomId, int groupId) {
        this.messageHandler = messageHandler;
        this.roomId = roomId;
        this.groupId = groupId;
        init();
    }

    /**
     * 客户端初始化，连接弹幕服务器并登陆房间及弹幕池
     */
    private void init() {
        //连接弹幕服务器
        this.connectServer();
        //登陆指定房间
        this.loginRoom(roomId);
        //加入指定的弹幕池
        this.joinGroup(roomId, groupId);
        //设置客户端就绪标记为就绪状态
        readyFlag = true;
    }

    /**
     * 获取弹幕客户端就绪标记
     */
    public boolean getReadyFlag() {
        return readyFlag;
    }

    /**
     * 连接弹幕服务器
     */
    private void connectServer() {
        try {
            //获取弹幕服务器访问host
            String host = InetAddress.getByName(HOST_NAME).getHostAddress();
            //建立socket连接
            sock = new Socket(host, PORT);
            //设置socket输入及输出
            bos = new BufferedOutputStream(sock.getOutputStream());
            bis = new BufferedInputStream(sock.getInputStream());
        } catch (Exception e) {
            log.error("", e);
        }
        log.debug("Server Connect Successfully!");
    }

    /**
     * 登录指定房间
     */
    private void loginRoom(int roomId) {
        //获取弹幕服务器登陆请求数据包
        byte[] loginRequestData = DyMessage.getLoginRequestData(roomId);

        try {
            //发送登陆请求数据包给弹幕服务器
            bos.write(loginRequestData, 0, loginRequestData.length);
            bos.flush();

            //初始化弹幕服务器返回值读取包大小
            byte[] recvByte = new byte[MAX_BUFFER_LENGTH];
            //获取弹幕服务器返回值
            bis.read(recvByte, 0, recvByte.length);

            //解析服务器返回的登录信息
            if (DyMessage.parseLoginRespond(recvByte)) {
                log.debug("Receive login response successfully!");
            } else {
                log.error("Receive login response failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加入弹幕分组池
     */
    private void joinGroup(int roomId, int groupId) {
        //获取弹幕服务器加弹幕池请求数据包
        byte[] joinGroupRequest = DyMessage.getJoinGroupRequest(roomId, groupId);

        try {
            //想弹幕服务器发送加入弹幕池请求数据
            bos.write(joinGroupRequest, 0, joinGroupRequest.length);
            bos.flush();
            log.debug("Send join group request successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Send join group request failed!");
        }
    }

    /**
     * 服务器心跳连接
     */
    public void keepAlive() {
        //获取与弹幕服务器保持心跳的请求数据包
        byte[] keepAliveRequest = DyMessage.getKeepAliveData((int) (System.currentTimeMillis() / 1000));
        try {
            //向弹幕服务器发送心跳请求数据包
            bos.write(keepAliveRequest, 0, keepAliveRequest.length);
            bos.flush();
            log.debug("Send keep alive request successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Send keep alive request failed!");
        }
    }

    /**
     * 获取服务器返回信息
     */
    public void getServerMsg() {
        Instant start = now();

        //初始化获取弹幕服务器返回信息包大小
        byte[] recvByte = new byte[MAX_BUFFER_LENGTH];
        //定义服务器返回信息的字符串
        String dataStr;
        try {
            //读取服务器返回信息，并获取返回信息的整体字节长度
            int recvLen = bis.read(recvByte, 0, recvByte.length);

            log.debug("当前消息读取时间: {} ms", Duration.between(start, now()).toMillis());

            //根据实际获取的字节数初始化返回信息内容长度
            byte[] realBuf = new byte[recvLen];
            //按照实际获取的字节长度读取返回信息
            System.arraycopy(recvByte, 0, realBuf, 0, recvLen);
            //根据TCP协议获取返回信息中的字符串信息
            dataStr = new String(realBuf, 12, realBuf.length - 12);

            //循环处理socekt黏包情况
            while (dataStr.lastIndexOf("type@=") > 5) {
                //对黏包中最后一个数据包进行解析
                MsgView msgView = new MsgView(StringUtils.substring(dataStr, dataStr.lastIndexOf("type@=")));
                //分析该包的数据类型，以及根据需要进行业务操作
                parseServerMsg(msgView.getMessageList());
                //处理黏包中的剩余部分
                dataStr = StringUtils.substring(dataStr, 0, dataStr.lastIndexOf("type@=") - 12);
            }
            //对单一数据包进行解析
            MsgView msgView = new MsgView(StringUtils.substring(dataStr, dataStr.lastIndexOf("type@=")));
            //分析该包的数据类型，以及根据需要进行业务操作
            parseServerMsg(msgView.getMessageList());
        } catch (Exception e) {
            log.error("", e);
        }

        log.info("当前消息处理时间: {} ms", Duration.between(start, now()).toMillis());
    }

    /**
     * 解析从服务器接受的协议，并根据需要订制业务需求
     */
    private void parseServerMsg(Map<String, Object> msg) {
        MessageWrapper messageWrapper = MessageWrapper.wrapper(msg);

        if (messageWrapper.getType() != null) {
            //服务器反馈错误信息
            if (messageWrapper.getType().equals("error")) {
                log.debug(messageWrapper.toString());
                //结束心跳和获取弹幕线程
                this.readyFlag = false;
            }
            msg.put("roomId", roomId);
            messageHandler.next(messageWrapper);
        }
    }
}
