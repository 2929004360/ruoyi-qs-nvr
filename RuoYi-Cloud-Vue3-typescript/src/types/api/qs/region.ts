/**
 * 区域实体
 */
export interface QsRegion {
    /**
     * 数据库自增ID
     * 对应 Java: int
     */
    id: number;

    /**
     * 区域国标编号
     * 对应 Java: String
     */
    deviceId: string;

    /**
     * 区域名称
     * 对应 Java: String
     */
    name: string;

    /**
     * 父区域国标ID
     * 对应 Java: Integer
     */
    parentId: number | null;

    /**
     * 父区域国标ID
     * 对应 Java: String
     */
    parentDeviceId: string | null;
}