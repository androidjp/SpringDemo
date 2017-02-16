package pojo;

/**
 * 理赔记录：
 * <p>
 * Created by androidjp on 2016/12/25.
 */
public class Record implements Cloneable {

//    @PrimaryKey
    private String record_id;//（主键）
//    @Required
    private String user_id;///用户ID（外键）
    public String location_id;///定位（外键）
//    @Ignore
    public Location location;//实际定位信息
//    @Required
    public long record_time;
    public int hurt_level;
    public long salary;//月薪
    public int relatives_count;//兄弟姐妹数量
    public boolean has_spouse;//有无配偶（false：无， true：有）
    public int id_type;///户口性质（城镇、农村）
    public int responsibility;//责任：全责、次要责任、同等责任、主要责任、无责
    public int driving_tools;///交通工具
    public float medical_free;//医药费
    public int hospital_days;
    public int tardy_days;///误工天数
    public int nutrition_days;//营养期
    private String result_id;///结果（外键）
//    @Ignore
    private RecordRes result;///结果
//    private RealmList<RelativeItemMsg> relative_msg_list;

    public RecordRes getResult() {
        return (result_id == null) ? null : result;
    }

    public void setResult(RecordRes result) {
        this.result = result;
        this.result_id = this.result.getResult_id();
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setResult_id(String result_id) {
        this.result_id = result_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getRecord_id() {
        return record_id;
    }

    public String getResult_id() {
        return result_id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

//    public RealmList<RelativeItemMsg> getRelative_msg_list() {
//        return relative_msg_list;
//    }
//
//    public void setRelative_msg_list(RealmList<RelativeItemMsg> relative_msg_list) {
//        this.relative_msg_list = relative_msg_list;
//    }

    @Override
    protected Record clone() throws CloneNotSupportedException {
        return (Record) super.clone();
    }



    /**
     * 将记录的数据复制到维护的对象中
     * @param record 传来的记录数据
     */
    public void copyData(Record record) {
        if (record.result_id != null && record.result != null) {
            this.result = record.result;
            this.result_id = record.result.getResult_id();
        }
        if (!(record.getLocation() == null || record.location_id == null)) {
            this.setLocation(record.location);
            this.location_id = this.location.getLocation_id();
        }
        this.hurt_level = record.hurt_level;
        this.salary = record.salary;//月薪
        this.relatives_count = record.relatives_count;//兄弟姐妹数量
        this.has_spouse = record.has_spouse;//有无配偶（false：无， true：有）
        this.id_type = record.id_type;///户口性质（城镇、农村）
        this.responsibility = record.responsibility;//责任：全责、次要责任、同等责任、主要责任、无责
        this.driving_tools = record.driving_tools;///交通工具
        this.medical_free = record.medical_free;//医药费
        this.hospital_days = record.hospital_days;
        this.tardy_days = record.tardy_days;///误工天数
        this.nutrition_days = record.nutrition_days;//营养期
//        if (this.relative_msg_list == null)
//            this.relative_msg_list = new RealmList<>();
//
//        this.relative_msg_list.clear();
//        if (record.relative_msg_list != null)
//            this.relative_msg_list.addAll(record.relative_msg_list);
    }
}
