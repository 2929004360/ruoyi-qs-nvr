package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 大类业务方案
 * @since ： Created in 2021/11/05 16:06
 */

public enum EM_CLASS_TYPE {
    /**
     * 未知业务
     */
    EM_CLASS_UNKNOWN(0, "未知业务"),
    /**
     * 视频浓缩
     */
    EM_CLASS_VIDEO_SYNOPSIS(1, "视频浓缩"),
    /**
     * 卡口
     */
    EM_CLASS_TRAFFIV_GATE(2, "卡口"),
    /**
     * 电警
     */
    EM_CLASS_ELECTRONIC_POLICE(3, "电警"),
    /**
     * 单球违停
     */
    EM_CLASS_SINGLE_PTZ_PARKING(4, "单球违停"),
    /**
     * 主从违停
     */
    EM_CLASS_PTZ_PARKINBG(5, "主从违停"),
    /**
     * 交通事件 Traffic 
     */
    EM_CLASS_TRAFFIC(6, "交通事件 Traffic "),
    /**
     * 通用行为分析 Normal 
     */
    EM_CLASS_NORMAL(7, "通用行为分析 Normal "),
    /**
     * 
     */
    EM_CLASS_PS(8, ""),
    /**
     * 金融行为分析 ATM 
     */
    EM_CLASS_ATM(9, "金融行为分析 ATM "),
    /**
     * 地铁行为分析
     */
    EM_CLASS_METRO(10, "地铁行为分析"),
    /**
     * 目标检测 FaceDetection 
     */
    EM_CLASS_FACE_DETECTION(11, "目标检测 FaceDetection "),
    /**
     * 目标识别 FaceRecognition 
     */
    EM_CLASS_FACE_RECOGNITION(12, "目标识别 FaceRecognition "),
    /**
     * 人数统计 NumberStat 
     */
    EM_CLASS_NUMBER_STAT(13, "人数统计 NumberStat "),
    /**
     * 热度图 HeatMap 
     */
    EM_CLASS_HEAT_MAP(14, "热度图 HeatMap "),
    /**
     * 视频诊断 VideoDiagnosis 
     */
    EM_CLASS_VIDEO_DIAGNOSIS(15, "视频诊断 VideoDiagnosis "),
    /**
     * 视频增强
     */
    EM_CLASS_VIDEO_ENHANCE(16, "视频增强"),
    /**
     * 烟火检测
     */
    EM_CLASS_SMOKEFIRE_DETECT(17, "烟火检测"),
    /**
     * 车辆特征识别 VehicleAnalyse 
     */
    EM_CLASS_VEHICLE_ANALYSE(18, "车辆特征识别 VehicleAnalyse "),
    /**
     * 人员特征识别
     */
    EM_CLASS_PERSON_FEATURE(19, "人员特征识别"),
    /**
     * 多预置点目标检测 SDFaceDetect 
     */
    EM_CLASS_SDFACEDETECTION(20, "多预置点目标检测 SDFaceDetect "),
    /**
     * 球机热度图计划 HeatMapPlan 
     */
    EM_CLASS_HEAT_MAP_PLAN(21, "球机热度图计划 HeatMapPlan "),
    /**
     * 球机客流量统计计划  NumberStatPlan 
     */
    EM_CLASS_NUMBERSTAT_PLAN(22, "球机客流量统计计划  NumberStatPlan "),
    /**
     * 金融目标检测，包括正常人脸、异常人脸、相邻人脸、头盔人脸等针对ATM场景特殊优化
     */
    EM_CLASS_ATMFD(23, "金融目标检测，包括正常人脸、异常人脸、相邻人脸、头盔人脸等针对ATM场景特殊优化"),
    /**
     * 高速交通事件检测 Highway 
     */
    EM_CLASS_HIGHWAY(24, "高速交通事件检测 Highway "),
    /**
     * 城市交通事件检测  City 
     */
    EM_CLASS_CITY(25, "城市交通事件检测  City "),
    /**
     * 民用简易跟踪 LeTrack 
     */
    EM_CLASS_LETRACK(26, "民用简易跟踪 LeTrack "),
    /**
     * 打靶相机 SCR 
     */
    EM_CLASS_SCR(27, "打靶相机 SCR "),
    /**
     * 立体视觉(双目) StereoVision 
     */
    EM_CLASS_STEREO_VISION(28, "立体视觉(双目) StereoVision "),
    /**
     * 人体检测 HumanDetect 
     */
    EM_CLASS_HUMANDETECT(29, "人体检测 HumanDetect "),
    /**
     * 人脸分析  FaceAnalysis 
     */
    EM_CLASS_FACE_ANALYSIS(30, "人脸分析  FaceAnalysis "),
    /**
     * X光检测  XRayDetection 
     */
    EM_CALSS_XRAY_DETECTION(31, "X光检测  XRayDetection "),
    /**
     * 双目相机客流量统计  StereoNumber 
     */
    EM_CLASS_STEREO_NUMBER(32, "双目相机客流量统计  StereoNumber "),
    /**
     * 人群分布图
     */
    EM_CLASS_CROWDDISTRIMAP(33, "人群分布图"),
    /**
     * 目标检测
     */
    EM_CLASS_OBJECTDETECT(34, "目标检测"),
    /**
     * IVSS目标检测  FaceAttribute 
     */
    EM_CLASS_FACEATTRIBUTE(35, "IVSS目标检测  FaceAttribute "),
    /**
     * IVSS目标识别  FaceCompare 
     */
    EM_CLASS_FACECOMPARE(36, "IVSS目标识别  FaceCompare "),
    /**
     * 立体行为分析  StereoBehavior 
     */
    EM_CALSS_STEREO_BEHAVIOR(37, "立体行为分析  StereoBehavior "),
    /**
     * 智慧城管  IntelliCityMgr 
     */
    EM_CALSS_INTELLICITYMANAGER(38, "智慧城管  IntelliCityMgr "),
    /**
     * 防护舱（ATM舱内） ProtectiveCabin 
     */
    EM_CALSS_PROTECTIVECABIN(39, "防护舱（ATM舱内） ProtectiveCabin "),
    /**
     * 飞机行为检测  AirplaneDetect 
     */
    EM_CALSS_AIRPLANEDETECT(40, "飞机行为检测  AirplaneDetect "),
    /**
     * 人群态势（人群分布图服务） CrowdPosture 
     */
    EM_CALSS_CROWDPOSTURE(41, "人群态势（人群分布图服务） CrowdPosture "),
    /**
     * 打电话检测  PhoneCallDetect 
     */
    EM_CLASS_PHONECALLDETECT(42, "打电话检测  PhoneCallDetect "),
    /**
     * 烟雾检测  SmokeDetection 
     */
    EM_CLASS_SMOKEDETECTION(43, "烟雾检测  SmokeDetection "),
    /**
     * 船只检测  BoatDetection 
     */
    EM_CLASS_BOATDETECTION(44, "船只检测  BoatDetection "),
    /**
     * 吸烟检测  SmokingDetect 
     */
    EM_CLASS_SMOKINGDETECT(45, "吸烟检测  SmokingDetect "),
    /**
     * 水利监测  WaterMonitor 
     */
    EM_CLASS_WATERMONITOR(46, "水利监测  WaterMonitor "),
    /**
     * 生成图规则  GenerateGraphDetection 
     */
    EM_CLASS_GENERATEGRAPHDETECTION(47, "生成图规则  GenerateGraphDetection "),
    /**
     * 交通停车  TrafficPark 
     */
    EM_CLASS_TRAFFIC_PARK(48, "交通停车  TrafficPark "),
    /**
     * 作业检测  OperateMonitor 
     */
    EM_CLASS_OPERATEMONITOR(49, "作业检测  OperateMonitor "),
    /**
     * 智慧零售大类  IntelliRetail 
     */
    EM_CLASS_INTELLI_RETAIL(50, "智慧零售大类  IntelliRetail "),
    /**
     * 教育智慧课堂 ClassroomAnalyse 
     */
    EM_CLASS_CLASSROOM_ANALYSE(51, "教育智慧课堂 ClassroomAnalyse "),
    /**
     * 特征向量提取大类  FeatureAbstract 
     */
    EM_CLASS_FEATURE_ABSTRACT(52, "特征向量提取大类  FeatureAbstract "),
    /**
     * 人体检测大类  FaceBodyDetect 
     */
    EM_CLASS_FACEBODY_DETECT(53, "人体检测大类  FaceBodyDetect "),
    /**
     * 人体识别大类  FaceBodyAnalyse 
     */
    EM_CLASS_FACEBODY_ANALYSE(54, "人体识别大类  FaceBodyAnalyse "),
    /**
     * 车辆密度  VehiclesDistri 
     */
    EM_CLASS_VEHICLES_DISTRI(55, "车辆密度  VehiclesDistri "),
    /**
     * 智慧养殖检测  IntelliBreed 
     */
    EM_CLASS_INTELLI_BREED(56, "智慧养殖检测  IntelliBreed "),
    /**
     *
     */
    EM_CLASS_INTELLI_PS(57, ""),
    /**
     * 电力检测  ElectricDetect 
     */
    EM_CLASS_ELECTRIC_DETECT(58, "电力检测  ElectricDetect "),
    /**
     * 雷达检测  RadarDetect 
     */
    EM_CLASS_RADAR_DETECT(59, "雷达检测  RadarDetect "),
    /**
     * 车位检测大类  ParkingSpace 
     */
    EM_CLASS_PARKINGSPACE(60, "车位检测大类  ParkingSpace "),
    /**
     * 智慧金融  IntelliFinance 
     */
    EM_CLASS_INTELLI_FINANCE(61, "智慧金融  IntelliFinance "),
    /**
     * 人群异常检测  CrowdAbnormal 
     */
    EM_CLASS_CROWD_ABNORMAL(62, "人群异常检测  CrowdAbnormal "),
    /**
     * 人体温智能检测  AnatomyTempDetect 
     */
    EM_CLASS_ANATOMY_TEMP_DETECT(63, "人体温智能检测  AnatomyTempDetect "),
    /**
     * 天气监控  WeatherMonitor 
     */
    EM_CLASS_WEATHER_MONITOR(64, "天气监控  WeatherMonitor "),
    /**
     * 电梯门禁  ElevatorAccessControl 
     */
    EM_CLASS_ELEVATOR_ACCESS_CONTROL(65, "电梯门禁  ElevatorAccessControl "),
    /**
     * 违章建筑	 BreakRuleBuilding 
     */
    EM_CLASS_BREAK_RULE_BUILDING(66, "违章建筑	 BreakRuleBuilding "),
    /**
     * 异物检测  ForeignDetection 
     */
    EM_CLASS_FOREIGN_DETECT(67, "异物检测  ForeignDetection "),
    /**
     * 全景交通  PanoramaTraffic 
     */
    EM_CLASS_PANORAMA_TRAFFIC(68, "全景交通  PanoramaTraffic "),
    /**
     * 传送带阻塞  ConveyorBlock 
     */
    EM_CLASS_CONVEY_OR_BLOCK(69, "传送带阻塞  ConveyorBlock "),
    /**
     * 厨房有害动物检测  KitchenAnimal 
     */
    EM_CLASS_KITCHEN_ANIMAL(70, "厨房有害动物检测  KitchenAnimal "),
    /**
     * 万物检测  AllSeeingEye 
     */
    EM_CLASS_ALLSEEINGEYE(71, "万物检测  AllSeeingEye "),
    /**
     * 智慧消防  IntelliFireControl 
     */
    EM_CLASS_INTELLI_FIRE_CONTROL(72, "智慧消防  IntelliFireControl "),
    /**
     * 传送带检测  ConveyerBelt 
     */
    EM_CLASS_CONVERYER_BELT(73, "传送带检测  ConveyerBelt "),
    /**
     * 智慧物流  IntelliLogistics 
     */
    EM_CLASS_INTELLI_LOGISTICS(74, "智慧物流  IntelliLogistics "),
    /**
     * 烟火检测 SmokeFire 
     */
    EM_CLASS_SMOKE_FIRE(75, "烟火检测 SmokeFire "),
    /**
     * 物品监控 ObjectMonitor 
     */
    EM_CLASS_OBJECT_MONITOR(76, "物品监控 ObjectMonitor "),
    /**
     * 智能停车 IntelliParking 
     */
    EM_CLASS_INTELLI_PARKING(77, "智能停车 IntelliParking "),
    /**
     * 智慧消防 FireControl
     */
    EM_CLASS_FIRE_CONTROL(78, "智慧消防 FireControl "),
    /**
     * 动物检测  AnimalDetection
     */
    EM_CLASS_ANIMAL_DETECTION(79, "动物检测  AnimalDetection "),
    /**
     * 火警监控  FireControlMonitor
     */
    EM_CLASS_FIRE_CONTROL_MONITOR(80, "火警监控  FireControlMonitor "),
    /**
     * 鱼群监测 FishMonitor
     */
    EM_CLASS_FISH_MONITOR(81, "鱼群监测 FishMonitor "),
    /**
     * 工程车检测 ShopTruckDetect
     */
    EM_CLASS_SHOPTRUCK_DETECT(82, "工程车检测 ShopTruckDetect ");

    private int value;
    private String note;

    public String getNote() {
             return note;
         }

    public int getValue() {
             return value;
         }

    EM_CLASS_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_CLASS_TYPE enumType : EM_CLASS_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_CLASS_TYPE enumType : EM_CLASS_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_CLASS_TYPE getEnum(int value) {
        for (EM_CLASS_TYPE e : EM_CLASS_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_CLASS_TYPE.EM_CLASS_UNKNOWN;
    }
}

