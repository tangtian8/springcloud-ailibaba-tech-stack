package top.tangtian.tech.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;


/**
 * @author tangtian
 * @date 2025-12-31 09:17
 */
@Mapper
@Repository
public interface AccountMapper {

	@Select("SELECT money FROM account WHERE user_id = #{userId}")
	Integer getBalance(@Param("userId") String userId);

	@Update("UPDATE account SET money = money - #{price},update_time = #{updateTime} WHERE user_id = #{userId} AND money >= ${price}")
	int reduceBalance(@Param("userId") String userId, @Param("price") Integer price,
					  @Param("updateTime") Timestamp updateTime);

}
