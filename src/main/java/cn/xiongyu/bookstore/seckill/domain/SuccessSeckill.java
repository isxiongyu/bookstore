package cn.xiongyu.bookstore.seckill.domain;

/**
 * ClassName: SuccessSeckill
 * Package: cn.xiongyu.bookstore.seckill.domain
 * Description:
 * Date: 2020/2/18 下午5:10
 * Author: xiongyu
 */
public class SuccessSeckill {
    private int seckillId;
    private int restNum;
    private int state;

    public SuccessSeckill() {
    }

    public SuccessSeckill(int seckillId, int rest_num, int state) {
        this.seckillId = seckillId;
        this.restNum = rest_num;
        this.state = state;
    }

    public int getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(int seckillId) {
        this.seckillId = seckillId;
    }

    public int getRestNum() {
        return restNum;
    }

    public void setRestNum(int restNum) {
        this.restNum = restNum;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "SuccessSeckill{" +
                "seckillId=" + seckillId +
                ", rest_num=" + restNum +
                ", state=" + state +
                '}';
    }
}
