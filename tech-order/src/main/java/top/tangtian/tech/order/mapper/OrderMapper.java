package top.tangtian.tech.order.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;
import top.tangtian.tech.order.entity.Order;

/**
 * @author tangtian
 * @date 2025-12-31 12:44
 */
@Mapper
@Repository
public interface OrderMapper {

	@Insert("INSERT INTO `order` (user_id, commodity_code,money,create_time,update_time) VALUES (#{userId}, #{commodityCode},#{money},#{createTime},#{updateTime})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	int saveOrder(Order order);

}
