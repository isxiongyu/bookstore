package cn.xiongyu.bookstore.seckill.domain;

/**
 * ClassName: SeckillBook
 * Package: cn.xiongyu.bookstore.seckill.domain
 * Description:
 * Date: 2020/2/18 下午2:50
 * Author: xiongyu
 */
public class SeckillBook {
    private int seckillId;
    private String seckillName;
    private int seckillNumber;
    private int version;

    public SeckillBook() {
    }

    public SeckillBook(int seckillId, String seckillName, int seckillNumber, int version) {
        this.seckillId = seckillId;
        this.seckillName = seckillName;
        this.seckillNumber = seckillNumber;
        this.version = version;
    }

    public int getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(int seckillId) {
        this.seckillId = seckillId;
    }

    public String getSeckillName() {
        return seckillName;
    }

    public void setSeckillName(String seckillName) {
        this.seckillName = seckillName;
    }

    public int getSeckillNumber() {
        return seckillNumber;
    }

    public void setSeckillNumber(int seckillNumber) {
        this.seckillNumber = seckillNumber;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "SeckillBook{" +
                "seckillId=" + seckillId +
                ", seckillName='" + seckillName + '\'' +
                ", seckillNumber=" + seckillNumber +
                ", version=" + version +
                '}';
    }
}
