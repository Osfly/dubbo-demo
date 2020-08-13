import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Description
 * @Author peter
 * @Date 2019-04-30 15:58
 */
public class MD5Util {

    public static String toMd5(String str){
        return DigestUtils.md5Hex(str);
    }
}
