package com.xiaohuashifu.recruit.common.mybatis.type;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class LongSetTypeHandler extends BaseTypeHandler<Set<Long>> {

    private final TypeReference<Set<Long>> type = new TypeReference<>() {};

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Set<Long> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSON.toJSONString(parameter));
    }

    @Override
    public Set<Long> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        if (value != null) {
            return JSON.parseObject(value, type);
        }
        return null;
    }

    @Override
    public Set<Long> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        if (value != null) {
            return JSON.parseObject(value, type);
        }
        return null;
    }

    @Override
    public Set<Long> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        if (value != null) {
            return JSON.parseObject(value, type);
        }
        return null;
    }
}