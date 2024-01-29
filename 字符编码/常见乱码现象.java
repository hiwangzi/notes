import java.nio.charset.StandardCharsets;

    String str = "法治与宪政紧密相关，其内涵不但是要求所有人民守法，更侧重于法律对政府权力的控制和拘束";
    byte[] strUtf8 = str.getBytes(StandardCharsets.UTF_8);
    byte[] strGBK = str.getBytes("GBK");

    "以GBK读取UTF-8";
    new String(strUtf8, "GBK");

    "以UTF-8读取GBK";
    new String(strGBK, StandardCharsets.UTF_8);

    "以ISO8859-1读取UTF-8";
    new String(strUtf8, StandardCharsets.ISO_8859_1);

    "以ISO8859-1读取GBK";
    new String(strGBK, StandardCharsets.ISO_8859_1);

    "以GBK方式读取UTF-8编码的中文，然后又用UTF-8的格式再次读取";
    new String(
            new String(strUtf8, "GBK").getBytes("GBK"),
            StandardCharsets.UTF_8)
    ;

    "以UTF-8方式读取GBK编码的中文，然后又用GBK的格式再次读取";
    new String(
            new String(strGBK, StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8),
            "GBK"
    );
