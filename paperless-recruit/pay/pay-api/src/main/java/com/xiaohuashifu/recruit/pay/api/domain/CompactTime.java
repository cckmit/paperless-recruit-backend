package com.xiaohuashifu.recruit.pay.api.domain;

import com.xiaohuashifu.recruit.common.domain.Domain;
import lombok.NonNull;
import lombok.Value;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * 描述：压缩的时间
 *          格式 {yyyyMMddHHmmss}
 *
 * @author xhsf
 * @create 2021/1/7 00:51
 */
@Value
public class CompactTime implements Domain {

    /**
     * 压缩时间正则
     */
    public static final String COMPACT_TIME_REGEX =
            "^\\d{4}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])([01]\\d|2[0-3])([0-5]\\d){2}$";

    /**
     * 构造静态的匹配模式
     */
    public static final Pattern p = Pattern.compile(COMPACT_TIME_REGEX);

    /**
     * 压缩时间格式器
     */
    public static final DateTimeFormatter COMPACT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    String value;

    public CompactTime(@NonNull String compactTime) {
        if (!p.matcher(compactTime).matches()) {
            throw new ValidationException();
        }
        this.value = compactTime;
    }

    public CompactTime(@NonNull LocalDateTime compactTime) {
        this(COMPACT_DATE_TIME_FORMATTER.format(compactTime));
    }

    @Override
    public String toString() {
        return getValue();
    }

    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.parse(getValue(), COMPACT_DATE_TIME_FORMATTER);
    }

}
