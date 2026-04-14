/**
 * 业务分组实体
 */
export interface QsGroup {
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
     * 父分组ID
     * 对应 Java: Integer
     */
    parentId: number | null;

    /**
     * 父区域国标ID
     * 对应 Java: String
     */
    parentDeviceId: string | null;

    /**
     * 所属的业务分组国标编号
     * 对应 Java: String
     */
    businessGroup: string;

    /**
     * 行政区划
     * 对应 Java: String
     */
    civilCode: string;

    /**
     * 别名
     * 对应 Java: String
     */
    alias: string;
}