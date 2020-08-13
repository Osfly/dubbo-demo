import org.apache.commons.codec.binary.Base64;

public class Base64Utils {

	public static String encodeString(final String data) {
        return encodeString(StringUtil.stringToByteArray(data));
    }

    public static String encodeString(final byte[] data) {
        return Base64.encodeBase64String(data);
    }

    public static byte[] encode(final String data) {
        return encode(StringUtil.stringToByteArray(data));
    }

    public static byte[] encode(final byte[] data) {
        return Base64.encodeBase64(data);
    }

    //decode
    public static String decodeString(final String data) {
        return decodeString(StringUtil.stringToByteArray(data));
    }

    public static String decodeString(final byte[] data) {
        return StringUtil.byteArrayToString(Base64.decodeBase64(data));
    }

    public static byte[] decode(final String data) {
        return decode(StringUtil.stringToByteArray(data));
    }

    public static byte[] decode(final byte[] data) {
        return Base64.decodeBase64(data);
    }
}
