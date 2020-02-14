package cn.xiongyu.bookstore.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * ClassName: SerialiizeUtil
 * Package: cn.xiongyu.bookstore.cache
 * Description:
 * Date: 2020/2/13 下午2:41
 * Author: xiongyu
 */
public class SerializeUtil {
    /**
     * 序列化
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反序列化
     */
    public static Object unserialize(byte[] bytes) {
        if (bytes != null) {
            ByteArrayInputStream bais = null;
            try {
                // 反序列化
                bais = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                return ois.readObject();
            } catch (Exception e) {

            }
        }
        return null;
    }
}