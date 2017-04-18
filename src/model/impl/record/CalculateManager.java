package model.impl.record;

import base.Constant;
import model.ICalculate;
import model.RequestManager;
import pojo.Location;
import pojo.Record;
import pojo.RecordRes;
import pojo.RelativeItemMsg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 计算管理类
 * Created by androidjp on 2017/3/21.
 */
public class CalculateManager extends RequestManager<RecordRes> implements ICalculate {

    ///2016 城/乡 人均消费支出
    private final float[] consume2016 = {
            33616, 12363
    };

    private static final Map<String, Float> provinceIncome2016 = new HashMap<String, Float>();

    static {
        provinceIncome2016.put("北京市", 9227f);
        provinceIncome2016.put("天津市", 5729f);
        provinceIncome2016.put("河北市", 4511f);
        provinceIncome2016.put("山西市", 3299f);
        provinceIncome2016.put("内蒙古自治区", 4538f);
        provinceIncome2016.put("辽宁省", 4654f);
        provinceIncome2016.put("吉林省", 4930f);
        provinceIncome2016.put("黑龙江省", 4981f);
        provinceIncome2016.put("上海市", 8664f);
        provinceIncome2016.put("江苏省", 4862f);
        provinceIncome2016.put("浙江省", 5306f);
        provinceIncome2016.put("安徽省", 4294f);
        provinceIncome2016.put("福建省", 4664f);
        provinceIncome2016.put("江西省", 3787f);
        provinceIncome2016.put("山东省", 4880f);
        provinceIncome2016.put("河南省", 4382f);
        provinceIncome2016.put("湖北省", 4486f);
        provinceIncome2016.put("湖南省", 4025f);
        provinceIncome2016.put("广东省", 4670f);
        provinceIncome2016.put("广西壮族自治区", 3826f);
        provinceIncome2016.put("重庆市", 6181f);
        provinceIncome2016.put("四川省", 5150f);
        provinceIncome2016.put("贵州省", 4041f);
        provinceIncome2016.put("云南省", 4962f);
        provinceIncome2016.put("西藏自治区", 5550f);
        provinceIncome2016.put("陕西省", 5034f);
        provinceIncome2016.put("甘肃省", 5312f);
        provinceIncome2016.put("青海省", 5290f);
        provinceIncome2016.put("宁夏回族自治区", 5264f);
        provinceIncome2016.put("新疆维吾尔自治区", 5524f);
    }


    /**
     * 按全国居民五等份收入分组:
     * 低收入组人均可支配收入5529元，
     * 中等偏下收入组人均可支配收入12899元，
     * 中等收入组人均可支配收入20924元，
     * 中等偏上收入组人均可支配收入31990元，
     * 高收入组人均可支配收入59259元。
     * 贫困地区农村居民人均可支配收入8452元
     */
    private final float[] consumeLevel2016 = {

    };


    @Override
    public void calculateRecord(Record record) {


        RecordRes recordRes = new RecordRes();
        recordRes.setResult_id(record.getResult_id());

        /// 城镇/农村 户口
        int id_type = record.id_type;
        // 省在职人员平均月薪
        float provinceSalary = provinceIncome2016.get(record.getLocation().province);

        int dead = 11;
        int fen_mu = 10;
        //伤残金
        recordRes.money_hurt = (float) (consume2016[id_type] * ((record.hurt_level * 1.0) / fen_mu % 1) * 20);
        //安葬费
        recordRes.money_bury = (record.hurt_level == 11) ? (float) (provinceSalary * 6) : 0;
        if (recordRes.money_bury != 0)
            recordRes.money_bury_info = "所属地区：" + record.getLocation().province + "，在职人员平均月薪为：" + provinceSalary + "，赔偿金额按6个月算";
        //精神抚慰金(由于各省各种情况，这里设定一个平均值，5000元)
        recordRes.money_heart = (float) (5000 * 6.00);
        recordRes.money_heart_info = "各省情况不同，这个设定一个平均值为5000元，5000元每个月，按六个月算。";
        //护理费
        recordRes.money_nursing = record.nursing_days * 60;
        recordRes.money_nursing_info = "护理期为"+record.nursing_days+"天，护理费用标准为60元/天。";
        ///误工费
        recordRes.money_tardy = record.tardy_days * record.salary;
        recordRes.money_tardy_info = "误工费用 = 误工天数 x 您的平均工资 = "+record.tardy_days +"x"+ record.salary+"。";
        ///医药费
        recordRes.money_medical = record.medical_free;
        ///营养费
        recordRes.money_nutrition = record.nutrition_days * 50;
        recordRes.money_nutrition_info = "营养期为"+record.nursing_days+"天，日均营养费用标准为50元/天。";
        ///住院补贴
        recordRes.money_hospital_allowance = record.hospital_days * 100;
        recordRes.money_hospital_allowance_info = "住院天数为"+record.hospital_days+"天，日均住院补贴标准为100元/天。";
        ///如果有需抚养人，则需要根据抚养人的各自岁数进行分析
        ///死亡赔偿金计算
        if (record.hurt_level == 11 && record.getRelative_msg_list() != null) {
            List<RelativeItemMsg> list = record.getRelative_msg_list();
            StringBuilder sb = new StringBuilder();
            float money = 0;
            for (RelativeItemMsg item : list) {
                switch (item.getRelation()) {
                    case Constant.RELATION_CHILDREN:
                        money = consume2016[id_type] * ((18 - item.getAge() < 0 ? 0 : 18 - item.getAge()));
                        sb.append("一个子女" + item.getAge() + "岁,所得赔偿为：" + consume2016[id_type] + "元 x " + (((18 - item.getAge() < 0 ? 0 : 18 - item.getAge())) + "年 = " + money + "元。"));
                        recordRes.money_relatives += money;
                        break;
                    case Constant.RELATION_PARENTS:
                        if (item.getAge() <= 60) {
                            money = consume2016[id_type] * 20;
                        } else if (item.getAge() > 60 && item.getAge() < 75) {
                            money = consume2016[id_type] * (20 - (item.getAge() - 60));
                        } else {
                            money = consume2016[id_type] * 5;
                        }
                        recordRes.money_relatives += money;
                        sb.append("一个父母辈的需抚养人" + item.getAge() + "岁,所得赔偿为：" + money + "元。");
                        recordRes.money_relatives += money;
                        break;
                }
            }
            recordRes.money_relatives_info = sb.toString();
        }
        recordRes.calculateAllPay();

        ///计算完毕，需要返回RecordRes，并将总赔偿金额给到record表中
        if (mCallback != null) {
            mCallback.finish(recordRes);
        }
    }
}
