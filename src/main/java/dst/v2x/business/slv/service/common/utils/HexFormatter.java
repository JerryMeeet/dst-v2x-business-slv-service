package dst.v2x.business.slv.service.common.utils;

import java.util.ArrayList;
import java.util.List;

public class HexFormatter {

    public static String  formatHexToDotted(String hex) {
        // 检查输入是否为null或空字符串
        if (hex == null || hex.isEmpty()) {
            throw new IllegalArgumentException("输入字符串不能为空");
        }

        // 检查长度是否为偶数
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException("输入的十六进制字符串长度必须是偶数");
        }

        // 检查是否仅包含有效的十六进制字符
        if (!hex.matches("^[0-9a-fA-F]+$")) {
            throw new IllegalArgumentException("输入包含非法的十六进制字符");
        }

        List<String> parts = new ArrayList<>();

        try {
            for (int i = 0; i < hex.length(); i += 2) {
                String pair = hex.substring(i, i + 2);
                int decimal = Integer.parseInt(pair, 16);
                parts.add(Integer.toString(decimal));
            }
        } catch (NumberFormatException e) {
            // 由于已经通过正则检查，理论上此处不会抛出异常
            throw new AssertionError("解析十六进制时发生意外错误", e);
        }

        return String.join(".", parts);
    }

    public static void main(String[] args) {
        // 示例测试
        String input = "020105013e";
        try {
            String output = formatHexToDotted(input);
            System.out.println(output); // 输出: 2.1.5.1.62
        } catch (IllegalArgumentException e) {
            System.err.println("错误: " + e.getMessage());
        }
    }
}