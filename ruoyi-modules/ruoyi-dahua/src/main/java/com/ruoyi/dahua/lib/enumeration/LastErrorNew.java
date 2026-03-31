package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 260611
 * @description 根据NetSDK返回的通用错误码制作的对应枚举类
 * @date 2022/03/02
 */
public enum LastErrorNew {
    // 未收录错误 0x800000FE
    UNRECORDED_ERROR((0x80000000 | (-2)),"未收录错误","Unrecorded error"),
    // 未知错误 0x800000FF
    NET_ERROR((0x80000000 | (-1)),"未知错误","Unknown error"),
    // 没有错误 0x80000000
    NET_NOERROR((0x80000000 | (0)),"没有错误","No error"),
    // 系统出错 0x80000001
    NET_SYSTEM_ERROR((0x80000000 | (1)),"系统出错","system error"),
    // 网络错误,可能是因为网络超时 0x80000002
    NET_NETWORK_ERROR((0x80000000 | (2)),"网络错误,可能是因为网络超时","Protocol error it may result from network timeout"),
    // 设备协议不匹配 0x80000003
    NET_DEV_VER_NOMATCH((0x80000000 | (3)),"设备协议不匹配","Device protocol does not match"),
    // 句柄无效 0x80000004
    NET_INVALID_HANDLE((0x80000000 | (4)),"句柄无效","Handle is invalid"),
    // 打开通道失败 0x80000005
    NET_OPEN_CHANNEL_ERROR((0x80000000 | (5)),"打开通道失败","Failed to open channel"),
    // 关闭通道失败 0x80000006
    NET_CLOSE_CHANNEL_ERROR((0x80000000 | (6)),"关闭通道失败","Failed to close channel"),
    // 用户参数不合法 0x80000007
    NET_ILLEGAL_PARAM((0x80000000 | (7)),"用户参数不合法","User parameter is illegal"),
    // SDK初始化出错 0x80000008
    NET_SDK_INIT_ERROR((0x80000000 | (8)),"SDK初始化出错","SDK initialization error"),
    // SDK清理出错 0x80000009
    NET_SDK_UNINIT_ERROR((0x80000000 | (9)),"SDK清理出错","SDK clear error"),
    // 申请render资源出错 0x8000000A
    NET_RENDER_OPEN_ERROR((0x80000000 | (10)),"申请render资源出错","Error occurs when apply for render resources."),
    // 打开解码库出错 0x8000000B
    NET_DEC_OPEN_ERROR((0x80000000 | (11)),"打开解码库出错","Error occurs when opening the decoder library"),
    // 关闭解码库出错 0x8000000C
    NET_DEC_CLOSE_ERROR((0x80000000 | (12)),"关闭解码库出错","Error occurs when closing the decoder library"),
    // 多画面预览中检测到通道数为0 0x8000000D
    NET_MULTIPLAY_NOCHANNEL((0x80000000 | (13)),"多画面预览中检测到通道数为0","The detected channel number is 0 in multiple-channel preview."),
    // 录音库初始化失败 0x8000000E
    NET_TALK_INIT_ERROR((0x80000000 | (14)),"录音库初始化失败","Failed to initialize record library"),
    // 录音库未经初始化 0x8000000F
    NET_TALK_NOT_INIT((0x80000000 | (15)),"录音库未经初始化","The record library has not been initialized"),
    // 发送音频数据出错 0x80000010
    NET_TALK_SENDDATA_ERROR((0x80000000 | (16)),"发送音频数据出错","Error occurs when sending out audio data"),
    // 实时数据已经处于保存状态 0x80000011
    NET_REAL_ALREADY_SAVING((0x80000000 | (17)),"实时数据已经处于保存状态","The real-time has been protected."),
    // 未保存实时数据 0x80000012
    NET_NOT_SAVING((0x80000000 | (18)),"未保存实时数据","The real-time data has not been save."),
    // 打开文件出错 0x80000013
    NET_OPEN_FILE_ERROR((0x80000000 | (19)),"打开文件出错","Error occurs when opening the file."),
    // 启动云台控制定时器失败 0x80000014
    NET_PTZ_SET_TIMER_ERROR((0x80000000 | (20)),"启动云台控制定时器失败","Failed to enable PTZ to control timer."),
    // 对返回数据的校验出错 0x80000015
    NET_RETURN_DATA_ERROR((0x80000000 | (21)),"对返回数据的校验出错","Error occurs when verify returned data."),
    // 没有足够的缓存 0x80000016
    NET_INSUFFICIENT_BUFFER((0x80000000 | (22)),"没有足够的缓存","There is no sufficient buffer."),
    // 当前SDK未支持该功能 0x80000017
    NET_NOT_SUPPORTED((0x80000000 | (23)),"当前SDK未支持该功能","The current SDK does not support this funcntion."),
    // 查询不到录象 0x80000018
    NET_NO_RECORD_FOUND((0x80000000 | (24)),"查询不到录象","There is no searched result."),
    // 无操作权限 0x80000019
    NET_NOT_AUTHORIZED((0x80000000 | (25)),"无操作权限","You have no operation right."),
    // 暂时无法执行 0x8000001A
    NET_NOT_NOW((0x80000000 | (26)),"暂时无法执行","Can not operate right now."),
    // 未发现对讲通道 0x8000001B
    NET_NO_TALK_CHANNEL((0x80000000 | (27)),"未发现对讲通道","There is no audio talk channel."),
    // 未发现音频 0x8000001C
    NET_NO_AUDIO((0x80000000 | (28)),"未发现音频","There is no audio."),
    // 网络SDK未经初始化 0x8000001D
    NET_NO_INIT((0x80000000 | (29)),"网络SDK未经初始化","The network SDK has not been initialized."),
    // 下载已结束 0x8000001E
    NET_DOWNLOAD_END((0x80000000 | (30)),"下载已结束","The download completed."),
    // 查询结果为空 0x8000001F
    NET_EMPTY_LIST((0x80000000 | (31)),"查询结果为空","There is no searched result."),
    // 获取系统属性配置失败 0x80000020
    NET_ERROR_GETCFG_SYSATTR((0x80000000 | (32)),"获取系统属性配置失败","Failed to get system property setup."),
    // 获取序列号失败 0x80000021
    NET_ERROR_GETCFG_SERIAL((0x80000000 | (33)),"获取序列号失败","Failed to get SN."),
    // 获取常规属性失败 0x80000022
    NET_ERROR_GETCFG_GENERAL((0x80000000 | (34)),"获取常规属性失败","Failed to get general property."),
    // 获取DSP能力描述失败 0x80000023
    NET_ERROR_GETCFG_DSPCAP((0x80000000 | (35)),"获取DSP能力描述失败","Failed to get DSP capacity description."),
    // 获取网络配置失败 0x80000024
    NET_ERROR_GETCFG_NETCFG((0x80000000 | (36)),"获取网络配置失败","Failed to get network channel setup."),
    // 获取通道名称失败 0x80000025
    NET_ERROR_GETCFG_CHANNAME((0x80000000 | (37)),"获取通道名称失败","Failed to get channel name."),
    // 获取视频属性失败 0x80000026
    NET_ERROR_GETCFG_VIDEO((0x80000000 | (38)),"获取视频属性失败","Failed to get video property."),
    // 获取录象配置失败 0x80000027
    NET_ERROR_GETCFG_RECORD((0x80000000 | (39)),"获取录象配置失败","Failed to get record setup"),
    // 获取解码器协议名称失败 0x80000028
    NET_ERROR_GETCFG_PRONAME((0x80000000 | (40)),"获取解码器协议名称失败","Failed to get decoder protocol name"),
    // 获取232串口功能名称失败 0x80000029
    NET_ERROR_GETCFG_FUNCNAME((0x80000000 | (41)),"获取232串口功能名称失败","Failed to get 232 COM function name."),
    // 获取解码器属性失败 0x8000002A
    NET_ERROR_GETCFG_485DECODER((0x80000000 | (42)),"获取解码器属性失败","Failed to get decoder property"),
    // 获取232串口配置失败 0x8000002B
    NET_ERROR_GETCFG_232COM((0x80000000 | (43)),"获取232串口配置失败","Failed to get 232 COM setup"),
    // 获取外部报警输入配置失败 0x8000002C
    NET_ERROR_GETCFG_ALARMIN((0x80000000 | (44)),"获取外部报警输入配置失败","Failed to get external alarm input setup"),
    // 获取动态检测报警失败 0x8000002D
    NET_ERROR_GETCFG_ALARMDET((0x80000000 | (45)),"获取动态检测报警失败","Failed to get motion detection alarm"),
    // 获取设备时间失败 0x8000002E
    NET_ERROR_GETCFG_SYSTIME((0x80000000 | (46)),"获取设备时间失败","Failed to get device time"),
    // 获取预览参数失败 0x8000002F
    NET_ERROR_GETCFG_PREVIEW((0x80000000 | (47)),"获取预览参数失败","Failed to get preview parameter"),
    // 获取自动维护配置失败 0x80000030
    NET_ERROR_GETCFG_AUTOMT((0x80000000 | (48)),"获取自动维护配置失败","Failed to get audio maintenance setup"),
    // 获取视频矩阵配置失败 0x80000031
    NET_ERROR_GETCFG_VIDEOMTRX((0x80000000 | (49)),"获取视频矩阵配置失败","Failed to get video matrix setup"),
    // 获取区域遮挡配置失败 0x80000032
    NET_ERROR_GETCFG_COVER((0x80000000 | (50)),"获取区域遮挡配置失败","Failed to get privacy mask zone setup"),
    // 获取图象水印配置失败 0x80000033
    NET_ERROR_GETCFG_WATERMAKE((0x80000000 | (51)),"获取图象水印配置失败","Failed to get video watermark setup"),
    // 获取配置失败位置：组播端口按通道配置 0x80000034
    NET_ERROR_GETCFG_MULTICAST((0x80000000 | (52)),"获取配置失败位置：组播端口按通道配置","Failed to get config, omulticast port by channel"),
    // 修改常规属性失败 0x80000037
    NET_ERROR_SETCFG_GENERAL((0x80000000 | (55)),"修改常规属性失败","Failed to modify general property"),
    // 修改网络配置失败 0x80000038
    NET_ERROR_SETCFG_NETCFG((0x80000000 | (56)),"修改网络配置失败","Failed to modify channel setup"),
    // 修改通道名称失败 0x80000039
    NET_ERROR_SETCFG_CHANNAME((0x80000000 | (57)),"修改通道名称失败","Failed to modify channel name"),
    // 修改视频属性失败 0x8000003A
    NET_ERROR_SETCFG_VIDEO((0x80000000 | (58)),"修改视频属性失败","Failed to modify video channel"),
    // 修改录象配置失败 0x8000003B
    NET_ERROR_SETCFG_RECORD((0x80000000 | (59)),"修改录象配置失败","Failed to modify record setup"),
    // 修改解码器属性失败 0x8000003C
    NET_ERROR_SETCFG_485DECODER((0x80000000 | (60)),"修改解码器属性失败","Failed to modify decoder property"),
    // 修改232串口配置失败 0x8000003D
    NET_ERROR_SETCFG_232COM((0x80000000 | (61)),"修改232串口配置失败","Failed to modify 232 COM setup"),
    // 修改外部输入报警配置失败 0x8000003E
    NET_ERROR_SETCFG_ALARMIN((0x80000000 | (62)),"修改外部输入报警配置失败","Failed to modify external input alarm setup"),
    // 修改动态检测报警配置失败 0x8000003F
    NET_ERROR_SETCFG_ALARMDET((0x80000000 | (63)),"修改动态检测报警配置失败","Failed to modify motion detection alarm setup"),
    // 修改设备时间失败 0x80000040
    NET_ERROR_SETCFG_SYSTIME((0x80000000 | (64)),"修改设备时间失败","Failed to modify device time"),
    // 修改预览参数失败 0x80000041
    NET_ERROR_SETCFG_PREVIEW((0x80000000 | (65)),"修改预览参数失败","Failed to modify preview parameter"),
    // 修改自动维护配置失败 0x80000042
    NET_ERROR_SETCFG_AUTOMT((0x80000000 | (66)),"修改自动维护配置失败","Failed to modify auto maintenance setup"),
    // 修改视频矩阵配置失败 0x80000043
    NET_ERROR_SETCFG_VIDEOMTRX((0x80000000 | (67)),"修改视频矩阵配置失败","Failed to modify video matrix setup"),
    // 修改区域遮挡配置失败 0x80000044
    NET_ERROR_SETCFG_COVER((0x80000000 | (68)),"修改区域遮挡配置失败","Failed to modify privacy mask zone"),
    // 修改图象水印配置失败 0x80000045
    NET_ERROR_SETCFG_WATERMAKE((0x80000000 | (69)),"修改图象水印配置失败","Failed to modify video watermark setup"),
    // 修改无线网络信息失败 0x80000046
    NET_ERROR_SETCFG_WLAN((0x80000000 | (70)),"修改无线网络信息失败","Failed to modify wireless network information"),
    // 选择无线网络设备失败 0x80000047
    NET_ERROR_SETCFG_WLANDEV((0x80000000 | (71)),"选择无线网络设备失败","Failed to select wireless network device"),
    // 修改主动注册参数配置失败 0x80000048
    NET_ERROR_SETCFG_REGISTER((0x80000000 | (72)),"修改主动注册参数配置失败","Failed to modify the actively registration parameter setup."),
    // 修改摄像头属性配置失败 0x80000049
    NET_ERROR_SETCFG_CAMERA((0x80000000 | (73)),"修改摄像头属性配置失败","Failed to modify camera property"),
    // 修改红外报警配置失败 0x8000004A
    NET_ERROR_SETCFG_INFRARED((0x80000000 | (74)),"修改红外报警配置失败","Failed to modify IR alarm setup"),
    // 修改音频报警配置失败 0x8000004B
    NET_ERROR_SETCFG_SOUNDALARM((0x80000000 | (75)),"修改音频报警配置失败","Failed to modify audio alarm setup"),
    // 修改存储位置配置失败 0x8000004C
    NET_ERROR_SETCFG_STORAGE((0x80000000 | (76)),"修改存储位置配置失败","Failed to modify storage position setup"),
    // 音频编码接口没有成功初始化 0x8000004D
    NET_AUDIOENCODE_NOTINIT((0x80000000 | (77)),"音频编码接口没有成功初始化","The audio encode port has not been successfully initialized."),
    // 数据过长 0x8000004E
    NET_DATA_TOOLONGH((0x80000000 | (78)),"数据过长","The data are too long."),
    // 设备不支持该操作 0x8000004F
    NET_UNSUPPORTED((0x80000000 | (79)),"设备不支持该操作","The device does not support current operation."),
    // 设备资源不足 0x80000050
    NET_DEVICE_BUSY((0x80000000 | (80)),"设备资源不足","Device resources is not sufficient."),
    // 服务器已经启动 0x80000051
    NET_SERVER_STARTED((0x80000000 | (81)),"服务器已经启动","The server has boot up"),
    // 服务器尚未成功启动 0x80000052
    NET_SERVER_STOPPED((0x80000000 | (82)),"服务器尚未成功启动","The server has not fully boot up"),
    // 输入序列号有误 0x80000053
    NET_LISTER_INCORRECT_SERIAL((0x80000000 | (83)),"输入序列号有误","Input serial number is not correct."),
    // 获取硬盘信息失败 0x80000054
    NET_QUERY_DISKINFO_FAILED((0x80000000 | (84)),"获取硬盘信息失败","Failed to get HDD information."),
    // 获取连接Session信息 0x80000055
    NET_ERROR_GETCFG_SESSION((0x80000000 | (85)),"获取连接Session信息","Failed to get connect session information."),
    // 输入密码错误超过限制次数 0x80000056
    NET_USER_FLASEPWD_TRYTIME((0x80000000 | (86)),"输入密码错误超过限制次数","The password you typed is incorrect. You have exceeded the maximum number of retries."),
    // 密码过期 0x80000063
    NET_LOGIN_ERROR_PASSWORD_EXPIRED((0x80000000 | (99)),"密码过期","password expired"),
    // 密码不正确 0x80000064
    NET_LOGIN_ERROR_PASSWORD((0x80000000 | (100)),"密码不正确","Password is not correct"),
    // 帐户不存在 0x80000065
    NET_LOGIN_ERROR_USER((0x80000000 | (101)),"帐户不存在","The account does not exist"),
    // 等待登录返回超时 0x80000066
    NET_LOGIN_ERROR_TIMEOUT((0x80000000 | (102)),"等待登录返回超时","Time out for log in returned value."),
    // 帐号已登录 0x80000067
    NET_LOGIN_ERROR_RELOGGIN((0x80000000 | (103)),"帐号已登录","The account has logged in"),
    // 帐号已被锁定 0x80000068
    NET_LOGIN_ERROR_LOCKED((0x80000000 | (104)),"帐号已被锁定","The account has been locked"),
    // 帐号已被列为禁止名单 0x80000069
    NET_LOGIN_ERROR_BLACKLIST((0x80000000 | (105)),"帐号已被列为禁止名单","The account has been in the block list"),
    // 资源不足,系统忙 0x8000006A
    NET_LOGIN_ERROR_BUSY((0x80000000 | (106)),"资源不足,系统忙","Resources are not sufficient. System is busy now."),
    // 登录设备超时,请检查网络并重试 0x8000006B
    NET_LOGIN_ERROR_CONNECT((0x80000000 | (107)),"登录设备超时,请检查网络并重试","Time out. Please check network and try again."),
    // 网络连接失败 0x8000006C
    NET_LOGIN_ERROR_NETWORK((0x80000000 | (108)),"网络连接失败","Network connection failed."),
    // 登录设备成功,但无法创建视频通道,请检查网络状况 0x8000006D
    NET_LOGIN_ERROR_SUBCONNECT((0x80000000 | (109)),"登录设备成功,但无法创建视频通道,请检查网络状况","Successfully logged in the device but can not create video channel. Please check network connection."),
    // 超过最大连接数 0x8000006E
    NET_LOGIN_ERROR_MAXCONNECT((0x80000000 | (110)),"超过最大连接数","exceed the max connect number"),
    // 只支持3代协议 0x8000006F
    NET_LOGIN_ERROR_PROTOCOL3_ONLY((0x80000000 | (111)),"只支持3代协议","protocol 3 support"),
    // 未插入U盾或U盾信息错误 0x80000070
    NET_LOGIN_ERROR_UKEY_LOST((0x80000000 | (112)),"未插入U盾或U盾信息错误","There is no USB or USB info error"),
    // 客户端IP地址没有登录权限 0x80000071
    NET_LOGIN_ERROR_NO_AUTHORIZED((0x80000000 | (113)),"客户端IP地址没有登录权限","Client-end IP address has no right to login"),
    // 账号或密码错误 0x80000075
    NET_LOGIN_ERROR_USER_OR_PASSOWRD((0x80000000 | (117)),"账号或密码错误","user or password error"),
    // 设备尚未初始化，不能登录，请先初始化设备 0x80000076
    NET_LOGIN_ERROR_DEVICE_NOT_INIT((0x80000000 | (118)),"设备尚未初始化，不能登录，请先初始化设备","cannot login because the device has not been init,please init the device and then login"),
    // 登录受限,可能是IP受限、时间段受限、有效期受限 0x80000077
    NET_LOGIN_ERROR_LIMITED((0x80000000 | (119)),"登录受限,可能是IP受限、时间段受限、有效期受限","Limited login, it could be IP limited, time limited or expiration limited"),
    // Render库打开音频出错 0x80000078
    NET_RENDER_SOUND_ON_ERROR((0x80000000 | (120)),"Render库打开音频出错","Error occurs when Render library open audio."),
    // Render库关闭音频出错 0x80000079
    NET_RENDER_SOUND_OFF_ERROR((0x80000000 | (121)),"Render库关闭音频出错","Error occurs when Render library close audio"),
    // Render库控制音量出错 0x8000007A
    NET_RENDER_SET_VOLUME_ERROR((0x80000000 | (122)),"Render库控制音量出错","Error occurs when Render library control volume"),
    // Render库设置画面参数出错 0x8000007B
    NET_RENDER_ADJUST_ERROR((0x80000000 | (123)),"Render库设置画面参数出错","Error occurs when Render library set video parameter"),
    // Render库暂停播放出错 0x8000007C
    NET_RENDER_PAUSE_ERROR((0x80000000 | (124)),"Render库暂停播放出错","Error occurs when Render library pause play"),
    // Render库抓图出错 0x8000007D
    NET_RENDER_SNAP_ERROR((0x80000000 | (125)),"Render库抓图出错","Render library snapshot error"),
    // Render库步进出错 0x8000007E
    NET_RENDER_STEP_ERROR((0x80000000 | (126)),"Render库步进出错","Render library stepper error"),
    // Render库设置帧率出错 0x8000007F
    NET_RENDER_FRAMERATE_ERROR((0x80000000 | (127)),"Render库设置帧率出错","Error occurs when Render library set frame rate."),
    // Render库设置显示区域出错 0x80000080
    NET_RENDER_DISPLAYREGION_ERROR((0x80000000 | (128)),"Render库设置显示区域出错","Error occurs when Render lib setting show region"),
    // Render库获取当前播放时间出错 0x80000081
    NET_RENDER_GETOSDTIME_ERROR((0x80000000 | (129)),"Render库获取当前播放时间出错","An error occurred when Render library getting current play time"),
    // 组名已存在 0x8000008C
    NET_GROUP_EXIST((0x80000000 | (140)),"组名已存在","Group name has been existed."),
    // 组名不存在 0x8000008D
    NET_GROUP_NOEXIST((0x80000000 | (141)),"组名不存在","The group name does not exist."),
    // 组的权限超出权限列表范围 0x8000008E
    NET_GROUP_RIGHTOVER((0x80000000 | (142)),"组的权限超出权限列表范围","The group right exceeds the right list!"),
    // 组下有用户,不能删除 0x8000008F
    NET_GROUP_HAVEUSER((0x80000000 | (143)),"组下有用户,不能删除","The group can not be removed since there is user in it!"),
    // 组的某个权限被用户使用,不能出除 0x80000090
    NET_GROUP_RIGHTUSE((0x80000000 | (144)),"组的某个权限被用户使用,不能出除","The user has used one of the group right. It can not be removed."),
    // 新组名同已有组名重复 0x80000091
    NET_GROUP_SAMENAME((0x80000000 | (145)),"新组名同已有组名重复","New group name has been existed"),
    // 用户已存在 0x80000092
    NET_USER_EXIST((0x80000000 | (146)),"用户已存在","The user name has been existed"),
    // 用户不存在 0x80000093
    NET_USER_NOEXIST((0x80000000 | (147)),"用户不存在","The account does not exist."),
    // 用户权限超出组权限 0x80000094
    NET_USER_RIGHTOVER((0x80000000 | (148)),"用户权限超出组权限","User right exceeds the group right."),
    // 保留帐号,不容许修改密码 0x80000095
    NET_USER_PWD((0x80000000 | (149)),"保留帐号,不容许修改密码","Reserved account. It does not allow to be modified."),
    // 密码不正确 0x80000096
    NET_USER_FLASEPWD((0x80000000 | (150)),"密码不正确","password is not correct"),
    // 密码不匹配 0x80000097
    NET_USER_NOMATCHING((0x80000000 | (151)),"密码不匹配","Password is invalid"),
    // 账号正在使用中 0x80000098
    NET_USER_INUSE((0x80000000 | (152)),"账号正在使用中","account in use"),
    // 获取网卡配置失败 0x8000012C
    NET_ERROR_GETCFG_ETHERNET((0x80000000 | (300)),"获取网卡配置失败","Failed to get network card setup."),
    // 获取无线网络信息失败 0x8000012D
    NET_ERROR_GETCFG_WLAN((0x80000000 | (301)),"获取无线网络信息失败","Failed to get wireless network information."),
    // 获取无线网络设备失败 0x8000012E
    NET_ERROR_GETCFG_WLANDEV((0x80000000 | (302)),"获取无线网络设备失败","Failed to get wireless network device."),
    // 获取主动注册参数失败 0x8000012F
    NET_ERROR_GETCFG_REGISTER((0x80000000 | (303)),"获取主动注册参数失败","Failed to get actively registration parameter."),
    // 获取摄像头属性失败 0x80000130
    NET_ERROR_GETCFG_CAMERA((0x80000000 | (304)),"获取摄像头属性失败","Failed to get camera property"),
    // 获取红外报警配置失败 0x80000131
    NET_ERROR_GETCFG_INFRARED((0x80000000 | (305)),"获取红外报警配置失败","Failed to get IR alarm setup"),
    // 获取音频报警配置失败 0x80000132
    NET_ERROR_GETCFG_SOUNDALARM((0x80000000 | (306)),"获取音频报警配置失败","Failed to get audio alarm setup"),
    // 获取存储位置配置失败 0x80000133
    NET_ERROR_GETCFG_STORAGE((0x80000000 | (307)),"获取存储位置配置失败","Failed to get storage position"),
    // 获取邮件配置失败 0x80000134
    NET_ERROR_GETCFG_MAIL((0x80000000 | (308)),"获取邮件配置失败","Failed to get mail setup."),
    // 暂时无法设置 0x80000135
    NET_CONFIG_DEVBUSY((0x80000000 | (309)),"暂时无法设置","Can not set right now."),
    // 配置数据不合法 0x80000136
    NET_CONFIG_DATAILLEGAL((0x80000000 | (310)),"配置数据不合法","The configuration setup data are illegal."),
    // 获取夏令时配置失败 0x80000137
    NET_ERROR_GETCFG_DST((0x80000000 | (311)),"获取夏令时配置失败","Failed to get DST setup"),
    // 设置夏令时配置失败 0x80000138
    NET_ERROR_SETCFG_DST((0x80000000 | (312)),"设置夏令时配置失败","Failed to set DST"),
    // 获取视频OSD叠加配置失败 0x80000139
    NET_ERROR_GETCFG_VIDEO_OSD((0x80000000 | (313)),"获取视频OSD叠加配置失败","Failed to get video OSD setup."),
    // 设置视频OSD叠加配置失败 0x8000013A
    NET_ERROR_SETCFG_VIDEO_OSD((0x80000000 | (314)),"设置视频OSD叠加配置失败","Failed to set video OSD"),
    // 获取CDMA\GPRS网络配置失败 0x8000013B
    NET_ERROR_GETCFG_GPRSCDMA((0x80000000 | (315)),"获取CDMA\\GPRS网络配置失败","Failed to get CDMA\\GPRS configuration"),
    // 设置CDMA\GPRS网络配置失败 0x8000013C
    NET_ERROR_SETCFG_GPRSCDMA((0x80000000 | (316)),"设置CDMA\\GPRS网络配置失败","Failed to set CDMA\\GPRS configuration"),
    // 获取IP过滤配置失败 0x8000013D
    NET_ERROR_GETCFG_IPFILTER((0x80000000 | (317)),"获取IP过滤配置失败","Failed to get IP Filter configuration"),
    // 设置IP过滤配置失败 0x8000013E
    NET_ERROR_SETCFG_IPFILTER((0x80000000 | (318)),"设置IP过滤配置失败","Failed to set IP Filter configuration"),
    // 获取语音对讲编码配置失败 0x8000013F
    NET_ERROR_GETCFG_TALKENCODE((0x80000000 | (319)),"获取语音对讲编码配置失败","Failed to get Talk Encode configuration"),
    // 设置语音对讲编码配置失败 0x80000140
    NET_ERROR_SETCFG_TALKENCODE((0x80000000 | (320)),"设置语音对讲编码配置失败","Failed to set Talk Encode configuration"),
    // 获取录像打包长度配置失败 0x80000141
    NET_ERROR_GETCFG_RECORDLEN((0x80000000 | (321)),"获取录像打包长度配置失败","Failed to get The length of the video package configuration"),
    // 设置录像打包长度配置失败 0x80000142
    NET_ERROR_SETCFG_RECORDLEN((0x80000000 | (322)),"设置录像打包长度配置失败","Failed to set The length of the video package configuration"),
    // 不支持网络硬盘分区 0x80000143
    NET_DONT_SUPPORT_SUBAREA((0x80000000 | (323)),"不支持网络硬盘分区","Not support Network hard disk partition"),
    // 获取设备上主动注册服务器信息失败 0x80000144
    NET_ERROR_GET_AUTOREGSERVER((0x80000000 | (324)),"获取设备上主动注册服务器信息失败","Failed to get the register server information"),
    // 主动注册重定向注册错误 0x80000145
    NET_ERROR_CONTROL_AUTOREGISTER((0x80000000 | (325)),"主动注册重定向注册错误","Failed to control actively registration"),
    // 断开主动注册服务器错误 0x80000146
    NET_ERROR_DISCONNECT_AUTOREGISTER((0x80000000 | (326)),"断开主动注册服务器错误","Failed to disconnect actively registration"),
    // 获取mms配置失败 0x80000147
    NET_ERROR_GETCFG_MMS((0x80000000 | (327)),"获取mms配置失败","Failed to get mms configuration"),
    // 设置mms配置失败 0x80000148
    NET_ERROR_SETCFG_MMS((0x80000000 | (328)),"设置mms配置失败","Failed to set mms configuration"),
    // 获取短信激活无线连接配置失败 0x80000149
    NET_ERROR_GETCFG_SMSACTIVATION((0x80000000 | (329)),"获取短信激活无线连接配置失败","Failed to get SMS configuration"),
    // 设置短信激活无线连接配置失败 0x8000014A
    NET_ERROR_SETCFG_SMSACTIVATION((0x80000000 | (330)),"设置短信激活无线连接配置失败","Failed to set SMS configuration"),
    // 获取拨号激活无线连接配置失败 0x8000014B
    NET_ERROR_GETCFG_DIALINACTIVATION((0x80000000 | (331)),"获取拨号激活无线连接配置失败","Failed to get activation of a wireless connection"),
    // 设置拨号激活无线连接配置失败 0x8000014C
    NET_ERROR_SETCFG_DIALINACTIVATION((0x80000000 | (332)),"设置拨号激活无线连接配置失败","Failed to set activation of a wireless connection"),
    // 查询视频输出参数配置失败 0x8000014D
    NET_ERROR_GETCFG_VIDEOOUT((0x80000000 | (333)),"查询视频输出参数配置失败","Failed to get the parameter of video output"),
    // 设置视频输出参数配置失败 0x8000014E
    NET_ERROR_SETCFG_VIDEOOUT((0x80000000 | (334)),"设置视频输出参数配置失败","Failed to set the configuration of video output"),
    // 获取osd叠加使能配置失败 0x8000014F
    NET_ERROR_GETCFG_OSDENABLE((0x80000000 | (335)),"获取osd叠加使能配置失败","Failed to get osd overlay enabling"),
    // 设置osd叠加使能配置失败 0x80000150
    NET_ERROR_SETCFG_OSDENABLE((0x80000000 | (336)),"设置osd叠加使能配置失败","Failed to set OSD overlay enabling"),
    // 设置数字通道前端编码接入配置失败 0x80000151
    NET_ERROR_SETCFG_ENCODERINFO((0x80000000 | (337)),"设置数字通道前端编码接入配置失败","Failed to set digital input configuration of front encoders"),
    // 获取TV调节配置失败 0x80000152
    NET_ERROR_GETCFG_TVADJUST((0x80000000 | (338)),"获取TV调节配置失败","Failed to get TV adjust configuration"),
    // 设置TV调节配置失败 0x80000153
    NET_ERROR_SETCFG_TVADJUST((0x80000000 | (339)),"设置TV调节配置失败","Failed to set TV adjust configuration"),
    // 请求建立连接失败 0x80000154
    NET_ERROR_CONNECT_FAILED((0x80000000 | (340)),"请求建立连接失败","Failed to request to establish a connection"),
    // 请求刻录文件上传失败 0x80000155
    NET_ERROR_SETCFG_BURNFILE((0x80000000 | (341)),"请求刻录文件上传失败","Failed to request to upload burn files"),
    // 获取抓包配置信息失败 0x80000156
    NET_ERROR_SNIFFER_GETCFG((0x80000000 | (342)),"获取抓包配置信息失败","Failed to get capture configuration information"),
    // 设置抓包配置信息失败 0x80000157
    NET_ERROR_SNIFFER_SETCFG((0x80000000 | (343)),"设置抓包配置信息失败","Failed to set capture configuration information"),
    // 查询下载限制信息失败 0x80000158
    NET_ERROR_DOWNLOADRATE_GETCFG((0x80000000 | (344)),"查询下载限制信息失败","Failed to get download restrictions information"),
    // 设置下载限制信息失败 0x80000159
    NET_ERROR_DOWNLOADRATE_SETCFG((0x80000000 | (345)),"设置下载限制信息失败","Failed to set download restrictions information"),
    // 查询串口参数失败 0x8000015A
    NET_ERROR_SEARCH_TRANSCOM((0x80000000 | (346)),"查询串口参数失败","Failed to query serial port parameters"),
    // 获取预制点信息错误 0x8000015B
    NET_ERROR_GETCFG_POINT((0x80000000 | (347)),"获取预制点信息错误","Failed to get the preset info"),
    // 设置预制点信息错误 0x8000015C
    NET_ERROR_SETCFG_POINT((0x80000000 | (348)),"设置预制点信息错误","Failed to set the preset info"),
    // SDK没有正常登出设备 0x8000015D
    NET_SDK_LOGOUT_ERROR((0x80000000 | (349)),"SDK没有正常登出设备","SDK log out the device abnormally"),
    // 获取车载配置失败 0x8000015E
    NET_ERROR_GET_VEHICLE_CFG((0x80000000 | (350)),"获取车载配置失败","Failed to get vehicle configuration"),
    // 设置车载配置失败 0x8000015F
    NET_ERROR_SET_VEHICLE_CFG((0x80000000 | (351)),"设置车载配置失败","Failed to set vehicle configuration"),
    // 获取atm叠加配置失败 0x80000160
    NET_ERROR_GET_ATM_OVERLAY_CFG((0x80000000 | (352)),"获取atm叠加配置失败","Failed to get ATM overlay configuration"),
    // 设置atm叠加配置失败 0x80000161
    NET_ERROR_SET_ATM_OVERLAY_CFG((0x80000000 | (353)),"设置atm叠加配置失败","Failed to set ATM overlay configuration"),
    // 获取atm叠加能力失败 0x80000162
    NET_ERROR_GET_ATM_OVERLAY_ABILITY((0x80000000 | (354)),"获取atm叠加能力失败","Failed to get ATM overlay ability"),
    // 获取解码器解码轮巡配置失败 0x80000163
    NET_ERROR_GET_DECODER_TOUR_CFG((0x80000000 | (355)),"获取解码器解码轮巡配置失败","Failed to get decoder tour configuration"),
    // 设置解码器解码轮巡配置失败 0x80000164
    NET_ERROR_SET_DECODER_TOUR_CFG((0x80000000 | (356)),"设置解码器解码轮巡配置失败","Failed to set decoder tour configuration"),
    // 控制解码器解码轮巡失败 0x80000165
    NET_ERROR_CTRL_DECODER_TOUR((0x80000000 | (357)),"控制解码器解码轮巡失败","Failed to control decoder tour"),
    // 超出设备支持最大用户组数目 0x80000166
    NET_GROUP_OVERSUPPORTNUM((0x80000000 | (358)),"超出设备支持最大用户组数目","Beyond the device supports for the largest number of user groups"),
    // 超出设备支持最大用户数目 0x80000167
    NET_USER_OVERSUPPORTNUM((0x80000000 | (359)),"超出设备支持最大用户数目","Beyond the device supports for the largest number of users"),
    // 获取SIP配置失败 0x80000170
    NET_ERROR_GET_SIP_CFG((0x80000000 | (368)),"获取SIP配置失败","Failed to get SIP configuration"),
    // 设置SIP配置失败 0x80000171
    NET_ERROR_SET_SIP_CFG((0x80000000 | (369)),"设置SIP配置失败","Failed to set SIP configuration"),
    // 获取SIP能力失败 0x80000172
    NET_ERROR_GET_SIP_ABILITY((0x80000000 | (370)),"获取SIP能力失败","Failed to get SIP capability"),
    // 获取WIFI ap配置失败 0x80000173
    NET_ERROR_GET_WIFI_AP_CFG((0x80000000 | (371)),"获取WIFI ap配置失败","Failed to get \"WIFI ap\' configuration"),
    // 设置WIFI ap配置失败 0x80000174
    NET_ERROR_SET_WIFI_AP_CFG((0x80000000 | (372)),"设置WIFI ap配置失败","Failed to set \"WIFI ap\" configuration"),
    // 获取解码策略配置失败 0x80000175
    NET_ERROR_GET_DECODE_POLICY((0x80000000 | (373)),"获取解码策略配置失败","Failed to get decode policy"),
    // 设置解码策略配置失败 0x80000176
    NET_ERROR_SET_DECODE_POLICY((0x80000000 | (374)),"设置解码策略配置失败","Failed to set decode policy"),
    // 拒绝对讲 0x80000177
    NET_ERROR_TALK_REJECT((0x80000000 | (375)),"拒绝对讲","refuse talk"),
    // 对讲被其他客户端打开 0x80000178
    NET_ERROR_TALK_OPENED((0x80000000 | (376)),"对讲被其他客户端打开","talk has opened by other client"),
    // 资源冲突 0x80000179
    NET_ERROR_TALK_RESOURCE_CONFLICIT((0x80000000 | (377)),"资源冲突","resource conflict"),
    // 不支持的语音编码格式 0x8000017A
    NET_ERROR_TALK_UNSUPPORTED_ENCODE((0x80000000 | (378)),"不支持的语音编码格式","unsupported encode type"),
    // 无权限 0x8000017B
    NET_ERROR_TALK_RIGHTLESS((0x80000000 | (379)),"无权限","no right"),
    // 请求对讲失败 0x8000017C
    NET_ERROR_TALK_FAILED((0x80000000 | (380)),"请求对讲失败","request failed"),
    // 获取机器相关配置失败 0x8000017D
    NET_ERROR_GET_MACHINE_CFG((0x80000000 | (381)),"获取机器相关配置失败","Failed to get device relative config"),
    // 设置机器相关配置失败 0x8000017E
    NET_ERROR_SET_MACHINE_CFG((0x80000000 | (382)),"设置机器相关配置失败","Failed to set device relative config"),
    // 设备无法获取当前请求数据 0x8000017F
    NET_ERROR_GET_DATA_FAILED((0x80000000 | (383)),"设备无法获取当前请求数据","get data failed"),
    // MAC地址验证失败 0x80000180
    NET_ERROR_MAC_VALIDATE_FAILED((0x80000000 | (384)),"MAC地址验证失败","MAC validate failed"),
    // 获取服务器实例失败 0x80000181
    NET_ERROR_GET_INSTANCE((0x80000000 | (385)),"获取服务器实例失败","Failed to get server instance"),
    // 生成的json字符串错误 0x80000182
    NET_ERROR_JSON_REQUEST((0x80000000 | (386)),"生成的json字符串错误","Generated json string is error"),
    // 响应的json字符串错误 0x80000183
    NET_ERROR_JSON_RESPONSE((0x80000000 | (387)),"响应的json字符串错误","The responding json string is error"),
    // 协议版本低于当前使用的版本 0x80000184
    NET_ERROR_VERSION_HIGHER((0x80000000 | (388)),"协议版本低于当前使用的版本","The protocol version is lower than current version"),
    // 热备操作失败, 容量不足 0x80000185
    NET_SPARE_NO_CAPACITY((0x80000000 | (389)),"热备操作失败, 容量不足","Hotspare disk operation failed. The capacity is low"),
    // 显示源被其他输出占用 0x80000186
    NET_ERROR_SOURCE_IN_USE((0x80000000 | (390)),"显示源被其他输出占用","Display source is used by other output"),
    // 高级用户抢占低级用户资源 0x80000187
    NET_ERROR_REAVE((0x80000000 | (391)),"高级用户抢占低级用户资源","advanced users grab low-level user resource"),
    // 禁止入网 0x80000188
    NET_ERROR_NETFORBID((0x80000000 | (392)),"禁止入网","net forbid"),
    // 获取MAC过滤配置失败 0x80000189
    NET_ERROR_GETCFG_MACFILTER((0x80000000 | (393)),"获取MAC过滤配置失败","get MAC filter configuration error"),
    // 设置MAC过滤配置失败 0x8000018A
    NET_ERROR_SETCFG_MACFILTER((0x80000000 | (394)),"设置MAC过滤配置失败","set MAC filter configuration error"),
    // 获取IP/MAC过滤配置失败 0x8000018B
    NET_ERROR_GETCFG_IPMACFILTER((0x80000000 | (395)),"获取IP/MAC过滤配置失败","get IP/MAC filter configuration error"),
    // 设置IP/MAC过滤配置失败 0x8000018C
    NET_ERROR_SETCFG_IPMACFILTER((0x80000000 | (396)),"设置IP/MAC过滤配置失败","set IP/MAC filter configuration error"),
    // 当前操作超时 0x8000018D
    NET_ERROR_OPERATION_OVERTIME((0x80000000 | (397)),"当前操作超时","operation over time"),
    // 高级校验失败 0x8000018E
    NET_ERROR_SENIOR_VALIDATE_FAILED((0x80000000 | (398)),"高级校验失败","senior validation failure"),
    // 设备ID不存在 0x8000018F
    NET_ERROR_DEVICE_ID_NOT_EXIST((0x80000000 | (399)),"设备ID不存在","device ID is not exist"),
    // 不支持当前操作 0x80000190
    NET_ERROR_UNSUPPORTED((0x80000000 | (400)),"不支持当前操作","unsupport operation"),
    // 代理库加载失败 0x80000191
    NET_ERROR_PROXY_DLLLOAD((0x80000000 | (401)),"代理库加载失败","proxy dll load error"),
    // 代理用户参数不合法 0x80000192
    NET_ERROR_PROXY_ILLEGAL_PARAM((0x80000000 | (402)),"代理用户参数不合法","proxy user parameter is not legal"),
    // 代理句柄无效 0x80000193
    NET_ERROR_PROXY_INVALID_HANDLE((0x80000000 | (403)),"代理句柄无效","handle invalid"),
    // 代理登入前端设备失败 0x80000194
    NET_ERROR_PROXY_LOGIN_DEVICE_ERROR((0x80000000 | (404)),"代理登入前端设备失败","login device error"),
    // 启动代理服务失败 0x80000195
    NET_ERROR_PROXY_START_SERVER_ERROR((0x80000000 | (405)),"启动代理服务失败","start proxy server error"),
    // 请求喊话失败 0x80000196
    NET_ERROR_SPEAK_FAILED((0x80000000 | (406)),"请求喊话失败","request speak failed"),
    // 设备不支持此F6接口调用 0x80000197
    NET_ERROR_NOT_SUPPORT_F6((0x80000000 | (407)),"设备不支持此F6接口调用","unsupport F6"),
    // 光盘未就绪 0x80000198
    NET_ERROR_CD_UNREADY((0x80000000 | (408)),"光盘未就绪","CD is not ready"),
    // 目录不存在 0x80000199
    NET_ERROR_DIR_NOT_EXIST((0x80000000 | (409)),"目录不存在","Directory does not exist"),
    // 设备不支持的分割模式 0x8000019A
    NET_ERROR_UNSUPPORTED_SPLIT_MODE((0x80000000 | (410)),"设备不支持的分割模式","The device does not support the segmentation model"),
    // 开窗参数不合法 0x8000019B
    NET_ERROR_OPEN_WND_PARAM((0x80000000 | (411)),"开窗参数不合法","Open the window parameter is illegal"),
    // 开窗数量超过限制 0x8000019C
    NET_ERROR_LIMITED_WND_COUNT((0x80000000 | (412)),"开窗数量超过限制","Open the window more than limit"),
    // 请求命令与当前模式不匹配 0x8000019D
    NET_ERROR_UNMATCHED_REQUEST((0x80000000 | (413)),"请求命令与当前模式不匹配","Request command with the current pattern don't match"),
    // Render库启用高清图像内部调整策略出错 0x8000019E
    NET_RENDER_ENABLELARGEPICADJUSTMENT_ERROR((0x80000000 | (414)),"Render库启用高清图像内部调整策略出错","Render Library to enable high-definition image internal adjustment strategy error"),
    // 设备升级失败 0x8000019F
    NET_ERROR_UPGRADE_FAILED((0x80000000 | (415)),"设备升级失败","Upgrade equipment failure"),
    // 找不到目标设备 0x800001A0
    NET_ERROR_NO_TARGET_DEVICE((0x80000000 | (416)),"找不到目标设备","Can't find the target device"),
    // 找不到验证设备 0x800001A1
    NET_ERROR_NO_VERIFY_DEVICE((0x80000000 | (417)),"找不到验证设备","Can't find the verify device"),
    // 无级联权限 0x800001A2
    NET_ERROR_CASCADE_RIGHTLESS((0x80000000 | (418)),"无级联权限","No cascade permissions"),
    // 低优先级 0x800001A3
    NET_ERROR_LOW_PRIORITY((0x80000000 | (419)),"低优先级","low priority"),
    // 远程设备请求超时 0x800001A4
    NET_ERROR_REMOTE_REQUEST_TIMEOUT((0x80000000 | (420)),"远程设备请求超时","The remote device request timeout"),
    // 输入源超出最大路数限制 0x800001A5
    NET_ERROR_LIMITED_INPUT_SOURCE((0x80000000 | (421)),"输入源超出最大路数限制","Input source beyond maximum route restrictions"),
    // 设置日志打印失败 0x800001A6
    NET_ERROR_SET_LOG_PRINT_INFO((0x80000000 | (422)),"设置日志打印失败","Failed to set log print"),
    // 入参的dwsize字段出错 0x800001A7
    NET_ERROR_PARAM_DWSIZE_ERROR((0x80000000 | (423)),"入参的dwsize字段出错","\"dwSize\" is not initialized in input param"),
    // 电视墙数量超过上限 0x800001A8
    NET_ERROR_LIMITED_MONITORWALL_COUNT((0x80000000 | (424)),"电视墙数量超过上限","TV wall exceed limit"),
    // 部分过程执行失败 0x800001A9
    NET_ERROR_PART_PROCESS_FAILED((0x80000000 | (425)),"部分过程执行失败","Fail to execute part of the process"),
    // 该功能不支持转发 0x800001AA
    NET_ERROR_TARGET_NOT_SUPPORT((0x80000000 | (426)),"该功能不支持转发","Fail to transmit due to not supported by target"),
    // 访问文件失败 0x800001FE
    NET_ERROR_VISITE_FILE((0x80000000 | (510)),"访问文件失败","Access to the file failed"),
    // 设备忙 0x800001FF
    NET_ERROR_DEVICE_STATUS_BUSY((0x80000000 | (511)),"设备忙","Device busy"),
    // 修改密码无权限 0x80000200
    NET_USER_PWD_NOT_AUTHORIZED((0x80000000 | (512)),"修改密码无权限","Fail to change the password"),
    // 密码强度不够 0x80000201
    NET_USER_PWD_NOT_STRONG((0x80000000 | (513)),"密码强度不够","Password strength is not enough"),
    // 没有对应的配置 0x80000202
    NET_ERROR_NO_SUCH_CONFIG((0x80000000 | (514)),"没有对应的配置","No corresponding setup"),
    // 录音失败 0x80000203
    NET_ERROR_AUDIO_RECORD_FAILED((0x80000000 | (515)),"录音失败","Failed to record audio"),
    // 数据发送失败 0x80000204
    NET_ERROR_SEND_DATA_FAILED((0x80000000 | (516)),"数据发送失败","Failed to send out data"),
    // 废弃接口 0x80000205
    NET_ERROR_OBSOLESCENT_INTERFACE((0x80000000 | (517)),"废弃接口","Abandoned port"),
    // 内部缓冲不足 0x80000206
    NET_ERROR_INSUFFICIENT_INTERAL_BUF((0x80000000 | (518)),"内部缓冲不足","Internal buffer is not sufficient"),
    // 修改设备ip时,需要校验密码 0x80000207
    NET_ERROR_NEED_ENCRYPTION_PASSWORD((0x80000000 | (519)),"修改设备ip时,需要校验密码","verify password when changing device IP"),
    // 设备不支持此记录集 0x80000208
    NET_ERROR_NOSUPPORT_RECORD((0x80000000 | (520)),"设备不支持此记录集","device not support the record"),
    // 设备正在升级 0x80000209
    NET_ERROR_DEVICE_IN_UPGRADING((0x80000000 | (521)),"设备正在升级","Device is in upgrading"),
    // 智能分析任务不存在 0x8000020A
    NET_ERROR_ANALYSE_TASK_NOT_EXIST((0x80000000 | (522)),"智能分析任务不存在","Analyse Task Not Exist"),
    // 智能分析任务已经满 0x8000020B
    NET_ERROR_ANALYSE_TASK_FULL((0x80000000 | (523)),"智能分析任务已经满","Analyse Task Full"),
    // 设备重启 0x8000020C
    NET_ERROR_DEVICE_RESTART((0x80000000 | (524)),"设备重启","Device restart"),
    // 设备关机 0x8000020D
    NET_ERROR_DEVICE_SHUTDOWN((0x80000000 | (525)),"设备关机","Device shutdown"),
    // 文件系统错误 0x8000020E
    NET_ERROR_FILE_SYSTEM_ERROR((0x80000000 | (526)),"文件系统错误","File system error"),
    // 硬盘写错误 0x8000020F
    NET_ERROR_HARDDISK_WRITE_ERROR((0x80000000 | (527)),"硬盘写错误","Harddisk write error"),
    // 硬盘读错误 0x80000210
    NET_ERROR_HARDDISK_READ_ERROR((0x80000000 | (528)),"硬盘读错误","Harddisk read error"),
    // 无硬盘记录日志 0x80000211
    NET_ERROR_NO_HARDDISK_RECORD_LOG((0x80000000 | (529)),"无硬盘记录日志","No harddisk record log"),
    // 无工作盘(没有读写盘) 0x80000212
    NET_ERROR_NO_HARDDISK((0x80000000 | (530)),"无工作盘(没有读写盘)","No harddisk"),
    // 硬盘其他错误 0x80000213
    NET_ERROR_HARDDISK_OTHER_ERRORS((0x80000000 | (531)),"硬盘其他错误","Harddisk other errors"),
    // 硬盘坏道轻微错误 0x80000214
    NET_ERROR_HARDDISK_BADSECTORS_MINOR_ERRORS((0x80000000 | (532)),"硬盘坏道轻微错误","Harddisk badsectors minor error"),
    // 硬盘坏道严重错误 0x80000215
    NET_ERROR_HARDDISK_BADSECTORS_CRITICAL_ERRORS((0x80000000 | (533)),"硬盘坏道严重错误","Harddisk badsectors critical error"),
    // 硬盘物理坏道轻微 0x80000216
    NET_ERROR_HARDDISK_PHYSICAL_BADSECTORS_SLIGHT((0x80000000 | (534)),"硬盘物理坏道轻微","Harddisk physical badsectors are slight"),
    // 硬盘物理坏道严重 0x80000217
    NET_ERROR_HARDDISK_PHYSICAL_BADSECTORS_SERIOUS((0x80000000 | (535)),"硬盘物理坏道严重","Harddisk physical badsectors are serious"),
    // 断网报警 0x80000218
    NET_ERROR_NETWORK_DISCONNECTION_ALARM((0x80000000 | (536)),"断网报警","Network disconnection alarm"),
    // 网络断开 0x80000219
    NET_ERROR_NETWORK_DISCONNECTION((0x80000000 | (537)),"网络断开","Network disconnection"),
    // 设置视频源数量超出限制 0x8000021A
    NET_ERROR_SET_SOURCE_EXCEED((0x80000000 | (538)),"设置视频源数量超出限制","Set source exceed"),
    // 上传文件尺寸超出范围(uploadFile方法) 0x8000021B
    NET_ERROR_SIZE_EXCEED((0x80000000 | (539)),"上传文件尺寸超出范围(uploadFile方法)","Upload file size out of range"),
    // 存在日志配置文件，以日志打印配置文件为准，日志打印接口失效 0x8000021C
    NET_ERROR_LOGOPEN_DISABLE((0x80000000 | (540)),"存在日志配置文件，以日志打印配置文件为准，日志打印接口失效","LogCfg is exist, the CLIENT_LogOpen interface is unable."),
    // 封装音频头失败 0x8000021D
    NET_ERROR_STREAM_PACKAGE_ERROR((0x80000000 | (541)),"封装音频头失败","stream package error"),
    // 磁盘读数据限制 0x8000021E
    NET_ERROR_READ_LIMIT((0x80000000 | (542)),"磁盘读数据限制","Read_Limit"),
    // 多画面预览已打开，资源不足，压缩回放失败 0x8000021F
    NET_ERROR_PREVIEWOPENED((0x80000000 | (543)),"多画面预览已打开，资源不足，压缩回放失败","PreviewOpened"),
    // 压缩回放功能已打开，导致失败 0x80000220
    NET_ERROR_COMPRESSOPENED((0x80000000 | (544)),"压缩回放功能已打开，导致失败","CompressOpened"),
    // 未知的压缩失败原因 0x80000221
    NET_ERROR_COMPRESSERROR_UNKNOWN((0x80000000 | (545)),"未知的压缩失败原因","CompressErrorUnknown"),
    // 超出解码能力，导致压缩失败 0x80000222
    NET_ERROR_COMPRESSERROR_OVERDECODE((0x80000000 | (546)),"超出解码能力，导致压缩失败","CompressErrorOverDecode"),
    // 超出压缩能力，导致压缩失败 0x80000223
    NET_ERROR_COMPRESSERROR_OVERENCODE((0x80000000 | (547)),"超出压缩能力，导致压缩失败","CompressErrorOverEncode"),
    // 无原始码流，导致压缩失败 0x80000224
    NET_ERROR_COMPRESSERROR_NONESTREAM((0x80000000 | (548)),"无原始码流，导致压缩失败","CompressErrorNoneStream"),
    // 压缩通道所在的从片掉线，导致压缩失败 0x80000225
    NET_ERROR_COMPRESSERROR_CHIPOFFLINE((0x80000000 | (549)),"压缩通道所在的从片掉线，导致压缩失败","CompressErrorChipOffline"),
    // 通道未添加 0x80000226
    NET_ERROR_CHANNELNOTADD((0x80000000 | (550)),"通道未添加","ChannelNotAdd"),
    // 删除目标库失败 0x80000386
    NET_ERROR_FACE_RECOGNITION_SERVER_DELETE_GROUP_ERROR((0x80000000 | (900)),"删除目标库失败","Target recognition server delete group error"),
    // 命名格式错误 0x80000387
    NET_ERROR_FACE_RECOGNITION_SERVER_NAME_FORMAT_ERROR((0x80000000 | (901)),"命名格式错误","Target recognition server name format error"),
    // 图片保存路径未设置 0x80000388
    NET_ERROR_FACE_RECOGNITION_SERVER_FILEPATH_NOT_SET((0x80000000 | (902)),"图片保存路径未设置","Target recognition server file path not set"),
    // 添加小区名字重复 0x80000389
    NET_ERROR_FACE_RECOGNITION_SERVER_AREAS_NAME_REPEAT((0x80000000 | (903)),"添加小区名字重复","Areas Name Repeat"),
    // 添加小区ID重复 0x8000038A
    NET_ERROR_FACE_RECOGNITION_SERVER_AREAS_ID_REPEAT((0x80000000 | (904)),"添加小区ID重复","Areas ID Repeat"),
    // 添加小区通道重复 0x8000038B
    NET_ERROR_FACE_RECOGNITION_SERVER_AREAS_CHANNEL_REPEAT((0x80000000 | (905)),"添加小区通道重复","Areas Channel Repeat"),
    // 导出任务超出数量限制 0x8000038C
    NET_ERROR_FACE_RECOGNITION_SERVER_EXPORT_TASK_COUNT_EXCEED((0x80000000 | (906)),"导出任务超出数量限制","Export Task Count Exceed"),
    // 设备不支持以图搜图 0x8000038D
    NET_ERROR_FACE_RECOGNITION_SERVER_PIC_SEARCH_NOT_SUPPORT((0x80000000 | (907)),"设备不支持以图搜图","Pic Search Not Support"),
    // 设备不支持从大图中检测指定目标图片 0x8000038E
    NET_ERROR_FACE_RECOGNITION_SERVER_DETECT_MULTI_FACE_NOT_SUPPORT((0x80000000 | (908)),"设备不支持从大图中检测指定目标图片","Detect Multi Face Not Support"),
    // 人员已经存在 0x8000038F
    NET_ERROR_FACE_RECOGNITION_SERVER_PERSON_ALREADY_EXISTS((0x80000000 | (909)),"人员已经存在","The person already exists"),
    // 数据序列化错误 0x800003F2
    NET_ERROR_SERIALIZE_ERROR((0x80000000 | (1010)),"数据序列化错误","Failed to serialize data"),
    // 数据反序列化错误 0x800003F3
    NET_ERROR_DESERIALIZE_ERROR((0x80000000 | (1011)),"数据反序列化错误","Failed to deserialize data"),
    // 该无线ID已存在 0x800003F4
    NET_ERROR_LOWRATEWPAN_ID_EXISTED((0x80000000 | (1012)),"该无线ID已存在","the wireless id is already existed"),
    // 无线ID数量已超限 0x800003F5
    NET_ERROR_LOWRATEWPAN_ID_LIMIT((0x80000000 | (1013)),"无线ID数量已超限","the wireless id limited"),
    // 无线异常添加 0x800003F6
    NET_ERROR_LOWRATEWPAN_ID_ABNORMAL((0x80000000 | (1014)),"无线异常添加","add the wireless id abnormaly"),
    // 加密数据失败 0x800003F7
    NET_ERROR_ENCRYPT((0x80000000 | (1015)),"加密数据失败","encrypt data fail"),
    // 新密码不合规范 0x800003F8
    NET_ERROR_PWD_ILLEGAL((0x80000000 | (1016)),"新密码不合规范","new password illegal"),
    // 设备已经初始化 0x800003F9
    NET_ERROR_DEVICE_ALREADY_INIT((0x80000000 | (1017)),"设备已经初始化","device is already init"),
    // 安全码错误 0x800003FA
    NET_ERROR_SECURITY_CODE((0x80000000 | (1018)),"安全码错误","security code check out fail"),
    // 安全码超出有效期 0x800003FB
    NET_ERROR_SECURITY_CODE_TIMEOUT((0x80000000 | (1019)),"安全码超出有效期","security code out of time"),
    // 获取密码规范失败 0x800003FC
    NET_ERROR_GET_PWD_SPECI((0x80000000 | (1020)),"获取密码规范失败","get passwd specification fail"),
    // 无权限进行该操作 0x800003FD
    NET_ERROR_NO_AUTHORITY_OF_OPERATION((0x80000000 | (1021)),"无权限进行该操作","no authority of operation"),
    // 解密数据失败 0x800003FE
    NET_ERROR_DECRYPT((0x80000000 | (1022)),"解密数据失败","decrypt data fail"),
    // 2D code校验失败 0x800003FF
    NET_ERROR_2D_CODE((0x80000000 | (1023)),"2D code校验失败","2D code check out fail"),
    // 非法的RPC请求 0x80000400
    NET_ERROR_INVALID_REQUEST((0x80000000 | (1024)),"非法的RPC请求","invalid request"),
    // 密码重置功能已关闭 0x80000401
    NET_ERROR_PWD_RESET_DISABLE((0x80000000 | (1025)),"密码重置功能已关闭","pwd reset unable"),
    // 显示私有数据，比如规则框等失败 0x80000402
    NET_ERROR_PLAY_PRIVATE_DATA((0x80000000 | (1026)),"显示私有数据，比如规则框等失败","failed to display private data,such as rule box"),
    // 机器人操作失败 0x80000403
    NET_ERROR_ROBOT_OPERATE_FAILED((0x80000000 | (1027)),"机器人操作失败","robot operate failed"),
    // 图片大小超限 0x80000404
    NET_ERROR_PHOTOSIZE_EXCEEDSLIMIT((0x80000000 | (1028)),"图片大小超限","photosize exceeds limit"),
    // 用户ID不存在 0x80000405
    NET_ERROR_USERID_INVALID((0x80000000 | (1029)),"用户ID不存在","userid invalid"),
    // 照片特征值提取失败 0x80000406
    NET_ERROR_EXTRACTFEATURE_FAILED((0x80000000 | (1030)),"照片特征值提取失败","photo extract feature failed"),
    // 照片已存在 0x80000407
    NET_ERROR_PHOTO_EXIST((0x80000000 | (1031)),"照片已存在","photo exist"),
    // 照片数量超过上限 0x80000408
    NET_ERROR_PHOTO_OVERFLOW((0x80000000 | (1032)),"照片数量超过上限","photo over flow"),
    // 通道已经打开 0x80000409
    NET_ERROR_CHANNEL_ALREADY_OPENED((0x80000000 | (1033)),"通道已经打开","channel has already been opened"),
    // 创建套接字失败 0x8000040A
    NET_ERROR_CREATE_SOCKET((0x80000000 | (1034)),"创建套接字失败","create socket error"),
    // 通道号错误 0x8000040B
    NET_ERROR_CHANNEL_NUM((0x80000000 | (1035)),"通道号错误","invalid channel num"),
    // 图片格式错误 0x8000040C
    NET_ERROR_PHOTO_FORMAT((0x80000000 | (1036)),"图片格式错误","photo format error"),
    // 内部错误(比如：相关硬件问题，获取公钥失败，内部接口调用失败，写文件失败等等) 0x8000040D
    NET_ERROR_DIGITAL_CERTIFICATE_INTERNAL_ERROR((0x80000000 | (1037)),"内部错误(比如：相关硬件问题，获取公钥失败，内部接口调用失败，写文件失败等等)","Internal error"),
    // 获取设备ID失败 0x8000040E
    NET_ERROR_DIGITAL_CERTIFICATE_GET_ID_FAILED((0x80000000 | (1038)),"获取设备ID失败","Get ID failed"),
    // 证书文件非法(格式不支持或者不是证书文件) 0x8000040F
    NET_ERROR_DIGITAL_CERTIFICATE_IMPORT_ILLEGAL((0x80000000 | (1039)),"证书文件非法(格式不支持或者不是证书文件)","Import illegal"),
    // 证书sn重复或错误或不规范 0x80000410
    NET_ERROR_DIGITAL_CERTIFICATE_SN_ERROR((0x80000000 | (1040)),"证书sn重复或错误或不规范","SN error"),
    // 证书commonName非法(本地设备证书与系统中的不匹配devid_cryptoID,或者对端的不符合规则(devid_crytoID)) 0x80000411
    NET_ERROR_DIGITAL_CERTIFICATE_COMMON_NAME_ILLEGAL((0x80000000 | (1041)),"证书commonName非法(本地设备证书与系统中的不匹配devid_cryptoID,或者对端的不符合规则(devid_crytoID))","Cert common name illegal"),
    // 根证书未导入或不存在 0x80000412
    NET_ERROR_DIGITAL_CERTIFICATE_NO_ROOT_CERT((0x80000000 | (1042)),"根证书未导入或不存在","No root cert"),
    // 证书被吊销 0x80000413
    NET_ERROR_DIGITAL_CERTIFICATE_CERT_REVOKED((0x80000000 | (1043)),"证书被吊销","Cert revoked"),
    // 证书不可用或未生效或已过期 0x80000414
    NET_ERROR_DIGITAL_CERTIFICATE_CERT_INVALID((0x80000000 | (1044)),"证书不可用或未生效或已过期","Cert invalid"),
    // 证书签名不匹配 0x80000415
    NET_ERROR_DIGITAL_CERTIFICATE_CERT_ERROR_SIGN((0x80000000 | (1045)),"证书签名不匹配","Cert error sign"),
    // 超出证书导入上限 0x80000416
    NET_ERROR_DIGITAL_CERTIFICATE_COUNTS_UPPER_LIMIT((0x80000000 | (1046)),"超出证书导入上限","Counts upper limit"),
    // 证书文件不存在(导出证书或者获取对应证书的公钥) 0x80000417
    NET_ERROR_DIGITAL_CERTIFICATE_CERT_NO_EXIST((0x80000000 | (1047)),"证书文件不存在(导出证书或者获取对应证书的公钥)","Cert no exist"),
    // 默认搜索端口无法使用（5050,37810） 0x80000418
    NET_ERROR_DEFULAT_SEARCH_PORT((0x80000000 | (1048)),"默认搜索端口无法使用（5050,37810）","default search port can't use(5050,37810)"),
    // 批量添加目标停止 0x80000419
    NET_ERROR_FACE_RECOGNITION_SERVER_MULTI_APPEND_STOUP((0x80000000 | (1049)),"批量添加目标停止","Target recognition server multi append stop"),
    // 批量添加目标失败 0x8000041A
    NET_ERROR_FACE_RECOGNITION_SERVER_MULTI_APPEND_ERROR((0x80000000 | (1050)),"批量添加目标失败","Target recognition server multi append error"),
    // 组ID超过最大值 0x8000041B
    NET_ERROR_FACE_RECOGNITION_SERVER_GROUP_ID_EXCEED((0x80000000 | (1051)),"组ID超过最大值","Target recognition server group id exceed"),
    // 组ID不存在或为空 0x8000041C
    NET_ERROR_FACE_RECOGNITION_SERVER_GROUP_ID_NOT_IN_REGISTER_GROUP((0x80000000 | (1052)),"组ID不存在或为空","Target recognition server group id not in register group"),
    // 无图片数据 0x8000041D
    NET_ERROR_FACE_RECOGNITION_SERVER_PICTURE_NOT_FOUND((0x80000000 | (1053)),"无图片数据","Target recognition server picture not found"),
    // 生成组ID超出范围 0x8000041E
    NET_ERROR_FACE_RECOGNITION_SERVER_GENERATE_GROUP_ID_FAILED((0x80000000 | (1054)),"生成组ID超出范围","Target recognition server generate group id failed"),
    // 设置配置失败 0x8000041F
    NET_ERROR_FACE_RECOGNITION_SERVER_SET_CONFIG_FAILED((0x80000000 | (1055)),"设置配置失败","Target recognition server set config failed"),
    // 图片文件打开失败 0x80000420
    NET_ERROR_FACE_RECOGNITION_SERVER_FILE_OPEN_FAILED((0x80000000 | (1056)),"图片文件打开失败","Target recognition server file open failed"),
    // 图片文件读取失败 0x80000421
    NET_ERROR_FACE_RECOGNITION_SERVER_FILE_READ_FAILED((0x80000000 | (1057)),"图片文件读取失败","Target recognition server file read failed"),
    // 图片文件写入失败 0x80000422
    NET_ERROR_FACE_RECOGNITION_SERVER_FILE_WRITE_FAILED((0x80000000 | (1058)),"图片文件写入失败","Target recognition server file write failed"),
    // 图片分辨率异常 0x80000423
    NET_ERROR_FACE_RECOGNITION_SERVER_PICTURE_DPI_ERROR((0x80000000 | (1059)),"图片分辨率异常","Target recognition server picture dpi error"),
    // 图片像素异常 0x80000424
    NET_ERROR_FACE_RECOGNITION_SERVER_PICTURE_PX_ERROR((0x80000000 | (1060)),"图片像素异常","Target recognition server picture px error"),
    // 图片大小不对 0x80000425
    NET_ERROR_FACE_RECOGNITION_SERVER_PICTURE_SIZE_ERROR((0x80000000 | (1061)),"图片大小不对","Target recognition server picture size error"),
    // 数据库操作失败 0x80000426
    NET_ERROR_FACE_RECOGNITION_SERVER_DATA_BASE_ERROR((0x80000000 | (1062)),"数据库操作失败","Target recognition server database error"),
    // 人员数量超过限制 0x80000427
    NET_ERROR_FACE_RECOGNITION_SERVER_FACE_MAX_NUM((0x80000000 | (1063)),"人员数量超过限制","Target recognition server target max num"),
    // 生日日期格式错误 0x80000428
    NET_ERROR_FACE_RECOGNITION_SERVER_BIRTH_DAY_FORMAT_ERROR((0x80000000 | (1064)),"生日日期格式错误","Target recognition server birthday format error"),
    // 人员UID不存在或为空 0x80000429
    NET_ERROR_FACE_RECOGNITION_SERVER_UID_ERROR((0x80000000 | (1065)),"人员UID不存在或为空","Target recognition server uid error"),
    // 令牌不存在或为空 0x8000042A
    NET_ERROR_FACE_RECOGNITION_SERVER_TOKEN_ERROR((0x80000000 | (1066)),"令牌不存在或为空","Target recognition server token error"),
    // 查询起始数大于总数 0x8000042B
    NET_ERROR_FACE_RECOGNITION_SERVER_BEGIN_NUM_OVER_RUN((0x80000000 | (1067)),"查询起始数大于总数","Target recognition server begin num over run"),
    // 需手动建模人数为0 0x8000042C
    NET_ERROR_FACE_RECOGNITION_SERVER_ABSTRACT_NUM_ZERO((0x80000000 | (1068)),"需手动建模人数为0","Target recognition server abstract num zero"),
    // 建模分析器启动失败 0x8000042D
    NET_ERROR_FACE_RECOGNITION_SERVER_ABSTRACT_INIT_ERROR((0x80000000 | (1069)),"建模分析器启动失败","Target recognition server abstract init error"),
    // 设备正在自动建模 0x8000042E
    NET_ERROR_FACE_RECOGNITION_SERVER_AUTO_ABSTRACT_STATE((0x80000000 | (1070)),"设备正在自动建模","Target recognition server auto abstract state"),
    // 设备正在手动建模 0x8000042F
    NET_ERROR_FACE_RECOGNITION_SERVER_ABSTRACT_STATE((0x80000000 | (1071)),"设备正在手动建模","Target recognition server abstract state"),
    // 设备正在导入导出 0x80000430
    NET_ERROR_FACE_RECOGNITION_SERVER_IM_EX_STATE((0x80000000 | (1072)),"设备正在导入导出","Target recognition server im ex state"),
    // 图片写入失败 0x80000431
    NET_ERROR_FACE_RECOGNITION_SERVER_PIC_WRITE_FAILED((0x80000000 | (1073)),"图片写入失败","Target recognition server pic write failed"),
    // 超出目标库空间大小限制 0x80000432
    NET_ERROR_FACE_RECOGNITION_SERVER_GROUP_SPACE_EXCEED((0x80000000 | (1074)),"超出目标库空间大小限制","Target recognition server group space exceed"),
    // 超出目标库图片数量限制 0x80000433
    NET_ERROR_FACE_RECOGNITION_SERVER_GROUP_PIC_COUNT_EXCEED((0x80000000 | (1075)),"超出目标库图片数量限制","Target recognition server group pic count exceed"),
    // 目标库不存在 0x80000434
    NET_ERROR_FACE_RECOGNITION_SERVER_GROUP_NOT_FOUND((0x80000000 | (1076)),"目标库不存在","Target recognition server group not found"),
    // 查询原目标库数据结果无效 0x80000435
    NET_ERROR_FACE_RECOGNITION_SERVER_FIND_RECORDS_ERROR((0x80000000 | (1077)),"查询原目标库数据结果无效","Target recognition server find record error"),
    // 删除原目标库数据失败 0x80000436
    NET_ERROR_FACE_RECOGNITION_SERVER_DELETE_PERSON_ERROR((0x80000000 | (1078)),"删除原目标库数据失败","Target recognition server delete person error"),
    // 设备解析协议错误 0x80000437
    NET_ERROR_DEVICE_PARSE_PROTOCOL((0x80000000 | (1079)),"设备解析协议错误","device parses protocol error"),
    // 设备返回无效请求 0x80000438
    NET_ERROR_DEVICE_INVALID_REQUEST((0x80000000 | (1080)),"设备返回无效请求","device returns invalid request"),
    // 设备内部错误 0x80000439
    NET_ERROR_DEVICE_INTERNAL_ERROR((0x80000000 | (1081)),"设备内部错误","device internal error"),
    // 设备内部请求超时 0x8000043A
    NET_ERROR_DEVICE_REQUEST_TIMEOUT((0x80000000 | (1082)),"设备内部请求超时","device internal request timeout"),
    // 设备保活失败 0x8000043B
    NET_ERROR_DEVICE_KEEPALIVE_FAIL((0x80000000 | (1083)),"设备保活失败","device keeps alive fail"),
    // 设备网络错误 0x8000043C
    NET_ERROR_DEVICE_NETWORK_ERROR((0x80000000 | (1084)),"设备网络错误","device network error"),
    // 设备内部未知错误 0x8000043D
    NET_ERROR_DEVICE_UNKNOWN_ERROR((0x80000000 | (1085)),"设备内部未知错误","device unknown error"),
    // 设备组件接口没有找到 0x8000043E
    NET_ERROR_DEVICE_COM_INTERFACE_NOTFOUND((0x80000000 | (1086)),"设备组件接口没有找到","device not found com interface"),
    // 设备组件实现没有找到 0x8000043F
    NET_ERROR_DEVICE_COM_IMPLEMENT_NOTFOUND((0x80000000 | (1087)),"设备组件实现没有找到","device not found com implement"),
    // 设备接入组件没有找到 0x80000440
    NET_ERROR_DEVICE_COM_NOTFOUND((0x80000000 | (1088)),"设备接入组件没有找到","device not found client component"),
    // 设备接入组件实例不存在 0x80000441
    NET_ERROR_DEVICE_COM_INSTANCE_NOTEXIST((0x80000000 | (1089)),"设备接入组件实例不存在","device not found client com instance"),
    // 设备组件工厂创建组件失败 0x80000442
    NET_ERROR_DEVICE_CREATE_COM_FAIL((0x80000000 | (1090)),"设备组件工厂创建组件失败","device creates com fail"),
    // 设备组件工厂获取组件实例失败 0x80000443
    NET_ERROR_DEVICE_GET_COM_FAIL((0x80000000 | (1091)),"设备组件工厂获取组件实例失败","device gets com instance fail"),
    // 设备业务请求不被接受 0x80000444
    NET_ERROR_DEVICE_BAD_REQUEST((0x80000000 | (1092)),"设备业务请求不被接受","device can not accept bad request"),
    // 设备已经在处理请求，不接受重复请求 0x80000445
    NET_ERROR_DEVICE_REQUEST_IN_PROGRESS((0x80000000 | (1093)),"设备已经在处理请求，不接受重复请求","device does not accept repeat request when in progressing"),
    // 设备资源不足 0x80000446
    NET_ERROR_DEVICE_LIMITED_RESOURCE((0x80000000 | (1094)),"设备资源不足","device limited resource"),
    // 设备业务超时 0x80000447
    NET_ERROR_DEVICE_BUSINESS_TIMEOUT((0x80000000 | (1095)),"设备业务超时","device business timeout"),
    // 设备接收过多请求 0x80000448
    NET_ERROR_DEVICE_TOO_MANY_REQUESTS((0x80000000 | (1096)),"设备接收过多请求","device accepts too many requests"),
    // 设备未准备就绪，不接受业务请求 0x80000449
    NET_ERROR_DEVICE_NOT_ALREADY((0x80000000 | (1097)),"设备未准备就绪，不接受业务请求","device not already and cannot accept request"),
    // 设备录像查询超时 0x8000044A
    NET_ERROR_DEVICE_SEARCHRECORD_TIMEOUT((0x80000000 | (1098)),"设备录像查询超时","device searchs record timeout"),
    // 设备录像查询时间无效 0x8000044B
    NET_ERROR_DEVICE_SEARCHTIME_INVALID((0x80000000 | (1099)),"设备录像查询时间无效","device checks search record time invalid"),
    // 设备校验SSID无效 0x8000044C
    NET_ERROR_DEVICE_SSID_INVALID((0x80000000 | (1100)),"设备校验SSID无效","device checks SSID invalid"),
    // 设备校验通道号或码流类型无效 0x8000044D
    NET_ERROR_DEVICE_CHANNEL_STREAMTYPE_ERROR((0x80000000 | (1101)),"设备校验通道号或码流类型无效","device checks channel or streamtype invalid"),
    // 设备不支持该码流打包格式 0x8000044E
    NET_ERROR_DEVICE_STREAM_PACKINGFORMAT_UNSUPPORT((0x80000000 | (1102)),"设备不支持该码流打包格式","device does not support stream packing format"),
    // 设备不支持该语音编码格式 0x8000044F
    NET_ERROR_DEVICE_AUDIO_ENCODINGFORMAT_UNSUPPORT((0x80000000 | (1103)),"设备不支持该语音编码格式","device does not support audio encoding format"),
    // 校验请求安全码失败,可使用本地GUI方式重置密码 0x80000450
    NET_ERROR_SECURITY_ERROR_SUPPORT_GUI((0x80000000 | (1104)),"校验请求安全码失败,可使用本地GUI方式重置密码","check request security failed, using local GUI reset password"),
    // 校验请求安全码失败,可使用APP、configtool工具重置密码 0x80000451
    NET_ERROR_SECURITY_ERROR_SUPPORT_MULT((0x80000000 | (1105)),"校验请求安全码失败,可使用APP、configtool工具重置密码","check request security failed, using private APP or configtool reset password"),
    // 校验请求安全码失败,可登陆Web页面重置密码 0x80000452
    NET_ERROR_SECURITY_ERROR_SUPPORT_UNIQUE((0x80000000 | (1106)),"校验请求安全码失败,可登陆Web页面重置密码","check request security failed, using Web reset password"),
    // 转码库缺失 0x80000453
    NET_ERROR_STREAMCONVERTOR_DEFECT((0x80000000 | (1107)),"转码库缺失","streamconvertor defect"),
    // 调用加密库产生安全码失败 0x80000454
    NET_ERROR_SECURITY_GENERATE_SAFE_CODE((0x80000000 | (1108)),"调用加密库产生安全码失败","generate safe code failed"),
    // 获取联系方式失败 0x80000455
    NET_ERROR_SECURITY_GET_CONTACT((0x80000000 | (1109)),"获取联系方式失败","get contact failed"),
    // 获取重置密码的二维码信息失败 0x80000456
    NET_ERROR_SECURITY_GET_QRCODE((0x80000000 | (1110)),"获取重置密码的二维码信息失败","get QR code information failed of reset passwd"),
    // 设备未初始化,无法重置 0x80000457
    NET_ERROR_SECURITY_CANNOT_RESET((0x80000000 | (1111)),"设备未初始化,无法重置","device uninitialized, can't reset"),
    // 不支持设置该种联系方式,如只支持设置手机号，却请求设置邮箱 0x80000458
    NET_ERROR_SECURITY_NOT_SUPPORT_CONTACT_MODE((0x80000000 | (1112)),"不支持设置该种联系方式,如只支持设置手机号，却请求设置邮箱","unsupported contact mode"),
    // 对端响应超时 0x80000459
    NET_ERROR_SECURITY_RESPONSE_TIMEOUT((0x80000000 | (1113)),"对端响应超时","server response time out"),
    // 尝试校验AuthCode次数过多，禁止校验 0x8000045A
    NET_ERROR_SECURITY_AUTHCODE_FORBIDDEN((0x80000000 | (1114)),"尝试校验AuthCode次数过多，禁止校验","had check AuthCode too much, forbid check"),
    // (虚拟转码)登陆远程设备失败 0x8000045B
    NET_ERROR_TRANCODE_LOGIN_REMOTE_DEV((0x80000000 | (1115)),"(虚拟转码)登陆远程设备失败","(virtual transcode)login remote device failed"),
    // (虚拟转码)没有可用的通道资源 0x8000045C
    NET_ERROR_TRANCODE_NOFREE_CHANNEL((0x80000000 | (1116)),"(虚拟转码)没有可用的通道资源","(virtual transcode)no free virtual channel"),
    // VK信息解密失败 0x8000045D
    NET_ERROR_VK_INFO_DECRYPT_FAILED((0x80000000 | (1117)),"VK信息解密失败","VK info decrypt failed"),
    // VK信息解析失败 0x8000045E
    NET_ERROR_VK_INFO_DESERIALIZE_FAILED((0x80000000 | (1118)),"VK信息解析失败","VK info deserial failed"),
    // SDK GDPR功能未使能 0x8000045F
    NET_ERROR_GDPR_ABILITY_NOT_ENABLE((0x80000000 | (1119)),"SDK GDPR功能未使能","SDK GDPR ability not enable"),
    // 门禁快速复核:无权限 0x80000460
    NET_ERROR_FAST_CHECK_NO_AUTH((0x80000000 | (1120)),"门禁快速复核:无权限","access control fast check:no authority"),
    // 门禁快速复核:文件不存在 0x80000461
    NET_ERROR_FAST_CHECK_NO_FILE((0x80000000 | (1121)),"门禁快速复核:文件不存在","access control fast check:file does not exist"),
    // 门禁快速复核:文件准备失败 0x80000462
    NET_ERROR_FAST_CHECK_FILE_FAIL((0x80000000 | (1122)),"门禁快速复核:文件准备失败","access control fast check:fail to prepare file"),
    // 门禁快速复核:系统忙 0x80000463
    NET_ERROR_FAST_CHECK_BUSY((0x80000000 | (1123)),"门禁快速复核:系统忙","access control fast check:system is busy"),
    // 门禁快速复核:未定义密码, 不允许导出 0x80000464
    NET_ERROR_FAST_CHECK_NO_PASSWORD((0x80000000 | (1124)),"门禁快速复核:未定义密码, 不允许导出","access control fast check:no password, and import is not allowed"),
    // 门禁快速导入:发送门禁数据失败 0x80000465
    NET_ERROR_IMPORT_ACCESS_SEND_FAILD((0x80000000 | (1125)),"门禁快速导入:发送门禁数据失败","access control fast import:fail to send access control data"),
    // 门禁快速导入:系统忙, 已经有导入任务 0x80000466
    NET_ERROR_IMPORT_ACCESS_BUSY((0x80000000 | (1126)),"门禁快速导入:系统忙, 已经有导入任务","access control fast import:system is busy"),
    // 门禁快速导入:数据包检验失败 0x80000467
    NET_ERROR_IMPORT_ACCESS_DATAERROR((0x80000000 | (1127)),"门禁快速导入:数据包检验失败","access control fast import:packet data check failed"),
    // 门禁快速导入:数据包非法 0x80000468
    NET_ERROR_IMPORT_ACCESS_DATAINVALID((0x80000000 | (1128)),"门禁快速导入:数据包非法","access control fast import:illegal packet data"),
    // 门禁快速导入:同步失败,数据库无法生成 0x80000469
    NET_ERROR_IMPORT_ACCESS_SYNC_FALID((0x80000000 | (1129)),"门禁快速导入:同步失败,数据库无法生成","access control fast import:fail to synchronization"),
    // 门禁快速导入:数据库已满, 无法导入 0x8000046A
    NET_ERROR_IMPORT_ACCESS_DBFULL((0x80000000 | (1130)),"门禁快速导入:数据库已满, 无法导入","access control fast import:data base is full"),
    // 门禁快速导入:存储空间已满, 无法导入 0x8000046B
    NET_ERROR_IMPORT_ACCESS_SDFULL((0x80000000 | (1131)),"门禁快速导入:存储空间已满, 无法导入","access control fast import:SD is full"),
    // 门禁快速导入:导入压缩包密码不对 0x8000046C
    NET_ERROR_IMPORT_ACCESS_CIPHER_ERROR((0x80000000 | (1132)),"门禁快速导入:导入压缩包密码不对","access control fast import:password error"),
    // 参数无效 0x8000046D
    NET_ERROR_INVALID_PARAM((0x80000000 | (1133)),"参数无效","invalid param"),
    // 密码无效 0x8000046E
    NET_ERROR_INVALID_PASSWORD((0x80000000 | (1134)),"密码无效","invalid password"),
    // 信息无效 0x8000046F
    NET_ERROR_INVALID_FINGERPRINT((0x80000000 | (1135)),"信息无效","invalid infomation"),
    // 目标无效 0x80000470
    NET_ERROR_INVALID_FACE((0x80000000 | (1136)),"目标无效","invalid target"),
    // 卡无效 0x80000471
    NET_ERROR_INVALID_CARD((0x80000000 | (1137)),"卡无效","invalid card"),
    // 用户无效 0x80000472
    NET_ERROR_INVALID_USER((0x80000000 | (1138)),"用户无效","invalid user"),
    // 能力集子服务获取失败 0x80000473
    NET_ERROR_GET_SUBSERVICE((0x80000000 | (1139)),"能力集子服务获取失败","device get sub service fail"),
    // 获取组件的方法集失败 0x80000474
    NET_ERROR_GET_METHOD((0x80000000 | (1140)),"获取组件的方法集失败","device get method fail"),
    // 获取资源实体能力集失败 0x80000475
    NET_ERROR_GET_SUBCAPS((0x80000000 | (1141)),"获取资源实体能力集失败","device get sub caps fail"),
    // 已达插入上限 0x80000476
    NET_ERROR_UPTO_INSERT_LIMIT((0x80000000 | (1142)),"已达插入上限","up to insert limit"),
    // 已达最大插入速度 0x80000477
    NET_ERROR_UPTO_MAX_INSERT_RATE((0x80000000 | (1143)),"已达最大插入速度","up tp max insert rate"),
    // 清除信息数据失败 0x80000478
    NET_ERROR_ERASE_FINGERPRINT((0x80000000 | (1144)),"清除信息数据失败","erase infomation data fail"),
    // 清除目标数据失败 0x80000479
    NET_ERROR_ERASE_FACE((0x80000000 | (1145)),"清除目标数据失败","erase target data fail"),
    // 清除卡数据失败 0x8000047A
    NET_ERROR_ERASE_CARD((0x80000000 | (1146)),"清除卡数据失败","erase card data fail"),
    // 没有记录 0x8000047B
    NET_ERROR_NO_RECORD((0x80000000 | (1147)),"没有记录","no record"),
    // 查找到最后，没有更多记录 0x8000047C
    NET_ERROR_NOMORE_RECORDS((0x80000000 | (1148)),"查找到最后，没有更多记录","no more records"),
    // 下发卡或信息时，数据重复 0x8000047D
    NET_ERROR_RECORD_ALREADY_EXISTS((0x80000000 | (1149)),"下发卡或信息时，数据重复","record already exist"),
    // 超过个人最大信息记录数 0x8000047E
    NET_ERROR_EXCEED_MAX_FINGERPRINT_PERUSER((0x80000000 | (1150)),"超过个人最大信息记录数","exceed max infomation per user"),
    // 超过个人最大卡片记录数 0x8000047F
    NET_ERROR_EXCEED_MAX_CARD_PERUSER((0x80000000 | (1151)),"超过个人最大卡片记录数","exceed max card per user"),
    // 超过门禁管理员个数限制 0x80000480
    NET_ERROR_EXCEED_ADMINISTRATOR_LIMIT((0x80000000 | (1152)),"超过门禁管理员个数限制","exceed administrator limit"),
    // 设备不支持高安全等级登录 0x80000481
    NET_LOGIN_ERROR_DEVICE_NOT_SUPPORT_HIGHLEVEL_SECURITY_LOGIN((0x80000000 | (1153)),"设备不支持高安全等级登录","device not support high level security login"),
    // 设备只支持高安全等级登录 0x80000482
    NET_LOGIN_ERROR_DEVICE_ONLY_SUPPORT_HIGHLEVEL_SECURITY_LOGIN((0x80000000 | (1154)),"设备只支持高安全等级登录","device only support high level security login"),
    // 表示此视频通道处于离线，拉流失败 0x80000483
    NET_ERROR_VIDEO_CHANNEL_OFFLINE((0x80000000 | (1155)),"表示此视频通道处于离线，拉流失败","current video channel is offline, play failed"),
    // 用户编号不规范 0x80000484
    NET_ERROR_USERID_FORMAT_INCORRECT((0x80000000 | (1156)),"用户编号不规范","The format of the User ID is incorrect - should be all digital numbers"),
    // 找不到该SN对应的通道 0x80000485
    NET_ERROR_CANNOT_FIND_CHANNEL_RELATE_TO_SN((0x80000000 | (1157)),"找不到该SN对应的通道","The corresponding channel to this serial number could not be found"),
    // 该通道的任务队列满 0x80000486
    NET_ERROR_TASK_QUEUE_OF_CHANNEL_IS_FULL((0x80000000 | (1158)),"该通道的任务队列满","The task queue for this channel is full"),
    // 申请不到新的用户信息(权限)块 0x80000487
    NET_ERROR_APPLY_USER_INFO_BLOCK_FAIL((0x80000000 | (1159)),"申请不到新的用户信息(权限)块","Applying for new user information blocks failed"),
    // 用户密码数量超过限制 0x80000488
    NET_ERROR_EXCEED_MAX_PASSWD_PERUSER((0x80000000 | (1160)),"用户密码数量超过限制","The maximum number of user's password exceeded"),
    // 设备内部异常引起协议解析错误 0x80000489
    NET_ERROR_PARSE_PROTOCOL((0x80000000 | (1161)),"设备内部异常引起协议解析错误","Internal error when parsing protocol packages"),
    // 卡号已存在 0x8000048A
    NET_ERROR_CARD_NUM_EXIST((0x80000000 | (1162)),"卡号已存在","card num already exist"),
    // 信息已存在 0x8000048B
    NET_ERROR_FINGERPRINT_EXIST((0x80000000 | (1163)),"信息已存在","infomation already exist"),
    // 打开播放组失败 0x8000048C
    NET_ERROR_OPEN_PLAYGROUP_FAIL((0x80000000 | (1164)),"打开播放组失败","open play group fail"),
    // 已位于播放组中 0x8000048D
    NET_ERROR_ALREADY_IN_PLAYGROUP((0x80000000 | (1165)),"已位于播放组中","play handle already in play group"),
    // 查询播放组时间失败 0x8000048E
    NET_ERROR_QUERY_PLAYGROUP_TIME_FAIL((0x80000000 | (1166)),"查询播放组时间失败","query play group time fail"),
    // 设置播放组基准通道失败 0x8000048F
    NET_ERROR_SET_PLAYGROUP_BASECHANNEL_FAIL((0x80000000 | (1167)),"设置播放组基准通道失败","set play group base channel fail"),
    // 设置播放组方向失败 0x80000490
    NET_ERROR_SET_PLAYGROUP_DIRECTION_FAIL((0x80000000 | (1168)),"设置播放组方向失败","set play group direction fail"),
    // 设置播放组速度失败 0x80000491
    NET_ERROR_SET_PLAYGROUP_SPEED_FAIL((0x80000000 | (1169)),"设置播放组速度失败","set play group speed fail"),
    // 加入播放组失败 0x80000492
    NET_ERROR_ADD_PLAYGROUP_FAIL((0x80000000 | (1170)),"加入播放组失败","add play handle to play group fail"),
    // 导出AOL日志:无权限 0x80000493
    NET_ERROR_EXPORT_AOL_LOGFILE_NO_AUTH((0x80000000 | (1171)),"导出AOL日志:无权限","export aol log file:no auth"),
    // 导出AOL日志:文件不存在 0x80000494
    NET_ERROR_EXPORT_AOL_LOGFILE_NO_FILE((0x80000000 | (1172)),"导出AOL日志:文件不存在","export aol log file:no file"),
    // 导出AOL日志:文件准备失败 0x80000495
    NET_ERROR_EXPORT_AOL_LOGFILE_FILE_FAIL((0x80000000 | (1173)),"导出AOL日志:文件准备失败","export aol log file:prepare file fail"),
    // 导出AOL日志:系统忙 0x80000496
    NET_ERROR_EXPORT_AOL_LOGFILE_BUSY((0x80000000 | (1174)),"导出AOL日志:系统忙","export aol log file:device busy"),
    // License为空 0x80000497
    NET_ERROR_EMPTY_LICENSE((0x80000000 | (1175)),"License为空","Empty license"),
    // 不支持该模式 0x80000498
    NET_ERROR_UNSUPPORTED_MODE((0x80000000 | (1176)),"不支持该模式","Unsupported mode"),
    // URL与APP不匹配 0x80000499
    NET_ERROR_URL_APP_NOT_MATCH((0x80000000 | (1177)),"URL与APP不匹配","Url and App are not match"),
    // 读取信息失败 0x8000049A
    NET_ERROR_READ_INFO_FAILED((0x80000000 | (1178)),"读取信息失败","Read info failed"),
    // 写入失败 0x8000049B
    NET_ERROR_WRITE_FAILED((0x80000000 | (1179)),"写入失败","Write failed"),
    // 未找到APP 0x8000049C
    NET_ERROR_NO_SUCH_APP((0x80000000 | (1180)),"未找到APP","No such App"),
    // 校验失败 0x8000049D
    NET_ERROR_VERIFIF_FAILED((0x80000000 | (1181)),"校验失败","Verify failed"),
    // License已过期 0x8000049E
    NET_ERROR_LICENSE_OUT_DATE((0x80000000 | (1182)),"License已过期","License out of date"),
    // 升级程序版本过低 0x8000049F
    NET_ERROR_UPGRADE_PROGRAM_TOO_OLD((0x80000000 | (1183)),"升级程序版本过低","upgrade program version too old"),
    // 加密传输被裁剪 0x800004A0
    NET_ERROR_SECURE_TRANSMIT_BEEN_CUT((0x80000000 | (1184)),"加密传输被裁剪","secure transmit has been cut"),
    // 设备不支持安全传输 0x800004A1
    NET_ERROR_DEVICE_NOT_SUPPORT_SECURE_TRANSMIT((0x80000000 | (1185)),"设备不支持安全传输","device not support secure transmit"),
    // 主码流成功的情况下，辅码流登录失败 0x800004A2
    NET_ERROR_EXTRA_STREAM_LOGIN_FAIL_CAUSE_BY_MAIN_STREAM((0x80000000 | (1186)),"主码流成功的情况下，辅码流登录失败","when main stream success, extra stream login fail"),
    // 辅码流被前端关闭 0x800004A3
    NET_ERROR_EXTRA_STREAM_CLOSED_BY_REMOTE_DEVICE((0x80000000 | (1187)),"辅码流被前端关闭","extra stream closed by remote device"),
    // 目标库导入:发送目标库数据失败 0x800004A4
    NET_ERROR_IMPORT_FACEDB_SEND_FAILD((0x80000000 | (1188)),"目标库导入:发送目标库数据失败","Import targetdb: Failed to send target database data"),
    // 目标库导入:系统忙, 已经有导入任务 0x800004A5
    NET_ERROR_IMPORT_FACEDB_BUSY((0x80000000 | (1189)),"目标库导入:系统忙, 已经有导入任务","Import targetdb: The system is busy. There are import tasks"),
    // 目标库导入:数据包检验失败 0x800004A6
    NET_ERROR_IMPORT_FACEDB_DATAERROR((0x80000000 | (1190)),"目标库导入:数据包检验失败","Import targetdb: Packet validation failed"),
    // 目标库导入:数据包非法 0x800004A7
    NET_ERROR_IMPORT_FACEDB_DATAINVALID((0x80000000 | (1191)),"目标库导入:数据包非法","Import targetdb: Invalid packet"),
    // 目标库导入:上传失败 0x800004A8
    NET_ERROR_IMPORT_FACEDB_UPGRADE_FAILD((0x80000000 | (1192)),"目标库导入:上传失败","Import targetdb: Upload failed"),
    // 目标库导入:用户无权限 0x800004A9
    NET_ERROR_IMPORT_FACEDB_NO_AUTHORITY((0x80000000 | (1193)),"目标库导入:用户无权限","Import targetdb: No authority"),
    // 目标库导入:文件格式异常 0x800004AA
    NET_ERROR_IMPORT_FACEDB_ABNORMAL_FILE((0x80000000 | (1194)),"目标库导入:文件格式异常","Import targetdb: Abnormal file"),
    // 目标库导入:同步失败,数据库无法生成 0x800004AB
    NET_ERROR_IMPORT_FACEDB_SYNC_FALID((0x80000000 | (1195)),"目标库导入:同步失败,数据库无法生成","Import targetdb: Synchronization failed, database could not be generated"),
    // 目标库导入:数据库已满, 无法导入 0x800004AC
    NET_ERROR_IMPORT_FACEDB_DBFULL((0x80000000 | (1196)),"目标库导入:数据库已满, 无法导入","Import targetdb: Database is full, unable to import"),
    // 目标库导入:存储空间已满, 无法导入 0x800004AD
    NET_ERROR_IMPORT_FACEDB_SDFULL((0x80000000 | (1197)),"目标库导入:存储空间已满, 无法导入","Import targetdb: Storage space is full, unable to import"),
    // 目标库导入:导入压缩包密码不对 0x800004AE
    NET_ERROR_IMPORT_FACEDB_CIPHER_ERROR((0x80000000 | (1198)),"目标库导入:导入压缩包密码不对","Import targetdb: Incorrect password for importing compressed package"),
    // 目标库导出:无权限 0x800004AF
    NET_ERROR_EXPORT_FACEDB_NO_AUTH((0x80000000 | (1199)),"目标库导出:无权限","Export targetdb: No authority"),
    // 目标库导出:文件不存在 0x800004B0
    NET_ERROR_EXPORT_FACEDB_NO_FILE((0x80000000 | (1200)),"目标库导出:文件不存在","Export targetdb: File does not exist"),
    // 目标库导出:文件准备失败 0x800004B1
    NET_ERROR_EXPORT_FACEDB_FILE_FAIL((0x80000000 | (1201)),"目标库导出:文件准备失败","Export targetdb: File preparation failed"),
    // 目标库导出:系统忙 0x800004B2
    NET_ERROR_EXPORT_FACEDB_BUSY((0x80000000 | (1202)),"目标库导出:系统忙","Export targetdb: System busy"),
    // 目标库导出:未定义密码, 不允许导出 0x800004B3
    NET_ERROR_EXPORT_FACEDB_NO_PASSWORD((0x80000000 | (1203)),"目标库导出:未定义密码, 不允许导出","Export targetdb: No password defined, export not allowed"),
    // 获取了过多的数据，设备无法处理 0x800004B4
    NET_ERROR_REQUESTED_TOO_MUCH_DATA((0x80000000 | (1204)),"获取了过多的数据，设备无法处理","Requested Too Much Data at one time."),
    // 在业务批量执行过程中有错误发生 0x800004B5
    NET_ERROR_BATCH_PROCESS_ERROR((0x80000000 | (1205)),"在业务批量执行过程中有错误发生","Batch Process Error"),
    // 某种原因，业务被取消执行 0x800004B6
    NET_ERROR_OPERATION_CANCELLED((0x80000000 | (1206)),"某种原因，业务被取消执行","Operation Cancelled"),
    // 设备型号不对，无法进一步处理 0x800004B7
    NET_ERROR_DEVICE_INVALID((0x80000000 | (1207)),"设备型号不对，无法进一步处理","Device Invalid"),
    // 无法获取到设备状态信息 0x800004B8
    NET_ERROR_DEVICE_UNAVAILABLE((0x80000000 | (1208)),"无法获取到设备状态信息","Device Unavailable - Can not get the device status."),
    // 信息通过URL下载方式下载失败 0x800004B9
    NET_ERROR_FINGERPRINT_DOWNLOAD_FAIL((0x80000000 | (1209)),"信息通过URL下载方式下载失败","Information Download Fail"),
    // 账户登录中 0x800004BA
    NET_ERROR_ACCOUNT_IN_USE((0x80000000 | (1210)),"账户登录中","Account In Use"),
    // 更新用户眼睛信息时,用户不存在目标 0x800004BB
    NET_ERROR_IRIS_INFO_NOT_EXISTED((0x80000000 | (1211)),"更新用户眼睛信息时,用户不存在目标","Eyes Info Not Existed"),
    // 下发的眼睛数据格式、特征值大小错误 0x800004BC
    NET_ERROR_INVALID_IRIS_DATA((0x80000000 | (1212)),"下发的眼睛数据格式、特征值大小错误","Invalid Eyes Data"),
    // 眼睛信息已存在 0x800004BD
    NET_ERROR_IRIS_ALREADY_EXIST((0x80000000 | (1213)),"眼睛信息已存在","Eyes Info Already Exist"),
    // 眼睛信息删除失败 0x800004BE
    NET_ERROR_ERASE_IRIS_FAILED((0x80000000 | (1214)),"眼睛信息删除失败","Erase Eyes Info Failed"),
    // 超出个人所支持的眼睛信息组数量，两个眼睛(左右眼)为一组 0x800004BF
    NET_ERROR_EXCEED_MAX_IRIS_GROUP_COUNT_PER_USER((0x80000000 | (1215)),"超出个人所支持的眼睛信息组数量，两个眼睛(左右眼)为一组","Exceed Max Eyes Info Group Count Per User"),
    // 超出个人单组眼睛信息所能记录的最大数量 0x800004C0
    NET_ERROR_EXCEED_MAX_IRIS_COUNT_PER_GROUP((0x80000000 | (1216)),"超出个人单组眼睛信息所能记录的最大数量","Exceed Max Eyes Info Count Per Group"),
    // 门处于常开状态 0x800004C1
    NET_ERROR_DOOR_IN_NORMALLY_OPEN_STATUS((0x80000000 | (1217)),"门处于常开状态","The door is in a normally open status"),
    // 门处于常闭状态 0x800004C2
    NET_ERROR_DOOR_IN_NORMALLY_CLOSED_STATUS((0x80000000 | (1218)),"门处于常闭状态","The door is in a normally closed status"),
    // 门处于互锁状态 0x800004C3
    NET_ERROR_DOOR_IN_INTERLOCK_STATUS((0x80000000 | (1219)),"门处于互锁状态","The door is in an interlocking status"),
    // 图片中检测到0个目标 0x80000514
    NET_ERROR_FACEMANAGER_NO_FACE_DETECTED((0x80000000 | (1300)),"图片中检测到0个目标","No target detected"),
    // 图片中检测到多个目标，无法返回特征 0x80000515
    NET_ERROR_FACEMANAGER_MULTI_FACE_DETECTED((0x80000000 | (1301)),"图片中检测到多个目标，无法返回特征","Multi target detected, Can not extract eigenvalues"),
    // 图片解码错误 0x80000516
    NET_ERROR_FACEMANAGER_PICTURE_DECODING_ERROR((0x80000000 | (1302)),"图片解码错误","Picture decoding error"),
    // 图片质量太低 0x80000517
    NET_ERROR_FACEMANAGER_LOW_PICTURE_QUALITY((0x80000000 | (1303)),"图片质量太低","The picture quality is too low"),
    // 结果不推荐使用，比如：对外国人，特征提取成功，但算法支持不好，容易造成误识别 0x80000518
    NET_ERROR_FACEMANAGER_NOT_RECOMMENDED((0x80000000 | (1304)),"结果不推荐使用，比如：对外国人，特征提取成功，但算法支持不好，容易造成误识别","Not recommended, Eigen style mismatched to the algorithm model"),
    // 目标特征已存在 0x80000519
    NET_ERROR_FACEMANAGER_FACE_FEATURE_ALREADY_EXIST((0x80000000 | (1305)),"目标特征已存在","target eigenvalue already exist"),
    // 目标角度超过配置阈值 0x8000051B
    NET_ERROR_FACEMANAGER_FACE_ANGLE_OVER_THRESHOLDS((0x80000000 | (1307)),"目标角度超过配置阈值","target angle over thresholds"),
    // 目标占比超出范围，算法建议占比:不要超过2/3;不要小于1/3 0x8000051C
    NET_ERROR_FACEMANAGER_FACE_RADIO_EXCEEDS_RANGE((0x80000000 | (1308)),"目标占比超出范围，算法建议占比:不要超过2/3;不要小于1/3","target radio exceeds range"),
    // 目标过爆 0x8000051D
    NET_ERROR_FACEMANAGER_FACE_OVER_EXPOSED((0x80000000 | (1309)),"目标过爆","target over exposed"),
    // 目标欠爆 0x8000051E
    NET_ERROR_FACEMANAGER_FACE_UNDER_EXPOSED((0x80000000 | (1310)),"目标欠爆","target under exposed"),
    // 目标亮度不均衡 ,用于判断阴阳脸 0x8000051F
    NET_ERROR_FACEMANAGER_BRIGHTNESS_IMBALANCE((0x80000000 | (1311)),"目标亮度不均衡 ,用于判断阴阳脸","target brightness imbalance"),
    // 目标的置信度低 0x80000520
    NET_ERROR_FACEMANAGER_FACE_LOWER_CONFIDENCE((0x80000000 | (1312)),"目标的置信度低","target lower confidence level"),
    // 目标对齐分数低 0x80000521
    NET_ERROR_FACEMANAGER_FACE_LOW_ALIGN((0x80000000 | (1313)),"目标对齐分数低","target low align score"),
    // 目标存在遮挡、残缺不全 0x80000522
    NET_ERROR_FACEMANAGER_FRAGMENTARY_FACE_DETECTED((0x80000000 | (1314)),"目标存在遮挡、残缺不全","Fragmentary target detected"),
    // 目标瞳距小于阈值 0x80000523
    NET_ERROR_FACEMANAGER_PUPIL_DISTANCE_NOT_ENOUGH((0x80000000 | (1315)),"目标瞳距小于阈值","Pupil distance in the photo is not enough"),
    // 目标数据下载失败 0x80000524
    NET_ERROR_FACEMANAGER_FACE_DATA_DOWNLOAD_FAILED((0x80000000 | (1316)),"目标数据下载失败","target download failed"),
    // 工作模式错误 0x80000525
    NET_ERROR_CITIZENMANAGER_ERROR_WORKINGMODE_ERROR((0x80000000 | (1317)),"工作模式错误","Working mode error"),
    // 采集繁忙中 0x80000526
    NET_ERROR_CITIZENMANAGER_ERROR_CAPTURE_BUSY((0x80000000 | (1318)),"采集繁忙中","Busy collecting"),
    // 不支持该采集方式 0x80000527
    NET_ERROR_CITIZENMANAGER_ERROR_CAPTURE_TYPE_ERROR((0x80000000 | (1319)),"不支持该采集方式","Does not support this collection method"),
    // 普通用户不支持下发 0x80000528
    NET_ERROR_NORMAL_USER_NOTSUPPORT((0x80000000 | (1320)),"普通用户不支持下发","Ordinary users do not support delivery"),
    // 制冷机强制开机无效，当天开启次数已用完 0x80000529
    NET_ERROR_THERMOGRAPHY_REF_SENSOR_OPEN_INVALID((0x80000000 | (1321)),"制冷机强制开机无效，当天开启次数已用完","The forced start-up of the refrigerator is invalid, and the opening times have been used up on the same day"),
    // 制冷机延迟关闭无效，当天已达到延迟次数 0x8000052A
    NET_ERROR_THERMOGRAPHY_REF_DELAY_SHUT_DOWN_INVALID((0x80000000 | (1322)),"制冷机延迟关闭无效，当天已达到延迟次数","The delayed shutdown of the refrigerator is invalid, and the delay times have been reached on the same day"),
    // 证件号已存在 0x8000052B
    NET_ERROR_CITIZENID_EXIST((0x80000000 | (1323)),"证件号已存在","ID is already exist"),
    // 目标可检测，但特征值提取失败（算法场景） 0x8000052C
    NET_ERROR_FACEMANAGER_FACE_FFE_FAILED((0x80000000 | (1324)),"目标可检测，但特征值提取失败（算法场景）","The target can be detected, but the feature value extraction fails (algorithm scene)"),
    // 目标照片因口罩，帽子，墨镜等目标属性不符合而提取特征值错误 0x8000052D
    NET_ERROR_FACEMANAGER_PHOTO_FEATURE_FAILED_FOR_FA((0x80000000 | (1325)),"目标照片因口罩，帽子，墨镜等目标属性不符合而提取特征值错误","The feature value extracted from the target photo is incorrect due to the inconsistency of target attributes such as masks, hats, sunglasses, etc."),
    // 目标照片不完整 0x8000052E
    NET_ERROR_FACEMANAGER_FACE_DATA_PHOTO_INCOMPLETE((0x80000000 | (1326)),"目标照片不完整","Incomplete target photo"),
    // 数据库插入越上限 0x8000052F
    NET_ERROR_DATABASE_ERROR_INSERT_OVERFLOW((0x80000000 | (1327)),"数据库插入越上限","Database Error Insert Overflow"),
    // 工装检测合规库:组ID超过最大值 0x80000530
    NET_ERROR_WORKSUIT_COMPARE_SERVER_GROUPID_EXCEED((0x80000000 | (1328)),"工装检测合规库:组ID超过最大值","WorkSuitCompareServer:GroupIdExceed"),
    // 工装检测合规库:建模分析器启动失败 0x80000531
    NET_ERROR_WORKSUIT_COMPARE_SERVER_ABSTRACT_INIT_ERROR((0x80000000 | (1329)),"工装检测合规库:建模分析器启动失败","WorkSuitCompareServer:AbstractInitError"),
    // 工装检测合规库:组ID不存在或为空 0x80000532
    NET_ERROR_WORKSUIT_COMPARE_SERVER_GROUPID_NOT_FOUND((0x80000000 | (1330)),"工装检测合规库:组ID不存在或为空","WorkSuitCompareServer:GroupIdNotFound"),
    // 工装检测合规库:数据库操作失败（指的是database操作） 0x80000533
    NET_ERROR_WORKSUIT_COMPARE_SERVER_DATABASE_ERROR((0x80000000 | (1331)),"工装检测合规库:数据库操作失败（指的是database操作）","WorkSuitCompareServer:DatabaseError"),
    // 工装检测合规库:令牌不存在或为空 0x80000534
    NET_ERROR_WORKSUIT_COMPARE_SERVER_TOKEN_ERROR((0x80000000 | (1332)),"工装检测合规库:令牌不存在或为空","WorkSuitCompareServer:TokenError"),
    // 工装检测合规库:查询起始数大于总数 0x80000535
    NET_ERROR_WORKSUIT_COMPARE_SERVER_BEGINNUM_OVERRUN((0x80000000 | (1333)),"工装检测合规库:查询起始数大于总数","WorkSuitCompareServer:BeginNumOverRun"),
    // 工装检测合规库:设备正在建模 0x80000536
    NET_ERROR_WORKSUIT_COMPARE_SERVER_ABSTRACT_STATE((0x80000000 | (1334)),"工装检测合规库:设备正在建模","WorkSuitCompareServer:AbstractState"),
    // 工装检测合规库:单次导入全景图数量超过限制 0x80000537
    NET_ERROR_WORKSUIT_COMPARE_SERVER_BIGPIC_MAXNUM((0x80000000 | (1335)),"工装检测合规库:单次导入全景图数量超过限制","WorkSuitCompareServer:BigPicMaxNum"),
    // 工装检测合规库:工装数量超过限制 0x80000538
    NET_ERROR_WORKSUIT_COMPARE_SERVER_OBJECT_MAXNUM((0x80000000 | (1336)),"工装检测合规库:工装数量超过限制","WorkSuitCompareServer:ObjectMaxNum"),
    // 工装检测合规库:超出合规库空间大小限制 0x80000539
    NET_ERROR_WORKSUIT_COMPARE_SERVER_GROUP_SPACE_EXCEED((0x80000000 | (1337)),"工装检测合规库:超出合规库空间大小限制","WorkSuitCompareServer:GroupSpaceExceed"),
    // 工装检测合规库:需手动建模数量为0 0x8000053A
    NET_ERROR_WORKSUIT_COMPARE_SERVER_ABSTRACTNUM_ZERO((0x80000000 | (1338)),"工装检测合规库:需手动建模数量为0","WorkSuitCompareServer:AbstractNumZero"),
    // 工装检测合规库:无效参数 0x8000053B
    NET_ERROR_WORKSUIT_COMPARE_SERVER_INVALID_PARAM((0x80000000 | (1339)),"工装检测合规库:无效参数","WorkSuitCompareServer:InvalidParam"),
    // 卡号不存在 0x8000053C
    NET_ERROR_CARD_NOT_EXIST((0x80000000 | (1340)),"卡号不存在","Card not exist"),
    // 临时库过时 0x8000053D
    NET_ERROR_TEMPORARY_OUTDATED((0x80000000 | (1341)),"临时库过时","Temporary library outdated"),
    // 无效的连接 0x80000579
    NET_SUBBIZ_INVALID_SOCKET((0x80000000 | (1401)),"无效的连接","Invalid socket"),
    // 暂停媒体文件下载失败 0x8000057A
    NET_SUBBIZ_PAUSE_ERROR((0x80000000 | (1402)),"暂停媒体文件下载失败","Pause download media file error"),
    // 获取私有隧道向上侦听端口失败 0x8000057B
    NET_SUBBIZ_GET_PORT_ERROR((0x80000000 | (1403)),"获取私有隧道向上侦听端口失败","Failed to get private tunnel up listening port");
    private int value;
    private String ChnNote;
    private String EngNote;

    private LastErrorNew(int givenValue, String ChnNote, String EngNote) {
        this.value = givenValue;
        this.ChnNote = ChnNote;
        this.EngNote = EngNote;
    }

    public String getChnNote() {
        return ChnNote;
    }

    public String getEngNote() {
        return EngNote;
    }

    public int getValue() {
        return value;
    }

    public static String getChnNoteByValue(int givenValue) {
        for (LastErrorNew enumType : LastErrorNew.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getChnNote();
            }
        }
        return LastErrorNew.getChnNoteByValue(LastErrorNew.UNRECORDED_ERROR.value);
    }

    public static String getEngNoteByValue(int givenValue) {
        for (LastErrorNew enumType : LastErrorNew.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getEngNote();
            }
        }
        return LastErrorNew.getEngNoteByValue(LastErrorNew.UNRECORDED_ERROR.value);
    }
}

