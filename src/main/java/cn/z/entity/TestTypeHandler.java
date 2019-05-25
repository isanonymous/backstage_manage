package cn.z.entity;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.ResultSetMetaData;

public class TestTypeHandler extends BaseTypeHandler<SkillType> {
  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, SkillType parameter, JdbcType jdbcType) throws SQLException {
    System.out.println("\n        setNonNullParameter():  "+i+"  "+parameter);
    ps.setLong(i, parameter.getSkillTypeId());
  }

  @Override
  public SkillType getNullableResult(ResultSet rs, String columnName) throws SQLException {
    System.out.println("\n----getNullableResult---: " +columnName);
    ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
    List<String> colLi = new ArrayList<>();
    for(int i=1; i<=metaData.getColumnCount(); i++) {
      colLi.add(metaData.getColumnName(i));
    }
    SkillType st = new SkillType();
    if (colLi.contains(columnName)) {
      // st.setSkillTypeId(rs.getLong(columnName));  //"skill_type_id"));
      st.setSkillTypeId((Long) rs.getObject(columnName));  //"skill_type_id"));
      // st.setSkillTypeId(rs.getLong("skill_type_id"));
    }
    if (colLi.contains("skill_type_name")) {
      st.setSkillTypeName(rs.getString("skill_type_name"));
    }
    if (colLi.contains("parent")) {
      // st.setParent(rs.getLong("parent"));
      st.setParent((Long) rs.getObject("parent"));
    }
    // if (colLi.contains("level")) {
    //   st.setLevel(Byte.parseByte(rs.getString("level")));
    // }
    if (colLi.contains("is_leaf")) {
      st.setIsLeaf(Byte.parseByte(rs.getString("is_leaf")));
    }
    return st;
  }

  @Override
  public SkillType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    SkillType st = new SkillType();
    st.setSkillTypeId(rs.getLong(columnIndex));
    return st;
  }

  @Override
  public SkillType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return null;
  }
}
