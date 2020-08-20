package com.example.myloginmvvm.bean;


import java.io.File;



public class MyConstant {
    public static final String report_location_action = "report_location_action";
    public static final String GROU_CHANGE_MEG = "群组改变消息";
    public static final String BUILD_TYPE = "debug";

    //gxw-public final static String WEB_BUY = "http://localhost:8080/pay/vue?tok=tokenId&name=deviceIMEI";
    public final static String WEB_BUY = "http://www.qianniukeji.vip/#/pay?Authorization={Authorization}&deviceIMEI={deviceIMEI}";

    public final static String ROOT = "http://www.ayksos.com/";

     public final static String SERVER_ADDRESS = ROOT + "rescue/";
     public final static String SERVER_ADDRESS_DEBUG = ROOT + "rescue/";


     //public final static String SERVER_ADDRESS = "http://www.ayksos.com/rescue/";
      public final static String LOGIN_ADDRESS =  ROOT+"authorization/"; //现在登录验证与其它接口不在同一个服务器上15002933643gxw-e*/
    //public final static String LOGIN_ADDRESS =  "http://www.ayksos.com/rescue/authorization/"; //gxw+for 域名现在登录验证与其它接口不在同一个服务器上15002933643gxw-e*/



    public final static String IMAGE_ADDRESS = SERVER_ADDRESS+"app/file/downloadFile/";



    /**
     * 注册
     */
    public final static String API_REGISTER = LOGIN_ADDRESS + "guardian/register";
    public final static String API_FORGET = LOGIN_ADDRESS + "guardian/forgetpw";

    /**
     * 登录
     */
    public final static String API_LOGIN = LOGIN_ADDRESS + "guardian/login";
    //public final static String API_LOGIN = SERVER_ADDRESS + "/ReceiveFile/servlet/ReceiveFileServlet";



    //获取佩戴者信息接口
    public final static String API_GET_DEVICE_OWNER_INFO = SERVER_ADDRESS + "app/device/findOldMan";
    //设置佩戴者信息接口
    public final static String API_SET_DEVICE_OWNER_INFO = SERVER_ADDRESS + "app/device/oldManConfig";

    //获取亲情号列表
    public final static String API_GET_EMERGENCY = SERVER_ADDRESS + "app/device/findParentMsg";

    //添加亲情号
    public final static String API_ADD_EMERGENCY = SERVER_ADDRESS + "app/device/parentMsg";

    //删除亲情号
    public final static String API_DEL_EMERGENCY = SERVER_ADDRESS + "app/device/deleteParentMsg";


    public final static String API_SAVE_DEVICE_MANAGE = SERVER_ADDRESS + "app/device/modifyDeviceMobile";


    public final static String API_SET_DEVICE_LOC_INTERVAL = SERVER_ADDRESS + "app/device/intervelConfig";



    //监护人报警
    public final static String API_PRESS_ALARM = SERVER_ADDRESS + "app/device/guardianAlarm";


    /**
     * 退出登录
     */
    public final static String API_LOGIN_OUT = LOGIN_ADDRESS + "guardian/logout";


    //获取围栏列表
    public final static String API_GET_FENCE_LIST = SERVER_ADDRESS + "app/rail/get/deviceIMEI";

    //创建围栏
    public final static String API_CREATE_FENCE = SERVER_ADDRESS + "app/rail/create/deviceIMEI";

    //修改围栏
    public final static String API_UPDATE_FENCE = SERVER_ADDRESS + "app/rail/update/deviceIMEI/railId";

    //删除围栏
    public final static String API_DELETE_FENCE = SERVER_ADDRESS + "app/rail/delete/deviceIMEI/railId";

    //获取设备轨迹
    public final static String API_GET_TRACK = SERVER_ADDRESS + "app/device/locationOrbit";


    /**
     * 我的设备列表
     */
    public final static String API_MY_DEVICES = SERVER_ADDRESS + "app/guardian/device";


    /**
     * 设备报警记录
     */
    public final static String API_DEVICE_RECORD = SERVER_ADDRESS + "app/device/alarmInfo";

    /**
     * 获取验证码
     */
    public final static String API_GET_CHECK_CODE = SERVER_ADDRESS + "app/user/requestSMSCode";

    /**
     * 获取群列表
     */
    public final static String API_TEAM_LIST = SERVER_ADDRESS + "app/team/list";

    /**
     * 获取设备列表
     */
    public final static String API_DEVICE_LIST = SERVER_ADDRESS + "app/team/device";

 /**
  * 获取设备基本信息
  */
 public final static String API_DEVICE_INFO = SERVER_ADDRESS +"app/device/alarm";


    /**
     * 获取某个设备定位信息
     */
    public final static String API_DEVICE_LOCAITON = SERVER_ADDRESS +"app/device/location";


    /**
     * 获取所有报警记录
     */
    public final static String API_RECORDS_ALL = SERVER_ADDRESS +"app/guardian/device/alarmInfo";


    /**
     * 获取设备的位置轨迹
     */
    public final static String API_DEVICE_TRACK = SERVER_ADDRESS +"app/device/deviceIMEI/trail";


     /**
     * 绑定设备
     */
    public final static String API_BIND_DEVICE = SERVER_ADDRESS + "app/bindingdevice/bindingDevice";

    //解绑设备
    public final static String API_UNBIND_DEVICE = SERVER_ADDRESS + "app/bindingdevice/unbindingDevice";



    /**
     * 修改密码
     */

    public final static String API_MODIFY_PWD = LOGIN_ADDRESS + "guardian/modifypw";


    /**
     * 添加群成员
     */
    public final static String API_ADD_MEMBER = SERVER_ADDRESS + "app/team/nember/add";

    /**
     * 删除群成员
     */
    public final static String API_DEL_MEMBER = SERVER_ADDRESS + "app/team/nember/delete";


    /**
     * 解散群
     */
    public final static String API_DEL_GROUP= SERVER_ADDRESS + "app/team/deleteUserTeam";



    //编辑群
    public final static String API_EDIT_GROUP= SERVER_ADDRESS + "app/team/updateUserTeam";



    /**
     * 退出群
     */
    public final static String API_EXIT_GROUP= SERVER_ADDRESS + "app/team/exitUserTeam";

    //获取监护人账户资料
    public final static String API_GET_USER_INFO= SERVER_ADDRESS + "app/guardian/getGuardian";

    //修改监护人账户资料
    public final static String API_SET_USER_INFO= SERVER_ADDRESS + "app/guardian/modifyGuardian";

    //检查设备
    public final static String API_CHECK_DEVCIE= SERVER_ADDRESS + "app/bindingdevice/checkDevice";

    public final static int GROUP_LIST_POPUP_CODE = 1;



//    public static final String DATA_USER_AVATAR_FILE = SDCardUtils.getRootDirectoryPath() + File.separator + "userAvatar" + File.separator;

    //我的救援记录
    public final static String API_MY_SOS_LIST = SERVER_ADDRESS + "app/guardian/task/mySosList";  //我的救援记录，是我接过的单子，与API_SOS_LIST不同

    //确认完成
    public final static String API_SURE_COMPLETE = SERVER_ADDRESS + "app/guardian/task/complete";

    //救援详情
    public final static String API_RESCUE_DETAIL = SERVER_ADDRESS + "app/guardian/task/sosDetail";


    //获取呼救列表
    public final static String API_SOS_LIST = SERVER_ADDRESS + "app/guardian/device/alarmInfo";  //根据type参数请求全部呼救记录，1需要救助，2救助中记录，3已完成记录


    //查询任务单状态
    public final static String API_GET_TASK_STATE = SERVER_ADDRESS + "app/guardian/task/findStatus";

    public static final String USER_PIC = "user.jpg";


    public static final String ROUTE_PLAN_NODE = "routePlanNode";

    /**
     * SOS人信息
     */
    public final static String API_SOS_PERSON_INFO = SERVER_ADDRESS + "app/guardian/task/findDetail";


    //获取求救人的位置
    public final static String API_GET_SOS_LOCATION = SERVER_ADDRESS + "app/device/findLocation";  //获取求救人位置

    public static final int GET_SOSLOC_FROM_DB = 1;
    public static final int GET_SOSLOC_FROM_JPUSH = 2;

    public final static String API_RECEIVE_ORDER = SERVER_ADDRESS + "app/guardian/task/receive";  //去接单

    public static final String APP_FOLDER_NAME = "BNSDKProtector";

    //
    public final static String API_POST_MY_LOCATION = SERVER_ADDRESS + "app/guardian/task/location";  //上传自己的位置

    public final static String API_APP_UPDATE = SERVER_ADDRESS + "app/guardian/appUpdate";  //检查更新APP


    //设备类型：1代表平安符定位器，2代表GSM跌倒报警器，3代表BLE跌倒报警器，4代表BLE手机支架
    public static int GPA =  1;
    public static int GSM = 2;
    public static int BLE = 3;
    public static int MOBILE = 4;

}
