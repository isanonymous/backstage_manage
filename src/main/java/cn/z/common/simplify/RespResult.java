package cn.z.common.simplify;

import cn.z.State;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RespResult {
  // 定义jackson对象
  private static final ObjectMapper MAPPER = new ObjectMapper();
  
  private Integer status;
  private String msg;
  private Object data;

  public static RespResult buildByUpdateSqlResult(Long updResult) {
    int i = updResult.intValue();
    // return build(i, i > 0 ? "操作成功" : "操作失败");
    return buildByUpdateSqlResult(i);
  }
  public static RespResult buildByUpdateSqlResult(Integer updResult) {
    // int i = updResult.intValue();
    return build(updResult, updResult > 0 ? "操作成功" : "操作失败");
  }
  public static RespResult buildByUpdateSqlResult(Boolean updResult) {
    // int i = updResult.intValue();
    return build(updResult?State.SUCCESS:State.FAIL, updResult ? "操作成功" : "操作失败");
  }

  
  public static RespResult buildByUpdateSqlResult(Long updResult, Object data) {
    int i = updResult.intValue();
    // return build(i, i > 0 ? "操作成功" : "操作失败");
    return buildByUpdateSqlResult(i, data);
  }
  public static RespResult buildByUpdateSqlResult(Integer updResult, Object data) {
    // int i = updResult.intValue();
    return build(updResult>0?State.SUCCESS:State.FAIL, updResult > 0 ? "操作成功" : "操作失败", data);
  }
  public static RespResult buildByUpdateSqlResult(Boolean updResult, Object data) {
    // int i = updResult.intValue();
    return build(updResult?State.SUCCESS:State.FAIL, updResult ? "操作成功" : "操作失败", data);
  }

  
  public RespResult(Integer status, String msg, Object data) {
    this.status = status;
    this.msg = msg;
    this.data = data;
  }
  public RespResult() {
  }

  public static RespResult build(Integer status, String msg, Object data) {
    return new RespResult(status, msg, data);
  }
  public static RespResult build(Integer status, String msg) {
    return new RespResult(status, msg, null);
  }

  /**
   * 将json结果集转化为ResultMap对象
   *
   * @param jsonData json数据
   * @param clazz ResultMap中的object类型
   * @return
   */
  public static RespResult fmt2Pojo(String jsonData, Class<?> clazz) {
    try {
      if (clazz == null) {
        return MAPPER.readValue(jsonData, RespResult.class);
      }
      JsonNode jsonNode = MAPPER.readTree(jsonData);
      JsonNode data = jsonNode.get("data");
      Object obj = null;
      if (clazz != null) {
        if (data.isObject()) {
          obj = MAPPER.readValue(data.traverse(), clazz);
        } else if (data.isTextual()) {
          obj = MAPPER.readValue(data.asText(), clazz);
        }
      }
      return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
    } catch (Exception e) {
      return null;
    }
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
