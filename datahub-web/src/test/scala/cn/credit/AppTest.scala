package cn.credit

import java.util.Date

import cn.credit.utils.CustomizerValueFilter
import com.alibaba.fastjson.{JSON, JSONObject}
import com.alibaba.fastjson.serializer.{SerializerFeature, ValueFilter}
import org.junit

class AppTest {

  @junit.Test
  def print: Unit ={
    val obj = new JSONObject
    obj.put("d",new Date)
    println(JSON.toJSONString(obj,new CustomizerValueFilter))
  }

}


